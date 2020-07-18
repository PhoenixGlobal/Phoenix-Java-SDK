package crypto;

import message.transaction.ISerialize;
import org.bouncycastle.util.encoders.Hex;

public class UInt256 implements ISerialize {

    public final static int SIZE = 32;
    public final static byte [] ZERO = new byte[32];
    private byte [] value;

    public UInt256(final String value){
        fromString(value);
    }

    public UInt256(final byte [] value){
        fromBytes(value);
    }

    private void fromBytes(final byte [] value){
        this.value = value.length == UInt256.SIZE ? value : UInt256.ZERO;
    }

    private void fromString(final String value){
        fromBytes(Hex.decode(value));
    }

    public byte [] getBytes() {
        return value;
    }

}
