package memo1.ejercicio1;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de Account usando el constructor sin argumentos
        Account account1 = new Account();
        account1.setCbu(123456789L); // Asignar un CBU
        account1.setBalance(1000.0); // Establecer el balance inicial

        // Crear una instancia de Account usando el constructor con saldo inicial
        Account account2 = new Account(987654321L, 500.0);

        // Realizar operaciones de depósito y retiro
        account1.deposit(200.0);  // Depositar 200 en la cuenta 1
        boolean successWithdraw = account1.withdraw(300.0);  // Retirar 300 de la cuenta 1

        account2.deposit(100.0);  // Depositar 100 en la cuenta 2
        boolean successWithdraw2 = account2.withdraw(700.0);  // Intentar retirar 700 de la cuenta 2 (debería fallar)

        // Imprimir detalles de las cuentas
        System.out.println("Cuenta 1:");
        System.out.println("CBU: " + account1.getCbu());
        System.out.println("Balance: " + account1.getBalance());

        System.out.println("Cuenta 2:");
        System.out.println("CBU: " + account2.getCbu());
        System.out.println("Balance: " + account2.getBalance());

        // Verificar si las operaciones fueron exitosas
        System.out.println("Retiro en cuenta 1 fue " + (successWithdraw ? "exitoso" : "fallido"));
        System.out.println("Retiro en cuenta 2 fue " + (successWithdraw2 ? "exitoso" : "fallido"));
    }
}
