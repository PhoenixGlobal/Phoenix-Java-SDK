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
package crypto;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * This class provides the CPX Base58 dialect
 * @author Artem Eger
 * @since 20.08.2019
 */
public final class Base58CPX {

    private final static String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private final static String LEAD_CHAR = "A";
    private final static BigInteger BASE = BigInteger.valueOf(58L);

    public static String encode(byte [] bytes) {
       BigInteger bi = new BigInteger(1, bytes);
       StringBuilder sb = new StringBuilder();
       if (bi.compareTo(BigInteger.ZERO) > 0) {
           while (bi.compareTo(BASE) >= 0){
               BigInteger mod = bi.mod(BASE);
               sb.append(ALPHABET.charAt(mod.intValue()));
               bi = (bi.subtract(mod)).divide(BASE);
           }
       }
       return LEAD_CHAR + sb.reverse().toString();
    }

    public static String encodeChecked(byte[] payload) throws Exception {
        byte[] addressBytes;
        byte[] checksum = Arrays.copyOfRange(CryptoService.getSHA256(payload), 0, 4);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            out.write(payload);
            out.write(checksum);
            addressBytes = out.toByteArray();
        }
        return Base58CPX.encode(addressBytes);
    }

}
