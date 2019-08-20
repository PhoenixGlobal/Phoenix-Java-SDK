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
public final class Base58 {

    private final static String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private final static BigInteger BASE = BigInteger.valueOf(58L);

    public static String encode(byte [] bytes) {
        BigInteger value = new BigInteger(1, bytes);
        StringBuilder sb = new StringBuilder();
        if (value.compareTo(BigInteger.ZERO) > 0) {
            while (value.compareTo(BASE) >= 0) {
                BigInteger mod = value.mod(BASE);
                sb.insert(0, ALPHABET.charAt(mod.intValue()));
                value = (value.subtract(mod)).divide(BASE);
            }
        }
        sb.insert(0, ALPHABET.charAt(value.intValue()));
        return sb.toString();
    }

    public static String encodeChecked(byte[] payload) throws Exception {
        byte[] addressBytes;
        byte[] checksum = Arrays.copyOfRange(CryptoService.getSHA256(payload), 0, 4);
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            out.write(payload);
            out.write(checksum);
            addressBytes = out.toByteArray();
        }
        return Base58.encode(addressBytes);
    }

    public static byte[] decode(String input) throws Exception {
        BigInteger value = BigInteger.ZERO;
        for (int i = input.length() - 1; i >= 0; i--) {
            int index = ALPHABET.indexOf(input.charAt(i));
            if (index == -1) throw new Exception("Passed input is not in valid Format");
            value = value.add(BigInteger.valueOf(index).multiply(BigInteger.valueOf(58).pow(input.length() - 1 - i)));
        }
        byte[] bytes = value.toByteArray();
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] < 0;
        int stripSignBytePos = stripSignByte ? 1 : 0;
        int leadingZeros = 0;
        for (int i = 0; i < input.length() && input.charAt(i) == ALPHABET.charAt(0); i++) {
            leadingZeros++;
        }
        byte[] tmp = new byte[bytes.length - stripSignBytePos + leadingZeros];
        System.arraycopy(bytes, stripSignBytePos, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    public static byte[] decodeChecked(String input) throws Exception {
        byte [] bytes = Base58.decode(input);
        byte [] hash = Arrays.copyOfRange(bytes, 0, bytes.length - 4);
        byte [] checksum = Arrays.copyOfRange(bytes, bytes.length - 4, bytes.length);
        byte [] compare_checksum = Arrays.copyOfRange(CryptoService.getSHA256(hash), 0, 4);
        boolean check = Arrays.equals(compare_checksum, checksum);
        if(!check) throw new Exception("Checksum could not be verified");
        return hash;
    }

}
