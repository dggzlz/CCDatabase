# Credit Card Database Management System

This project implements a credit card database management system with the following main components:

- **Account Class**: Represents a credit card account with basic details such as account number, name, address, credit limit, and balance.
- **ArrayDB Class**: Implements the database using a sorted array and binary search.
- **HashTableDB Class**: Implements the database using a hash table with linear probing for collision resolution.
- **CCDatabase Interface**: Defines the standard operations that must be supported by the database classes.
- **DatabaseReader Class**: A utility class for testing database operations from an input file.

## Classes

### 1. Account

**Package**: `Database`

**Description**: This class represents an account with the following fields and methods.

- **Fields**:
  - `accountNumber` (long): The unique identifier for the account.
  - `accountName` (String): The name associated with the account.
  - `accountAddress` (String): The address associated with the account.
  - `accountCreditLimit` (double): The credit limit of the account.
  - `accountBalance` (double): The current balance of the account.

- **Methods**:
  - **Getters**: `getAccount()`, `getName()`, `getAddress()`, `getCreditLimit()`, `getBalance()`
  - **Setters**: `setAccount()`, `setName()`, `setAddress()`, `setCreditLimit()`, `setBalance()`

### 2. ArrayDB

**Package**: `Database`

**Implements**: `CCDatabase`

**Description**: This class manages the database using a sorted array and binary search. It provides methods for creating, deleting, and managing accounts.

- **Fields**:
  - `accounts` (Account[]): Array storing the accounts.
  - `accountsAdded` (int): Tracks the number of accounts added.

- **Methods**:
  - `createAccount()`: Creates a new account if it does not already exist.
  - `deleteAccount()`: Deletes an account by its number.
  - `adjustCreditLimit()`: Adjusts the credit limit of an account.
  - `getAccount()`: Retrieves account details.
  - `makePurchase()`: Processes a purchase on an account if funds are sufficient.
  
- **Helper Methods**:
  - `addAccount()`: Adds a new account in a sorted order.
  - `removeAccount()`: Removes an account and maintains sorting.
  - `getAccountIndex()`: Finds the index of an account using binary search.

### 3. HashTableDB

**Package**: `Database`

**Implements**: `CCDatabase`

**Description**: This class implements the database using a hash table. It uses linear probing for collision resolution and stores accounts as entries with key-value pairs.

- **Fields**:
  - `table` (Entry[]): Array representing the hash table.
  - `tableSize` (int): The size of the hash table.
  - `accountsAdded` (int): Tracks the number of accounts added.
  - `DEFUNCT`: A marker for deleted entries.

- **Methods**:
  - `createAccount()`: Creates a new account if it does not already exist.
  - `deleteAccount()`: Deletes an account using its hashed index.
  - `adjustCreditLimit()`: Adjusts the credit limit of an account.
  - `getAccount()`: Retrieves account details.
  
### 4. CCDatabase Interface

**Package**: `Database`

**Description**: This interface defines the methods that must be implemented by any class that manages the credit card database.

- **Methods**:
  - `createAccount()`
  - `deleteAccount()`
  - `adjustCreditLimit()`
  - `getAccount()`
  - `makePurchase()`

### 5. DatabaseReader

**Package**: `Database`

**Description**: This class provides a testing utility for the database operations by reading from a file with commands to test various functionalities.

- **Methods**:
  - `runTest()`: Executes database operations based on instructions read from a file and measures the time taken.

## Getting Started

1. Clone the repository.
2. Compile the classes in the `Database` package.
3. Use the `DatabaseReader` class to test the database functionalities.

## Note

There are main functions included in some classes for compatibility purposes but are not used in the main functionality. The main program runs in the `DatabaseReader.java` file.

## Author

- Name: Diego Gonzalez Reyes
- Email: dgonz348@mtroyal.ca
- Course: COMP 2631 - 002
- Instructor: Mariam Elhussein

## License

This project is for educational purposes under the supervision of the instructor Mariam Elhussein as part of COMP 2631 at Mount Royal University.

