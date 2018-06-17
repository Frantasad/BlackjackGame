package project.common.game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleHelpers {
    private static final Scanner READER = new Scanner(System.in);

    public static void printTitle(){
        System.out.println("\t\t.-----------.");
        System.out.println("\t\t| BLACKJACK |");
        System.out.println("\t\t'-----------'\n");
    }

    public static void printHelp(){
        System.out.println("Available commands: \n" +
                " Help: Show printHelp\n" +
                " Hit: Take another card from the dealer\n" +
                " Stand: Take no more model\n" +
                " Surrender: Surrender the round\n");
    }

    public static void printCardInfo(){
        System.out.println("Card suits: \n" +
                " S - Spades\n" +
                " H - Hearts\n" +
                " D - Diamonds\n" +
                " C - Clubs\n");
    }

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

    public static void invalidInput(){
        System.out.println("Invalid input");
    }
}

