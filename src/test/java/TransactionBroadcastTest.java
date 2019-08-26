import crypto.CPXKey;
import crypto.CryptoService;
import message.request.cmd.GetAccountCmd;
import message.request.cmd.SendRawTransactionCmd;
import message.response.ExecResult;
import message.transaction.FixedNumber;
import message.transaction.Transaction;
import message.transaction.TransactionType;
import message.util.RequestCallerService;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;

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
        final String fromHash = CPXKey.getScriptHash(privateKey);
        final String toHash = CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM");
        final GetAccountCmd getAccountCmd = new GetAccountCmd(CPXKey.getPublicAddressCPX(privateKey));

        final ExecResult responseAcc = url.postRequest(rpc_url, getAccountCmd, ExecResult.class);
        final long nonce = (int) responseAcc.getResult().get("nextNonce");
        final Transaction tx = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .fromPubKeyHash(fromHash)
                .toPubKeyHash(toHash)
                .amount(new FixedNumber(1.2))
                .nonce(nonce)
                .data(new byte[0])
                .gasPrice(new FixedNumber(0.0000003))
                .gasLimit(BigInteger.valueOf(300000L))
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();
        SendRawTransactionCmd cmd = new SendRawTransactionCmd(tx.getBytes(cryptoService, privateKey));
        ExecResult response = url.postRequest(rpc_url, cmd, ExecResult.class);
        assertEquals(200, response.getStatus());
    }

}
