/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
        try {
        myBookInventory.stringToBook(inventoryFile);
        }
        catch (IOException e2) {
            System.out.println("Oops");
            System.exit(0);
        }
        while (!done) {
        try {
        System.out.print("\n");
        System.out.print("Please enter an ISBN to search in the Book Inventory: ");
        searchISBN = kb.nextLong();
        myBookInventory.binaryBookSearch(BookInventory2.getBookArray(), 7, 6, searchISBN);
        done = true;
        }
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
        searchISBN = kb.nextLong();
        myBookInventory.sequentialBookSearch(BookInventory2.getBookArray(), 2, 6, searchISBN);
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
