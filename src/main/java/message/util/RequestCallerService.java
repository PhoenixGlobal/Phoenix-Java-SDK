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
package message.util;

import message.request.ARequestMessage;
import message.request.RequestMessageWriter;
import message.response.ExecResult;
import message.response.ResponseMessageWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class provides functionality to create post request to the RPC
 * server
 * @author Artem Eger
 * @since 17.08.2019
 */
public class RequestCallerService {

    private final String METHOD_POST = "POST";
    private final String PROPERTY_TYPE = "Content-Type";
    private final String JSON_TYPE = "application/json";

    private final RequestMessageWriter requestMessageWriter = new RequestMessageWriter();
    private final ResponseMessageWriter responseMessageWriter = new ResponseMessageWriter();

    /**
     * This method creates a POST request without authorization
     * @param urlString url of the RPC server
     * @param msg {@link ARequestMessage} type message to pass
     * @return {@link ExecResult} object response from the RPC server
     * @throws Exception on URL connection
     */
    public ExecResult postRequest(String urlString, ARequestMessage msg) throws Exception {
        URL url = new URL(urlString + "/" + msg.getRpcPath());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(METHOD_POST);
        con.setRequestProperty(PROPERTY_TYPE, JSON_TYPE);
        con.setDoOutput(true);
        try(OutputStream out = con.getOutputStream()) {
            out.write(requestMessageWriter.getBytesFromRequestObject(msg));
        }
        return responseMessageWriter.getExecResultFromString(contentToString(con));
    }

    /**
     * Helper method to read response
     * @param connection url connection providing response data
     * @return string of the content
     * @throws Exception on InputReader
     */
    private String contentToString(HttpURLConnection connection) throws Exception {
        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }

}
