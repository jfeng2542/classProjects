/**
* Creates two classes: BankAccount and Bank. BankAccount holds information about the customer. Bank holds information about the bank itself.
* @author Jeremy Feng
*/

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

class BankAccount {
  protected int accountNo;
  protected String customerName;
  protected double balance;
  
  /**
  * Creates a 3-arg constructor
  * @param User defined account number, customer name, and customer balance
  */
  public BankAccount(int accountNo, String customerName, double balance) {
    this.accountNo = accountNo;
    this.customerName = customerName;
    this.balance = balance;
  }
  
  /**
  * Creates a 2-arg constructor
  * @param User defined account number and customer name
  */
  public BankAccount(int accountNo, String customerName) {
    this(accountNo, customerName, 0.0);
  }
  
  /**
  * Creates a 0-arg constructor so the subclasses can access this superclass
  */
  public BankAccount() {
    this(0, "n/a", 0.0);
  }
  
  /**
  * Creates a getter method to get the account number
  * @return the account number
  */
  public int getAccountNum() {
    return this.accountNo;
  }
  
  /**
  * Creates a getter method to get the customer's name
  * @return the customer's name
  */
  public String getCustomerName() {
    return this.customerName;
  }
  
  /**
  * Creates a getter method to get the account's balance
  * @return the account's balance
  */
  public double getBalance() {
    return this.balance;
  }
  
  /**
  * Adds amount into balance to simulate the customer depositing into the bank account
  * @param User defined amount to deposit
  */
  public void deposit(double amt) {
    this.balance = this.balance + amt;
  }
  
  /**
  * Creates a string that consists of the account number, the customer's name, and the customer's balance
  * @return the customer's account information
  */
  public String toString() {
    String balanceF = String.format("%.2f", this.balance);      // Creates format for the file
    return String.format("%-8s%-18s%-15s", this.accountNo, this.customerName, balanceF);
  }
}

public class Bank {
  private String name;
  private BankAccount[] accounts;
  private int totalAccounts;
  public final static int ACCT_CAPACITY = 20;
  
  /**
  * Creates a 1-arg constructor that also initializes totalAccounts and length of accounts array
  * @param User defined bank name
  */
  public Bank(String name) {
    this.name = name;
    this.totalAccounts = 0;
    this.accounts = new BankAccount[ACCT_CAPACITY];
  }
  
  /**
  * Creates a getter method to get the bank's name
  * @return the bank's name
  */
  public String getName() {
    return this.name;
  }
  
  /**
  * Adds an object to accounts array. The object represents either a savings or a checkings account.
  * @param User defined account. The account is a BankAccount object, and not part of the array yet.
  */
  public void addAccount(BankAccount newAcct) {
    for(int i = 0; i < accounts.length; i++) {    // If the array doesn't have a customer name at an index, there is no account in the index
      if(accounts[i] == null) {
        accounts[i] = newAcct;
        break;
      } else if(i == accounts.length - 1) {       // If the whole array is filled with accounts
        printBankSummary();
        System.out.println("You've reached the maximum amount of accounts allowed. Program will exit.");
        System.exit(1);
      }
    }
  }
  
  /**
  * Prints summary of the bank account information
  */
  public void printBankSummary() {
    String strAcctFile;
    File targetFile = new File("bankSummary.txt");
    try {
      PrintWriter output = new PrintWriter(targetFile);
      output.write("Bank Name: Lehigh Lender\nStarting Balance:\n");
      strAcctFile = toString();       // Prints balances before interest rates and monthly fees are applied
      for(int i = 0; i < strAcctFile.length(); i++) {
        output.write(strAcctFile.charAt(i));
      }
      output.write("\nSummary:\n");
      accrueInterest();
      applyFee();
      strAcctFile = toString();       // Prints balances after they're applied
      for(int i = 0; i < strAcctFile.length(); i++) {
        output.write(strAcctFile.charAt(i));
      }
      output.close();
    } catch(IOException excptIO) {
      System.out.println("Caught IOException: " + excptIO.getMessage());
      System.exit(1);
    }
  }
  
