package memo1.ejercicio1;

public class SameAccountOriginDestination extends Exception{

    public SameAccountOriginDestination() {
        super("The CBU or Alias of your account is entered, it cannot be transferred to the same account");
    }
}
