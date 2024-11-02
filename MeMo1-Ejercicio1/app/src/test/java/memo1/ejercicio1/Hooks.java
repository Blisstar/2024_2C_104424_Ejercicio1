package memo1.ejercicio1;

import io.cucumber.java.After;

public class Hooks {
    @After
    public void afterScenario() {
        BankingSystem.resetInstance();
    }
}
