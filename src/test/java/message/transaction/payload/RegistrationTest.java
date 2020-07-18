package message.transaction.payload;

import crypto.CPXKey;
import message.transaction.FixedNumber;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;


import static org.junit.Assert.*;

public class RegistrationTest {

    @Test
    public void testValidRegistration() throws Exception {
        final Registration classUnderTest = Registration.builder()
                .version(1)
                .fromPubKeyHash(CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM"))
                .operationType(OperationType.REGISTER)
                .country("country")
                .url("url")
                .name("name")
                .address("address")
                .longitude(0)
                .latitude(0)
                .voteCounts(new FixedNumber(0, FixedNumber.CPX))
                .register(true)
                .frozen(false)
                .genesisWitness(false)
                .build();
        final Registration classUnderTest2 = Registration.builder()
                .version(1)
                .fromPubKeyHash(CPXKey.getScriptHashFromCPXAddress("APEt5ThLdoXiMGQkDmGnfY271vJrii5LxxM"))
                .operationType(OperationType.REGISTER)
                .country("country")
                .url("url")
                .name("name")
                .address("address")
                .longitude(0)
                .latitude(0)
                .voteCounts(new FixedNumber(0, FixedNumber.CPX))
                .register(true)
                .frozen(false)
                .genesisWitness(false)
                .build();
        assertEquals(Hex.toHexString(classUnderTest.getBytes()), Hex.toHexString(classUnderTest2.getBytes()));
    }
}