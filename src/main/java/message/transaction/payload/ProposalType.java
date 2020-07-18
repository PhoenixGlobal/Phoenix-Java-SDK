package message.transaction.payload;

public enum  ProposalType {

    BLOCK_AWARD(0x01),
    TX_MIN_GAS(0x02),
    TX_GAS_LIMIT(0x03);

    public final byte value;

    ProposalType(final Integer value) {
        this.value = value.byteValue();
    }
}
