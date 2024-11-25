package memo1.ejercicio1;

public class ThisClientIsNotCoowner extends Exception{

    public ThisClientIsNotCoowner() {
        super("This client is not coowner of your account.");
    }

}
