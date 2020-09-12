/**
* Creates a subclass SavingsAccount of the super class BankAccount
* SavingsAccount stores account information for specifically savings accounts
* @author Jeremy Feng
*/

public class SavingsAccount extends BankAccount {
  private double interestRate;
  
  /**
  * Creates a 4-arg constructor using instances from BankAccount
  * @param User defined account information, including the interest rate
  */
  public SavingsAccount(int accountNo, String customerName, double balance, double interestRate) {
    this.accountNo = accountNo;
    this.customerName = customerName;
    this.balance = balance;
    this.interestRate = interestRate;
  }
  
  /**
  * Creates a 3-arg constructor using instances from BankAccount, and sets bank balance to 0.00
  * @param User defined account information, including the interest rate
  */
  public SavingsAccount(int accountNum, String customerName, double interestRate) {
    this(accountNum, customerName, 0.0, interestRate);
  }
  
  /**
  * Creates a getter method that gets the interest rate
  * @return the interest rate
  */
  public double getInterestRate() {
    return this.interestRate;
  }
  
  /**
  * Creates a method that calculates how much interest is made and adds that value to the balance
  */
  public void accrueInterest() {
    this.balance = (1 + this.interestRate) * this.balance;
  }
  
  /**
  * Creates an override method that creates a string containing the account information
  * @return the account information
  */
  @Override
  public String toString() {
    String acctInfo = super.toString();         // Calls to BankAccount method toString()
    double intrstRateF = interestRate * 100;    // Formats as percentage
    return acctInfo + "Interest Rate: " + intrstRateF + "%\n";
  }
}