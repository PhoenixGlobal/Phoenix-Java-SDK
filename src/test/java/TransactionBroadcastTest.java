import crypto.CPXKey;
import crypto.CryptoService;
import message.request.cmd.SendRawTransactionCmd;
import message.response.ExecResult;
import message.transaction.Transaction;
import message.transaction.TransactionType;
import message.util.RequestCallerService;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;

@Ignore
public class TransactionBroadcastTest {

    private final String privKeyRaw = "97b7c5875b8a5207e0cdf5b4050cb8215065ddcb36622bf733d55eca40250c39";
    private final String fromAddress = "APPebWC9pvw6SvkGHV38AqG559TvJ4tuc1V";
    private final String toHash = "8dc34d5f4a634db23def2e6ba35df5537a9a304f";
    private CryptoService cryptoService = new CryptoService();
    private RequestCallerService url = new RequestCallerService();

    @Test
    public void sendTransactionTest() throws Exception {

        final ECPrivateKey privateKey = cryptoService.getECPrivateKeyFromRawString(privKeyRaw);
        final Transaction tx = Transaction.builder()
                .txType(TransactionType.TRANSFER)
                .from(CPXKey.getPublicAddressCPX(privateKey))
                .toPubKeyHash(toHash)
                .amount(1)
                .nonce(1L)
                .data(Hex.encode(new byte [0]))
                .gasPrice(30000)
                .gasLimit(BigInteger.valueOf(300000L))
                .version(1)
                .executeTime(0L)
                .build();
        byte [] signatureBytes;
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(tx.getVersion());
                dataOut.writeByte(tx.getTxType());
                dataOut.write(tx.getFrom().getBytes());
                dataOut.write(tx.getToPubKeyHash().getBytes());
                dataOut.writeDouble(tx.getAmount());
                dataOut.writeLong(tx.getNonce());
                dataOut.write(tx.getData());
                dataOut.writeDouble(tx.getGasPrice());
                dataOut.write(tx.getGasLimit().toByteArray());
                dataOut.writeLong(tx.getExecuteTime());
                signatureBytes = out.toByteArray();
            }
        }
        tx.setSignature(Hex.encode(cryptoService.getSignature(privateKey, signatureBytes)));
        byte [] serializedTx;
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()) {
            try (ObjectOutputStream obj = new ObjectOutputStream(out)) {
                obj.writeObject(tx);
                serializedTx = out.toByteArray();
            }
        }
        SendRawTransactionCmd cmd = new SendRawTransactionCmd(Hex.toHexString(serializedTx));
        ExecResult response = url.postRequest("http://172.28.1.1:8080", cmd, ExecResult.class);
        System.out.println(response);
    }

}
