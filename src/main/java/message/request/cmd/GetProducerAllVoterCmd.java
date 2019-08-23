package message.request.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import message.request.RPCPathName;
import message.request.IRPCMessage;
import message.request.RequestMessageFields;

/**
 * This class represents a RPC message to request all voter addresses for one producer
 * @author Artem Eger
 * @since 16.08.2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetProducerAllVoterCmd implements IRPCMessage {

    /**
     * The target producer hash to request
     */
    @JsonProperty(RequestMessageFields.ADDRESS)
    @NonNull private String address;

    /**
     * @return target RPC Endpoint for this message
     */
    @JsonIgnore
    @Override
    public String getRpcPath() {
        return RPCPathName.GET_PRODUCER_VOTE;
    }

}
