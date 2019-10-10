package src.errorHandling.types;

public abstract class abstract_error {
    private final String remedy;
    private final String type;

    public abstract_error(String remedy, String type) {
        this.remedy = remedy;
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringBuilder("\n")
                .append(type)
                .append("Error: ")
                .append(remedy)
                .append(", '%s' (%s:%d:%d). \n")
                .toString();
    }
}
