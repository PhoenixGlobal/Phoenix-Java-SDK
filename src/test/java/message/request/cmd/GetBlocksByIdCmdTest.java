package message.request.cmd;

import message.RPCPathName;
import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetBlocksByIdCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetBlockByIdCmd msg = (GetBlockByIdCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetBlockByIdCmd\", \"hash\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetBlockByIdCmd msg = (GetBlockByIdCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.GET_BLOCK, classUnderTest.getRpcPath());
    }

}
