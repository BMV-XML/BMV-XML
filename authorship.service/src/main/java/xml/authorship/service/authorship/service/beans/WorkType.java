package xml.authorship.service.authorship.service.beans;

public enum WorkType {

    KNJIZEVNO_DJELO(0),
    MUZICKO_DJELO(1),
    LIKOVNO_DJELO(2),
    RACUNARSKI_PROGRAM(3),
    DRUGO(4);

    private final int value;

    WorkType(int value) {
        this.value = value;
    }

    public static WorkType valueOf(int value) {
        switch (value) {
            case 0:
                return WorkType.KNJIZEVNO_DJELO;
            case 1:
                return WorkType.MUZICKO_DJELO;
            case 2:
                return WorkType.LIKOVNO_DJELO;
            case 3:
                return WorkType.RACUNARSKI_PROGRAM;
            default:
                return WorkType.DRUGO;
        }
    }

    public int getValue() {
        return value;
    }
}
