package memo1.ejercicio1;

public class MarriageDateCantBeBeforeBirth extends Exception{
    public MarriageDateCantBeBeforeBirth() {
        super("The marriage date cannot be before the birth.");
    }
}
