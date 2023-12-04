package cz.prague.vida.strava.authenticator;

import com.google.gson.annotations.SerializedName;
import cz.prague.vida.strava.entities.Athlete;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthResponse {
	@SerializedName("access_token")
	String accessToken;
	Athlete athlete;
}