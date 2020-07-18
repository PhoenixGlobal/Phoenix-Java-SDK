package message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.request.cmd.GetProposalCmd;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenericJacksonWriterTest {

    private final GenericJacksonWriter classUnderTest = new GenericJacksonWriter();
    private final String validJsonString = "{\"name\":\"GetProposalCmd\",\"id\":" +
            "\"5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf\"}";

    @Test
    public void testWithCustomMapper() throws JsonProcessingException {
        final GenericJacksonWriter testWithCustomMapper = new GenericJacksonWriter(new ObjectMapper());
        final GetProposalCmd command = new GetProposalCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String result = testWithCustomMapper.getStringFromRequestObject(command);
        assertEquals(validJsonString, result);
    }

    @Test
    public void testWithFromBytes() throws JsonProcessingException {
        final GetProposalCmd command = new GetProposalCmd("5487b77c71dd2730b8537cd28580da7d0f93d90dcf6753de110646897807fecf");
        final String result = new String(classUnderTest.getBytesFromRequestObject(command));
        assertEquals(validJsonString, result);
    }

}
