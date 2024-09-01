/* Name: Diego Gonzalez Reyes
 * email: dgonz348@mtroyal.ca
 * Student ID: 201724348
 * Course Name: Information Structure
 * Course: 2631-002
 * Instructor: Mariam Elhussein
 * Assignment 3
 * 
 * DETAILS: 
 * This program test the run time of two database. It uses different data sets of different 
 * sizes to test the efficiency of the databases. The databases are implemented in two ways:
 * 	- Sorted Array
 * 	- Hash Table
 * 
 * SOURCES:	
 * Most of the Implementations of algortihms came from the slides provided from classes, 
 * and little details came from my previous knowledge to the lenguage. Some extra stuff came from 
 * asking ChatGPT rather than googling stuff, like what is the Instant, or Duration class. 
 * The details to what I asked ChatGPT are described throughout the source code. 
 * Some of the errors were debugged with the help of ChatGPT.
 */

package Database;

import java.time.Duration;
import java.time.Instant;

/***MAIN PROGRAM***/
public class Main {
	public static void main(String[] args){
	    Instant start, end;
	    Duration duration;
	    
	    /***DATABASES***/
	    ArrayDB arrayDatabase = new ArrayDB(200000);
	    HashTableDB hashDatabase = new HashTableDB(200000);
	    
	    // start the timer of the tests
	    start = Instant.now();
	    
	    //testing with size 20
	    String arrayTest20 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-20.txt");
	    String hashTest20 = DatabaseReader.runTest(hashDatabase,"src/data/ops-20.txt");
	    
	    System.out.println("Array Run time test for 20 accounts: " + arrayTest20);
	    System.out.println("Hash Run time test for 20 accounts: " + hashTest20);
	    System.out.println("\n");
	    
	    //testing with size 200
	    String arrayTest200 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-200.txt");
	    String hashTest200 = DatabaseReader.runTest(hashDatabase,"src/data/ops-200.txt");
	    
	    System.out.println("Array Run time test for 200 accounts: " + arrayTest200);
	    System.out.println("Hash Run time test for 200 accounts: " + hashTest200);
	    System.out.println("\n");

	    //testing with size 2000
	    String arrayTest2000 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-2000.txt");
	    String hashTest2000 = DatabaseReader.runTest(hashDatabase,"src/data/ops-2000.txt");
	    
	    System.out.println("Array Run time test for 2000 accounts: " + arrayTest2000);
	    System.out.println("Hash Run time test for 2000 accounts: " + hashTest2000);
	    System.out.println("\n");
	    
	    //testing with size 20000
	    String arrayTest20000 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-20000.txt");
	    String hashTest20000 = DatabaseReader.runTest(hashDatabase,"src/data/ops-20000.txt");
	    
	    System.out.println("Array Run time test for 20000 accounts: " + arrayTest20000);
	    System.out.println("Hash Run time test for 20000 accounts: " + hashTest20000);
	    System.out.println("\n");
	    
	    //testing with size 200000
	    String arrayTest200000 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-200000.txt");
	    String hashTest200000 = DatabaseReader.runTest(hashDatabase,"src/data/ops-200000.txt");
	    
	    System.out.println("Array Run time test for 200000 accounts: " + arrayTest200000);
	    System.out.println("Hash Run time test for 200000 accounts: " + hashTest200000);
	    System.out.println("\n");
	    
	    //testing with size 200000
	    String arrayTest2000000 = DatabaseReader.runTest(arrayDatabase, "src/data/ops-2000000.txt");
	    String hashTest2000000 = DatabaseReader.runTest(hashDatabase,"src/data/ops-2000000.txt");
	    
	    System.out.println("Array Run time test for 2000000 accounts: " + arrayTest2000000);
	    System.out.println("Hash Run time test for 2000000 accounts: " + hashTest2000000);
	    System.out.println("\n");
	    
	    // end the timer of the test
	    end = Instant.now();         
	 
	    // Total Time of the run test
	    duration = Duration.between(start, end);
	
	    // Extract individual components
	    long hours = duration.toHours();
	    long minutes = duration.toMinutesPart();
	    long seconds = duration.toSecondsPart();
	    long milliseconds = duration.toMillisPart();
	
	    // Print the formatted duration
	    System.out.printf("Duration: %02d:%02d:%02d.%03d\n", hours, minutes, seconds, milliseconds);   
	}
}
