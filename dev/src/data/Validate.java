package data;

public class Validate {
	public static boolean username(String username) {
		return !(username.equals(null) || username.equals(""));
	}
}
