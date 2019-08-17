/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 - 2019
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package message.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.NoArgsConstructor;
import message.request.cmd.*;

/**
 * This class mainly provides abstraction for the {@link RequestMessageWriter}
 * @author Artem Eger
 * @since 16.08.2019
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
@JsonSubTypes({@JsonSubTypes.Type(value = GetBlockByHeightCmd.class),
        @JsonSubTypes.Type(value = GetBlockByIdCmd.class),
        @JsonSubTypes.Type(value = GetContractByIdCmd.class),
        @JsonSubTypes.Type(value = GetProducerCmd.class),
        @JsonSubTypes.Type(value = GetProducerAllVoterCmd.class),
        @JsonSubTypes.Type(value = GetProposalCmd.class),
        @JsonSubTypes.Type(value = GetProducersCmd.class),
        @JsonSubTypes.Type(value = GetVotesCmd.class),
        @JsonSubTypes.Type(value = GetAccountCmd.class),
        @JsonSubTypes.Type(value = GetAllProposalCmd.class),
        @JsonSubTypes.Type(value = GetAllProposalVotesCmd.class),
        @JsonSubTypes.Type(value = GetBlockCountCmd.class),
        @JsonSubTypes.Type(value = GetBlockCountResult.class),
        @JsonSubTypes.Type(value = GetBlocksCmd.class),
        @JsonSubTypes.Type(value = GetLatestBlockInfoCmd.class),
        @JsonSubTypes.Type(value = SendRawTransactionCmd.class)})
@NoArgsConstructor
public abstract class ARequestMessage {

    @JsonIgnore
    public abstract String getRpcPath();

}
