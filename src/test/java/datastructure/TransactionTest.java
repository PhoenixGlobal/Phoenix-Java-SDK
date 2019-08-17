package datastructure;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;

import static junit.framework.TestCase.assertEquals;

public class TransactionTest {

    @Test
    public void createValidTxTest() throws IOException {
        final Transaction tx = Transaction.builder()
                .txType(TransactionType.MINER)
                .from("APPebWC9pvw6SvkGHV38AqG559TvJ4tuc1V")
                .toPubKeyHash("2103d11d8395700babdf022252081180e8909fe311ca01f46cb585566249172a9743ac")
                .amount(0.1)
                .nonce(0L)
                .data("test".getBytes())
                .gasPrice(3000.5)
                .gasLimit(BigInteger.valueOf(1000000L))
                .signature(new byte[0])
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();
        final String jsonString = tx.getAsJsonString();
        Transaction tx2 = new Transaction();
        tx2 = (Transaction) tx2.getObjectFromJsonString(jsonString);
        assertEquals(tx, tx2);
    }

}
