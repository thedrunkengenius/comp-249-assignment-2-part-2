package comp249assignment2part2;

import java.util.Arrays;
import java.util.*;
import java.io.*;

public class Comp249assignment2part2 {

    public static void main(String[] args) {
        String inventoryFile = "Sorted_Book_Info.txt";
        long searchISBN;
        int indexOfSearchResult;
        boolean done = false;
        Scanner kb = new Scanner(System.in);
        BookInventory2 myBookInventory = new BookInventory2();
        myBookInventory.addRecords(inventoryFile);
        
        //Try taking inventory file and converting lines to Book objects
        try {
        myBookInventory.stringToBook(inventoryFile);
        }
        catch (IOException e2) {
            System.out.println("An error occured while trying to read your file. Program will now terminate.");
            System.exit(0);
        }
        while (!done) {
        try {
        System.out.print("\n");
        System.out.print("Please enter an ISBN to search in the Book Inventory: ");
        //Program takes the ISBN entered by the user, and searches for it in the array provided
        searchISBN = kb.nextLong();
        myBookInventory.binaryBookSearch(BookInventory2.getBookArray(), 1, 6, searchISBN);
        //If no exceptions are thrown, loop terminates
        done = true;
        }
        //If user enters invalid input, InputMismatchException is thrown
        catch (InputMismatchException e1) {
            kb.nextLine();
            System.out.println("That's not a number! Please try again.");
        }
        }
        done = false;
        while (!done) {
        try {
        System.out.print("\n");
        System.out.print("Please enter an ISBN to search in the Book Inventory sequentially: ");
        //Sequential search is performed on the provided array, using the user inputted ISBN
        searchISBN = kb.nextLong();
        myBookInventory.sequentialBookSearch(BookInventory2.getBookArray(), 0, (BookInventory2.getBookArray().length-1), searchISBN);
        done = true;
        }
        catch (InputMismatchException e1) {
            kb.nextLine();
            System.out.println("That's not a number! Please try again.");
        }
        }
        System.out.println("");
        myBookInventory.writeBooksToBinaryFile(BookInventory2.getBookArray(), "Book_Inventory.dat");
        System.out.println("");
        System.out.println("Program done. Thanks for trusting us with your inventory.");
        
    }

}
