package by.barysevich.password.manager.security;

import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.exception.ServiceException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AesEncoder {

    private static final String ENCRYPT_ALGORITHM = "AES";
    private static final String HASH_ALGORITHM = "SHA-1";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int KEY_LENGTH = 16;

    private SecretKeySpec secretKeySpec;

    public AesEncoder(String key) {
        try {
            var keyBytes = key.getBytes(StandardCharsets.UTF_8);
            var sha = MessageDigest.getInstance(HASH_ALGORITHM);
            var hashedKeyBytes = sha.digest(keyBytes);
            var formedKeyBytes = Arrays.copyOf(hashedKeyBytes, KEY_LENGTH);
            secretKeySpec = new SecretKeySpec(formedKeyBytes, ENCRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String data) {
        try {
            var cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_ERROR);
        }
    }

    public String decrypt(String data) {
        try {
            var cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_ERROR);
        }
    }

//    public static void main(String[] args) {
//        final AesEncoder aesEncoder1 = new AesEncoder("key");
//        final AesEncoder aesEncoder2 = new AesEncoder("keynew");
//
//        final String enc = aesEncoder1.encrypt("data");
//        System.out.println(aesEncoder1.decrypt(enc));
//        System.out.println(aesEncoder2.decrypt(enc));
//    }

}
