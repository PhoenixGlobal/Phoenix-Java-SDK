package crypto;

import org.bouncycastle.asn1.eac.ECDSAPublicKey;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

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

    @Test
    public void printToApexMainnetKeys() throws Exception {
        classUnderTest.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));
        final KeyPair keyPair = classUnderTest.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
        ECPrivateKey priv = (ECPrivateKey) keyPair.getPrivate();
        priv.getS();
        X9ECParameters params = SECNamedCurves.getByName("secp256r1");
        ECPoint point = params.getG().multiply(priv.getS());
        String privKeyRaw = new String(Hex.encode(priv.getS().toByteArray()));
        String pubKeyComp = new String(Hex.encode(point.getEncoded(true)));
        String pubKeyScript = "21" + pubKeyComp + "ac";
        System.out.println("priv raw: " + privKeyRaw);
        System.out.println("pub compressed: " + pubKeyComp);
        System.out.println("pub key script: " + pubKeyScript);
        System.out.println(classUnderTest.getRIPEMD160(pubKeyScript).length);
    }

    @After
    public void cleanUp() throws IOException {
        if(Files.exists(Paths.get(keyStoreName + ".ubr"))){
            Files.delete(Paths.get(keyStoreName + ".ubr"));
        }
    }

}
