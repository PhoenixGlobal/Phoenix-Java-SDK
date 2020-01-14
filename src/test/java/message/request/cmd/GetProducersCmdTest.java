package message.request.cmd;

import message.request.ProducerListType;
import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetProducersCmdTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetProducersCmd classUnderTest = new GetProducersCmd(ProducerListType.ALL);
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetProducersCmd msg = writer.getObjectFromString(GetProducersCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetProducersCmd\", \"listType\" : \"all\"}";
        final GetProducersCmd classUnderTest = new GetProducersCmd(ProducerListType.ALL);
        final GetProducersCmd msg = writer.getObjectFromString(GetProducersCmd.class, validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetProducersCmd classUnderTest = new GetProducersCmd(ProducerListType.ALL);
        assertEquals(RPCPathName.GET_PRODUCER_ALL, classUnderTest.getRpcPath());
    }

}
