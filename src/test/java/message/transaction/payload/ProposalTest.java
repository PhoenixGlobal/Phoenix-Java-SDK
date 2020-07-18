package message.transaction.payload;

import message.transaction.FixedNumber;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProposalTest {

    @Test
    public void testValidProposal() throws IOException {
        final Proposal classUnderTest = Proposal.builder()
                .type(ProposalType.BLOCK_AWARD)
                .activeTime(1L)
                .value(new FixedNumber(1.0, FixedNumber.CPX))
                .version(1)
                .build();
        final Proposal classUnderTest2 = Proposal.builder()
                .type(ProposalType.BLOCK_AWARD)
                .activeTime(1L)
                .value(new FixedNumber(1.0, FixedNumber.CPX))
                .version(1)
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()), Hex.toHexString(classUnderTest2.getBytes()));
    }
}