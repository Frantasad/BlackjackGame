package project.common.game;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for helping with communicating through console
 * @author František Holubec
 */
public class ConsoleHelpers {
    private static final Scanner READER = new Scanner(System.in);

    /**
     * Print title of Blackjack
     */
    public static void printTitle(){
        System.out.println("\t\t.-----------.");
        System.out.println("\t\t| BLACKJACK |");
        System.out.println("\t\t'-----------'\n");
    }

    /**
     * Print Help
     */
    public static void printHelp(){
        System.out.println("Available commands: \n" +
                " Help: Show printHelp\n" +
                " Hit: Take another card from the dealer\n" +
                " Stand: Take no more model\n" +
                " Surrender: Surrender the round\n");
    }

    /**
     * Print info about cards
     */
    public static void printCardInfo(){
        System.out.println("Card suits: \n" +
                " S - Spades\n" +
                " H - Hearts\n" +
                " D - Diamonds\n" +
                " C - Clubs\n");
    }

    /**
     * Ask in console for yes / no and return true / false
     * @param text - call before input
     * @return True for input "yes" and False for "no"
     */
    public static boolean askYesOrNo(String text){
        while(true){
            System.out.print(text + " (Yes/No): ");
            String answer = READER.nextLine().toLowerCase();
            switch (answer) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    /**
     * Ask in console for one of the enum types of given enum type
     * @param text - Call before input
     * @param enumClass - Enum class to get result form
     * @param <E> - Given Enum type
     * @return Parsed Enum type by input
     */
    public static <E extends Enum<E>> E askForEnum(String text, Class<E> enumClass) {
        while(true){
            System.out.print(text + ": ");
            String answer = READER.nextLine().toLowerCase();
            for(E type : enumClass.getEnumConstants()){
                if(type.toString().toLowerCase().equals(answer)){
                    return type;
                }
            }
            invalidInput();
        }
    }

    /**
     * Ask in console for number in given range
     * @param text - call before input
     * @param min - minimal possible value
     * @param max - maximal possible value
     * @return Parsed int from input
     */
    public static int askForNumber(String text, int min, int max){
        while(true){
            System.out.print(text + " (min: " + min + ", max: " + max + "): ");
            int answer;
            try{
                answer = READER.nextInt();
                READER.nextLine();
            } catch (InputMismatchException e){
                invalidInput();
                READER.nextLine();
                continue;
            }
            if(answer < min || answer > max){
                invalidInput();
                continue;
            }
            return answer;
        }
    }

    /**
     * Ask in console for string with maximal given length
     * @param text - call before input
     * @param maxLen - maximal length of given input
     * @return Parsed string
     */
    public static String askForText(String text, int maxLen){
        while(true){
            System.out.print(text + ": ");
            String answer = READER.nextLine();
            if(answer.length() > maxLen){
                invalidInput();
                continue;
            }
            return answer;
        }

    }

    /**
     * Print "invalid input"
     */
    public static void invalidInput(){
        System.out.println("Invalid input");
    }
}

