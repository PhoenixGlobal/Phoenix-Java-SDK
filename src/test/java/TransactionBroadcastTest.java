import crypto.CPXKey;
import crypto.CryptoService;
import message.request.cmd.GetAccountCmd;
import message.request.cmd.SendRawTransactionBatchCmd;
import message.request.cmd.SendRawTransactionCmd;
import message.response.ExecResult;
import message.transaction.*;
import message.util.GenericJacksonWriter;
import message.util.RequestCallerService;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

/**
 * This test is only useful if you have valid parameters for a valid rpc endpoint.
 * Its not executed in the build lifecycle
 *
 * This version will work with this Docker setup:
 * https://github.com/APEX-Network/APEX-Docker-Test
 */
@Ignore
public class TransactionBroadcastTest {

    @Test
    public void sendTransactionTest() throws Exception {

        final String rpc_url = "http://172.28.1.1:8080";
        final String privKeyRaw = "97b7c5875b8a5207e0cdf5b4050cb8215065ddcb36622bf733d55eca40250c39";
        final CryptoService cryptoService = new CryptoService();
        final RequestCallerService url = new RequestCallerService();
        final ECPrivateKey privateKey = cryptoService.getECPrivateKeyFromRawString(privKeyRaw);
        final String toHash = CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM");
        final GetAccountCmd getAccountCmd = new GetAccountCmd(CPXKey.getPublicAddressCPX(privateKey));
        final GenericJacksonWriter writer = new GenericJacksonWriter();
        final IProduceTransaction txFactory = new TransactionFactory();

        final ExecResult responseAcc = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, getAccountCmd));
        HashMap<String, Object> responseMap = (HashMap<String, Object>) responseAcc.getResult();
        final long nonce = (int) responseMap.get("nextNonce");

        final Transaction tx = txFactory.create(TxObj.TRANSFER, privateKey, () -> new byte[0],
                toHash, nonce, 1.2, 0.0000003, 300000L);

        SendRawTransactionCmd cmd = new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx));
        ExecResult response = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, cmd));
        assertEquals(200, response.getStatus());

        SendRawTransactionBatchCmd batch = new SendRawTransactionBatchCmd();
        ArrayList<SendRawTransactionCmd> txList = new ArrayList<>();
        tx.setNonce(tx.getNonce() + 1L);
        cmd = new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx));
        txList.add(cmd);
        tx.setNonce(tx.getNonce() + 1L);
        SendRawTransactionCmd cmd2 = new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx));
        txList.add(cmd2);
        batch.setBatch(txList);
        ExecResult responseBatch = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, batch));
        assertEquals(200, responseBatch.getStatus());

    }

}
