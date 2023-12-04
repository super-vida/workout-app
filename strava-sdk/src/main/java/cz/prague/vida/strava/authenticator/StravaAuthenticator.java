package cz.prague.vida.strava.authenticator;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class StravaAuthenticator {

	private static final String TOKEN_URL = "https://www.strava.com/oauth/token";
	private final String secrete;
	private final String clientId;

	public String getRequestAccessUrl(String clientId) {
		return "http://www.strava.com/oauth/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=activity:read_all";
	}

	public StravaAuthenticator(String secrete, String clientId) {
		this.secrete = secrete;
		this.clientId = clientId;
	}

	public AuthResponse getToken(String code, Proxy proxy) throws URISyntaxException, IOException {
		if (secrete == null) {
			throw new IllegalStateException("Application secrete is not set");
		}
		URI uri = new URI(TOKEN_URL);
		URL url = uri.toURL();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
		try {
			String sb = "client_id=" + clientId
					+ "&client_secret=" + secrete
					+ "&code=" + code
					+ "&grant_type=authorization_code";

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(sb.getBytes(StandardCharsets.UTF_8));

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			Reader br = new InputStreamReader((conn.getInputStream()));
			Gson gson = new Gson();
			return gson.fromJson(br, AuthResponse.class);

		}
		finally {
			conn.disconnect();
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.6.180", 3128));
		Properties properties = new Properties();
		properties.load(new BufferedReader(new FileReader("application-secret.properties")));
		StravaAuthenticator authenticator = new StravaAuthenticator(properties.getProperty("clientSecret"), properties.getProperty("clientId"));
		System.out.println(authenticator.getRequestAccessUrl(properties.getProperty("clientId")));
		System.out.println(authenticator.getToken("8f01f409e0a14559cceda3e422b28ffd8341698c", proxy));

	}

}
