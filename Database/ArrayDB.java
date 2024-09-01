/* ArrayDB
 * Implements CCDatabase
 * 
 * For the interface, ArrayDb uses CCDatabase.
 * The ArrayDB is implemented by using a Sorted Array. 
 * The Database also implements a binary search to find the account.
 * 
 * NOTE: there is a main function, but does nothing as Eclipse wouldn't stop complaining about not having a main in this file
 * The Main program runs in the DatabaseReader.java File
 */
package Database;

public class ArrayDB implements CCDatabase{
    
	private Account[] accounts; // Storage of the Elements in the Database
    private int accountsAdded;  // Tracking the amount of accounts added

    /***Constructor***/
    public ArrayDB(int capacity){
        accounts = new Account[capacity];
        accountsAdded = 0;
        
        // Filling the spaces up in the array
        // This is the way a null account is represent
        for (int i = 0; i < capacity; i++)
            accounts[i] = new Account(0, "", "", 0, 0);
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
     * The method takes the information of an account. It then looks for match in the Database.
     * If an account with the same number is found, it does not create a new account. Otherwise it
     * creates the account and adds it to the Database sorted. The method uses getAccountIndex to find the match,
     * and addAccount to create the new account.
     * 
     * Return (Boolean):
     * If an account is not found with the same number it returns true indicating it was successfully created.
     * If an account is found with the same number it returns false indicating it was unsuccessful.
     */
    public boolean createAccount (long accountNumber, String name, String address, double creditLimit, double balance){
        if (getAccountIndex(accountNumber, 0, accounts.length-1) == -1){
            addAccount(new Account(accountNumber, name, address, creditLimit, balance));
            return true;
        }
        return false;       
    }
    
	
	/**
	 * Method: Delete Account (Boolean)
	 * 
	 * Parameters:
	 * (long) accountNumber
	 * 
	 * Details: 
	 * The method takes the Account number. It then looks for match in the Database.
	 * If the account is found, it proceeds to delete the account from the database.
	 * The method uses getAccountIndex to look for the account and removeAccount to delete the account. 
	 * 
	 * Return (Boolean):
	 * If an account is not found with the same number it returns false indicating it was unsuccessful.
	 * If an account is found with the same number it returns true indicating it was successfully deleted.
	 */
    public boolean deleteAccount (long accountNumber){
        int index = getAccountIndex(accountNumber, 0, accounts.length-1);
        
        if (index == -1)
            return false;
        
        removeAccount(index);
        return true;
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
	 * The method uses getAccountIndex to look for the account 
	 * 
	 * Return (Boolean):
	 * If an account is not found with the same number it returns false indicating it was unsuccessful.
	 * If the current limit is the same as the new limit it returns false indicating it was unsuccessful.
	 * If an account is found and the limit changed it returns true indicating it was successfully adjusted.
	 */
    public boolean adjustCreditLimit (long accountNumber, double newLimit){
        int index = getAccountIndex(accountNumber, 0, accounts.length-1);
        if (index == -1)
            return false;
        if (accounts[index].getCreditLimit() == newLimit)
            return false;
        else{
            accounts[index].setCreditLimit(newLimit);
            return true;
        }
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
	 * The method uses getAccountIndex to look for the account 
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
    public String getAccount(long accountNumber){
        String account,
               name, 
               address, 
               creditLimit,
               balance;
        
        int index = getAccountIndex(accountNumber, 0, accounts.length-1);
        
        if (index != -1){
            account = String.valueOf(accounts[index].getAccount()) ;
            name = accounts[index].getName();
            address = accounts[index].getAddress();
            creditLimit = String.valueOf(accounts[index].getCreditLimit());
            balance = String.valueOf(accounts[index].getBalance());

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
	 * then updates the current balance to the new balance. The method uses getAccountIndex to look for the account
	 * 
	 * Return (Boolean):
	 * If an account is not found or does not have sufficient funds it returns false indicating it was unsuccessful.
	 * If an account is found it and has sufficient funds returns true indicating it was successful.
	 */
     public boolean makePurchase(long accountNumber, double price){
        int index = getAccountIndex(accountNumber, 0, accountsAdded-1);
        
        if (index == -1)
            return false;

            double newBal = accounts[index].getBalance() + price;
        if (newBal > accounts[index].getCreditLimit())
            return false;

        accounts[index].setBalance(newBal); 
        return true;
     }


/******************************************************************************************************************************/
     /***HELPER METHODS***/
     
	/**
	 * Method: Add Account
	 * 
	 * Parameters:
	 * (Account) account
	 * 
	 * Details: 
	 * The method adds the account to the array in a sorted way. 
	 * The sort is from smaller to bigger, left to right. 
	 * 
	 */
    private void addAccount(Account account){
        long accountNumber = account.getAccount();

        if (accountsAdded < accounts.length || accountNumber > accounts[accountsAdded-1].getAccount()){
            if(accountsAdded < accounts.length)
                accountsAdded++;
        
            int i = accountsAdded - 1; //increments accounts added and then works as index of the array
            
            //While i > 0 and the previous account is smaller than the account to be added 
            while (i > 0 && accounts[i-1].getAccount() > accountNumber) {
                //Shift everything to the right [i] = [i-1]
            	accounts[i] = accounts[i-1];
                i--;
            }
            accounts[i] = account; //finally at the current index insert the new account.
        }
     }

	/**
	 * Method: Add Account
	 * 
	 * Parameters:
	 * (int) Account Index 
	 * 
	 * Details: 
	 * The method remove the account at the index indicated, 
	 * keeping the array sorted. 
	 */
    private void removeAccount(int accountIndex){
        for (int i = accountIndex; i < accountsAdded-1; i++)
            accounts[i] = accounts[i+1];
        //at the last position of the account added 
        //all the values are reseted 
        accounts[accountsAdded-1].setAccount();
        accounts[accountsAdded-1].setName();
        accounts[accountsAdded-1].setAddress("");
        accounts[accountsAdded-1].setCreditLimit(0);
        accounts[accountsAdded-1].setBalance(0);;
        
        //decrease the account added
        accountsAdded--;
    }

	/**
	 * Method: Get Account Index (int)
	 * 
	 * Parameters:
	 * (long) Account Number 
	 * (int) low 
	 * (int) high
	 * 
	 * Details: 
	 * The method look for the account indicated.
	 * The method implements a Binary Search Algorithm.	 
	 *  
	 * Return (int):
	 * if the account found it returns the index.
	 * if not found, it returns -1.
	 */
    private int getAccountIndex(long accountNumber, int low, int high){
        
    	if (low > high)//Indicates that the search went through the array and didn't find the account 
            return -1;
        else{
            int mid = (low + high)/2;
            if (accountNumber == accounts[mid].getAccount())//Found the account
                return mid;
            else if (accountNumber < accounts[mid].getAccount())
                return getAccountIndex(accountNumber, low, mid - 1); //Recursively go through
            else
                return getAccountIndex(accountNumber, mid + 1, high);//Recursively go through
        }
    }
    /***main***/
    /*Not used, it just there because eclipse was complaining about not having a main function*/
    public static void main(String[] args){}
}
