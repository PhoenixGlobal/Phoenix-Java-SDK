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

import java.security.interfaces.ECPrivateKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

        final ExecResult responseAcc = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, getAccountCmd));
        HashMap<String, Object> responseMap = (HashMap<String, Object>) responseAcc.result;
        final long nonce = 1;

        final Transaction tx = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(nonce)
                .data(new byte[0])
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();

        final Transaction tx2 = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(nonce + 1L)
                .data(new byte[0])
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();

        final Transaction tx3 = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2, FixedNumber.CPX))
                .nonce(nonce + 1L)
                .data(new byte[0])
                .gasPrice(new FixedNumber(3, FixedNumber.KGP))
                .gasLimit(new FixedNumber(300, FixedNumber.KP))
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();

        SendRawTransactionCmd cmd = new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx));
        ExecResult response = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, cmd));
        assertEquals(200, response.status);

        List<SendRawTransactionCmd> txList = Arrays.asList(
                new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx2)),
                new SendRawTransactionCmd(cryptoService.signBytes(privateKey, tx3)));

        SendRawTransactionBatchCmd batch = new SendRawTransactionBatchCmd(txList);
        ExecResult responseBatch = writer.getObjectFromString(ExecResult.class, url.postRequest(rpc_url, batch));
        assertEquals(200, responseBatch.status);
    }
}
