import java.util.Scanner;

/**
 * A HangmanGame class that presents a text based hangman game to the user. 
 * The program gets letter input from the user and continues to ask for letters
 * until the game is over.
 * 
 * @author Selim Can Güler - 22002811
 * @version 12.02.2021
 */
public class HangmanGame {
    public static void main(String[] args)  {

        // Constants
        final String PROG_NAME = "Hangman v1.0";

        // Variables
        Scanner scan;
        int letterFound;
        char letter;
        boolean play;
        String playAgain;
        Hangman hangmanGame;

        // Program Code
        // Print a welcome message and set the initial properties
        scan = new Scanner( System.in );
        System.out.println( "Welcome to " + PROG_NAME + " game!");

        play = true;
        while ( play ) {
            // Creates a new Hangman object for every time the user plays a new game
            // so the chosen word will be different everytime
            hangmanGame = new Hangman();

            System.out.println( "A secret word has been chosen. You have " + hangmanGame.getMaxAllowedIncorrectTries()
                    + " tries in total to guess the word.");

            do {
                // Displays the information about the secret word and letters
                System.out.println( "The word so far: " + hangmanGame.getKnownSoFar());
                System.out.println( "All letters: " + hangmanGame.getAllLetters());
                System.out.println( "Used letters: " + hangmanGame.getUsedLetters());

                // Asks for and gets user's guess
                System.out.print( "Guess a letter: ");
                letter = scan.nextLine().charAt( 0 );

                System.out.println(); // for the program to look cleaner

                // Reports the result of the guess
                letterFound = hangmanGame.tryThis( letter );

                // Character was either not in english alphabet or invalid
                if ( letterFound == -1 ) {
                    System.out.println( "Character is invalid.");
                    System.out.println( "Try again!");
                }
                // Letter was in the usedLetters String
                else if ( letterFound == -2 ) {
                    System.out.println( "Letter was already used. Try again.");
                }
                // Game is over
                else if ( letterFound == -3 ) {
                    if ( hangmanGame.hasLost() ) {
                        System.out.println( "Unfortunately, You have lost.");
                        System.out.println( "The word was: " + hangmanGame.getKnownSoFar());
                        // The word cannot be given to the user as it is a private property
                        // (StringBuffer) without a getter method.
                    } else {
                        System.out.println( "Congrats, you won!");
                        System.out.println( "The word is: " + hangmanGame.getKnownSoFar());
                    }
                } else if ( letterFound == 0 ) {
                    System.out.println( "You have guessed wrong.");
                    System.out.println( "You have "
                            + (hangmanGame.getMaxAllowedIncorrectTries() - hangmanGame.getNumOfIncorrectTries())
                            + " lives left.");
                } else {
                    System.out.println( "'" + letter + "' has been found " + letterFound + " times.");
                    System.out.println( "You have "
                            + ( hangmanGame.getMaxAllowedIncorrectTries() - hangmanGame.getNumOfIncorrectTries() )
                            + " lives left.");
                }

                System.out.println(); // for the program to look cleaner

            } while ( ! hangmanGame.isGameOver() );
            System.out.println();

            // Asks for and gets user's choice about playing again
            System.out.println( "Would you like to play again?");
            System.out.println( "To exit the game press 'N'.");
            System.out.println( "If you want to continue playing, type any character other than 'N'");
            System.out.print( "Continue?: ");
            playAgain = scan.nextLine();

            if ( playAgain.equalsIgnoreCase("N") ) {
                play = false;
            }
        }

        System.out.println( "That was fun. Can't wait for the next time!");
        scan.close();
    }
}
