package message.request.cmd;

import message.request.RPCPathName;
import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetProducersCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetProducersCmd classUnderTest = new GetProducersCmd("list");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetProducersCmd msg = (GetProducersCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetProducersCmd\", \"listType\" : \"list\"}";
        final GetProducersCmd classUnderTest = new GetProducersCmd("list");
        final GetProducersCmd msg = (GetProducersCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetProducersCmd classUnderTest = new GetProducersCmd("list");
        assertEquals(RPCPathName.GET_PRODUCER_ALL, classUnderTest.getRpcPath());
    }

}
