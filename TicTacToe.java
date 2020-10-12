import java.util.Scanner;

public class TicTacToe {
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    String[][] board = {  // Creates 2D string array that will be used to create the board
      {"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}
    };
    String token = "O";
    printBoard(board);
    while(true) {        // Loops until someone wins or it's a draw
      System.out.println("Player 1, it's your turn.");
      checkInput(board, token, scnr);
      if((checkWin(board)) == 1) {
        System.out.println("The winner is Player 1!");
        break;
      } else if((checkWin(board)) == 2) {
        System.out.println("It's a tie.");
        break;
      }
      printBoard(board);  // Calls to methods to check for valid input, print the board, and check if someone won
      token = "X";
      System.out.println("Player 2, it's your turn.");  
      checkInput(board, token, scnr);
      if(checkWin(board) == 1) {
        System.out.println("The winner is Player 2!");
        break;
      } else if((checkWin(board)) == 2) {
        System.out.println("It's a tie.");
        break;
      }
      printBoard(board);
      token = "O";
    }
    printBoard(board);    // Prints board again to show the ending board
  }
  
  public static void printBoard(String[][] board) { // Prints out the board
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[i].length; j++) {
        System.out.print("\t" + board[i][j]);
      }
      System.out.println("");
    }
  }
  
  public static void checkInput(String[][] board, String token, Scanner scnr) {
    boolean valid;
    int placeToken;
    while(true) {                                 // Asks the user which number to place the O token
      System.out.print("Type the number where you'll place your token: ");
      if(scnr.hasNextInt()) {                     // Checks if input is an integer
        placeToken = scnr.nextInt();
        if(1 <= placeToken && placeToken <= 9) {  // Checks if input is between numbers 1-9
          valid = checkTokenPlace(board, token, placeToken);
          if(valid) {                             // Checks if token is being placed on a number without a token.
            break;
          } else {
            System.out.println("A token is already on that number.");
          }
        } else {
          System.out.println("Input out of range.");
        }
      } else {
        String junk = scnr.next();
        System.out.println("Invalid input.");
      }
    }
    return;
  }
  
  public static boolean checkTokenPlace(String[][] board, String token, int placeToken) {
    boolean tokenDetect = true;
    int i = 0, j = 0, count = 0;    // Used to check if the number has been reached on the board
    for(i = 0; i < board.length; i++) {
      for(j = 0; j < board[i].length; j++) {
        count++;
        if(count == placeToken) {   // Checks if there's any token on the number input
          break;
        }
      }
      if(count == placeToken) {     // Repeated to completely exit the "for" loop
        break;
      }
    }
    if((board[i][j].equals("X") == true) || (board[i][j].equals("O") == true)) {
      tokenDetect = false;
    } else {                // If all conditions met, replace the number with a token
      tokenDetect = true;
      board[i][j] = board[i][j].replace(board[i][j], token);
    }
    return tokenDetect;
  }
  
  public static int checkWin(String[][] board) {
    int win = 0;
    int count = 0;
    int i = 0, j = 0;
    for(i = 0; i < board.length; i++) { // Checks each row for a win
      if((board[i][0].equals(board[i][1]) == true) && (board[i][1].equals(board[i][2]) == true)) {
        win = 1;
        break;
      }
    }
    for(j = 0; j < board.length; j++) { // Checks each column for a win
      if((board[0][j].equals(board[1][j]) == true) && (board[1][j].equals(board[2][j]) == true)) {
        win = 1;
        break;
      }
    }                                   // Next statement checks for diagonals
    if((board[0][0].equals(board[1][1]) == true) && (board[1][1].equals(board[2][2]) == true)) {
      win = 1;
    } else if((board[0][2].equals(board[1][1]) == true) && (board[1][1].equals(board[2][0]) == true)) {
      win = 1;
    }
    for(i = 0; i < board.length; i++) { // Checks if all spaces have been filled with tokens
      for(j = 0; j < board[i].length; j++) {
        if((board[i][j].equals("X") == true) || (board[i][j].equals("O") == true)) {
          count++;
        }
      }
    }
    if(count == 9) {                    // If all numbers were replaced with tokens and there's no win, it's a tie
      win = 2;
    }
    return win;
  }
}