package cz.prague.vida.strava.authenticator;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RefreshTokenResponse {
	@SerializedName("token_type")
	private String tokenType;
	@SerializedName("access_token")
	private String accessToken;
	@SerializedName("expires_at")
	private int expiresAt;
	@SerializedName("expires_in")
	private int expiresIn;
	@SerializedName("refresh_token")
	private String refreshToken;

}
