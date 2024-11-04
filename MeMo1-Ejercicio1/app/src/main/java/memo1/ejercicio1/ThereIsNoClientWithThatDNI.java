package memo1.ejercicio1;

public class ThereIsNoClientWithThatDNI extends Exception{

    public ThereIsNoClientWithThatDNI(){
        super("There is no client with that DNI.");
    }
}
