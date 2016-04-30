
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contribute {

    private static final String IP_DELIMETER = ".";
    private static final int HASH_LENGTH = 40;
    private static final String HASH_ALGO = "SHA-1";

    public static void main(String[] args) throws Exception {
        System.out.println(isBanned("example.minecraft.com"));
    }

    public static String hashServer(String serverAddress) {
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

    public static boolean isBanned(String serverAddress) throws IOException {
        File bannedList = new File("./blockedservers.txt");
        List<String> lines = Files.readAllLines(bannedList.toPath());
        for (String line : lines) {
            if (line.startsWith("/") || line.trim().isEmpty()) {
                continue;
            }

            String hash = line.substring(0, HASH_LENGTH);
            if (hash.equals(hashServer(serverAddress.trim().toLowerCase()))) {
                return true;
            }

            //\\ is used for escaping regEx
            List<String> components = new ArrayList<>(Arrays.asList(serverAddress.split("\\" + IP_DELIMETER)));
            boolean isIp = components.size() == 4;
            if (isIp) {
                for (String component : components) {
                    try {
                        int number = Integer.parseInt(component);
                        if (number < 0 || number > 255) {
                            //ip range is from 0.0.0.0 to 255.255.255.255
                            isIp = false;
                            break;
                        }
                    } catch (NumberFormatException numberFormatException) {
                        //not a ip
                        isIp = false;
                        break;
                    }
                }
            }

            while (components.size() > 1) {
                components.remove(isIp ? (components.size() - 1) : 0);

                String toTest;
                if (isIp) {
                    //example: 0.0.0.*
                    toTest = String.join(IP_DELIMETER, components) + ".*";
                } else {
                    //example: *.server.com
                    toTest = ("*." + String.join(IP_DELIMETER, components));
                }

                if (hash.equals(hashServer(toTest))) {
                    return true;
                }
            }
        }

        return false;
    }
}
