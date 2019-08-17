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
package datastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class represents abstraction for Objects that can be processed
 * as Jackson objects
 * @author Artem Eger
 * @since 17.08.2019
 */
public abstract class AJacksonObject {

    /**
     * Object mapper for jackson
     */
    @JsonIgnore
    final ObjectMapper mapper = new ObjectMapper();

    /**
     * This method allows casting this object as a JSON String
     * @return JSON String value
     * @throws JsonProcessingException on processing error
     */
    @JsonIgnore
    public String getAsJsonString() throws JsonProcessingException {
        return mapper.writer().writeValueAsString(this);
    }

    /**
     * This method allows casting this object as a byte array
     * @return byte array value
     * @throws JsonProcessingException on processing error
     */
    @JsonIgnore
    public byte [] getAsByteArray() throws JsonProcessingException {
        return mapper.writeValueAsBytes(this);
    }

    /**
     * This method allows to create a Jackson object out of a String
     * @param jsonString the JSON String
     * @return The AJacksonObject
     * @throws IOException on processing error
     */
    public abstract AJacksonObject getObjectFromJsonString(String jsonString) throws IOException;

}
