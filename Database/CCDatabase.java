package Database;
public interface CCDatabase {

    public boolean createAccount (long accountNumber, String name, String address, double creditLimit, double balance);

    public boolean deleteAccount (long accountNumber);

    public boolean adjustCreditLimit (long accountNumber, double newLimit);

    public String getAccount (long accountNumber);

    public boolean makePurchase (long accountNumber, double price) throws Exception;
}
