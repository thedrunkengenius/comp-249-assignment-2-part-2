package comp249assignment2part2;

import java.io.*;
import java.util.Scanner;

public class BookInventory2 implements Serializable {
    private static Book[] bookArray;
    
    public BookInventory2() {
        bookArray = null;
    }
    public static Book[] getBookArray() {
        return bookArray;
    }
    public int binaryBookSearch(Book[] bookArray, int startIndex, int endIndex, long isbnNumber) {
        int midPoint;
        
        //Stopping case. If you've reached this point, the key value was not found
        if (endIndex < startIndex) {
            System.out.println("The ISBN you are looking for is not in this document.");
            return -1;
        }
        midPoint = (startIndex + endIndex)/2;
        
        if (bookArray[midPoint].getIsbn() > isbnNumber) {
            //key is in lower half
            return binaryBookSearch(bookArray, startIndex, midPoint-1, isbnNumber);
        }
        else if (bookArray[midPoint].getIsbn() < isbnNumber) {
            //key is in upper half
            return binaryBookSearch(bookArray, midPoint+1, endIndex, isbnNumber);
        }
        else
            //key has been found!
            System.out.println("ISBN " + isbnNumber + " was found at position " + (midPoint+1));
            return midPoint;
    } 
    public int sequentialBookSearch(Book[] bookArray, int startIndex, int endIndex, long isbnNumber) {
        long isbnCheck;
        if (startIndex > bookArray.length || startIndex < 0 || endIndex > bookArray.length || endIndex < 0 || startIndex > endIndex) {
            System.out.println("You are not playing by the rules, and hence a match was not found");
            return -1;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            if (isbnNumber == bookArray[i].getIsbn()) {
                System.out.println("A match for your ISBN " + isbnNumber + " was found at position " + (i+1));
                return i;
            }
        }
        System.out.println("A match for your ISBN was not found.");
        return -1;
    }
    public Book[] stringToBook(String inputName) throws FileNotFoundException, IOException {
        int bookCount;
        
        //Call private helper method to get number of books or lines in text file
        bookCount = getBookQuantity(inputName);
        //If count is 0 or less than 2, close program because no work is needed (always no duplicates)
        if (bookCount == 0) {
            System.out.println("This file is empty. No operations needed.");
            System.exit(0);
        }
        if (bookCount < 2) {
            System.out.println("This file contains no duplicates. No operations needed.");
            System.exit(0);
        }
        try {
        Scanner inputStream = new Scanner(new FileInputStream(inputName));
        //Set bookArray attribute to size bookCount
        bookArray = new Book[bookCount];
        for (int i = 0; i < bookArray.length; i++) {
            Book myBook = new Book();
            myBook.setIsbn(inputStream.nextLong());
            myBook.setTitle(inputStream.next());
            myBook.setIssueYear(inputStream.nextInt());
            myBook.setAuthorName(inputStream.next());
            myBook.setPrice(inputStream.nextDouble());
            myBook.setNumberOfPages(inputStream.nextInt());
            bookArray[i] = myBook;
        } 
        inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(inputName + " not found.");
            System.exit(0);
        }
        return bookArray;
    }
    public void addRecords(String outputFile) {
        boolean done = false;
        String newRecord;
        PrintWriter pw = null;
        Scanner kb = new Scanner(System.in);
        System.out.println("Adding records. Enter \"E\" at anytime to end process.");
            //while (!done) {
        try {
            while (!done) {
            pw = new PrintWriter(new FileOutputStream(outputFile, true));
            System.out.print("Enter new record: ");
            newRecord = kb.nextLine();
            if (newRecord.equalsIgnoreCase("e")) {
                done = true;
            }
            else {
            pw.print("\n");
            pw.print(newRecord);
            }
        }
            }
        catch (FileNotFoundException e1) {
            System.out.println("Either disk is too full or we have a bigger problem.");
            System.out.println("You can enter \"E\" at anytime to end the process.");
        }
    //} // while(!done) ends here
        finally {
            if (pw != null) {
                pw.close();
            }
        }
        displayFileContents(outputFile);
    }
    //Determines number of books in inputFile
    private int getBookQuantity(String inputName) throws FileNotFoundException, IOException {
        int bookCount = 0;
        Scanner inputStream;
        try {
        inputStream = new Scanner(new FileInputStream(inputName));
      
        while (inputStream.hasNextInt()) {
            bookCount++;
            inputStream.nextLine();
            }
        inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }
        return bookCount;
    }
    
    public void displayFileContents(String inputFile) {
        String nextLine;
        boolean done = false;
        BufferedReader inputStream = null;
        try {
        inputStream = new BufferedReader(new FileReader(inputFile));
        System.out.println("");
        System.out.println("Here are the contents of file " + inputFile + " after addition of new records");
        System.out.println("=======================================================================");
        while (!done) {
            nextLine = inputStream.readLine();
            if (nextLine == null) {
                done = true;
            }
            else {
            System.out.println(nextLine);
            }
        }
        }
        catch (FileNotFoundException e1) {
            System.out.println("File does not exist. This is a problem.");
            System.exit(0);
        }
        catch (IOException e2) {
            System.out.println("This could be a problem.");
            System.exit(0);
        }
        finally {
        if (inputStream != null) {
        try {
        inputStream.close();
        }
        catch (IOException e) {
            System.exit(0);
        }
        }
        }
    }
    public void writeBooksToBinaryFile(Book[] bookArray, String fileName) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            for (int i = 0; i < bookArray.length; i++) {
                System.out.println("Writing book " + (bookArray[i].getTitle()) + " to file " + fileName + ".");
                oos.writeObject(bookArray[i]);
            }
            
        }
        catch (FileNotFoundException e1) {
            System.out.println("Your disk must be full, because I can't create a new file. Make some space and try again!");
            System.exit(0);
        }
        catch (IOException e2) {
            System.out.println("Some hiccup happened while trying to establish a connection, and I'm really sorry about that.");
            System.out.println("Program will now terminate.");
            System.exit(0);
        }
        finally {
            try {
            if (oos != null) {
                oos.close();
            }
            }
            catch (IOException e3) {
                    System.exit(0);
                    }
        }
    }
}
