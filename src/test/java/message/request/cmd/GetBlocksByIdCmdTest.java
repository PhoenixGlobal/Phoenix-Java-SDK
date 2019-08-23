package message.request.cmd;

import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetBlocksByIdCmdTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetBlockByIdCmd msg = writer.getObjectFromString(GetBlockByIdCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"hash\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetBlockByIdCmd msg = writer.getObjectFromString(GetBlockByIdCmd.class, validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetBlockByIdCmd classUnderTest = new GetBlockByIdCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.GET_BLOCK, classUnderTest.getRpcPath());
    }

}
