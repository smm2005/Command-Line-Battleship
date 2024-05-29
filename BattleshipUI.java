import java.util.*; // Required for use of scanner, ArrayLists since it is a part of collections, etc.
import java.io.*; // Required for file handling as the instructions file will be processed.

public class BattleshipUI {

    public BattleshipUI(){

    }

    // Main Battleship Manager
    static BattleshipManager bm = new BattleshipManager(); // Class constructor for the BattleshipManager file

    // Boards
    static String[][] computer = bm.configureBoard(10, 10); // Create a new 10x10 board for the computer
    static String[][] player = bm.configureBoard(10, 10); // Create a new 10x10 board for the player
    static String[][] secondary = bm.configureBoard(10, 10); // Create a secondary 10x10 board that tracks the missile launches of the player

    // Computer ships
    // These arraylists contain info on each type of ship (how they are oriented and what rows and columns they take up)
    static ArrayList<Integer> destroyer = bm.destroyer;
    static ArrayList<Integer> submarine = bm.submarine;
    static ArrayList<Integer> cruiser = bm.cruiser;
    static ArrayList<Integer> battleship = bm.battleship;
    static ArrayList<Integer> carrier = bm.carrier;

    // Player ships
    // These arraylists contain info on each type of ship (how they are oriented and what rows and columns they take up)
    static ArrayList<Integer> p_destroyer = bm.p_destroyer;
    static ArrayList<Integer> p_submarine = bm.p_submarine;
    static ArrayList<Integer> p_cruiser = bm.p_cruiser;
    static ArrayList<Integer> p_battleship = bm.p_battleship;
    static ArrayList<Integer> p_carrier = bm.p_carrier;

    // Computer's data
    // Data on the computers ships used as helpers to process the real ships above
    static ArrayList<Integer> destroyer_data = new ArrayList<Integer>();
    static ArrayList<Integer> submarine_data = new ArrayList<Integer>();
    static ArrayList<Integer> cruiser_data = new ArrayList<Integer>();
    static ArrayList<Integer> battleship_data = new ArrayList<Integer>();
    static ArrayList<Integer> carrier_data = new ArrayList<Integer>();

    // Player's data
    // Data on the players ships used as helpers to process the real ships above
    static ArrayList<Integer> player_destroyer_data = new ArrayList<Integer>();
    static ArrayList<Integer> player_submarine_data = new ArrayList<Integer>();
    static ArrayList<Integer> player_cruiser_data = new ArrayList<Integer>();
    static ArrayList<Integer> player_battleship_data = new ArrayList<Integer>();
    static ArrayList<Integer> player_carrier_data = new ArrayList<Integer>();

    // ANSI colour codes for coloured text
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_RESET = "\u001B[0m"; // Code to reset the colour of text back to white
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RED = "\u001B[31m";

    // Boolean value to confirm if game is over, computer has won or player has won
    static boolean over = false;
    static boolean computer_won = false;
    static boolean player_won = false;

    // Boolean values to determine sunk ship for computer
    static boolean destroyer_sunk = false;
    static boolean submarine_sunk = false;
    static boolean cruiser_sunk = false;
    static boolean battleship_sunk = false;
    static boolean carrier_sunk = false;

    // Boolean values to determine sunk ship for computer
    static boolean player_destroyer_sunk = false;
    static boolean player_submarine_sunk = false;
    static boolean player_cruiser_sunk = false;
    static boolean player_battleship_sunk = false;
    static boolean player_carrier_sunk = false;

    // Boolean values to confirm that the declaration for player's attacks have been made already
    static boolean[] printed = {false, false, false, false, false, false, false, false, false, false};
    // These values exist so that once a declaration of a ship being sunk has been made, the same declaration can not be made again

    // Function to generate ship data onto an ArrayList
    public static ArrayList<Integer> playerGenerateShips(int offset, int orient, int a, int b, int c, ArrayList<Integer> data){
        boolean configured;
        switch (orient){
            case 0:
                configured = bm.configureShipsVertical(player, offset, a, b, c); // If orient = 0, ships are aligned vertically
                data.add(orient);
                data.add(a);
                data.add(b);
                data.add(c);
            case 1:
                configured = bm.configureShipsHorizontal(player, offset, a, b, c); // If orient = 1, ships are aligned horizontally
                data.add(orient);
                data.add(a);
                data.add(b);
                data.add(c);
        }
        return data; // Data list has been modified and will serve as the output of this function
    }

    public static void instructions(String filename) throws FileNotFoundException{ // FileNotFoundException thrown in the case that the BATTLESHIP.txt file has been relocated to a different folder
        File f = new File(filename); // File class created provided the filename
        Scanner s = new Scanner(f); // Scanner created to iterate through the file class
        while (s.hasNextLine()){
            // Formatting codes exist to appropriately format each ship, hit or miss that the board will experience.
            // Basically, these formats allow for the computer to read specific parts of some line in colour.
            String current = s.nextLine();
            if (current.contains("%1$s")){
                if (current.contains("%3$s")){
                    if (current.contains("*")){
                        current = String.format(current, ANSI_BLUE, ANSI_RESET, ANSI_YELLOW);
                    }
                    else if (current.contains("M")){
                        current = String.format(current, ANSI_BLUE, ANSI_RESET, ANSI_GREEN);
                    }
                }
                current = String.format(current, ANSI_BLUE, ANSI_RESET);
            }
            System.out.println(current);
        }
    }

