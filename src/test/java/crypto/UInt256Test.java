package crypto;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UInt256Test {

    private final static String TEST_STRING = "4c8de7f71527bdc76f85b95c6c21c21eb5062c1728229b668909602c48d8c6b4";

    @Test
    public void uInt256FromStringTest(){
        final UInt256 classUnderTest = new UInt256();
        classUnderTest.fromString(TEST_STRING);
        assertEquals(Hex.toHexString(classUnderTest.getBytes()),TEST_STRING);
    }

    @Test(expected = DecoderException.class)
    public void uInt256FromStringFailsOnTooMuchChars(){
        final UInt256 classUnderTest = new UInt256();
        classUnderTest.fromString(TEST_STRING + "0");
    }

    @Test(expected = DecoderException.class)
    public void uInt256FromStringInvalidWillBeZero(){
        final UInt256 classUnderTest = new UInt256();
        classUnderTest.fromString("NotAValidUInt256");
        assertEquals(UInt256.ZERO, classUnderTest.getBytes());
    }

}