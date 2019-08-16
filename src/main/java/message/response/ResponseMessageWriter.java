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
package message.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class provides functionality to convert {@link ExecResult} to JSON
 * Strings and vice versa
 * @author Artem Eger
 * @since 16.08.2019
 */
public class ResponseMessageWriter {

    /**
     * {@link ObjectMapper}
     */
    private ObjectMapper mapper;

    /**
     * Default constructor
     */
    public ResponseMessageWriter(){
        this.mapper = new ObjectMapper();
    }

    /**
     * Constructor allowing for a custom mapper
     */
    public ResponseMessageWriter(ObjectMapper mapper){
        this.mapper = mapper;
    }

    /**
     * Converts a JSON String to a {@link ExecResult}
     * @param jsonString String to be converted
     * @return a valid {@link ExecResult}
     */
    public ExecResult getExecResultFromString(String jsonString) throws IOException {
        return mapper.readValue(jsonString, ExecResult.class);
    }

    /**
     * Converts a {@link ExecResult} to a JSON String
     * @param result {@link ExecResult} to be converted
     * @return String value of the {@link ExecResult}
     */
    public String getStringFromExecResult(ExecResult result) throws JsonProcessingException {
        return mapper.writer().writeValueAsString(result);
    }

}
