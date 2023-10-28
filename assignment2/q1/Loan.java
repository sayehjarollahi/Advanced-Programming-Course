import java.util.ArrayList;

public class Loan {
    private static ArrayList<Loan> allLoans=new ArrayList<>();
    private Customer customer;
    private int duration;
    private int remainingPayments;
    private int interest;
    private int amount;

    public Loan(Customer customer,int duration, int interest, int amount) {
        this.customer = customer;
        this.interest = interest;
        this.amount = amount;
        this.duration=duration;
        this.remainingPayments=duration;
        allLoans.add(this);
    }

    public static void passMonth(){
        for(int i=0;i<allLoans.size();i++){
            Loan loan=allLoans.get(i);
            loan.passMonthEach();
            if(allLoans.get(i).remainingPayments==0){
                allLoans.remove(allLoans.get(i));
                i-=1;
            }

        }
    }
    private double getPaymentAmount(){
        return (double)amount*((double)interest/100+1)/duration;
    }

    private void passMonthEach(){
        if(!this.customer.canPayLoan(this.getPaymentAmount())){
            this.customer.addNegativeScore();
        }
        else{
            remainingPayments-=1;
            double moneyToTakeout=this.getPaymentAmount();
            this.customer.payLoan(moneyToTakeout);
            }
        }
    }

