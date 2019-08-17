package message.request.cmd;

import message.request.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetAllProposalCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetAllProposalCmd classUnderTest = new GetAllProposalCmd();
        assertEquals(RPCPathName.GET_PROPOSAL_ALL, classUnderTest.getRpcPath());
    }

}
