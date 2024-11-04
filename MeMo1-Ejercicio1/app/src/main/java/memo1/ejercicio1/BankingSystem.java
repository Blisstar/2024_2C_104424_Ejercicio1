package memo1.ejercicio1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BankingSystem {
    private static BankingSystem instance;

    private HashMap<Long, Account> accounts;
    private HashMap<String, Long> cbuByAlias;
    private HashMap<String, Client> clients;
    private HashMap<Integer, Branch> branchs;

    private Set<Long> generatedCBUs;
    private Random random;

    private ArrayList<Transaction> transactions;
    private int nextIDEmployee;
    
    private BankingSystem() {
        accounts = new HashMap<>();
        cbuByAlias = new HashMap<>();
        clients = new HashMap<>();
        branchs = new HashMap<>();

        generatedCBUs = new HashSet<>();
        random = new Random();

        transactions = new ArrayList<>();
        nextIDEmployee = -1;
    }

    public static BankingSystem getInstance() {
        if (instance == null) {
            synchronized (BankingSystem.class) {
                if (instance == null) {
                    instance = new BankingSystem();
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void addAccount(Account account) throws Exception {
        if (accounts.containsKey(account.getCbu())) throw new AccountWithCBUAlreadyExists();
        if (cbuByAlias.containsKey(account.getAlias())) throw new AccountWithAliasAlreadyExists();
        cbuByAlias.put(account.getAlias(), account.getCbu());
        accounts.put(account.getCbu(), account);
    }

    public Account getAccountByCBU(long cbu) throws ThereIsNoAccountWithThatCBU {
        if (!accounts.containsKey(cbu)) throw new ThereIsNoAccountWithThatCBU();
        return accounts.get(cbu);
    }

    public Account getAccountByAlias(String alias) throws ThereIsNoAccountWithThatAlias {
        if (!cbuByAlias.containsKey(alias)) throw new ThereIsNoAccountWithThatAlias();
        return accounts.get(cbuByAlias.get(alias));
    }

    public void registerClient(String dni, String lastName, String firstName, LocalDate birtDate, Address address) throws Exception {
        if (clients.containsKey(dni)) throw new ClientAlreadyExists();
        clients.put(dni, new Client(dni, lastName, firstName, birtDate, address));
    }

    public Client getClient(String dni) throws Exception {
        if (!clients.containsKey(dni)) throw new ThereIsNoClientWithThatDNI();
        return clients.get(dni);
    }

    public void registerBranch(Branch branch) throws BranchAlreadyExists {
        if (branchs.containsKey(branch.getNumber())) throw new BranchAlreadyExists();
        branchs.put(branch.getNumber(), branch);
    }

    public Branch getBranch(int branchNumber) throws NonexistentBranch {
        if (!branchs.containsKey(branchNumber)) throw new NonexistentBranch();
        return branchs.get(branchNumber);
    }

    public Long generateNextCBU(){
        int newSize = generatedCBUs.size() + 1;
        Long newCBU = 0L;
        do {
            newCBU = random.nextLong();
            generatedCBUs.add(newCBU);
        } while (generatedCBUs.size() != newSize);
        return newCBU;
    }

    public int generateNextTransactionNumber() {
        return transactions.size();
    }

    public int generateNextIDEmployee() {
        nextIDEmployee++;
        return nextIDEmployee;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public int getTransactionsCount() {
        return transactions.size();
    }
}
