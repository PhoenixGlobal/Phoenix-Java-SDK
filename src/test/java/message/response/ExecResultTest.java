package message.response;

import message.util.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecResultTest {

    private final GenericJacksonWriter writer = new GenericJacksonWriter();

    @Test
    public void validExecResultTest() throws IOException {
        final ExecResult classUnderTest = ExecResult.builder()
                .succeed(true)
                .status(200)
                .message("dummy")
                .result(new HashMap<>())
                .build();
        final String jsonString = writer.getStringFromRequestObject(classUnderTest);
        final ExecResult result = writer.getObjectFromString(ExecResult.class, jsonString);
        assertEquals(classUnderTest, result);
        assertEquals("dummy", classUnderTest.message);
        assertEquals(200, classUnderTest.status);
        assertTrue(classUnderTest.succeed);
        assertTrue(result.result.isEmpty());
    }

    @Test
    public void validExecResultFromStringTest() throws IOException {
        final String validJsonString = "{" +
                "\"succeed\": true," +
                "\"status\": 200," +
                "\"result\": {}," +
                "\"message\": \"dummy\"}";
        final ExecResult classUnderTest = ExecResult.builder()
                .succeed(true)
                .status(200)
                .message("dummy")
                .result(new HashMap<>())
                .build();
        final ExecResult result = writer.getObjectFromString(ExecResult.class, validJsonString);
        assertEquals(classUnderTest, result);
    }

}
