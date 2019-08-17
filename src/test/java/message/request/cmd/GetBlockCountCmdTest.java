package message.request.cmd;

import message.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetBlockCountCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetBlockCountCmd classUnderTest = new GetBlockCountCmd();
        assertEquals(RPCPathName.GET_BLOCK_HEIGHT, classUnderTest.getRpcPath());
    }

}
