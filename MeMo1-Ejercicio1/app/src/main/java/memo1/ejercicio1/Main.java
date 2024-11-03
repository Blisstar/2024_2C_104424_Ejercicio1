package memo1.ejercicio1;

public class Main {
    public static void main(String[] args) throws Exception {
        Address address = new Address("A", "B", "C", "D", 1);
        Branch branch = new Branch(1, "Suc. Belgrando", address);

        // Crear una instancia de Account usando el constructor sin argumentos
        Account account1 = new Account(123456789L,"alias", 1000.0);
        account1.register(branch);

        // Crear una instancia de Account usando el constructor con saldo inicial
        Account account2 = new Account(987654321L, "alias", 500.0);
        account2.register(branch);

        // Realizar operaciones de depósito y retiro
        account1.deposit(200.0);  // Depositar 200 en la cuenta 1
        boolean operationResult1 = true;
        try {
            account1.withdraw(300.0);  // Retirar 300 de la cuenta 1
        } catch (Exception e) {
            operationResult1 = false;
        }
        

        account2.deposit(100.0);  // Depositar 100 en la cuenta 2
        boolean operationResult2 = true;
        try {
            account2.withdraw(700.0);  // Intentar retirar 700 de la cuenta 2 (debería fallar)
        } catch (Exception e) {
            operationResult2 = false;
        } 

        printAccountsDetails(account1, account2);

        // Verificar si las operaciones fueron exitosas
        System.out.println("Retiro en cuenta 1 fue exitoso " + (operationResult1? "exitoso":"fallido"));
        System.out.println("Retiro en cuenta 2 fue exitoso " + (operationResult2? "exitoso":"fallido"));

        printAccountsDetails(account1, account2);

        System.out.println("Transferencia de la cuenta 1 a la cuenta 2 de 200.0 pesos fue exitosa");
    }

    public static void printAccountsDetails(Account account1, Account account2) {
        // Imprimir detalles de las cuentas
        System.out.println("Cuenta 1:");
        System.out.println("CBU: " + account1.getCbu());
        System.out.println("Balance: " + account1.getBalance());

        System.out.println("Cuenta 2:");
        System.out.println("CBU: " + account2.getCbu());
        System.out.println("Balance: " + account2.getBalance());
    }
}
