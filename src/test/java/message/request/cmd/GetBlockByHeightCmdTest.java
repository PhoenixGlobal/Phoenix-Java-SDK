package message.request.cmd;

import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetBlockByHeightCmdTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetBlockByHeightCmd classUnderTest = new GetBlockByHeightCmd(1);
        final String jsonString = writer.getStringFromRequestObject(new GetBlockByHeightCmd(1));
        final GetBlockByHeightCmd msg = writer.getObjectFromString(GetBlockByHeightCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetBlockByHeightCmd\", \"height\" : 1}";
        final GetBlockByHeightCmd classUnderTest = new GetBlockByHeightCmd(1);
        final GetBlockByHeightCmd msg = writer.getObjectFromString(GetBlockByHeightCmd.class, validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetBlockByHeightCmd classUnderTest = new GetBlockByHeightCmd(1);
        assertEquals(RPCPathName.GET_BLOCK, classUnderTest.getRpcPath());
    }

}
