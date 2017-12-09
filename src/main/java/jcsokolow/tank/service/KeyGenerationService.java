package jcsokolow.tank.service;

import jcsokolow.tank.backend.Backend;
import org.apache.commons.io.IOUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

public class KeyGenerationService {

    Backend backend;
    Random random = new SecureRandom();

    private static final String SALT_KEY = "salt";


    public KeyGenerationService(Backend backend) {
        this.backend = backend;
    }

    public SecretKey getKeyFromPassword(String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        byte[] salt;
        char[] pass = password.toCharArray();

        if(backend.hasStream(SALT_KEY)) {
            salt = IOUtils.toByteArray(backend.getStream(SALT_KEY));
        } else {
            salt = generateAndSaveNewSalt();
        }

        KeySpec spec = new PBEKeySpec(pass, salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey tempKey = factory.generateSecret(spec);
        return new SecretKeySpec(tempKey.getEncoded(), "AES");


    }

    private byte[] generateAndSaveNewSalt() throws IOException {
        byte[] newSalt = new byte[16];

        random.nextBytes(newSalt);
        backend.putStream(SALT_KEY, new ByteArrayInputStream(newSalt));


        return newSalt;
    }

}
