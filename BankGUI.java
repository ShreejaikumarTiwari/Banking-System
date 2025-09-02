import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BankGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable accountTable;

    public BankGUI() {
        setTitle("Banking System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Table for accounts ===
        tableModel = new DefaultTableModel(new String[]{"Name", "Account No", "Balance"}, 0);
        accountTable = new JTable(tableModel);

        // Start with Menu
        showMainMenu();

        setVisible(true);
    }

    // === Main Menu ===
    private void showMainMenu() {
        getContentPane().removeAll();

        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton createBtn = new JButton("Create Account");
        JButton deleteBtn = new JButton("Delete Account");
        JButton depositBtn = new JButton("Deposit Money");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton showBtn = new JButton("Show Accounts");
        JButton exitBtn = new JButton("Exit");

        createBtn.addActionListener(e -> showCreateForm());
        deleteBtn.addActionListener(e -> showDeleteForm());
        depositBtn.addActionListener(e -> showDepositForm());
        withdrawBtn.addActionListener(e -> showWithdrawForm());
        showBtn.addActionListener(e -> showAccountTable());
        exitBtn.addActionListener(e -> System.exit(0));

        menuPanel.add(createBtn);
        menuPanel.add(deleteBtn);
        menuPanel.add(depositBtn);
        menuPanel.add(withdrawBtn);
        menuPanel.add(showBtn);
        menuPanel.add(exitBtn);

        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // === Create Account Form ===
    private void showCreateForm() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField accNoField = new JTextField();
        JTextField balanceField = new JTextField();

        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Account No:"));
        panel.add(accNoField);
        panel.add(new JLabel("Balance:"));
        panel.add(balanceField);

        panel.add(saveBtn);
        panel.add(backBtn);

        saveBtn.addActionListener(e -> {
            String name = nameField.getText();
            String accNo = accNoField.getText();
            double balance = Double.parseDouble(balanceField.getText());

            BankAccount acc = new BankAccount(name, accNo, balance);
            BankDatabase.addAccount(acc);
            BankDatabase.saveToFile();

            JOptionPane.showMessageDialog(this, "Account Created!");
            showMainMenu();
        });

        backBtn.addActionListener(e -> showMainMenu());

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // === Delete Account Form ===
    private void showDeleteForm() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField accNoField = new JTextField();
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Account No:"));
        panel.add(accNoField);
        panel.add(deleteBtn);
        panel.add(backBtn);

        deleteBtn.addActionListener(e -> {
            BankAccount acc = BankDatabase.findAccount(accNoField.getText());
            if (acc != null) {
                BankDatabase.getAccounts().remove(acc);
                BankDatabase.saveToFile();
                JOptionPane.showMessageDialog(this, "Account Deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Account Not Found!");
            }
            showMainMenu();
        });

        backBtn.addActionListener(e -> showMainMenu());

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // === Deposit Form ===
    private void showDepositForm() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField accNoField = new JTextField();
        JTextField amountField = new JTextField();
        JButton depositBtn = new JButton("Deposit");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Account No:"));
        panel.add(accNoField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(depositBtn);
        panel.add(backBtn);

        depositBtn.addActionListener(e -> {
            BankAccount acc = BankDatabase.findAccount(accNoField.getText());
            if (acc != null) {
                acc.deposit(Double.parseDouble(amountField.getText()));
                BankDatabase.saveToFile();
                JOptionPane.showMessageDialog(this, "Deposit Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Account Not Found!");
            }
            showMainMenu();
        });

        backBtn.addActionListener(e -> showMainMenu());

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // === Withdraw Form ===
    private void showWithdrawForm() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField accNoField = new JTextField();
        JTextField amountField = new JTextField();
        JButton withdrawBtn = new JButton("Withdraw");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Account No:"));
        panel.add(accNoField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(withdrawBtn);
        panel.add(backBtn);

        withdrawBtn.addActionListener(e -> {
            BankAccount acc = BankDatabase.findAccount(accNoField.getText());
            if (acc != null) {
                if (acc.withdraw(Double.parseDouble(amountField.getText()))) {
                    BankDatabase.saveToFile();
                    JOptionPane.showMessageDialog(this, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account Not Found!");
            }
            showMainMenu();
        });

        backBtn.addActionListener(e -> showMainMenu());

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // === Show Accounts Table ===
    private void showAccountTable() {
        getContentPane().removeAll();

        tableModel.setRowCount(0);
        for (BankAccount acc : BankDatabase.getAccounts()) {
            tableModel.addRow(new Object[]{acc.getAccountHolder(), acc.getAccountNumber(), acc.getBalance()});
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(accountTable), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> showMainMenu());
        panel.add(backBtn, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankDatabase.loadFromFile();
            new BankGUI();
        });
    }
}
