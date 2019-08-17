package message.request.cmd;

import message.request.RPCPathName;
import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetProducerCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetProducerCmd classUnderTest = new GetProducerCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetProducerCmd msg = (GetProducerCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetProducerCmd\", \"address\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetProducerCmd classUnderTest = new GetProducerCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetProducerCmd msg = (GetProducerCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetProducerCmd classUnderTest = new GetProducerCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.GET_PRODUCER, classUnderTest.getRpcPath());
    }

}
