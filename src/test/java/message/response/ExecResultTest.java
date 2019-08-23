package message.response;

import message.util.GenericJacksonWriter;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class ExecResultTest {

    private GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validExecResultTest() throws IOException {
        final ExecResult classUnderTest = ExecResult.builder()
                .succeed(true)
                .status(200)
                .message("dummy")
                .result("result")
                .build();
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final ExecResult result = writer.getObjectFromString(ExecResult.class, jsonString);
        assertEquals(classUnderTest, result);
    }

    @Test
    public void validExecResultFromStringTest() throws IOException {
        final String validJsonString = "{" +
                "\"succeed\": true," +
                "\"status\": 200," +
                "\"result\": \"result\"," +
                "\"message\": \"dummy\"}";
        final ExecResult classUnderTest = ExecResult.builder()
                .succeed(true)
                .status(200)
                .message("dummy")
                .result("result")
                .build();
        final ExecResult result = writer.getObjectFromString(ExecResult.class, validJsonString);
        assertEquals(classUnderTest, result);
    }

}
