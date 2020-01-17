package crypto;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CPXKeyTest {

    private CryptoService cryptoService = new CryptoService();
    private static String password = "mypassword";

    @Test
    public void printMainnetValuesTest() throws Exception {
        final KeyStore keyStore = cryptoService.generateKeystore(password);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            keyStore.store(out, password.toCharArray());
            final KeyPair keyPair = cryptoService.loadKeyPairFromKeyStore(out.toByteArray(), password, CryptoService.KEY_NAME);
            final String privKeyRaw = CPXKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate());
            final String privKeyWif = CPXKey.getPrivKeyWIF((ECPrivateKey) keyPair.getPrivate());
            final String pubKey = CPXKey.getPubKeyCompressed((ECPrivateKey) keyPair.getPrivate());
            final String pubKeyScript = CPXKey.getPubKeyScript((ECPrivateKey) keyPair.getPrivate());
            final String scriptHash = CPXKey.getScriptHash((ECPrivateKey) keyPair.getPrivate());
            final String cpxAddress = CPXKey.getPublicAddressCPX((ECPrivateKey) keyPair.getPrivate());
            final String neoAddress = CPXKey.getPublicAddressNEO((ECPrivateKey) keyPair.getPrivate());
            final String mnemonic = MnemonicUtils.generateMnemonic(Hex.decode(privKeyRaw));
            final String privKeyRawFromMnemonic = Hex.toHexString(MnemonicUtils.generateEntropy(mnemonic));

            assertEquals(privKeyRaw, privKeyRawFromMnemonic);
            final String decodedScriptHash = CPXKey.getScriptHashFromCPXAddress(cpxAddress);
            assertEquals(scriptHash, decodedScriptHash);
            final String decodedRawFromWif = CPXKey.getRawFromWIF(privKeyWif);
            assertEquals(privKeyRaw, decodedRawFromWif);
            final KeyStore keyStore2 = cryptoService.generateKeyStoreFromRawString(password, CryptoService.KEY_NAME, decodedRawFromWif);
            try (ByteArrayOutputStream out2 = new ByteArrayOutputStream()){
                keyStore2.store(out2, password.toCharArray());
                final KeyPair keyPair2 = cryptoService.loadKeyPairFromKeyStore(out2.toByteArray(), password, CryptoService.KEY_NAME);
                assertEquals(keyPair.getPublic().hashCode(), keyPair2.getPublic().hashCode());
                assertEquals(keyPair.getPrivate().hashCode(), keyPair2.getPrivate().hashCode());
                assertEquals(keyPair.getPrivate().getFormat(), keyPair2.getPrivate().getFormat());
                assertEquals(((ECPrivateKey) keyPair.getPrivate()).getS().intValue(), ((ECPrivateKey) keyPair2.getPrivate()).getS().intValue());
                final String cpxAddress2 = CPXKey.getPublicAddressCPX((ECPrivateKey) keyPair2.getPrivate());
                final String privKeyRaw2 = CPXKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate());
                assertEquals(privKeyRaw, privKeyRaw2);
                assertEquals(cpxAddress2, cpxAddress2);
                final byte[] checksumBytes = new SecureRandom().generateSeed(64);
                final byte[] signature = cryptoService.getSignature(keyPair.getPrivate(), checksumBytes);
                final byte[] signature2 = cryptoService.getSignature(keyPair2.getPrivate(), checksumBytes);
                assertTrue(cryptoService.verifySignature(keyPair2.getPublic(), checksumBytes, signature));
                assertTrue(cryptoService.verifySignature(keyPair.getPublic(), checksumBytes, signature2));
            }
        }
    }

}
