import java.util.ArrayList;

public class Customer {
    private static ArrayList<Customer> allCustomers = new ArrayList<>();
    private String name;
    private double moneyInSafe;
    private ArrayList<Account> allActiveAccounts = new ArrayList<>();
    private int totalNumberOfAccountsCreated;
    private int negativeScore;

    public Customer(String name, double moneyInSafe) {
        this.name = name;
        this.moneyInSafe = moneyInSafe;
        allCustomers.add(this);
    }

    public static Customer getCustomerByName(String name) {
        for (Customer allCustomer : allCustomers) {
            if (allCustomer.name.equals(name)) {
                return allCustomer;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getNegativeScore() {
        return negativeScore;
    }

    public void createNewAccount(Bank bank, int money, int duration, int interest) {
        totalNumberOfAccountsCreated += 1;
        Account account = new Account(bank, totalNumberOfAccountsCreated, money, duration, interest, this);
        allActiveAccounts.add(account);
        moneyInSafe -= money;
    }

    public void leaveAccount(int accountId) {
        if (!(allActiveAccounts.contains(getAccountWithId(accountId)))||allActiveAccounts.isEmpty()) {
            System.out.println("Chizi zadi?!");
        } else {
            Account removingAccount = getAccountWithId(accountId);
            moneyInSafe += removingAccount.getAmountOfMoneyForLeaving();
            allActiveAccounts.remove(removingAccount);
            Account.deleteAccount(removingAccount);
        }
    }

    public boolean canPayLoan(double amount){
        if(amount>=moneyInSafe)
            return false;
        else return true;
    }

    public double getMoneyInSafe() {
        return moneyInSafe;
    }

    public void setMoneyInSafe(double moneyInSafe) {
        this.moneyInSafe += moneyInSafe;
    }

    public void getLoan(int duration,int interest,int money){
        moneyInSafe+=money;
        new Loan(this,duration,interest,money);
    }

    public void payLoan(double amount){
        moneyInSafe -=amount;
    }

    public boolean canGetLoan(){
        if(negativeScore>=5)
            return false;
        else
            return true;
    }

    public void addNegativeScore(){
        negativeScore+=1;
    }

    public boolean hasActiveAccountInBank(Bank bank) {
        for (Account activeAccount : allActiveAccounts) {
            if (activeAccount.getBank().equals(bank))
                return true;
        }
        return false;
    }

    private Account getAccountWithId(int id) {
        for (Account allActiveAccount : allActiveAccounts) {
            if (allActiveAccount.getId() == id)
                return allActiveAccount;
        }
        return null;
    }


}