    public static void trueGenerate(){

        // Ships are generated below provided the datas for the ship
        // The offset denotes the number of spaces occupied in the board by a certain ship

        destroyer = bm.generateShips(computer, 2, destroyer_data); 
        submarine = bm.generateShips(computer, 3, submarine_data);
        cruiser = bm.generateShips(computer, 3, cruiser_data);
        battleship = bm.generateShips(computer, 4, battleship_data);
        carrier = bm.generateShips(computer, 5, carrier_data);
        p_destroyer = bm.generateShips(player, 2, player_destroyer_data);
        p_submarine = bm.generateShips(player, 3, player_submarine_data);
        p_cruiser = bm.generateShips(player, 3, player_cruiser_data);
        p_battleship = bm.generateShips(player, 4, player_battleship_data);
        p_carrier = bm.generateShips(player, 5, player_carrier_data);

        /*
        Suppose the number of ships generated is NOT exactly 17
        This would not work as there should be 2 + 3 + 3 + 4 + 5 = 17 slots filled up
        Thus, a while loop is called and the entire data lists are cleared until the ships can be generated properly
        The board is also cleared as well to rid the board of the invalid ships as the ship count is incorrect.
        Once the ship count reaches 17, only then can the game proceed.
        */

        while (bm.shipsCount(computer) != 17){
            destroyer_data.clear();
            submarine_data.clear();
            cruiser_data.clear();
            battleship_data.clear();
            carrier_data.clear();
            computer = bm.configureBoard(10, 10);
            destroyer = bm.generateShips(computer, 2, destroyer_data);
            submarine = bm.generateShips(computer, 3, submarine_data);
            cruiser = bm.generateShips(computer, 3, cruiser_data);
            battleship = bm.generateShips(computer, 4, battleship_data);
            carrier = bm.generateShips(computer, 5, carrier_data);
            if (bm.shipsCount(computer) == 17){
                break;
            }
        }
        while (bm.shipsCount(player) != 17){
            player_destroyer_data.clear();
            player_submarine_data.clear();
            player_cruiser_data.clear();
            player_battleship_data.clear();
            player_carrier_data.clear();
            player = bm.configureBoard(10, 10);
            p_destroyer = bm.generateShips(player, 2, player_destroyer_data);
            p_submarine = bm.generateShips(player, 3, player_submarine_data);
            p_cruiser = bm.generateShips(player, 3, player_cruiser_data);
            p_battleship = bm.generateShips(player, 4, player_battleship_data);
            p_carrier = bm.generateShips(player, 5, player_carrier_data);
        }
    }

