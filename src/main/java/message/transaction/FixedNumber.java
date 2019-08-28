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

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This class wraps valid CPX amounts
 * @author Artem Eger
 * @since 26.08.2019
 */
@Getter
@Setter
public class FixedNumber {

    public final static long ZERO_VALUE = 0L;
    public final static long MIN_VALUE = 1L;
    public final static long ONE_VALUE = 1000000000000000000L;

    public final static BigInteger P = BigInteger.valueOf(MIN_VALUE);
    public final static BigInteger KP  = P.multiply(P);
    public final static BigInteger MP = KP.multiply(KP);
    public final static BigInteger GP = MP.multiply(MP);
    public final static BigInteger KGP = GP.multiply(GP);
    public final static BigInteger MGP = KGP.multiply(KGP);
    public final static BigInteger CPX = BigInteger.valueOf(ONE_VALUE);

    private BigInteger value;

    public FixedNumber(BigDecimal value){
        this.value = value.multiply(BigDecimal.valueOf(ONE_VALUE)).toBigInteger();
    }

    public FixedNumber(double value){
        this.value = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(ONE_VALUE)).toBigInteger();
    }

    public byte [] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()) {
            try (DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.write(this.value.toByteArray().length);
                dataOut.write(this.value.toByteArray());
                return out.toByteArray();
            }
        }
    }

}
