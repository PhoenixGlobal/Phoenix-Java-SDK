package message.request.cmd;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GetBlockCountResultTest {

    @Test
    public void RPCEndpointTest(){
        final GetBlockCountResult classUnderTest = new GetBlockCountResult(10L);
        assertEquals("", classUnderTest.getRpcPath());
    }

}
