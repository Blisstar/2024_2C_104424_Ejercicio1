package memo1.ejercicio1;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        HashMap<Long, Account> accounts = new HashMap<>();

        // Crear una instancia de Account usando el constructor sin argumentos
        Account account1 = new Account(123456789L,"alias", 1000.0);
        accounts.put(123456789L, account1);

        // Crear una instancia de Account usando el constructor con saldo inicial
        Account account2 = new Account(987654321L, "alias", 500.0);
        accounts.put(987654321L, account2);

        // Realizar operaciones de depósito y retiro
        account1.deposit(200.0);  // Depositar 200 en la cuenta 1
        account1.withdraw(300.0);  // Retirar 300 de la cuenta 1

        account2.deposit(100.0);  // Depositar 100 en la cuenta 2
        account2.withdraw(700.0);  // Intentar retirar 700 de la cuenta 2 (debería fallar)

        printAccountsDetails(account1, account2);

        // Verificar si las operaciones fueron exitosas
        System.out.println("Retiro en cuenta 1 fue exitoso");
        System.out.println("Retiro en cuenta 2 fue exitoso");

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
