package message.request.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import message.request.RPCPathName;
import message.request.ARequestMessage;
import message.request.RequestMessageFields;

/**
 * This class represents a RPC message to request all voter addresses for one producer
 * @author Artem Eger
 * @since 16.08.2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
public class GetProducerAllVoterCmd extends ARequestMessage {

    /**
     * The target producer hash to request
     */
    @JsonProperty(RequestMessageFields.ADDRESS)
    private String address;

    /**
     * @return target RPC Endpoint for this message
     */
    @JsonIgnore
    @Override
    public String getRpcPath() {
        return RPCPathName.GET_PRODUCER_VOTE;
    }

}
