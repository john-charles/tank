package jcsokolow.tank.backend;


import jcsokolow.tank.service.KeyGenerationService;
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
import java.security.spec.InvalidKeySpecException;

public class AESEncryptionBackend implements Backend {

    private SecretKey key;
    private Backend backend;


    public AESEncryptionBackend(Backend backend, String key) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        KeyGenerationService keyGenerationService = new KeyGenerationService(backend);

        this.key = keyGenerationService.getKeyFromPassword(key);
        this.backend = backend;
    }

    public AESEncryptionBackend(Backend backend, SecretKey aesKey) {
        this.key = aesKey;
        this.backend = backend;
    }

    @Override
    public boolean hasStream(String id) throws IOException {
        return backend.hasStream(id);
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
