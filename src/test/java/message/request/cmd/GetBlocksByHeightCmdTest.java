package message.request.cmd;

import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetBlocksByHeightCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetBlocksByHeightCmd classUnderTest = new GetBlocksByHeightCmd(1);
        final String jsonString = writer.getStringFromResponseObject(new GetBlocksByHeightCmd(1));
        final GetBlocksByHeightCmd msg = (GetBlocksByHeightCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

}
