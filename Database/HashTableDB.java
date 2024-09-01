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

public class HashTableDB implements CCDatabase{
    private class Entry{
        long key;
        Account account;
        
        /* Constructor */
        private Entry(Long accountNumber, Account account){
            this.key = accountNumber;
            this.account = account;
        }
        private long getKey(){return this.key;}
        private Account getValue(){return this.account;}
    }

    private Entry[] table;
    private int tableSize;
    private int accountsAdded;

    private final Entry DEFUNCT = new Entry(0L, null);
    
    /* Constructor */
    public HashTableDB(int capacity){
        accountsAdded = 0;
    	tableSize = 9999 % capacity; //Max range of values
        table = new Entry[tableSize]; 
        
        //To fill the table, it represents a null position.
        //this is because the program throws an error if it finds a actual null value.
        for (int i = 0; i < tableSize; i++) {
        	table[i] = new Entry(0L,new Account(0,"","",0,0));
        }	
    }
    
    /**
     * Method: Create Account (Boolean)
     * 
     * Parameters:
     * (long) accountNumber
     * (String) name 
     * (String) address 
     * (double) creditLimit 
     * (double) balance
     * 
     * Details: 
     * The method takes the information of an account. It then hash the account number to find a match in the Database.
     * If an account with the same number is found, it does not create a new account. Otherwise it
     * creates the account and adds it to the Database. The method uses hashFunction to look for the account.
     * 
     * Return (Boolean):
     * If an account is not found with the same number it returns true indicating it was successfully created.
     * If an account is found with the same number it returns false indicating it was unsuccessful.
     */
    public boolean createAccount(long accountNumber, String name, String address, double creditLimit, double balance){
        int hash = hashFunction(accountNumber);
        int initialHash = hash;

        if (isFull())
            throw new IllegalStateException("Hash table is full");

        do {
            if (table[hash] == null || table[hash] == DEFUNCT){
                Account newAccount = new Account(accountNumber, name, address, creditLimit, balance);
                table[hash] = new Entry(accountNumber, newAccount);
                accountsAdded++;
                return true;
            }
            hash = (hash + 1) % tableSize;
        } while (hash != initialHash);

        return false;
    }

    /**
	 * Method: Delete Account (Boolean)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * 
	 * Details: 
	 * The method takes the Account number and hash it. It then looks for the match in the Database.
	 * If the account is found, it proceeds to delete by assigning DEFUNC to the account from the database.
	 * The method uses hashFunction to look for the account. 
	 * 
	 * Return (Boolean):
	 * If an account is not found with the same number it returns false indicating it was unsuccessful.
	 * If an account is found with the same number it returns true indicating it was successfully deleted.
	 */
    public boolean deleteAccount(long accountNumber){
        if (!isEmpty()){
            int hash = hashFunction(accountNumber),
                initialHash = hash; //ChatGPT mentioned it wouldn't work properly without this variable
            do {
                if (table[hash] != null && table[hash].getKey() == accountNumber){
                    table[hash] = DEFUNCT;
                    accountsAdded--;
                    return true;
                } 
                hash = (hash + 1) % accountsAdded;
            }
            while (hash != initialHash && table[hash] != null);
        }
        return false;
    }

	/**
	 * Method: Adjust Credit Limit (Boolean)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * (double) newLimit
	 * 
	 * Details: 
	 * The method takes the Account number. It then looks for match in the Database.
	 * If the account is found, it adjust the current credit limit to the new limit(newLimit) of the account.
	 * The method uses hashFunction to look for the account.
	 * 
	 * Return (Boolean):
	 * If an account is not found with the same number it returns false indicating it was unsuccessful.
	 * If the current limit is the same as the new limit it returns false indicating it was unsuccessful.
	 * If an account is found and the limit changed it returns true indicating it was successfully adjusted.
	 */
    public boolean adjustCreditLimit (long accountNumber, double newLimit){ 
        int hash = hashFunction(accountNumber);
        
        if ((table[hash] != null || table[hash] != DEFUNCT) && newLimit != table[hash].account.getCreditLimit()){
            table[hash].account.setCreditLimit(newLimit);
            return true;
        }
        
        return false;
    }
    
