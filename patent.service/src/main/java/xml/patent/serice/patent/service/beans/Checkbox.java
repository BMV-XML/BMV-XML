package xml.patent.serice.patent.service.beans;


public enum Checkbox {
    DA(0),
    NE(1);

    private final int value;

    Checkbox(int value) {
        this.value = value;
    }

    public static Checkbox valueOf(int value) {
        switch (value) {
            case 1:
                return Checkbox.NE;
            default:
                return Checkbox.DA;
        }
    }

    public int getValue() {
        return value;
    }
}