package xml.authorship.service.authorship.service.beans;

public enum RecordForm {

    STAMPANI_TEKST(0),
    OPTICKI_DISK(1),
    DRUGO(2);

    private final int value;

    RecordForm(int value) {
        this.value = value;
    }

    public static RecordForm valueOf(int value) {
        switch (value) {
            case 0:
                return RecordForm.STAMPANI_TEKST;
            case 1:
                return RecordForm.OPTICKI_DISK;
            default:
                return RecordForm.DRUGO;
        }
    }

    public int getValue() {
        return value;
    }
}
