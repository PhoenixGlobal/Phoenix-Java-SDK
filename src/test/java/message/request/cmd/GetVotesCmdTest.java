package message.request.cmd;

import message.request.RPCPathName;
import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetVotesCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetVotesCmd classUnderTest = new GetVotesCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetVotesCmd msg = (GetVotesCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetVotesCmd\", \"address\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetVotesCmd classUnderTest = new GetVotesCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetVotesCmd msg = (GetVotesCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetVotesCmd classUnderTest = new GetVotesCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.GET_VOTE, classUnderTest.getRpcPath());
    }

}
