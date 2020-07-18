package message.transaction.payload;

import crypto.UInt256;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProposalVoteTest {

    @Test
    public void testValidProposalVote() throws IOException {
        final ProposalVote classUnderTest = ProposalVote.builder()
                .version(1)
                .proposalId(new UInt256("6ab03992e745aa06f4ae1325e1ce2c14689fa53f7b3e6ef614431b661ae88817"))
                .vote(true)
                .build();
        final ProposalVote classUnderTest2 = ProposalVote.builder()
                .version(1)
                .proposalId(new UInt256("6ab03992e745aa06f4ae1325e1ce2c14689fa53f7b3e6ef614431b661ae88817"))
                .vote(true)
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()), Hex.toHexString(classUnderTest2.getBytes()));
    }
}