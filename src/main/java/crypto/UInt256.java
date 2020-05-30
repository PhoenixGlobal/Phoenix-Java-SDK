package crypto;

import message.transaction.ISerialize;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UInt256 implements ISerialize {

    public final static int SIZE = 32;
    public final static byte [] ZERO = new byte[32];
    private byte [] value;

    public void fromBytes(final byte [] value){
        this.value = value.length == UInt256.SIZE ? value : UInt256.ZERO;
    }

    public void fromString(final String value){
        fromBytes(Hex.decode(value));
    }

    public byte [] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.write(value);
                return out.toByteArray();
            }
        }
    }

}
