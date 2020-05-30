package message.transaction.payload;

import crypto.UInt256;
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
public class ProposalVote implements ISerialize {

    // AP1xWDozWvuVah1W86DKtcWzdw1LqdV35rk
    public static final String SCRIPT_HASH = "0000000000000000000000000000000000000104";

    private int version;

    private UInt256 proposalId;

    private boolean vote;

    @Override
    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(this.version);
                dataOut.write(proposalId.getBytes());
                dataOut.writeBoolean(vote);
                return out.toByteArray();
            }
        }
    }
}
