package message.request.cmd;

import message.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetBlocksCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetBlocksCmd classUnderTest = new GetBlocksCmd();
        assertEquals(RPCPathName.GET_BLOCK_MULTIPLE, classUnderTest.getRpcPath());
    }

}
