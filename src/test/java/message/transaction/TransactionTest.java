package message.transaction;

import crypto.CPXKey;
import crypto.CryptoService;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.security.interfaces.ECPrivateKey;

import static org.junit.Assert.*;

public class TransactionTest {

    private final ECPrivateKey privateKey;
    private String toHash;

    public TransactionTest() throws Exception {
        final String privKeyRaw = "97b7c5875b8a5207e0cdf5b4050cb8215065ddcb36622bf733d55eca40250c39";
        final CryptoService cryptoService = new CryptoService();
        privateKey = cryptoService.getECPrivateKeyFromRawString(privKeyRaw);
        toHash = CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM");
    }

    @Test
    public void testValidTransaction() throws Exception {
        final Transaction classUnderTest = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(1L)
                .data(new byte[0])
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(0)
                .build();
        final Transaction classUnderTest2 = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(1L)
                .data(new byte[0])
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(0)
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()),
                Hex.toHexString(classUnderTest2.getBytes()));
    }

    @Test
    public void testValidTransactionWithValidPayload() throws Exception {
        final Transaction classUnderTest = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(1L)
                .data("testpayload".getBytes())
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(0)
                .build();
        final Transaction classUnderTest2 = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(1L)
                .data("testpayload".getBytes())
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(0)
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()),
                Hex.toHexString(classUnderTest2.getBytes()));
    }
}