    /**
	 * Method: Get Account (String)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * 
	 * Details: 
	 * The method takes the Account number. It then looks for match in the Database.
	 * If the account is found, it gets all its details as Strings. 
	 * The method uses hashFunction to look for the account 
	 * The format of the String is as follows:
	 * 
	 * credit card number
	 * name of card holder
	 * address of card holder
	 * credit limit
     * balance 
     * 
     * each on a separate line.
	 * 
	 * Return (String):
	 * If an account is not found with the same number it returns null indicating there is no account with this match.
	 * If an account is found it returns the account information in the indicated format.
	 */
    public String getAccount (long accountNumber){ 
        int hash = hashFunction(accountNumber);
        String account,
                name, 
                address, 
                creditLimit,
                balance;

        if (table[hash] != null || table[hash] != DEFUNCT){
            account = String.valueOf(table[hash].account.getAccount()) ;
            name = table[hash].account.getName();
            address = table[hash].account.getAddress();
            creditLimit = String.valueOf(table[hash].account.getCreditLimit());
            balance = String.valueOf(table[hash].account.getBalance());

            return account + "\n" + name + "\n" + address + "\n" + creditLimit + "\n" + balance + "\n";
        }
        return null;
    } 

    /**
	 * Method: Make Purchase (Boolean)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * (double) price
	 * 
	 * Details: 
	 * The method takes the Account number. It then looks for match in the Database.
	 * If the account is found, it makes a purchase on the account if the account has sufficient credit. it
	 * then updates the current balance to the new balance. The method uses hashFunction to look for the account
	 * 
	 * Return (Boolean):
	 * If an account is not found or does not have sufficient funds it returns false indicating it was unsuccessful.
	 * If an account is found it and has sufficient funds returns true indicating it was successful.
	 */
    public boolean makePurchase (long accountNumber, double price){
        int hash = hashFunction(accountNumber);
        if (table[hash] == null || table[hash] == DEFUNCT) 
            return false;

        double newBal = table[hash].account.getBalance() + price;
        if (newBal > table[hash].account.getCreditLimit())
            return false;

        table[hash].account.setBalance(newBal); 
        return true;
    }

/******************************************************************************************************************************/
    /***HELPER METHODS***/
    
    private boolean isEmpty(){return accountsAdded == 0;}
    private boolean isFull(){return accountsAdded == tableSize;}
    private int currentAccounts(){return accountsAdded;}

    /**
	 * Method: Hash Function (int)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * 
	 * Details: 
	 * The method takes the Account number. It then hash the account number.
	 * The formula to hash the account is the following:
	 * 
	 * (17*c1 + 17*c2^2 + 17*c3^3 + 17*c4^4) % tableSize
	 * 
	 * To split the account into 4 separates number, it uses split method to do so.
	 * 
	 * Limitations: the program does not use short for values for the calculation, due to time
	 * I just changed the values to long
	 * 
	 * Return (int):
	 * the hash value of the account.
	 */
    private int hashFunction(long accountNumber){
        String[] ccNumber = split(accountNumber);
        
        //asked ChatGPT which library has the power method.
        Long c1 = Long.parseLong(ccNumber[0]) * 17
            ,c2 = Long.parseLong(ccNumber[1]) * Long.parseLong(String.valueOf((int)Math.pow(17, 2)))
            ,c3 = Long.parseLong(ccNumber[2]) * Long.parseLong(String.valueOf((int)Math.pow(17, 3)))
            ,c4 = Long.parseLong(ccNumber[3]) * Long.parseLong(String.valueOf((int)Math.pow(17, 4)));
        
        Long sum = c1 + c2 +c3 + c4; 
        return (int)(sum % tableSize);//hash
    }

    /**
	 * Method: Split (String[])
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * 
	 * Details: 
	 * it takes the account number and divided into for pieces of code.
	 * 
	 * 
	 * 
	 * Return (String[]):
	 * An array of String with the 4 chunks of number from the account
	 */
    private String[] split(long accountNumber){
        String toString = String.valueOf(accountNumber);
        String[] cc = toString.split("(?<=\\G.{4})");//asked ChatGPT how to format to 4 pieces of numbers
        return cc;
    }
  
    /***main***/
    /*Not used, it just there because eclipse was complaining about not having a main function*/
    public static void main(String[] args){}
}