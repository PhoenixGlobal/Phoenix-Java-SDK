package message.transaction.payload;

public final class ProposalStatus {

    private ProposalStatus(){}

    public static final byte VOTING = 0x01;
    public static final byte PENDING = 0x02;

}
