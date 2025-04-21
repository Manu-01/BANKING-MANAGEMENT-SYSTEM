import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// Model class
class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accNo, String name, double balance) {
        this.accountNumber = accNo;
        this.accountHolderName = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getDetails() {
        return "Account Number: " + accountNumber +
               "\nHolder: " + accountHolderName +
               "\nBalance: $" + balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// GUI class
public class BankSystemGUI extends JFrame {
    private Map<String, String> users = new HashMap<>();
    private Map<String, BankAccount> accounts = new HashMap<>();

    public BankSystemGUI() {
        // Add a default user
        users.put("admin", "1234");
        showAuthMenu();
    }

    private void showAuthMenu() {
        JFrame authFrame = new JFrame("Bank System - Login/Register");
        authFrame.setSize(300, 200);
        authFrame.setLayout(new GridLayout(3, 1));

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton exitBtn = new JButton("Exit");

        authFrame.add(loginBtn);
        authFrame.add(registerBtn);
        authFrame.add(exitBtn);

        loginBtn.addActionListener(e -> {
            authFrame.dispose();
            showLogin();
        });

        registerBtn.addActionListener(e -> {
            authFrame.dispose();
            showRegister();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        authFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        authFrame.setVisible(true);
    }

    private void showLogin() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(4, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (users.containsKey(user) && users.get(user).equals(pass)) {
                loginFrame.dispose();
                showMainUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
            }
        });

        loginFrame.setVisible(true);
    }

    private void showRegister() {
        JFrame regFrame = new JFrame("Register");
        regFrame.setSize(300, 200);
        regFrame.setLayout(new GridLayout(4, 1));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerBtn = new JButton("Register");

        regFrame.add(new JLabel("Choose Username:"));
        regFrame.add(usernameField);
        regFrame.add(new JLabel("Choose Password:"));
        regFrame.add(passwordField);
        regFrame.add(registerBtn);

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (users.containsKey(user)) {
                JOptionPane.showMessageDialog(regFrame, "Username already exists.");
            } else {
                users.put(user, pass);
                JOptionPane.showMessageDialog(regFrame, "Registration successful. Please login.");
                regFrame.dispose();
                showLogin();
            }
        });

        regFrame.setVisible(true);
    }

    private void showMainUI() {
        setTitle("Bank Account Management");
        setSize(400, 300);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton displayBtn = new JButton("Display Account");
        JButton exitBtn = new JButton("Exit");

        add(createBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(displayBtn);
        add(exitBtn);

        createBtn.addActionListener(e -> createAccount());
        depositBtn.addActionListener(e -> depositMoney());
        withdrawBtn.addActionListener(e -> withdrawMoney());
        displayBtn.addActionListener(e -> displayDetails());
        exitBtn.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createAccount() {
        String accNo = JOptionPane.showInputDialog("Enter Account Number:");
        if (accounts.containsKey(accNo)) {
            JOptionPane.showMessageDialog(this, "Account already exists!");
            return;
        }
        String name = JOptionPane.showInputDialog("Enter Account Holder Name:");
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Deposit:"));
        accounts.put(accNo, new BankAccount(accNo, name, amount));
        JOptionPane.showMessageDialog(this, "Account created successfully.");
    }

    private void depositMoney() {
        String accNo = JOptionPane.showInputDialog("Enter Account Number:");
        BankAccount acc = accounts.get(accNo);
        if (acc != null) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Deposit Amount:"));
            acc.deposit(amount);
            JOptionPane.showMessageDialog(this, "Deposit successful.");
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.");
        }
    }

    private void withdrawMoney() {
        String accNo = JOptionPane.showInputDialog("Enter Account Number:");
        BankAccount acc = accounts.get(accNo);
        if (acc != null) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Withdraw Amount:"));
            if (acc.withdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.");
        }
    }

    private void displayDetails() {
        String accNo = JOptionPane.showInputDialog("Enter Account Number:");
        BankAccount acc = accounts.get(accNo);
        if (acc != null) {
            JOptionPane.showMessageDialog(this, acc.getDetails());
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.");
        }
    }

    public static void main(String[] args) {
        new BankSystemGUI();
    }
}
