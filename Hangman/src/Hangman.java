import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A hangman model class that keeps the details of the hangman game
 * 
 * @author Selim Can Güler - 22002811
 * @version 12.02.2021
 */
public class Hangman {

    // Properties
    private StringBuffer allLetters;
    private StringBuffer usedLetters;
    private StringBuffer knownSoFar;
    private StringBuffer secretWord;
    private int numberOfIncorrectTries;
    private int maxAllowedIncorrectTries;

    // Constructor
    public Hangman() {

        // the method will initialize the variable secretWord
        numberOfIncorrectTries = 0;
        maxAllowedIncorrectTries = 6;

        secretWord = new StringBuffer( chooseSecretWord() );
        allLetters = new StringBuffer( "abcdefghijklmnopqrstuvwxyz");
        usedLetters = new StringBuffer("");

        knownSoFar = new StringBuffer("");
        // Adds underscore character in place of all characters of the choosen
        // secretWord
        for ( int i = 0; i < secretWord.length(); i++ ) {
            knownSoFar.append( "*" );
        }
    }

    // Methods

    /**
     * Checks if a letter is in the secretWord and add it to usedLetters. If it is
     * in secretWord, updates knownSoFar with the discovered letter.
     * 
     * @param letter the guessed letter
     * @return number of occurrences of letter in secretWord
     * @return -1 character is invalid
     * @return -2 letter was already used
     * @return -3 game is over
     */
    public int tryThis( char letter ) {
        int count;

        // Converting the character to lower case in the case of a upper case input
        letter = Character.toLowerCase( letter );

        // A character that is not in allLetters is invalid and method returns -1.
        if (allLetters.indexOf( String.valueOf( letter ) ) == -1 ) {
            return -1;
        }
        // If a letter was already used, the method
        // will return -2 as an indicator.
        if (usedLetters.indexOf( String.valueOf( letter )) == -1 ) {
            usedLetters.append( letter );
        } else {
            return -2;
        }

        count = 0;
        for ( int i = 0; i < secretWord.length(); i++ ) {
            if ( secretWord.charAt(i) == letter ) {
                // Replace the blank letters with the correct letter.
                // knownSoFar is assumed to have the same length as secretWord.
                knownSoFar.replace( i, i + 1, String.valueOf( letter ) );
                count++;
            }
        }
        if ( count == 0 ) {
            numberOfIncorrectTries++;
        }

        // Returns -3 as an indicator if the game is over
        if ( isGameOver() ) {
            knownSoFar = new StringBuffer( secretWord );
            return -3;
        }

        return count;
    }

    /**
     * Chooses a random word from the list
     * 
     * @return the chosen word
     */
    private String chooseSecretWord() {
        String[] words = { "bird", "cat", "computer", "java", "Davenport" }; // Some random words
        int index;
        String chosenWord;

        // Assigning a random index in the range of the array
        index = ( int ) ( Math.random() * words.length );

        // Getting the word from the array at that index
        chosenWord = words[index];

        return chosenWord;
    }

    /**
     * Reads a set of words from a text file that has 1 word on each line and
     * selects one word
     * 
     * @param file
     * @return the chosen secret word
     * @throws FileNotFoundException
     */
    private String chooseSecretWord(File file) throws FileNotFoundException {
        // Initialize the properties
        Scanner fileReader;
        ArrayList<String> words;
        int index;
        String chosenWord;

        fileReader = new Scanner(file);
        words = new ArrayList<>();

        // Add each word on the txt file to the list
        while ( fileReader.hasNextLine() ) {
            words.add(fileReader.nextLine());
        }

        fileReader.close();

        // Calculate a random index and use it to get a word from the list
        index = ( int ) ( Math.random() * words.size() );
        chosenWord = words.get(index);

        return chosenWord;


    }

    /**
     *
     * @return All letters in the alphabet
     */
    public String getAllLetters() {
        return allLetters.toString();
    }

    /**
     * 
     * @return Letters that have been tried
     */
    public String getUsedLetters() {
        return usedLetters.toString();
    }

    /**
     * 
     * @return Incorrect number of tries so far
     */
    public int getNumOfIncorrectTries() {
        return numberOfIncorrectTries;
    }

    /**
     *
     * @return Get the maximum allowed tries
     */
    public int getMaxAllowedIncorrectTries() {
        return maxAllowedIncorrectTries;
    }

    /**
     *
     * @return Get the letters that have been tested so far
     */
    public String getKnownSoFar() {
        return knownSoFar.toString();
    }

    /**
     * The game is over when either the user reaches the maximum number of allowed
     * incorrect tries or the user has found the correct answer
     * 
     * @return Whether the game is over or not
     */
    public boolean isGameOver() {
        return numberOfIncorrectTries == maxAllowedIncorrectTries
                || knownSoFar.toString().equals( secretWord.toString() );

    }

    /**
     * The game is lost when the user reaches the maximum number of allowed
     * incorrect tries
     * 
     * @return Whether the game is lost or won
     */
    public boolean hasLost() {
        return numberOfIncorrectTries == maxAllowedIncorrectTries;
    }
}
