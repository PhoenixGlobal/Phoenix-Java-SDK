package message.transaction.payload;

import lombok.*;
import message.transaction.FixedNumber;
import message.transaction.ISerialize;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@AllArgsConstructor
@Builder
public class Proposal implements ISerialize {

    // AP1xWDozWvuVah1W86DKtcWzdw1LqRxiSfr
    public static final String SCRIPT_HASH = "0000000000000000000000000000000000000103";

    @NonNull
    public final int version;

    @NonNull
    public final ProposalType type;

    @NonNull
    public final long activeTime;

    @NonNull
    public final FixedNumber value;

    @Override
    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(this.version);
                dataOut.write(type.value);
                dataOut.writeLong(activeTime);
                dataOut.write(this.value.getBytes().length);
                dataOut.write(this.value.getBytes());
                return out.toByteArray();
            }
        }
    }
}
