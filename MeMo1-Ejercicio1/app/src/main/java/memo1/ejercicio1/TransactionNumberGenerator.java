package memo1.ejercicio1;

public class TransactionNumberGenerator {
    private static TransactionNumberGenerator instance;

    private int nextTransactionNumber;
    
    private TransactionNumberGenerator() {
        nextTransactionNumber = -1;
    }

    public static TransactionNumberGenerator getInstance() {
        if (instance == null) {
            synchronized (TransactionNumberGenerator.class) {
                if (instance == null) {
                    instance = new TransactionNumberGenerator();
                }
            }
        }
        return instance;
    }

    public int generateNextTransactionNumber() {
        nextTransactionNumber++;
        return nextTransactionNumber;
    }
}
