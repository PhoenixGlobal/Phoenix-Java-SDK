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
package message.transaction.payload;

import lombok.*;
import message.transaction.FixedNumber;
import message.transaction.ISerialize;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@AllArgsConstructor
@Builder
public class Registration implements ISerialize {

    // AP1xWDozWvuVah1W86DKtcWzdw1LqCFuExk
    public static final String SCRIPT_HASH = "0000000000000000000000000000000000000101";

    @NonNull
    public final String fromPubKeyHash;

    @NonNull
    public final int version;

    @NonNull
    public final boolean genesisWitness;

    @NonNull
    public final String name;

    @NonNull
    public final String url;

    @NonNull
    public final String country;

    @NonNull
    public final String address;

    @NonNull
    public final int longitude;

    @NonNull
    public final int latitude;

    @NonNull
    public final FixedNumber voteCounts;

    @NonNull
    public final boolean register;

    @NonNull
    public final boolean frozen;

    @NonNull
    public final OperationType operationType;

    @Override
    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.write(Hex.decode(this.fromPubKeyHash));
                dataOut.writeInt(this.version);
                dataOut.write(Hex.decode(this.fromPubKeyHash));
                dataOut.writeBoolean(this.genesisWitness);
                dataOut.write(this.name.length());
                dataOut.writeBytes(this.name);
                dataOut.write(this.url.length());
                dataOut.writeBytes(this.url);
                dataOut.write(this.country.length());
                dataOut.writeBytes(this.country);
                dataOut.write(this.address.length());
                dataOut.writeBytes(this.address);
                dataOut.writeInt(this.longitude);
                dataOut.writeInt(this.latitude);
                dataOut.write(this.voteCounts.getBytes());
                dataOut.writeBoolean(this.register);
                dataOut.writeBoolean(this.frozen);
                dataOut.write(operationType.value);
                return out.toByteArray();
            }
        }
    }
}
