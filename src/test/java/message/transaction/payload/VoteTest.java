package message.transaction.payload;

import crypto.CPXKey;
import message.transaction.FixedNumber;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoteTest {

    @Test
    public void testValidVote() throws Exception {
        final Vote classUnderTest = Vote.builder()
                .amount(new FixedNumber(1.0, FixedNumber.CPX))
                .operationType(OperationType.REGISTER)
                .voterPubKeyHash(CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM"))
                .build();
        final Vote classUnderTest2 = Vote.builder()
                .amount(new FixedNumber(1.0, FixedNumber.CPX))
                .operationType(OperationType.REGISTER)
                .voterPubKeyHash(CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM"))
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()), Hex.toHexString(classUnderTest2.getBytes()));
    }
}