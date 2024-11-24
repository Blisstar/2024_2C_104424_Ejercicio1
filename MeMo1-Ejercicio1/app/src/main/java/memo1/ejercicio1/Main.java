package memo1.ejercicio1;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        Address clientAddress1 = new Address("A", "B" ,"C", "D", 2);
        Client client1 = new Client("12345678", "Fer", "Joel", LocalDate.of(1984,1,1), clientAddress1);
        Address clienAddress2 = new Address("A", "B" ,"C", "D", 2);
        Client client2 = new Client("12345678", "Fer", "Joel", LocalDate.of(1984,1,1), clienAddress2);
        Address address = new Address("A", "B", "C", "D", 1);
        Branch branch = new Branch(1, "Suc. Belgrando", address);

        // Crear una instancia de Account usando el constructor sin argumentos
        Account account1 = new Account(123456789L,"alias1", client1, branch, 1000.0);
        BankingSystem.getInstance().addAccount(account1);

        // Crear una instancia de Account usando el constructor con saldo inicial
        Account account2 = new Account(987654321L, "alias2", client2, branch, 500.0);
        BankingSystem.getInstance().addAccount(account2);

        //Mostrar estado inicial de las cuentas
        System.out.println("Estado inicial de las cuentas 1 y 2:");
        printAccountsDetails(account1, account2);

        // Realizar operaciones de depósito y retiro
        account1.deposit(200.0);  // Depositar 200 en la cuenta 1
        account2.deposit(100.0);  // Depositar 100 en la cuenta 2
        System.out.println("Deposito de 200.0 en cuenta 1 fue exitoso");
        System.out.println("Deposito de 100.0 en cuenta 2 fue exitoso");
        printAccountsDetails(account1, account2);


        account1.withdraw(300.0);  // Retirar 300 de la cuenta 1
        boolean operationResult = true;
        try {
            account2.withdraw(700.0);  // Intentar retirar 700 de la cuenta 2 (debería fallar)
        } catch (Exception e) {
            operationResult = false;
        }

        // Verificar si las operaciones fueron exitosas
        System.out.println("Retiro de 300.0 en cuenta 1 fue exitoso");
        System.out.println("Retiro de 700.0 en cuenta 2 fue " + (operationResult? "exitoso":"fallido"));
        printAccountsDetails(account1, account2);

        //Realizar transferencia desde la cuenta 1 a la cuenta 2
        account1.transfer(account2.getCbu(), 200);
        System.out.println("Transferencia de la cuenta 1 a la cuenta 2 de 200.0 pesos fue exitosa");
        printAccountsDetails(account1, account2);
    }

    public static void printAccountsDetails(Account account1, Account account2) {
        // Imprimir detalles de las cuentas
        System.out.println("Cuenta 1:");
        System.out.println("CBU: " + account1.getCbu());
        System.out.println("Balance: " + account1.getBalance());

        System.out.println("Cuenta 2:");
        System.out.println("CBU: " + account2.getCbu());
        System.out.println("Balance: " + account2.getBalance());

        System.out.println("\n/*--------------------------------------------*/ \n");
    }
}
