package message.transaction.payload;

public final class ProposalType {

    private ProposalType(){}

    public static final byte BLOCK_AWARD = 0x01;
    public static final byte TX_MIN_GAS = 0x02;
    public static final byte TX_GAS_LIMIT = 0x03;

}
