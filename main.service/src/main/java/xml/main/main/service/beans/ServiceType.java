package xml.main.main.service.beans;

public enum ServiceType {
    PATENT(0),
    STAMP(1),
    AUTH(2),
    AUTHORSHIP(3),
    ALL(4);
    private final int value;
    ServiceType(int value) {
        this.value = value;
    }
}
