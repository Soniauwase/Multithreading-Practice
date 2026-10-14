import java.util.concurrent.Callable;

public class TransferTask  implements Callable<String> {
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private Double amountToTransfer;

    public TransferTask(BankAccount sourceAccount, BankAccount destinationAccount, Double amountToTransfer) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amountToTransfer = amountToTransfer;
    }

    public void run() {



    }


    @Override
    public String call() throws Exception {
        boolean success=sourceAccount.withdrawing(amountToTransfer);
        if(success){
            destinationAccount.depositing(amountToTransfer);
            return " Successfully recieve "+ amountToTransfer + "From" + sourceAccount.getAccountHolderName() + "to "+ destinationAccount.getAccountHolderName();
        }
        else {
            return " transaction failed  , insuficient balance on this account"+sourceAccount.getAccountHolderName()+ amountToTransfer;

        }
    }
}
