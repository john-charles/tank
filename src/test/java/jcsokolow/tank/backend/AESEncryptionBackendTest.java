package jcsokolow.tank.backend;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AESEncryptionBackendTest {


    Backend backend;
    SecretKey aesKey;

    static final String plainText = "Plain Text";

    class Bytes {
        byte[] bytes;

        Bytes(byte[] bytes) {
            this.bytes = bytes;
        }
    }

    private final Map<String, Bytes> bytes = new HashMap<>();


    @Before
    public void setUp() throws Exception {

        byte[] salt = new byte[]{'s', 'a', 'l', 't'};
        char[] pass = new char[]{'p', 'a', 's', 's'};

        KeySpec spec = new PBEKeySpec(pass, salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey tempKey = factory.generateSecret(spec);
        aesKey = new SecretKeySpec(tempKey.getEncoded(), "AES");

        backend = new Backend() {

            @Override
            public InputStream getStream(String id) throws IOException {
                return new ByteArrayInputStream(bytes.get(id).bytes);
            }

            @Override
            public void putStream(String id, InputStream input) throws IOException {
                bytes.put(id, new Bytes(IOUtils.toByteArray(input)));
            }

            @Override
            public boolean hasStream(String id) throws IOException {
                return false;
            }
        };
    }

    @Test
    public void encryptAndDecryptStreamTest() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {

        Backend cipherBackend = new AESEncryptionBackend(backend, aesKey);

        cipherBackend.putStream("my id", new ByteArrayInputStream(plainText.getBytes()));
        String result = IOUtils.toString(cipherBackend.getStream("my id"));

        assertEquals(plainText, result);
        assertNotNull(bytes.get("my id"));
        assertNotNull(bytes.get("my id.iv"));


    }
}