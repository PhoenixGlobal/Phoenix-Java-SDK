package message.request.cmd;

import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class GetAccountCmdTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final GetAccountCmd classUnderTest = new GetAccountCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final GetAccountCmd msg = writer.getObjectFromString(GetAccountCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"address\" : \"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";
        final GetAccountCmd classUnderTest = new GetAccountCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final GetAccountCmd msg = writer.getObjectFromString(GetAccountCmd.class, validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final GetAccountCmd classUnderTest = new GetAccountCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        assertEquals(RPCPathName.SHOW_ACCOUNT, classUnderTest.getRpcPath());
    }

}