  /**
  * Calculates how much interest is made and adds that value to the balance
  */
  public void accrueInterest() {
    for(int i = 0; i < accounts.length; i++) {
      if(accounts[i] instanceof SavingsAccount) {
        ((SavingsAccount)accounts[i]).accrueInterest();     // Upcasts so it can access accrueInterest() method
      }
    }
  }
  
  /**
  * Removes the monthly fee from the balance
  */
  public void applyFee() {
    for(int i = 0; i < accounts.length; i++) {
      if(accounts[i] instanceof CheckingAccount) {
        ((CheckingAccount)accounts[i]).applyFee();          // Upcasts so it can access applyFee() method
      }
    }
  }
  
  /**
  * Opens a Scanner to read acctInfo.txt. Create accounts based on the file and add them to the accounts array
  * @param the acctInfo.txt file
  */
  public void loadAccounts(File acctFile) {
    String inLine = "", nameCust;     // Creates private instances used for loadAccounts() method
    String[] splitTab;
    char accountType;
    int acctID;
    double acctBalance, intrstRate, feePerMonth;
    
    try {
      Scanner input = new Scanner(acctFile);              // Checks scanner for IOException
      while(input.hasNextLine()) {                        // If the text file has a line to read
        inLine = input.nextLine();
        while(inLine.contains("   ")) {
          inLine = inLine.replace("    ", "  ");          // Replaces all tab whitespace with a singular "  " so it can be split
          inLine = inLine.replace("   ", "  ");
        }
        splitTab = inLine.split("  ");
        for(int i = 0; i < splitTab.length; i++) {
          if((splitTab[i].charAt(i) != 'S') && (splitTab[i].charAt(i) != 'C')) {
            throw new UnknownAccountException("Account detected that is not labeled as savings or checkings. Program will exit.");
          }
          accountType = splitTab[i].charAt(i);            // 1st Index: checks if it's 'S' or 'C'
          i++;
          acctID = Integer.parseInt(splitTab[i]);         // 2nd Index: gets account number as int
          i++;
          nameCust = splitTab[i];                         // 3rd Index: gets customer's name
          i++;
          acctBalance = Double.parseDouble(splitTab[i]);  // 4th Index: gets account balance
          if(acctBalance < 0.0) {
            throw new InsufficientFundsException("Detected a negative balance. Program will exit.");
          }
          i++;
          if(accountType == 'S') {                        // 5th Index: distinguishes between interest rate and monthly fee
            intrstRate = Double.parseDouble(splitTab[i]);
            BankAccount newSave = new SavingsAccount(acctID, nameCust, acctBalance, intrstRate);
            addAccount(newSave);                          // Upcasts so it fills paramaters. Downcasts so it can be added to accounts array
          } else {
            feePerMonth = Double.parseDouble(splitTab[i]);
            BankAccount newCheck = new CheckingAccount(acctID, nameCust, acctBalance, feePerMonth);
            addAccount(newCheck);
          }
        }
      }
    } catch(IOException excptIO) {
      System.out.println("Caught IOException: " + excptIO.getMessage());
      System.exit(1);
    } catch (UnknownAccountException excptUn) {
      System.out.println(excptUn.getMessage());
      System.exit(1);
    } catch(InsufficientFundsException excptIn) {
      System.out.println(excptIn.getMessage());
      System.exit(1);
    }
  }
  
  /**
  * Writes all accounts from the accounts array onto bankSummary.txt
  */
  public String toString() {
    String writeLine = "";
    for(int i = 0; i < accounts.length; i++) {    // Checks all elements in accounts array to receive account information
      if(accounts[i] == null) { break; }          // If there's no account in the array index, leave the loop
      else {writeLine = writeLine.concat(accounts[i].toString()); }
    }
    return writeLine;
  }
}