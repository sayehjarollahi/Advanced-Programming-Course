import java.util.ArrayList;

public class Bank {
    private static ArrayList<Bank> allBanks = new ArrayList<>();
    private String name;

    public Bank(String name) {
        this.name = name;
        allBanks.add(this);
    }

    public String getName() {
        return name;
    }

    public static Bank getBankWithName(String name) {
        for (Bank bank : allBanks) {
            if (bank.name.equals(name))
                return bank;
        }
        return null;
    }

    public static boolean isThereBankWithName(String name) {
        for (Bank bank : allBanks) {
            if (bank.name.equals(name))
                return true;
        }
        return false;
    }

    public static int getAccountInterestFromName(String name) {
        if (name.equals("KOOTAH"))
            return 10;
        else if (name.equals("BOLAN"))
            return 30;
        else
            return 50;
    }


}
