public class TransferTask  implements Runnable{
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private Double amountToTransfer;

    public TransferTask(BankAccount sourceAccount, BankAccount destinationAccount, Double amountToTransfer) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amountToTransfer = amountToTransfer;
    }

    @Override
    public void run() {
        sourceAccount.withdrawing(amountToTransfer);
        destinationAccount.depositing(amountToTransfer);

    }


}
