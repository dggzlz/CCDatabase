/* HashTableDB
 * Implements CCDatabase
 * 
 * 
 * The ArrayDB is implemented by using a Sorted Array. 
 * The Database also implements a binary search to find the account.
 * 
 * NOTE: there is a main function, but does nothing as Eclipse wouldn't stop complaining about not having a main in this file
 * The Main program runs in the DatabaseReader.java File
 */
package Database;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class DatabaseReader {
	
	/**
	 * Method: Run Test (String)
	 * 
	 * Parameters:
	 * (CCDatabase) database
	 * (String) filename 
	 * 
	 * Details: 
	 * The method takes the database and a filename with data. it runs the test to see how efficient the data base is.
	 * 
	 * Return (String):
	 * The time of the test in seconds and milliseconds. e.g. 0.204
	 */
    public static String runTest(CCDatabase database, String filename) {
        try(Scanner opsFile = new Scanner(new File(filename))) {
        
	        Instant start = null;
	        Instant end =  null;
	
	        //while there is a line to read
	        while (opsFile.hasNextLine()) {
	            String operation = opsFile.nextLine();
	            
	            //Timing the test
	            if (operation.equals("start")) {
	                start = Instant.now();
	            } else if (operation.equals("stop")) {
	                end = Instant.now();
	            }
	
	            //This check which instruction it needs to be done
	            switch (operation) {
	                case "cre": //Instruction to create an account
	                    Long caseCreAccount = Long.parseLong(opsFile.nextLine());
	                    String caseCreName =  opsFile.nextLine(),
	                            caseCreAddress = opsFile.nextLine();
	                    Double caseCreCCLimit = Double.parseDouble(opsFile.nextLine()),
	                            caseCreBal = Double.parseDouble(opsFile.nextLine());
	                    
	                    database.createAccount(caseCreAccount, caseCreName, caseCreAddress, caseCreCCLimit, caseCreBal);
	                    break;
	               
	                case "del": //Instruction to delete an account
	                    database.deleteAccount(Long.parseLong(opsFile.nextLine())); 
	                    break;
	                
	                case "lim": //Instruction to change the limit of an account
	                    Long caseLimAccount = Long.parseLong(opsFile.nextLine());
	                    Double caseLimNewLimit = Double.parseDouble(opsFile.nextLine());
	                    
	                    database.adjustCreditLimit(caseLimAccount,caseLimNewLimit);
	                    break;
	                
	                case "pur": //Instruction to indicate a purchase in an account
	                    try {
	                        Long casePurAccount = Long.parseLong(opsFile.nextLine());
	                        Double casePurPrice = Double.parseDouble(opsFile.nextLine());
	                        database.makePurchase(casePurAccount, casePurPrice);
	                    }catch(Exception e){
	                        System.err.println("error");
	                    };
	                    break;
	            }  
	        }
	        
	        //Asked ChatGPT how to format the output
	        long seconds = Duration.between(start, end).getSeconds();
	        int milliseconds = Duration.between(start, end).getNano() / 1_000_000;
	
	        return seconds + "." + milliseconds;
        } catch(Exception e) {
            e.printStackTrace();
        };
        return null;
        }

    /***main***/
    /*Not used, it just there because eclipse was complaining about not having a main function*/
	public static void main(String[] args){}
}
