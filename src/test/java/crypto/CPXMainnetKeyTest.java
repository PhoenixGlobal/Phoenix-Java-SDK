package crypto;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class CPXMainnetKeyTest {

    private CryptoService cryptoService = new CryptoService();
    private static String keyStoreName = "signKeyStore";
    private static String keyName = "wallet1";
    private static String password = "mypassword";

    @Test
    public void printMainnetValuesTest() throws Exception {
        cryptoService.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));
        final KeyPair keyPair = cryptoService.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
        System.out.println("Private Key RAW: " + CPXMainnetKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("Private Key WIF: " + CPXMainnetKey.getPrivKeyWIF((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("Public Key: " + CPXMainnetKey.getPubKeyCompressed((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("Public Key Script: " + CPXMainnetKey.getPubKeyScript((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("RIPEMD160 Script Hash: " + CPXMainnetKey.getScriptHash((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("CPX Address: " + CPXMainnetKey.getPublicAddressCPX((ECPrivateKey) keyPair.getPrivate()));
        System.out.println("NEO Address: " + CPXMainnetKey.getPublicAddressNEO((ECPrivateKey) keyPair.getPrivate()));
    }

    @After
    public void cleanUp() throws IOException {
        if(Files.exists(Paths.get(keyStoreName + ".ubr"))){
            Files.delete(Paths.get(keyStoreName + ".ubr"));
        }
    }

}
