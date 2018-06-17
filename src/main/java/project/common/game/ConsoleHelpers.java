package project.common.game;

import java.util.Scanner;

public class ConsoleHelpers {
    public static final Scanner READER = new Scanner(System.in);

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
        System.out.print(text + " (Yes/No): ");
        while(true){
            String answer = READER.next().toLowerCase();
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

    public static void invalidInput(){
        System.out.println("Invalid input");
    }
}

