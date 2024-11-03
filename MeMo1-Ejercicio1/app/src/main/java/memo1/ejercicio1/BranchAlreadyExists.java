package memo1.ejercicio1;

public class BranchAlreadyExists extends Exception{
    public BranchAlreadyExists(){
        super("The branch with that number is already exists.");
    }
}
