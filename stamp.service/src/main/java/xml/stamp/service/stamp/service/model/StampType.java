package xml.stamp.service.stamp.service.model;


public enum StampType {
    INDIVIDULNI(0),
    KOLEKTIVNI(1),
    ZIG_GARANCIJE(2);

    private final int value;

    StampType(int value) {
        this.value = value;
    }

    public static StampType valueOf(int value) {
        switch (value) {
            case 2:
                return StampType.ZIG_GARANCIJE;
            case 1:
                return StampType.KOLEKTIVNI;
            default:
                return StampType.INDIVIDULNI;
        }
    }

    public int getValue() {
        return value;
    }
}