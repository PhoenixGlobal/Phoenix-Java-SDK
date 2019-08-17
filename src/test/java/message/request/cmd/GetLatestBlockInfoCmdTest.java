package message.request.cmd;

import message.request.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetLatestBlockInfoCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetLatestBlockInfoCmd classUnderTest = new GetLatestBlockInfoCmd();
        assertEquals(RPCPathName.GET_BLOCK_INFO, classUnderTest.getRpcPath());
    }

}
