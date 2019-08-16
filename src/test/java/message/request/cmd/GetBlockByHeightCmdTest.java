package message.request.cmd;

import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetBlockByHeightCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetBlockByHeightCmd classUnderTest = new GetBlockByHeightCmd(1);
        final String jsonString = writer.getStringFromRequestObject(new GetBlockByHeightCmd(1));
        final GetBlockByHeightCmd msg = (GetBlockByHeightCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetBlockByHeightCmd\", \"height\" : 1}";
        final GetBlockByHeightCmd classUnderTest = new GetBlockByHeightCmd(1);
        final GetBlockByHeightCmd msg = (GetBlockByHeightCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

}
