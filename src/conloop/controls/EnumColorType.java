package conloop.controls;

/**
 *
 * @author PatoWhiz 11/04/2018
 */
public enum EnumColorType {
    WHITE("white"),
    RED("red"),
    CYAN("cyan");

    private final String colorTypeValue;

     EnumColorType(String strColorValue) {
        this.colorTypeValue = strColorValue;
    }

    public String getColorTypeValue() {
        return this.colorTypeValue;
    }
}
