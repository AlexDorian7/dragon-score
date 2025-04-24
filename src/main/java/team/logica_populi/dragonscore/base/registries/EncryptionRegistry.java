package team.logica_populi.dragonscore.base.registries;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Encryption Registry handles all encryption and decryption for this program
 */
public class EncryptionRegistry {
    private static final Logger logger = Logger.getLogger(EncryptionRegistry.class.getName());

    private static final EncryptionRegistry instance = new EncryptionRegistry();

    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;

    /**
     * @see EncryptionRegistry#getInstance()
     */
    private EncryptionRegistry() {

    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    private long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    /**
     * Gets the instance of the encryption singleton registry.
     * @return The singleton instance
     */
    public static EncryptionRegistry getInstance() {
        return instance;
    }

    /**
     * Encrypt data.
     * @param data The data to encrypt
     * @return The encrypted data in base 64 format
     */
    public String encrypt(String data) {
        try {
            InputStream logo = getClass().getResourceAsStream("/assets/textures/logo.png");
            byte[] bk = new byte[256];
            logo.skip(1024);
            logo.read(bk, 0, 256);
            logo.close();
            String s = new String(bk);

            // Generate the IV
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Generate the salt
            byte[] nowBuffer = longToBytes(Instant.now().getEpochSecond());


            // Generate the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(s.toCharArray(), nowBuffer, ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");


            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedData = new byte[iv.length + cipherText.length + nowBuffer.length];
            System.arraycopy(nowBuffer, 0, encryptedData, 0, nowBuffer.length);
            System.arraycopy(iv, 0, encryptedData, nowBuffer.length, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, nowBuffer.length + iv.length, cipherText.length);

            logger.info(new String(iv));
            logger.info(new String(nowBuffer));


            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            // Handle the exception properly
            logger.log(Level.WARNING, "FAILED TO ENCRYPT DATA!", e);
            return null;
        }
    }

    /**
     * Decrypt data.
     * @param data The base 64 encoded data to decrypt
     * @return The plain text data
     */
    public String decrypt(String data) {
        try {

            InputStream logo = getClass().getResourceAsStream("/assets/textures/logo.png");
            byte[] bk = new byte[256];
            logo.skip(1024);
            logo.read(bk, 0, 256);
            logo.close();
            String s = new String(bk);

            byte[] encryptedData = Base64.getDecoder().decode(data);

            // grab IV and salt
            byte[] iv = new byte[16];
            byte[] salt = new byte[Long.BYTES];
            System.arraycopy(encryptedData, 0, salt, 0, salt.length);
            System.arraycopy(encryptedData, salt.length, iv, 0, iv.length);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);

            logger.info(new String(iv));
            logger.info(new String(salt));

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(s.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] cipherText = new byte[encryptedData.length - iv.length - salt.length];
            System.arraycopy(encryptedData, iv.length + salt.length, cipherText, 0, cipherText.length);

            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText, "UTF-8");
        } catch (Exception e) {
            // Handle the exception properly
            logger.log(Level.WARNING, "FAILED TO DECRYPT DATA!", e);
            return null;
        }
    }


    public void EncryptFile(File file) throws IOException {
        String data = Files.readString(file.toPath());
        data = getInstance().encrypt(data);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data.getBytes());
        outputStream.close();
    }
    public void DecryptFile(File file) throws IOException {
        String data = Files.readString(file.toPath());
        data = getInstance().decrypt(data);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(data.getBytes());
        outputStream.close();
    }
}
