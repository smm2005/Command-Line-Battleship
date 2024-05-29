import java.util.*;
import java.io.*;

public class BattleshipManager{

    // Computer ships
    ArrayList<Integer> destroyer;
    ArrayList<Integer> submarine;
    ArrayList<Integer> cruiser;
    ArrayList<Integer> battleship;
    ArrayList<Integer> carrier; 

    // Player ships
    ArrayList<Integer> p_destroyer;
    ArrayList<Integer> p_submarine;
    ArrayList<Integer> p_cruiser;
    ArrayList<Integer> p_battleship;
    ArrayList<Integer> p_carrier;

    // ANSI colour codes for coloured text
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_RED = "\u001B[31m";

    // Coordinates of attack
    ArrayList<ArrayList<Integer>> coordinates;
    ArrayList<ArrayList<Integer>> p_coordinates;

    // Boolean to keep track of whether coordinate is already launched at or not
    boolean error = false;

    public BattleshipManager(){
        // Computer ships
        destroyer = new ArrayList<Integer>();
        submarine = new ArrayList<Integer>();
        cruiser = new ArrayList<Integer>();
        battleship = new ArrayList<Integer>();
        carrier = new ArrayList<Integer>();

        // Player ships
        p_destroyer = new ArrayList<Integer>();
        p_submarine = new ArrayList<Integer>();
        p_cruiser = new ArrayList<Integer>();
        p_battleship = new ArrayList<Integer>();
        p_carrier = new ArrayList<Integer>();

        // Coordinate tracker of both the player and the computer
        coordinates = new ArrayList<ArrayList<Integer>>();
        p_coordinates = new ArrayList<ArrayList<Integer>>();

    }

