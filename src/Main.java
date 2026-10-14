import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static void main(String[] args) {
        List<BankAccount> bankAccountList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            bankAccountList.add(new BankAccount(" holder" + i, "ACC00" + i, 10000.0));
            ExecutorService pool = Executors.newFixedThreadPool(10);
            Random random = new Random();
            for (int i1 = 0; i1 < 100000; i1++) {
                int sourceIndex = random.nextInt(bankAccountList.size());
                int destIndex;
                do {
                    destIndex = random.nextInt(bankAccountList.size());
                } while (sourceIndex == destIndex); // Ensure source and destination are different

                BankAccount source = bankAccountList.get(sourceIndex);
                BankAccount destination = bankAccountList.get(destIndex);

                double amount = 1 + random.nextDouble() * 99;

                TransferTask task = new TransferTask(source, destination, amount);
                pool.execute(task);
            }
            pool.shutdown();

            try {
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
            double totalBalance = 0;
            for (BankAccount account : bankAccountList) {
                totalBalance += account.getBalance();
                System.out.println(account.getAccountHolderName() + " Final Balance: $" + account.getBalance());
            }

            System.out.println("----------------------------------------");
            System.out.println("Expected Total Money: $100,000.00");
            System.out.println("Actual Total Money:   $" + totalBalance);
        }
    }

    };

