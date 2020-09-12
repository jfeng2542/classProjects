/**
* Creates an exception class that will be thrown if the balance becomes negative
* @author Jeremy Feng
*/

public class InsufficientFundsException extends Exception {
  public InsufficientFundsException(String errorMessage) {    // Creates a 1-arg constructor that receives the error message
    super(errorMessage);                                      // Error message gets sent back to the throw-catch it was originally received from
  }
}