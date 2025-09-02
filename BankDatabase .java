import java.io.*;
import java.util.ArrayList;

class BankDatabase {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static final String FILE_NAME = "accounts.txt";

    public static void addAccount(BankAccount account) {
        accounts.add(account);
        saveToFile();
    }

    public static void deleteAccount(String accNo) {
        accounts.removeIf(acc -> acc.getAccountNumber().equals(accNo));
        saveToFile();
    }

    public static BankAccount findAccount(String accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNo)) {
                return acc;
            }
        }
        return null;
    }

    public static ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    // === File Operations ===
    public static void loadFromFile() {
        accounts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String accNo = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.add(new BankAccount(name, accNo, balance));
                }
            }
        } catch (IOException e) {
            // File may not exist on first run, that's fine
        }
    }

    public static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount acc : accounts) {
                pw.println(acc.getAccountHolder() + "," + acc.getAccountNumber() + "," + acc.getBalance());
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
