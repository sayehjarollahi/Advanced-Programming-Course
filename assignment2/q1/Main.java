import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine()).equals("Base dige, berid khonehatoon.")) {
            if (input.startsWith("Add a customer")) {
                matcher = getMatcher(input, "Add a customer with name (.*) and (\\d+) unit initial money.");
                new Customer(matcher.group(1), Integer.parseInt(matcher.group(2)));
            }
            else if (input.startsWith("Create bank")) {
                matcher = getMatcher(input, "Create bank (.*).");
                new Bank(matcher.group(1));
            }
            else if (input.startsWith("Create a")) {
                matcher = getMatcher(input, "Create a (KOOTAH|BOLAN|VIZHE) account for (.*) in (.*), with duration (\\d+) and initial deposit of (\\d+).");
                String bankName = matcher.group(3);
                String customerName = matcher.group(2);
                if (!Bank.isThereBankWithName(bankName))
                    System.out.println("In dige banke koodoom keshvarie?");
                else if ((Customer.getCustomerByName(customerName).getMoneyInSafe()) < Integer.parseInt(matcher.group(5)))
                    System.out.println("Boro baba pool nadari!");
                else {
                    Customer.getCustomerByName(customerName).createNewAccount(Bank.getBankWithName(bankName), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(4)), Bank.getAccountInterestFromName(matcher.group(1)));
                }
            }
            else if(input.startsWith("Give")){
                matcher = getMatcher(input, "Give (.*)'s money out of his account number (\\d+).");
                Customer removingCustomer=Customer.getCustomerByName(matcher.group(1));
                removingCustomer.leaveAccount(Integer.parseInt(matcher.group(2)));
            }
            else if(input.startsWith("Pay a")){
                matcher = getMatcher(input, "Pay a (\\d+) unit loan with %(\\d+) interest and (6|12) payments from (.*) to (.*).");
                Customer customer =Customer.getCustomerByName(matcher.group(5));
                if(!(Bank.isThereBankWithName(matcher.group(4)))){
                    System.out.println("Gerefti maro nesfe shabi?");
                }
                else if(!customer.canGetLoan()){
                    System.out.println("To yeki kheyli vazet bade!");
                }
                else{
                    customer.getLoan(Integer.parseInt(matcher.group(3)),Integer.parseInt(matcher.group(2)),Integer.parseInt(matcher.group(1)));
                }

            }
            else if(input.startsWith("Pass")){
                matcher = getMatcher(input, "Pass time by (\\d+) unit.");
                int time=Integer.parseInt(matcher.group(1));
                for(int i=0;i<time;i++){
                    passMonth();
                }
            }

            else if(input.endsWith("GAVSANDOOGH money.")){
                matcher = getMatcher(input, "Print (.*)'s GAVSANDOOGH money.");
                Customer customer=Customer.getCustomerByName(matcher.group(1));
                System.out.println((int)customer.getMoneyInSafe());
            }
            else if(input.endsWith("NOMRE MANFI count.")){
                matcher = getMatcher(input, "Print (.*)'s NOMRE MANFI count.");
                Customer customer = Customer.getCustomerByName(matcher.group(1));
                System.out.println(customer.getNegativeScore());
            }
            else if(input.startsWith("Does")){
                matcher = getMatcher(input, "Does (.*) have active account in (.*)\\?");
                Customer customer=Customer.getCustomerByName(matcher.group(1));
                Bank bank= Bank.getBankWithName(matcher.group(2));
                if(customer.hasActiveAccountInBank(bank))
                    System.out.println("yes");
                else
                    System.out.println("no");
            }
        }

    }
    private static void passMonth() {
        Loan.passMonth();
        Account.passMonth();
    }

    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }


}
