package crypto;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class CryptoServiceTest {

    private CryptoService classUnderTest = new CryptoService();
    private static String keyStoreName = "signKeyStore";
    private static String keyName = "wallet1";
    private static String password = "mypassword";

    @Test
    public void generateSaveLoadKeyStore() throws Exception {
        classUnderTest.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));
        classUnderTest.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
    }

    @Test(expected = Exception.class)
    public void LoadKeyStoreWithWrongPass() throws Exception {
        classUnderTest.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));
        classUnderTest.loadKeyPairFromKeyStore("keystore.ubr", "wallet2","this");
    }

    @Test
    public void createSignatureAndVerifyTest() throws Exception {
        classUnderTest.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName  + ".ubr")));
        KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
        byte[] data = new byte[20];
        new SecureRandom().nextBytes(data);
        byte[] signatureBytes = classUnderTest.getSignature(keyPair.getPrivate(), data);
        assertTrue(classUnderTest.verifySignature(keyPair.getPublic(), data, signatureBytes));
    }

    @Test
    public void encodedToPublicKeyTest() throws Exception {
        classUnderTest.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));
        final KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
        final PublicKey publicKey = keyPair.getPublic();
        byte [] encodedBytes = publicKey.getEncoded();
        final PublicKey publicKey2 = classUnderTest.encodedToPublicKey(encodedBytes);
        assertArrayEquals(publicKey.getEncoded(), publicKey2.getEncoded());
        assertEquals(publicKey.hashCode(), publicKey2.hashCode());
    }

    @After
    public void cleanUp() throws IOException {
        if(Files.exists(Paths.get(keyStoreName + ".ubr"))){
            Files.delete(Paths.get(keyStoreName + ".ubr"));
        }
    }

}
