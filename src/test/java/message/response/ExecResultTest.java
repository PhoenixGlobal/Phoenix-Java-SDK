package message.response;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class ExecResultTest {

    private ResponseMessageWriter writer = new ResponseMessageWriter();

    @Test
    public void validExecResultTest() throws IOException {
        final ExecResult classUnderTest = ExecResult.builder()
                .succeed(true)
                .status(200)
                .message("dummy")
                .result("result")
                .build();
        final String jsonString = writer.getStringFromExecResult(classUnderTest);
        final ExecResult result = writer.getExecResultFromString(jsonString);
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
        final ExecResult result = writer.getExecResultFromString(validJsonString);
        assertEquals(classUnderTest, result);
    }
}