    public static void main(String[] args) {
        // The trueGenerate() function is called
        trueGenerate();

        // Try and catch is enacted to handle any instances that the BATTLESHIP.txt file may not be present in the folder
        try{
            instructions("BATTLESHIP.txt");
        }
        catch (FileNotFoundException e){
            System.out.println("File not found. If only the BATTLESHIP.txt file has not been tampered with...");
        }

        // Since the game starts immediately after the game is launched
        while (!over){
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter row and column launch coordinates: ");
            String val = sc.nextLine();
            Scanner value = new Scanner(val);

            // A person can quit the game midway if the type quit or q onto the launch coordinates prompt
            if (val.equals("quit") || val.equals("q")){
                return;
            }

            while (value.hasNextLine()){
                try {
                    int row = value.nextInt();
                    int column = value.nextInt();
                    boolean b = bm.userlaunch(computer, row, column);

                    // The boolean b is evaluated to determine what to put on the secondary board for the player as the player launches missiles
                    if (b){
                        secondary[row][column] = ANSI_YELLOW + "*" + ANSI_RESET;
                    }
                    else if (!b){
                        secondary[row][column] = ANSI_GREEN + "M" + ANSI_RESET;
                    }
                    // bm.error is the boolean to indicate whether or not a coordinate has been launched at or not
                    if (bm.error == true){
                        break;
                    }

                    // Declarations are printed once and then no longer printed again if all parts of a specific ship have been sunk
                    if (bm.declare(computer, cruiser_data)){
                        if (printed[0] == false){
                            System.out.println(ANSI_GREEN + "-------------------\nYOU SUNK MY CRUISER!\n-------------------" + ANSI_RESET);
                            printed[0] = true;
                        }
                        cruiser_sunk = true;
                    }
                    if (bm.declare(computer, battleship_data)){
                        if (printed[1] == false){
                            System.out.println(ANSI_GREEN + "-------------------\nYOU SUNK MY BATTLESHIP!\n-------------------" + ANSI_RESET);
                            printed[1] = true;
                        }
                        battleship_sunk = true;
                    }
                    if (bm.declare(computer, carrier_data)){
                        if (printed[2] == false){
                            System.out.println(ANSI_GREEN + "-------------------\nYOU SUNK MY CARRIER!\n-------------------" + ANSI_RESET);
                            printed[2] = true;
                        }
                        carrier_sunk = true;
                    }
                    if (bm.declare(computer, submarine_data)){
                        if (printed[3] == false){
                            System.out.println(ANSI_GREEN + "-------------------\nYOU SUNK MY SUBMARINE!\n-------------------" + ANSI_RESET);
                            printed[3] = true;
                        }
                        submarine_sunk = true;
                    }
                    if (bm.declare(computer, destroyer_data)){
                        if (printed[4] == false){
                            System.out.println(ANSI_GREEN+ "-------------------\nYOU SUNK MY DESTROYER!\n-------------------" +ANSI_RESET);
                            printed[4] = true;
                        }
                        destroyer_sunk = true;
                    }

                    bm.computerlaunch(player);

                    // Both the player and secondary boards are printed to denote the launches of the computer and player
                    System.out.println("LIVE PLAYER BOARD");
                    for (int i = 0; i < player.length; i++){
                        System.out.println(Arrays.asList(player[i]));
                    }
                    System.out.println("SECONDARY PLAYER BOARD");
                    for (int j = 0; j < secondary.length; j++){
                        System.out.println(Arrays.asList(secondary[j]));
                    }

                    // Declarations are printed once and then no longer printed again if all parts of a specific ship have been sunk
                    if (bm.declare(player, player_cruiser_data)){
                        if (printed[5] == false){
                            System.out.println(ANSI_RED + "-------------------\nCOMPUTER SUNK YOUR CRUISER!\n-------------------" + ANSI_RESET);
                            printed[5] = true;
                        }
                        player_cruiser_sunk = true;
                    }
                    if (bm.declare(player, player_battleship_data)){
                        if (printed[6] == false){
                            System.out.println(ANSI_RED + "-------------------\nCOMPUTER SUNK YOUR BATTLESHIP!\n-------------------" + ANSI_RESET);
                            printed[6] = true;
                        }
                        player_battleship_sunk = true;
                    }
                    if (bm.declare(player, player_carrier_data)){
                        if (printed[7] == false){
                            System.out.println(ANSI_RED + "-------------------\nCOMPUTER SUNK YOUR CARRIER!\n-------------------" + ANSI_RESET);
                            printed[7] = true;
                        }
                        player_carrier_sunk = true;
                    }
                    if (bm.declare(player, player_submarine_data)){
                        if (printed[8] == false){
                            System.out.println(ANSI_RED + "-------------------\nCOMPUTER SUNK YOUR SUBMARINE!\n-------------------" + ANSI_RESET);
                            printed[8] = true;
                        }
                        player_submarine_sunk = true;
                    }
                    if (bm.declare(player, player_destroyer_data)){
                        if (printed[9] == false){
                            System.out.println(ANSI_RED + "-------------------\nCOMPUTER SUNK YOUR DESTROYER!\n-------------------" + ANSI_RESET);
                            printed[9] = true;
                        }
                        player_destroyer_sunk = true;
                    }
                }
                // Error handling in the event that invalid inputs were entered for the prompt
                catch (Exception e){
                    System.out.println(ANSI_RED + "---------------------------------------------------------------------------" + ANSI_RESET);
                    System.out.println(ANSI_RED + "ERROR: You may have entered invalid inputs for the coordinate launch prompt" + ANSI_RESET);
                    System.out.println(ANSI_RED + "---------------------------------------------------------------------------" + ANSI_RESET);
                }
                break;
            }
            // If all the ships were sunk by the player on the computer's end, the game is over and the player wins
            if (destroyer_sunk && battleship_sunk && cruiser_sunk && submarine_sunk && carrier_sunk){
                player_won = true;
                computer_won = false;
                over = true;
                break;
            }
            // If all the ships were sunk by the computer on the player's end, the game is over and the computer wins
            if (player_destroyer_sunk && player_battleship_sunk && player_cruiser_sunk && player_submarine_sunk && player_carrier_sunk){
                player_won = false;
                computer_won = true;
                over = true;
                break;
            }
        }
        // If the game is over, print who won
        if (over){
            if (player_won){
                System.out.println(ANSI_GREEN + "-----------" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "PLAYER WON!" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "-----------" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "GAME OVER!" + ANSI_RESET);
            }
            else if (computer_won){
                System.out.println(ANSI_RED + "-------------" + ANSI_RESET);
                System.out.println(ANSI_RED + "COMPUTER WON!" + ANSI_RESET);
                System.out.println(ANSI_RED + "-------------" + ANSI_RESET);
                System.out.println(ANSI_RED + "GAME OVER!" + ANSI_RESET);
            }
        }
    
    }
}
