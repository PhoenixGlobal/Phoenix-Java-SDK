package crypto;

import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CPXKeyTest {

    private CryptoService cryptoService = new CryptoService();
    private static String keyStoreName = "signKeyStore";
    private static String keyName = "wallet1";
    private static String password = "mypassword";

    @Test
    public void printMainnetValuesTest() throws Exception {
        cryptoService.generateKeystore(keyStoreName, keyName, password);
        assertTrue(Files.exists(Paths.get(keyStoreName + ".ubr")));

        final KeyPair keyPair = cryptoService.loadKeyPairFromKeyStore(keyStoreName + ".ubr", keyName, password);
        final String privKeyRaw = CPXKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate());
        final String privKeyWif = CPXKey.getPrivKeyWIF((ECPrivateKey) keyPair.getPrivate());
        final String pubKey = CPXKey.getPubKeyCompressed((ECPrivateKey) keyPair.getPrivate());
        final String pubKeyScript = CPXKey.getPubKeyScript((ECPrivateKey) keyPair.getPrivate());
        final String scriptHash = CPXKey.getScriptHash((ECPrivateKey) keyPair.getPrivate());
        final String cpxAddress = CPXKey.getPublicAddressCPX((ECPrivateKey) keyPair.getPrivate());
        final String neoAddress = CPXKey.getPublicAddressNEO((ECPrivateKey) keyPair.getPrivate());
        final String mnemonic = MnemonicUtils.generateMnemonic(Hex.decode(privKeyRaw));
        final String privKeyRawFromMnemonic = Hex.toHexString(MnemonicUtils.generateEntropy(mnemonic));
        System.out.println("Private Key RAW: " + privKeyRaw);
        System.out.println("Private Key WIF: " + privKeyWif);
        System.out.println("Public Key: " + pubKey);
        System.out.println("Public Key Script: " + pubKeyScript);
        System.out.println("RIPEMD160 Script Hash: " + scriptHash);
        System.out.println("CPX Address: " + cpxAddress);
        System.out.println("NEO Address: " + neoAddress);
        System.out.println("Mnemonic: " + mnemonic);

        assertEquals(privKeyRaw, privKeyRawFromMnemonic);
        final String decodedScriptHash = CPXKey.getScriptHashFromCPXAddress(cpxAddress);
        assertEquals(scriptHash, decodedScriptHash);
        final String decodedRawFromWif = CPXKey.getRawFromWIF(privKeyWif);
        assertEquals(privKeyRaw, decodedRawFromWif);
        cryptoService.generateKeyStoreFromRawString(keyStoreName + "2", keyName, password, decodedRawFromWif);
        final KeyPair keyPair2 = cryptoService.loadKeyPairFromKeyStore(keyStoreName + "2" + ".ubr", keyName, password);
        assertEquals(keyPair.getPublic().hashCode(), keyPair2.getPublic().hashCode());
        assertEquals(keyPair.getPrivate().hashCode(), keyPair2.getPrivate().hashCode());
        assertEquals(keyPair.getPrivate().getFormat(), keyPair2.getPrivate().getFormat());
        assertEquals(((ECPrivateKey) keyPair.getPrivate()).getS().intValue(), ((ECPrivateKey) keyPair2.getPrivate()).getS().intValue());
        final String cpxAddress2 = CPXKey.getPublicAddressCPX((ECPrivateKey) keyPair2.getPrivate());
        final String privKeyRaw2 = CPXKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate());
        assertEquals(privKeyRaw, privKeyRaw2);
        assertEquals(cpxAddress2, cpxAddress2);
        final byte [] checksumBytes = new SecureRandom().generateSeed(64);
        final byte [] signature = cryptoService.getSignature(keyPair.getPrivate(), checksumBytes);
        final byte [] signature2 = cryptoService.getSignature(keyPair2.getPrivate(), checksumBytes);
        assertTrue(cryptoService.verifySignature(keyPair2.getPublic(), checksumBytes, signature));
        assertTrue(cryptoService.verifySignature(keyPair.getPublic(), checksumBytes, signature2));
    }

    @After
    public void cleanUp() throws IOException {
        if(Files.exists(Paths.get(keyStoreName + ".ubr"))){
            Files.delete(Paths.get(keyStoreName + ".ubr"));
        }
        if(Files.exists(Paths.get(keyStoreName + "2.ubr"))){
            Files.delete(Paths.get(keyStoreName + "2.ubr"));
        }
    }

}
