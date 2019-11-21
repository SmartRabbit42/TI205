package general;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class Helper {
	public static final String addressRegex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):\\d{4}\\d?$";
	
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	
	public static String generateToken() {
	    byte[] randomBytes = new byte[24];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}
	
	public static String generateChatId(String fullAddress) {
		return String.format("c%s%d", fullAddress, new Date().getTime());
	}
	
	public static String generateUserId(String fullAddress) {
		return String.format("u%s%d", fullAddress, new Date().getTime());
	}
}