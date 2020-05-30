package message.transaction.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import message.transaction.ISerialize;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Proposal implements ISerialize {

    // AP1xWDozWvuVah1W86DKtcWzdw1LqRxiSfr
    public static final String SCRIPT_HASH = "0000000000000000000000000000000000000103";

    private int version;

    private byte type;

    private long activeTime;

    private byte [] value;

    @Override
    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(this.version);
                dataOut.write(type);
                dataOut.writeLong(activeTime);
                dataOut.write(this.value.length);
                dataOut.write(this.value);
                return out.toByteArray();
            }
        }
    }

}
