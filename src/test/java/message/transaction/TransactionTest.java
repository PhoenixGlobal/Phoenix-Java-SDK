package message.transaction;

import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;

import static junit.framework.TestCase.assertEquals;

public class TransactionTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

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
        final String jsonString = writer.getStringFromRequestObject(tx);
        Transaction tx2 = writer.getObjectFromString(Transaction.class, jsonString);
        assertEquals(tx, tx2);
    }

}
