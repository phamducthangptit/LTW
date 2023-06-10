package ptithcm.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class PasswordEncoder {
    public static String encodePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
<<<<<<< HEAD
=======
    public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(encodePassword("ggg"));
	}
>>>>>>> 01de4166bea5c321dbdebd61f7f3406f7962e498
}
