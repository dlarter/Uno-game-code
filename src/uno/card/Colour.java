package uno.card;

public enum Colour {

    RED("\033[31m"),
    YELLOW("\033[33m"),
    GREEN("\033[32m"),
    BLUE("\033[34m"),
    WILD("\033[37m"),
    BOLDRESET("\033[0m" + "\033[1m");

    private final String colorCode;

    Colour(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColourCode() {
        return this.colorCode;
    }


}
