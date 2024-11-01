package memo1.ejercicio1;

public class IncorrectDNI extends Exception {
    public IncorrectDNI() {
        super("The DNI is incorrect, it should contain only numbers and it be of 7 or 8 digits.");
    }
}
