public class BankAccount {
     private String accountHolderName;
     private String accountNumber;
     private double balance;

    public BankAccount(String accountHolderName, String accountNumber, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
         this.balance= balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void depositing(double depositAmount){
        if(depositAmount>0) {
            balance += depositAmount;
            System.out.println("you deposit this amount:" + depositAmount + "on this account " + getAccountNumber() + "in this name :" + accountHolderName + "successfully");
        }
    }
     public void withdrawing(double withdrawAmount){
        if(withdrawAmount>balance){
            System.out.println(" transaction failed,  insufficient balance ");
        }
        else{

            double updatedBalance= balance-withdrawAmount;
            System.out.println("you with draw this amount successfully"+ withdrawAmount + " your new balance is:"+ updatedBalance);
        }
     }
}
