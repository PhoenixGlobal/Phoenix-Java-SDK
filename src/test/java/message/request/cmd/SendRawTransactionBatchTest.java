package message.request.cmd;

import message.request.RPCPathName;
import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class SendRawTransactionBatchTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validRPCMessageTest() throws IOException {
        final SendRawTransactionBatchCmd classUnderTest = new SendRawTransactionBatchCmd();
        final SendRawTransactionCmd tx1 = new SendRawTransactionCmd("abbbe");
        final SendRawTransactionCmd tx2 = new SendRawTransactionCmd("cddde");
        ArrayList<SendRawTransactionCmd> txBatch = new ArrayList<>();
        txBatch.add(tx1);
        txBatch.add(tx2);
        classUnderTest.setBatch(txBatch);
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final SendRawTransactionBatchCmd msg = writer.getObjectFromString(SendRawTransactionBatchCmd.class, jsonString);
        assertEquals(classUnderTest, msg);
    }

    @Test
    public void validRPCMessageFromStringTest() throws IOException {
        final String validJsonString = "{\"txs\":[{\"rawTx\":\"abbbe\"},{\"rawTx\":\"cddde\"}]}";
        final SendRawTransactionBatchCmd classUnderTest = new SendRawTransactionBatchCmd();
        final SendRawTransactionCmd tx1 = new SendRawTransactionCmd("abbbe");
        final SendRawTransactionCmd tx2 = new SendRawTransactionCmd("cddde");
        ArrayList<SendRawTransactionCmd> txBatch = new ArrayList<>();
        txBatch.add(tx1);
        txBatch.add(tx2);
        classUnderTest.setBatch(txBatch);
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        assertEquals(jsonString, validJsonString);
    }

    @Test
    public void RPCEndpointTest(){
        final SendRawTransactionBatchCmd classUnderTest = new SendRawTransactionBatchCmd();
        assertEquals(RPCPathName.SEND_RAW_TX_BATCH, classUnderTest.getRpcPath());
    }

}

