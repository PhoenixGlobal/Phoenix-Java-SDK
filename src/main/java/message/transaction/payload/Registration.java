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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import message.transaction.FixedNumber;
import message.transaction.ISerialize;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Registration implements ISerialize {

    public static final String SCRIPT_HASH = "4bf81ec1b8714802bf78bb9f87779150b4f10b63";

    private String fromPubKeyHash;

    private byte version;

    private boolean genesisWitness;

    private String name;

    private String url;

    private String country;

    private String address;

    private int longitude;

    private int latitude;

    private FixedNumber voteCounts;

    private boolean register;

    private boolean frozen;

    private byte operationType;

    @Override
    public byte[] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()){
            try(DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.write(Hex.decode(this.fromPubKeyHash));
                dataOut.writeInt(this.version);
                dataOut.write(Hex.decode(this.fromPubKeyHash));
                dataOut.writeBoolean(this.genesisWitness);
                dataOut.writeBytes(this.name);
                dataOut.writeBytes(this.url);
                dataOut.writeBytes(this.country);
                dataOut.writeBytes(this.address);
                dataOut.writeInt(this.longitude);
                dataOut.writeInt(this.latitude);
                dataOut.write(this.voteCounts.getBytes());
                dataOut.writeBoolean(this.register);
                dataOut.writeBoolean(this.frozen);
                dataOut.write(operationType);
                return out.toByteArray();
            }
        }
    }

}
