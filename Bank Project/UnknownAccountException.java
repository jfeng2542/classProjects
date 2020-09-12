/**
* Creates an exception class that will be thrown if the input file provides an account that's neither a savings(S) or checking(C) account
* @author Jeremy Feng
*/

public class UnknownAccountException extends Exception {
  public UnknownAccountException(String errorMessage) {   // Creates a 1-arg constructor that receives the error message
    super(errorMessage);                                  // Error message gets sent back to the throw-catch it was originally received from
  }
}