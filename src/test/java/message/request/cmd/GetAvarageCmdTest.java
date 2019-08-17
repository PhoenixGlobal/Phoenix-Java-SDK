package message.request.cmd;

import message.request.RPCPathName;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetAvarageCmdTest {

    @Test
    public void RPCEndpointTest(){
        final GetAvarageCmd classUnderTest = new GetAvarageCmd();
        assertEquals(RPCPathName.GET_AVG_GAS, classUnderTest.getRpcPath());
    }

}
