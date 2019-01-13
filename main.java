import java.security.SecureRandom;
import java.util.*;

class Scratch {
    public static void main(String[] args) {
        Card card1 = new Card("Card 1");
        Card card2 = new Card("Card 2");
        int choice = 1;

        while (choice >= 1) {
            choice = menu();
            switch (choice) {
                case 1: //add credits
                    Terminal.loadCredits(card1);
                    Terminal.loadCredits(card2);
                    break;
                case 2: //show info
                    Terminal.getCardInfo(card1);
                    Terminal.getCardInfo(card2);
                    break;
                case 3: //transfer credits
                    Scanner input = new Scanner(System.in);
                    System.out.println("Choose FROM which card you want to move: ");
                    int fromc = input.nextInt();
                    System.out.println("Choose TO which card you want to move: ");
                    int toc = input.nextInt();
                    if (fromc == 1){
                        Terminal.moveCredits(card1, card2);
                    }
                    else{
                        Terminal.moveCredits(card2, card1);
                    }
                    break;
                case 4: //transfer tickets
                    Scanner transfer = new Scanner(System.in);
                    System.out.println("Choose FROM which card you want to move: ");
                    int fromcrd = transfer.nextInt();
                    System.out.println("Choose TO which card you want to move: ");
                    int tocrd = transfer.nextInt();
                    if (fromcrd == 1){
                        Terminal.moveTickets(card1, card2);
                    }
                    else{
                        Terminal.moveTickets(card2, card1);
                    }
                    break;
                case 5: //play game
                    Scanner cinput = new Scanner(System.in);
                    System.out.println("Which card do you want to use for playing?: ");
                    int selection = cinput.nextInt();
                    if (selection == 1){
                        Game.playGame(card1);
                    }
                    else if (selection == 2){
                        Game.playGame(card2);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static int menu() {
        int selection;
        Scanner input = new Scanner(System.in);
        System.out.println("Choose option below: ");
        System.out.println("-------------------------\n");
        System.out.println("1 - Add credit to cards");
        System.out.println("2 - Show cards info");
        System.out.println("3 - Transfer credit between cards");
        System.out.println("4 - Play a game");
        System.out.println("0 - Quit");
        selection = input.nextInt();
        return selection;
    }
}

class Card {
    private String idnumber;
    private String name;
    private double credits;
    private int tickets;

    //Class constructor
    Card(String nam){
        idnumber = RandomString.generate(10);
        credits = 0;
        tickets = 0;
        name = nam;
    }

    public String getId(Card card) {
        return card.idnumber;
    }

    public void setId(String id) {
        if (id.length() < 10){
            throw new IllegalArgumentException();
        }
        idnumber = id;
    }

    public String getName(Card card) {
        return card.name;
    }

    public void setName(String nm) {
        if (nm.length() < 3){
            throw new IllegalArgumentException();
        }
        name = nm;
    }

    public  double getCredits(Card card) {
        return card.credits;
    }

    public void setCredits(Card card, double credit) {
        if (credit < 0){
            throw new IllegalArgumentException();
        }
        card.credits = credit;
    }

    public int getTickets(Card card) {
        return card.tickets;
    }

    public void setTickets(Card card, int ticket) {
        if (ticket < 0){
            throw new IllegalArgumentException();
        }
        card.tickets = ticket;
    }
}

class Game {
    public static void playGame(Card card){
        Random rand = new Random();
        int gameresult = rand.nextInt(10) + 1;
        card.setCredits(card, card.getCredits(card) - 5);
        System.out.printf("\n%d Card credits payed for game.\n", 5);
        card.setTickets(card,card.getTickets(card) + gameresult);
        System.out.printf("%d tickets won from the game!\n", gameresult);
        System.out.printf("Card Tickets: %d\n\n", card.getTickets(card));
    }
}

class Prize {
    private String name;
    private int price;
    private int quantity;

}

class Terminal {
    public static void getCardInfo(Card card) {
        System.out.println("Card ID: " + card.getId(card));
        System.out.println("Card Name: " + card.getName(card));
        System.out.printf("Card Credits: %.2f\n", card.getCredits(card));
        System.out.printf("Card Tickets: %d\n", card.getTickets(card));
        System.out.println();
    }

    public static void loadCredits(Card card){
        System.out.print("Enter money to fill in card " + card.getName(card) + " -> " + card.getId(card) + " : ");
        Scanner scanner = new Scanner( System.in );
        double amount = Float.parseFloat(scanner.nextLine());
        card.setCredits(card, card.getCredits(card) + amount*3.14);
        System.out.printf(card.getName(card) + " Credits: %.2f\n", card.getCredits(card));
    }

    public static void moveCredits(Card fromcard, Card tocard){
        tocard.setCredits(tocard, fromcard.getCredits(fromcard) + tocard.getCredits(tocard));
        System.out.printf("Card Credits: %.2f\n", tocard.getCredits(tocard));
        fromcard.setCredits(fromcard, 0);
    }

    public static void moveTickets(Card fromcard, Card tocard){
        tocard.setTickets(tocard, fromcard.getTickets(fromcard) + tocard.getTickets(tocard));
        System.out.printf("Card Tickets: %d\n", tocard.getTickets(tocard));
        fromcard.setTickets(fromcard, 0);
    }
}

//Generating a random string for new Card IDs
//Code ready from Google
class RandomString {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
