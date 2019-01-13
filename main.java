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
        this.idnumber = RandomString.generate(10);
        this.credits = 0;
        this.tickets = 0;
        this.name = nam;
    }

    public String getId() {
        return this.idnumber;
    }

    public void setId(String id) {
        if (id.length() < 10){
            throw new IllegalArgumentException("id should be atleast 10 symbols");
        }
        this.idnumber = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String nm) {
        if (nm.length() < 3){
            throw new IllegalArgumentException("Name should be atleast 3 symbols long");
        }
        this.name = nm;
    }

    public double getCredits() {
        return this.credits;
    }

    public void setCredits(double credit) {
        if (credit < 0){
            throw new IllegalArgumentException("Credits cannot be negative");
        }
        this.credits = credit;
    }

    public int getTickets() {
        return this.tickets;
    }

    public void setTickets(int ticket) {
        if (ticket < 0){
            throw new IllegalArgumentException("Tickets cannot be negative");
        }
        this.tickets = ticket;
    }
}

class Game {
    public static void playGame(Card card){
        Random rand = new Random();
        int gameresult = rand.nextInt(10) + 1;
        card.setCredits(card.getCredits() - 5);
        System.out.printf("\n%d Card credits payed for game.\n", 5);
        card.setTickets(card.getTickets() + gameresult);
        System.out.printf("%d tickets won from the game!\n", gameresult);
        System.out.printf("Card Tickets: %d\n\n", card.getTickets());
    }
}

class Prize {
    private String name;
    private int price;
    private int quantity;

    public Prize(String name, int price, int quantity) {
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String nam) {
        this.name = nam;
    }
    
    public int getPrice() {
        return this.price;
    }
    
    public void setPrice(int price) {
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int quantity) {
        if (quantity < 0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}

class Terminal {
    private List<Prize> prizes; // TODO: use it
    
    public Terminal() {
        this.prizes = new ArrayList<>();
    }
    
    public static void getCardInfo(Card card) {
        System.out.println("Card ID: " + card.getId());
        System.out.println("Card Name: " + card.getName());
        System.out.printf("Card Credits: %.2f\n", card.getCredits());
        System.out.printf("Card Tickets: %d\n", card.getTickets());
        System.out.println();
    }

    public static void loadCredits(Card card){
        System.out.print("Enter money (whole number) to fill in card " + card.getName() + " -> " + card.getId() + " : ");
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        if (amount < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        card.setCredits(card.getCredits() + amount * 2.0);
        System.out.printf(card.getName() + " Credits: %f\n", card.getCredits());
    }

    public static void moveCredits(Card fromcard, Card tocard){
        tocard.setCredits(fromcard.getCredits() + tocard.getCredits());
        System.out.printf("Card Credits: %.2f\n", tocard.getCredits());
        fromcard.setCredits(0);
    }

    public static void moveTickets(Card fromcard, Card tocard){
        tocard.setTickets(fromcard.getTickets() + tocard.getTickets());
        System.out.printf("Card Tickets: %d\n", tocard.getTickets());
        fromcard.setTickets(0);
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
