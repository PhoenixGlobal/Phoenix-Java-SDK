package crypto;

import message.transaction.ISerialize;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.ECPrivateKey;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;

public class CryptoServiceTest {

    private CryptoService classUnderTest = new CryptoService();
    private String rawKey = "4b92e7309619a293cd2f54fa57bd3d1754c88c77de407859c558299e6892e9a0";
    private String wif = "KykciLABWjWUShVgm3dHhAMSe4tEWi863Tk6muHosEnFnJ4THi5B";
    private String address = "APEsKZ2pYmxteuQ226VhGwb2TJKKSLdTGD4";
    private String mnemonic = "shell scatter method illegal area bid law genius found maze hope negative kit soldier " +
            "promote various power true reward success own decrease retire raven";
    private String mnemonicPriv = "c5780e30b880b42c1f7b075c3131b5c9f7af9d2b0f8ca95d2ae2ec29e0726dfd";
    private String password = "mypassword";

    @Test
    public void generateKeyStore() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeystore(password);
        assertNotNull(keyStore.getKey(CryptoService.KEY_NAME, password.toCharArray()));
    }

    @Test(expected = Exception.class)
    public void LoadKeyStoreWithWrongPass() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeystore(password);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            keyStore.store(out, password.toCharArray());
            classUnderTest.loadKeyPairFromKeyStore(out.toByteArray(), "wrongpass", CryptoService.KEY_NAME);
        }
    }

    @Test
    public void LoadKeyStoreWithRightPass() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeystore(password);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            keyStore.store(out, password.toCharArray());
            final KeyPair pair = classUnderTest.loadKeyPairFromKeyStore(out.toByteArray(), password, CryptoService.KEY_NAME);
            assertEquals(pair.getPrivate().hashCode(), keyStore.getKey(CryptoService.KEY_NAME, password.toCharArray()).hashCode());
        }
    }

    @Test
    public void createSignatureAndVerifyTest() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeystore(password);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            keyStore.store(out, password.toCharArray());
            KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(out.toByteArray(), password, CryptoService.KEY_NAME);
            byte[] data = new byte[20];
            new SecureRandom().nextBytes(data);
            byte[] signatureBytes = classUnderTest.getSignature(keyPair.getPrivate(), data);
            assertTrue(classUnderTest.verifySignature(keyPair.getPublic(), data, signatureBytes));
        }
    }

    @Test
    public void encodedToPublicKeyTest() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeystore(password);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            keyStore.store(out, password.toCharArray());
            final KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(out.toByteArray(), password, CryptoService.KEY_NAME);
            final PublicKey publicKey = keyPair.getPublic();
            byte[] encodedBytes = publicKey.getEncoded();
            final PublicKey publicKey2 = classUnderTest.encodedToPublicKey(encodedBytes);
            assertArrayEquals(publicKey.getEncoded(), publicKey2.getEncoded());
            assertEquals(publicKey.hashCode(), publicKey2.hashCode());
        }
    }

    @Test
    public void generateKeyStoreFromWifTest() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeyStoreFromWif(password, wif);
        final ECPrivateKey privateKey = (ECPrivateKey) keyStore.getKey(CryptoService.KEY_NAME, password.toCharArray());
        assertEquals(address, CPXKey.getPublicAddressCPX(privateKey));
    }

    @Test
    public void generateKeyStoreFromMnemonicTest() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeyStoreFromMnemonic(password, mnemonic);
        final ECPrivateKey privateKey = (ECPrivateKey) keyStore.getKey(CryptoService.KEY_NAME, password.toCharArray());
        final ECPrivateKey mnemonicPrivKey = classUnderTest.getECPrivateKeyFromRawString(mnemonicPriv);
        assertEquals(mnemonicPrivKey.hashCode(), privateKey.hashCode());
    }

    @Test
    public void signBytesTest() throws Exception {
        final KeyStore keyStore = classUnderTest.generateKeyStoreFromMnemonic(password, mnemonic);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            keyStore.store(out, password.toCharArray());
            KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(out.toByteArray(), password, CryptoService.KEY_NAME);
            final ISerialize payload = "Testpayload"::getBytes;
            final byte[] signatureBytes = classUnderTest.signBytes(keyPair.getPrivate(), payload);
            assertNotNull(signatureBytes);
        }
    }
}
