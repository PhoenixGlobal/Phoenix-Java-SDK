package message.request.cmd;

import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;
import java.security.SecureRandom;

import static junit.framework.TestCase.assertEquals;

public class SendRawTransactionCmdTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final SendRawTransactionCmd classUnderTest = new SendRawTransactionCmd(new SecureRandom().generateSeed(64));
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final SendRawTransactionCmd msg = writer.getObjectFromString(SendRawTransactionCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"rawTx\" : \"dGVzdA==\"}";
        final SendRawTransactionCmd classUnderTest = new SendRawTransactionCmd("test".getBytes());
        final SendRawTransactionCmd msg = writer.getObjectFromString(SendRawTransactionCmd.class, validJsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void RPCEndpointTest(){
        final SendRawTransactionCmd classUnderTest = new SendRawTransactionCmd("test".getBytes());
        assertEquals(RPCPathName.SEND_RAW_TX, classUnderTest.getRpcPath());
    }

}
