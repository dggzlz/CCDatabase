/**
 * Class: Account for CC Database
 * 
 * Fields:
 *  (long) Account Number
 *  (String) Account Name
 *  (String) Account Address
 *  (double) Account Credit Limit
 *  (double) Account Balance
 * 
 * Getter Functions:
 *  Long getAccount
 *  String getName
 *  String getAddress
 *  getCreditLimit
 *  getBalance
 *  
 * Setter Functions:
 *  void setAccount
 *  void setName
 *  void setAddress
 *  void setCreditLimit
 *  void setBalance
 * 
 */
package Database;

public class Account{
    private long accountNumber;
    private String accountName;
    private String accountAddress; 
    private double accountCreditLimit; 
    private double accountBalance;

    public Account(long number, String name, String address, double creditLimit, double balance){
        accountNumber = number;
        accountName = name;
        accountAddress = address; 
        accountCreditLimit = creditLimit; 
        accountBalance = balance;
    }

    //Getter Functions
    public Long getAccount(){return this.accountNumber;}
    public String getName(){return this.accountName;}
    public String getAddress(){return this.accountAddress;}
    public Double getCreditLimit(){return this.accountCreditLimit;}
    public Double getBalance(){return this.accountBalance;}

    //Setter Functions
    public void setAccount(){accountNumber = 0;}
    public void setName(){accountName = "";}
    public void setAddress(String newAddress){accountAddress = newAddress;}
    public void setCreditLimit(double newCreditLimit){accountCreditLimit = newCreditLimit;}
    public void setBalance(double newBal){accountBalance = newBal;}
}