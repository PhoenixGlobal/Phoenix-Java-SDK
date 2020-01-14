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

import lombok.*;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * This class represents a Transaction object
 * @author Artem Eger
 * @since 17.08.2019
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements ISerialize {

    private int version;

    private byte txType;

    private String fromPubKeyHash;

    private String toPubKeyHash;

    private FixedNumber amount;

    private long nonce;

    private byte [] data = new byte[1];

    private FixedNumber gasPrice;

    private BigInteger gasLimit;

    private byte [] signature;

    private long executeTime = 0L;

    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.writeInt(this.version);
                dataOut.writeByte(this.txType);
                dataOut.write(Hex.decode(this.fromPubKeyHash));
                dataOut.write(Hex.decode(this.toPubKeyHash));
                dataOut.write(this.getAmount().getBytes());
                dataOut.writeLong(this.nonce);
                if(this.data.length <= 1) {
                    dataOut.write(new byte[1]);
                } else {
                    dataOut.write(this.getData().length);
                    dataOut.write(this.getData());
                }
                dataOut.write(this.getGasPrice().getBytes());
                dataOut.write(this.gasLimit.toByteArray().length);
                dataOut.write(this.gasLimit.toByteArray());
                dataOut.writeLong(this.getExecuteTime());
                return out.toByteArray();
            }
        }
    }

}
