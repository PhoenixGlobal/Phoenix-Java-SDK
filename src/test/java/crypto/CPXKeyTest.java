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
import static org.junit.Assert.assertNotNull;

public class CPXKeyTest {

    private final CryptoService cryptoService = new CryptoService();
    private static final String PASSWORD = "mypassword";
    private static final String CPX_ADDRESS = "APPebWC9pvw6SvkGHV38AqG559TvJ4tuc1V";
    private static final String NEO_ADDRESS = "AdTxvt1wLBG23hy9xUTcNdLXh4WfEyoHgL";
    private static final String KEY_RAW = "97b7c5875b8a5207e0cdf5b4050cb8215065ddcb36622bf733d55eca40250c39";
    private static final String KEY_WIF = "L2JdXVfeHYQdvhx7ivqpfMePXJYw5WUeSCfBpjvqUbkQ96fSUCvs";
    private static final String PUBKEY_COMP = "03d11d8395700babdf022252081180e8909fe311ca01f46cb585566249172a9743";
    private static final String PUBKEY_SCRIPT = "2103d11d8395700babdf022252081180e8909fe311ca01f46cb585566249172a9743ac";
    private static final String SCRIPT_HASH = "edefb082ed2e9487041abe8a28a5363c61d66bd5";

    @Test
    public void printMainnetValuesTest() throws Exception {
        final KeyStore keyStore = cryptoService.generateKeystore(PASSWORD);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            keyStore.store(out, PASSWORD.toCharArray());
            final KeyPair keyPair = cryptoService.loadKeyPairFromKeyStore(out.toByteArray(), PASSWORD, CryptoService.KEY_NAME);
            final String privKeyRaw = CPXKey.getPrivKeyRaw((ECPrivateKey) keyPair.getPrivate());
            final String privKeyWif = CPXKey.getPrivKeyWIF((ECPrivateKey) keyPair.getPrivate());
            final String scriptHash = CPXKey.getScriptHash((ECPrivateKey) keyPair.getPrivate());
            final String cpxAddress = CPXKey.getPublicAddressCPX((ECPrivateKey) keyPair.getPrivate());
            final String neoAddress = CPXKey.getPublicAddressNEO((ECPrivateKey) keyPair.getPrivate());
            final String mnemonic = MnemonicUtils.generateMnemonic(Hex.decode(privKeyRaw));
            final String mnemonicFromKey = CPXKey.generateMnemonic();
            final String privKeyRawFromMnemonic = Hex.toHexString(MnemonicUtils.generateEntropy(mnemonic));
            assertNotNull(neoAddress);
            assertNotNull(mnemonicFromKey);
            assertEquals(privKeyRaw, privKeyRawFromMnemonic);
            final String decodedScriptHash = CPXKey.getScriptHashFromCPXAddress(cpxAddress);
            assertEquals(scriptHash, decodedScriptHash);
            final String decodedRawFromWif = CPXKey.getRawFromWIF(privKeyWif);
            assertEquals(privKeyRaw, decodedRawFromWif);
            final KeyStore keyStore2 = cryptoService.generateKeyStoreFromRawString(PASSWORD, CryptoService.KEY_NAME, decodedRawFromWif);
            try (ByteArrayOutputStream out2 = new ByteArrayOutputStream()){
                keyStore2.store(out2, PASSWORD.toCharArray());
                final KeyPair keyPair2 = cryptoService.loadKeyPairFromKeyStore(out2.toByteArray(), PASSWORD, CryptoService.KEY_NAME);
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

    @Test
    public void testFixedValues() throws Exception {
        final ECPrivateKey privateKeyTest = cryptoService.getECPrivateKeyFromRawString(KEY_RAW);
        final String cpxKeyRawTest = CPXKey.getPrivKeyRaw(privateKeyTest);
        final String cpxKeyWifTest = CPXKey.getPrivKeyWIF(privateKeyTest);
        final String cpxKeyCompressedTest = CPXKey.getPubKeyCompressed(privateKeyTest);
        final String cpxKeyScriptHashTest = CPXKey.getPubKeyScript(privateKeyTest);
        final String cpxKeyCpxAddressTest = CPXKey.getPublicAddressCPX(privateKeyTest);
        final String cpxKeyNeoAddressTest = CPXKey.getPublicAddressNEO(privateKeyTest);
        assertEquals(KEY_RAW, cpxKeyRawTest);
        assertEquals(KEY_WIF, cpxKeyWifTest);
        assertEquals(PUBKEY_COMP, cpxKeyCompressedTest);
        assertEquals(PUBKEY_SCRIPT, cpxKeyScriptHashTest);
        assertEquals(CPX_ADDRESS, cpxKeyCpxAddressTest);
        assertEquals(NEO_ADDRESS, cpxKeyNeoAddressTest);
    }

}
