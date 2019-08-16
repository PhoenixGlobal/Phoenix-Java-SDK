package message.request.cmd;

import message.RPCPathName;
import message.request.RequestMessageWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetProposalCmdTest {

    private RequestMessageWriter writer = new RequestMessageWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetProposalCmd classUnderTest = new GetProposalCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetProposalCmd msg = (GetProposalCmd) writer.getRequestObjectFromString(jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"name\": \"GetProposalCmd\", \"id\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetProposalCmd classUnderTest = new GetProposalCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetProposalCmd msg = (GetProposalCmd) writer.getRequestObjectFromString(validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetProposalCmd classUnderTest = new GetProposalCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.GET_PROPOSAL, classUnderTest.getRpcPath());
    }

}