    // Configure board function to construct a "rows" x "columns" matrix with open space (denoted by "O")
    public String[][] configureBoard(int rows, int columns){
        String battlefield[][] = new String[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                battlefield[i][j] = "O";
            }
        }
        return battlefield;
    }

    // Generate ships function where both the orientation and the rows/columns data are generated at random and updated onto the data ArrayList
    public ArrayList<Integer> generateShips(String mat[][], int offset, ArrayList<Integer> data){

        // Boolean value assigned as configured to denote the return value of both configure ships functions
        boolean configured;

        // Modulo 10 is applied here so that a and b are strictly between 0-9
        int a = (int) (Math.random()*10) % 10;
        int b = (int) (Math.random()*10) % 10;
        int c = (Math.abs(b + offset) % 10);

        // Modulo 10 is NOT applied here because decide is BINARY (either 0 or 1)
        int decide = Math.round((float) Math.random());

        // Decide is the variable that indicates how the ships must be oriented
        switch (decide){
            case 0:
                // If decide = 0, apply configureShipsVertical with the provided offset
                configured = configureShipsVertical(mat, offset, a, b, c);

                // If the function returns false, configure the ships again until the variable becomes true
                if (!configured){
                    while (!configured){
                        a = (int) (Math.random()*10) % 10;
                        b = (int) (Math.random()*10) % 10;
                        c = (Math.abs(b + offset) % 10);
                        configured = configureShipsVertical(mat, offset, a, b, c);
                        if (configured){
                            break;
                        }
                    }
                }
                
                /*
                 Add the vertical orientation number (0) to data
                 Add the column number (a) to data
                 Add the first row limit number (b) to data
                 Add the second row limit number (c) to data
                 */
                data.add(0);
                data.add(a);
                data.add(b);
                data.add(c);
                break;
            case 1:
                // If decide = 1, apply configureShipsVertical with the provided offset
                configured = configureShipsHorizontal(mat, offset, a, b, c);

                // If the function returns false, configure the ships again until the variable becomes true
                if (!configured){
                    while (!configured){
                        a = (int) (Math.random()*10) % 10;
                        b = (int) (Math.random()*10) % 10;
                        c = (Math.abs(b + offset) % 10);
                        configured = configureShipsHorizontal(mat, offset, a, b, c);
                        if (configured){
                            break;
                        }
                    }
                }

                /*
                 Add the vertical orientation number (1) to data
                 Add the row number (a) to data
                 Add the first column limit number (b) to data
                 Add the second column limit number (c) to data
                 */

                data.add(1);
                data.add(a);
                data.add(b);
                data.add(c);
                break;
        }
        // Return the modified data arraylist
        return data;
        
    }


    // Function to configure ships horizontally
    public boolean configureShipsHorizontal(String[][] mat, int offset, int row, int column1, int column2){
        // If the absolute difference of columns is not equal to the offset, return false
        /*
         Why?
         - Suppose "offset" means the number of slots designated for a specific ship
         - The absolute difference of columns indicates the range at which the ship parts are occupied.
         - If the offset does not match the absolute difference of columns, the ships can not be configured onto the board
         */
        if (Math.abs(column1 - column2) != offset){
            return false;
        }
        for (int i = column1; i < column2; i++){
            // If ANY part of a ship occupies a coordinate, the entire ship is unable to occupy the indicated positions so configuration would be false
            if (mat[row][i].contains("S")){
                return false;
            }
            else{
                mat[row][i] = ANSI_BLUE + "S" + ANSI_RESET;
            }
        }
        return true;
    }

    // Function to configure ships vertically with nearly the same logic
    public boolean configureShipsVertical(String[][] mat, int offset, int column, int row1, int row2){
        if (Math.abs(row1 - row2) != offset){
            return false;
        }
        for (int i = row1; i < row2; i++){
            if (mat[i][column].contains("S")){
                return false;
            }
            else{
                mat[i][column] = ANSI_BLUE + "S" + ANSI_RESET;
            }
        }
        return true;
    }

    // Ship counter function to count the occurence of ships in the board
    public int shipsCount(String[][] mat){
        int result = 0;
        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[i].length; j++){
                if (mat[i][j].contains("S")){
                    result += 1;
                }
            }
        }
        return result;
    }

    // User launch function
    public boolean userlaunch(String[][] mat, int row, int column){
        // Initialize a coordinate arraylist
        ArrayList<Integer> p_coordinate = new ArrayList<Integer>();
        // Add the row and column to the player coordinate list
        p_coordinate.add(row);
        p_coordinate.add(column);
        // Initialize a boolean b as a return boolean for userlaunch
        boolean b = false;
        if (p_coordinates.contains(p_coordinate)){
            System.out.println(ANSI_RED + "--------------------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_RED + "ERROR: You already launched a missile at this coordinate" + ANSI_RESET);
            System.out.println(ANSI_RED + "--------------------------------------------------------" + ANSI_RESET);
            // Error is set to true since the coordinate exists in the player coordinates
            error = true;
        }
        else{
            error = false;
            if (mat[Math.abs(row % 10)][Math.abs(column % 10)].contains("S")){
                mat[Math.abs(row % 10)][Math.abs(column % 10)] = ANSI_YELLOW + "*" + ANSI_RESET;
                p_coordinates.add(p_coordinate);
                b = true;
            }
            else{
                mat[Math.abs(row % 10)][Math.abs(column % 10)] = ANSI_GREEN + "M" + ANSI_RESET;
                p_coordinates.add(p_coordinate);
                b = false;
            }
        }
        return b;
    }

    // Function for computer to launch missile at player board coordinates
    public boolean computerlaunch(String[][] mat){
        ArrayList<Integer> coordinate = new ArrayList<Integer>();
        int r = (int) (Math.random()*10) % 10;
        int c = (int) (Math.random()*10) % 10;
        coordinate.add(r);
        coordinate.add(c);
        while ((coordinates.contains(coordinate))){
            coordinate.clear();
            r = (int) (Math.random()*10) % 10;
            c = (int) (Math.random()*10) % 10;
            coordinate.add(r);
            coordinate.add(c);
        }
        coordinates.add(coordinate);
        System.out.println("-------------------------------------");
        System.out.println("Computer launches missile at " + Integer.toString(r) + " " + Integer.toString(c));
        System.out.println("-------------------------------------");
        if (mat[r][c].contains("S")){
            mat[r][c] = ANSI_YELLOW + "*" + ANSI_RESET;
            return true;
        }
        else{
            mat[r][c] = ANSI_GREEN + "M" + ANSI_RESET;
            return false;
        }
    }

    // The declare function compares whether or not all parts of a specific ship are hit
    public boolean declare(String[][] mat, ArrayList<Integer> identifier){
        int decide = identifier.get(0);
        int a = identifier.get(1);
        int b = identifier.get(2);
        int c = identifier.get(3);
        // Record to store values for a ship being hit
        String record = "";
        switch (decide){
            case 0:
                for (int i = b; i < c; i++){
                    if (mat[i][a].contains("*")){
                        record = record + mat[i][a];
                    }
                    // Math.abs(c - b) is multiplied by 10 because one instance of "*" contains the ANSI_YELLOW and ANSI_RESET codes
                    // Math.abs(c - b) should indicate the range of slots in the board that a ship occupies
                    if (record.length() == Math.abs(c - b) * 10){
                        return true;
                    }
                }
                break;
            case 1:
                for (int j = b; j < c; j++){
                    if (mat[a][j].contains("*")){
                        record = record + mat[a][j];
                    }
                    if (record.length() == Math.abs(c - b) * 10){
                        return true;
                    }
                }
                break;
        }
        // Since the record is not long enough for a declaration to be made, the value "false" should be returned.
        return false;
    }
}