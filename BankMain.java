public class BankMain {
    public static void main(String[] args) {
        System.out.println("🏦 Welcome to Simple Bank System");

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            p i = new p();
            int choice = i.getInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    String name = i.getString("Enter account holder name: ");
                    String accNo = i.getString("Enter account number: ");
                    double initialBal = i.getDouble("Enter initial balance: ");
                    BankAccount newAcc = new BankAccount(name, accNo, initialBal);
                    BankDatabase.addAccount(newAcc);
                    System.out.println("✅ Account created successfully!");
                    break;

                case 2:
                    accNo = i.getString("Enter account number: ");
                    BankAccount acc = BankDatabase.findAccount(accNo);
                    if (acc != null) {
                        double dep = i.getDouble("Enter amount to deposit: ");
                        BankOperations.depositMoney(acc, dep);
                    } else {
                        System.out.println("❌ Account not found.");
                    }
                    break;

                case 3:
                    accNo = i.getString("Enter account number: ");
                    acc = BankDatabase.findAccount(accNo);
                    if (acc != null) {
                        double wd = i.getDouble("Enter amount to withdraw: ");
                        BankOperations.withdrawMoney(acc, wd);
                    } else {
                        System.out.println("❌ Account not found.");
                    }
                    break;

                case 4:
                    accNo = i.getString("Enter account number: ");
                    acc = BankDatabase.findAccount(accNo);
                    if (acc != null) {
                        BankOperations.checkBalance(acc);
                    } else {
                        System.out.println("❌ Account not found.");
                    }
                    break;

                case 5:
                    System.out.println("👋 Thank you for using the Bank System!");
                    System.exit(0);

                default:
                    System.out.println("⚠️ Invalid choice.");
            }
        }
    }
}
