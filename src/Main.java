import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<BankAccount> bankAccountList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            bankAccountList.add(new BankAccount("Holder " + i, "ACC00" + i, 10000.0));
        }

        ExecutorService pool = Executors.newFixedThreadPool(10);
        Random random = new Random();

        List<Future<String>> results = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            int sourceIndex = random.nextInt(bankAccountList.size());
            int destIndex;
            do {
                destIndex = random.nextInt(bankAccountList.size());
            } while (sourceIndex == destIndex);

            BankAccount source = bankAccountList.get(sourceIndex);
            BankAccount destination = bankAccountList.get(destIndex);
            double amount = 1 + random.nextDouble() * 99;

            TransferTask task = new TransferTask(source, destination, amount);

            Future<String> future = pool.submit(task);
            results.add(future);
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

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        int successCount = 0;
        int failureCount = 0;

        for (Future<String> future : results) {
            try {
                String resultMessage = future.get();

                if (resultMessage.startsWith("SUCCESS")) {
                    successCount++;
                } else if (resultMessage.startsWith("FAILED")) {
                    failureCount++;
                }
            } catch (ExecutionException e) {
                System.out.println("Task threw an exception: " + e.getCause());
            }
        }

        double totalBalance = 0;
        for (BankAccount account : bankAccountList) {
            totalBalance += account.getBalance();
            System.out.println(account.getAccountHolderName() + " Final Balance: $" + account.getBalance());
        }

        System.out.println("----------------------------------------");
        System.out.println("Expected Total Money:  $100,000.00");
        System.out.println("Actual Total Money:    $" + totalBalance);
        System.out.println("Successful Transfers:  " + successCount);
        System.out.println("Failed Transfers:      " + failureCount);
        System.out.println("Execution Time:        " + totalTime + " ms");
    }
}