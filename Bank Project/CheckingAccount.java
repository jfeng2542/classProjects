/**
* Creates a subclass CheckingAccount of the super class BankAccount
* CheckingAccount stores account information for specifically checking accounts
* @author Jeremy Feng
*/

public class CheckingAccount extends BankAccount {
  private double monthlyFee;
  
  /**
  * Creates a 4-arg constructor using instances from BankAccount
  * @param User defined account information, including the monthly fee
  */
  public CheckingAccount(int accountNo, String customerName, double balance, double monthlyFee) {
    this.accountNo = accountNo;
    this.customerName = customerName;
    this.balance = balance;
    this.monthlyFee = monthlyFee;
  }
  
  /**
  * Creates a getter method that gets the monthly fee
  * @return the monthly fee
  */
  public double getMonthlyFee() {
    return this.monthlyFee;
  }
  
  /**
  * Creates a setter method that sets the monthly fee
  * @param User defined monthly fee
  */
  public void setMonthlyFee(double monthlyFee) {
    this.monthlyFee = monthlyFee;
  }
  
  /**
  * Creates a method that removes the monthly fee from the balance and checks for exception
  */
  public void applyFee() {
    this.balance = this.balance - this.monthlyFee;
    try {
      if(balance < 0.0) {
        throw new InsufficientFundsException("Detected a negative balance. Program will exit.");
      }
    } catch(InsufficientFundsException excptIn) {
      System.out.println(excptIn.getMessage());
      System.exit(1);
    }
  }
  
  /**
  * Creates an override method that creates a string containing the account information
  * @return the account information
  */
  @Override
  public String toString() {
    String acctInfo = super.toString();     // Calls to BankAccount method toString()
    String monthFeeF = String.format("%.2f", this.monthlyFee);
    return acctInfo + "Monthly Fee: $" + monthFeeF + "\n";
  }
}