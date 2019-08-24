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
package message.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class represents a Transaction object
 * @author Artem Eger
 * @since 17.08.2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

    @JsonProperty("txType")
    @NonNull private byte txType;

    @JsonProperty("from")
    @NonNull private String from;

    @JsonProperty("toPubKeyHash")
    @NonNull private String toPubKeyHash;

    @JsonProperty("amount")
    @NonNull private double amount;

    @JsonProperty("nonce")
    @NonNull private long nonce;

    @JsonProperty("data")
    private byte [] data;

    @JsonProperty("gasPrice")
    @NonNull private double gasPrice;

    @JsonProperty("gasLimit")
    @NonNull private BigInteger gasLimit;

    @JsonProperty("signature")
    private byte [] signature;

    @JsonProperty("version")
    @NonNull private int version;

    @JsonProperty("executeTime")
    @NonNull private long executeTime;

}
