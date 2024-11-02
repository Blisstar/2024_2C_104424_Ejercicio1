package memo1.ejercicio1;

public class InvalidDNI extends Exception {
    public InvalidDNI() {
        super("The DNI is incorrect, it should contain only numbers and it be of 7 or 8 digits.");
    }
}
