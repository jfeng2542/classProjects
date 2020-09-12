/**
* Creates the test class that connects the following classes:
* BankAccount, CheckingAccount, SavingsAccount, UnknownAccountException, InsufficientFundsException
* This class accesses the methods to add accounts and create the bankSummary.txt file
* @author Jeremy Feng
*/

import java.io.File;

public class TestBank {
  public static void main(String[] args) {
    try {
      if(args.length != 1) {            // If there is not exactly one command-line argument entered in the command prompt at program execution
        throw new Exception("Not enough account information. Program will exit.");
      }
    } catch(Exception excpt1) {
      System.out.println(excpt1.getMessage());
      System.exit(1);
    }
    Bank lehighLender = new Bank("Lehigh Lender");
    File sourceFile = new File(args[0]);
    
    lehighLender.loadAccounts(sourceFile);
    lehighLender.printBankSummary();
  }
}