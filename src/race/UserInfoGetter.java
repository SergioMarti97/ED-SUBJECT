package race;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * This class contains all the methods needed
 * to interact with the user and get info for the program.
 *
 * For example, the methods what are use to get one option
 * selected by the user, show the options on screen...
 *
 * This class needs to be instanced, because the objects
 * what it uses have to
 */
public class UserInfoGetter {

    /**
     * Scanner object
     */
    private Scanner scanner;

    /**
     * Constructor
     */
    public UserInfoGetter() {
        scanner = new Scanner(System.in);
    }

    /**
     * Most simple form to get some input from user,
     * show on screen some text and waits to read
     * the keyboard input
     * @param text the text showed on console screen
     * @return the user input string
     */
    public String getUserString(String text) {
        System.out.print(text);
        return scanner.nextLine();
    }

    public LocalDate getUserDate(String text, DateTimeFormatter formatter) {
        LocalDate date = null;
        boolean isValidDate = false;
        String userInput;
        do {
            userInput = getUserString(text);
            try {
                date = LocalDate.parse(userInput, formatter);
                isValidDate = true;
            } catch ( DateTimeParseException e ) {
                System.out.println("The input isn't valid date.");
            }
        } while ( !isValidDate );
        return date;
    }

    public LocalDate getUserDate(String text, String format) {
        return getUserDate(text, DateTimeFormatter.ofPattern(format));
    }

    /**
     * This method gets a number (inside the
     * specified interval) selected by the user
     * If the user enters not number input, or a
     * number which is outside the min and max values,
     * then loops until the user enters one
     * valid input
     * @param text text showed
     * @param min the min integer to select
     * @param max the max integer to select
     * @return a number between the min and max and selected by the user
     */
    public int getUserNumber(String text, int min, int max) {
        int userNumber = 0;
        boolean isValidInput = false;
        boolean isValidOption = false;
        String userInput;
        do {
            userInput = getUserString(text);
            try {
                userNumber = Integer.parseInt(userInput);
                isValidInput = true;
                isValidOption = userNumber >= min && userNumber <= max;
            } catch ( NumberFormatException e ) {
                System.out.println("Ha habido algún error: \n" + e.getMessage() + "\n¡Introduce un número!");
            }
            if ( isValidInput && !isValidOption ) {
                System.out.println("¡Elije un número entre " + min + " y " + max + "!");
            }
        } while ( !isValidInput || !isValidOption );
        return userNumber;
    }

    /**
     * This method builds a text menu option from an enumeration
     * @param values the enumeration values
     * @param title the title showed at the start of the enumeration values
     * @param <T> the enumeration type
     * @return a string with all the options of the enumeration
     */
    public <T extends Enum<T>> String buildTextFromEnum(String title, T[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title).append('\n');
        for ( int i = 0; i < values.length; i++ ) {
            stringBuilder.append(i).append(") ").append(values[i]).append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * This method gets one option from an enumeration
     * chosen by the user
     * @param text the question showed to get the user option
     * @param values the values of the enumeration
     * @param <T> the enumeration type
     * @return an option from an enumeration
     */
    public <T extends Enum<T>> T getUserOptionFromEnum(String text, T[] values) {
        return values[getUserNumber(text, 0, values.length - 1)];
    }

    /**
     * This method is like the before method, but also shows
     * the options before ask to the user for an option
     */
    public <T extends Enum<T>> T getUserOptionFromEnum(String title, String text, T[] values) {
        System.out.print(buildTextFromEnum(title, values));
        return getUserOptionFromEnum(text, values);
    }

    /**
     * This method asks for a url to the user and
     * parses the user input to a url
     * @param text the text with url
     * @return a url made by the user
     */
    public URL getURLFromUser(String text) {
        URL url = null;
        try {
            String urlName = getUserString(text);
            url = new URL(urlName);
        } catch ( MalformedURLException e ) {
            System.out.println("¡La URL no esta correctamente formada!");
            e.printStackTrace();
        }
        return url;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

}
