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
package message;

/**
 * This class provides valid RPC Path Endpoints 
 * @author Artem Eger
 * @since 16.08.2019
 */
public final class RPCPathName {

    public static final String GET_BLOCK = "getblock";
    public static final String GET_BLOCK_MULTIPLE = "getblocks";
    public static final String GET_BLOCK_HEIGHT = "getblockheight";
    public static final String GET_BLOCK_INFO = "getLatesBlockInfo";
    public static final String GET_PRODUCER = "getProducer";
    public static final String GET_PRODUCER_ALL = "getProducers";
    public static final String GET_PRODUCER_VOTE = "getProducerVotes";
    public static final String GET_PROPOSAL = "getProposal";
    public static final String GET_PROPOSAL_VOTE_ALL = "getAllProposalVote";
    public static final String GET_PROPOSAL_ALL = "getAllProposal";
    public static final String GET_AVG_GAS = "getAverageGasPrice";
    public static final String GET_VOTE = "getVote";
    public static final String SHOW_ACCOUNT = "showaccount";
    public static final String GET_CONTRACT = "getContract";
    public static final String SEND_RAW_TX = "sendrawtransaction";

}
