package message.request.cmd;

import message.request.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetAllProposalVotesCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetAllProposalVotesCmd classUnderTest = new GetAllProposalVotesCmd();
        assertEquals(RPCPathName.GET_PROPOSAL_VOTE_ALL, classUnderTest.getRpcPath());
    }

}
