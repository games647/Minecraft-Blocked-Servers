
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Contribute {

    private static final String HASH_ALGO = "SHA-1";

    public static void main(String[] args) {
        System.out.println(hash("pvp.muxcraft.eu"));
    }

    public static String hash(String serverAddress) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGO);
            byte[] inputBytes = serverAddress.getBytes(StandardCharsets.UTF_8);
            byte[] hashedBytes = digest.digest(inputBytes);
            return String.format("%0" + (hashedBytes.length << 1) + "x", new BigInteger(1, hashedBytes));
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            //ignore - sha1 is available in all vm
            return "";
        }
    }
}
