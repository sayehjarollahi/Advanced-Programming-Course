import java.util.ArrayList;

public class Account {
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private Bank bank;
    private int id;
    private int money;
    private int remainingDuration;
    private int interest;
    private Customer customer;

    public Account(Bank bank, int id, int money, int remainingDuration, int interest, Customer customer) {
        this.bank = bank;
        this.id = id;
        this.money = money;
        this.remainingDuration = remainingDuration;
        this.interest = interest;
        this.customer = customer;
        allAccounts.add(this);
    }

    public static void passMonth(){
        for(int i=0;i<allAccounts.size();i++){
            Account account=allAccounts.get(i);
            account.passMonthEach();
            if(account.remainingDuration==0){
                account.customer.leaveAccount(account.id);
                i-=1;
            }
        }

    }
    public int getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public double getAmountOfMoneyForLeaving() {
        if (remainingDuration > 0)
            return money;
        else if (remainingDuration == 0) {
            return money*((double)interest/100+1);
        }
        return 0;
    }

    public static void deleteAccount(Account account) {
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).equals(account)) {
                allAccounts.remove(i);
                break;
            }
        }
    }

    private void passMonthEach(){
        this.remainingDuration-=1;
        if(remainingDuration==0){
            this.customer.setMoneyInSafe(this.getAmountOfMoneyForLeaving()-this.money);
            this.interest=0;
        }
    }


}
