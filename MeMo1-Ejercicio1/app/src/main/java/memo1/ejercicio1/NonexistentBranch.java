package memo1.ejercicio1;

public class NonexistentBranch extends Exception{
    public NonexistentBranch(){
        super("The branch with that number doesn't exists.");
    }
}
