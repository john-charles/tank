package jcsokolow.tank.backend;


import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;

public class AESEncryptionBackend implements Backend {

    SecretKey key;
    Backend backend;

    private static final String CIPHER_SPEC = "AES/CBC/PKCS7Padding";


    public AESEncryptionBackend(Backend backend, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = key;
        this.backend = backend;
    }

    public InputStream getStream(String id) throws IOException {

        byte[] iv = IOUtils.toByteArray(backend.getStream(id + ".iv"));

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            return new CipherInputStream(backend.getStream(id), cipher);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IOException("Encryption Error", e);
        }
    }

    public void putStream(String id, InputStream input) throws IOException {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

            backend.putStream(id + ".iv", new ByteArrayInputStream(iv));
            backend.putStream(id, new CipherInputStream(input, cipher));

        } catch (Exception e) {
            throw new IOException("Encryption Error", e);
        }


    }
}
