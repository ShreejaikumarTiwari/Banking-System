
public class BankOperations {

    public static void depositMoney(BankAccount account, double amount) {
        account.deposit(amount);
        System.out.println("✅ Deposit successful. New balance: " + account.getBalance());
    }

    public static void withdrawMoney(BankAccount account, double amount) {
        if (account.withdraw(amount)) {
            System.out.println("✅ Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("❌ Insufficient balance.");
        }
    }

    public static void checkBalance(BankAccount account) {
        System.out.println("💰 Current balance: " + account.getBalance());
    }
}
