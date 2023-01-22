package xml.main.main.service.beans;


public enum Checkbox {
    DA(1),
    NE(0);

    private final int value;

    Checkbox(int value) {
        this.value = value;
    }

    public static Checkbox valueOf(int value) {
        switch (value) {
            case 0:
                return Checkbox.NE;
            default:
                return Checkbox.DA;
        }
    }

    public int getValue() {
        return value;
    }
}