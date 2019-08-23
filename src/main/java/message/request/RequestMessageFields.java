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

/**
 * This class defines valid message field keys for {@link IRPCMessage}
 * @author Artem Eger
 * @since 16.08.2019
 */
public final class RequestMessageFields {

    public static final String HEIGHT = "height";
    public static final String HASH = "hash";
    public static final String ID = "id";
    public static final String ADDRESS = "address";
    public static final String LIST_TYPE = "listType";
    public static final String COUNT = "count";
    public static final String RAW_TX = "rawTx";
    public static final String MULTIPLE_TX = "txs";

}
