package data;

public class Helper {
	public static boolean validateUsername(String username) {
		return !(username.equals(null) || username.equals(""));
	}
}
