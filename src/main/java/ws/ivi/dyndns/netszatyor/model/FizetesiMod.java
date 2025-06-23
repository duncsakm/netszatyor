package ws.ivi.dyndns.netszatyor.model;

public enum FizetesiMod {
    ATUTALAS("Átutalás"),
    UTANVET("Utánvét"),
    BANKKARTYA("Bankkártya");

    private final String label;

    FizetesiMod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
