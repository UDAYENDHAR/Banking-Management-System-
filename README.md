# 🏦 Banking Management System

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![OOP](https://img.shields.io/badge/Paradigm-OOP-green.svg)]()

A comprehensive **Banking Management System** built with Java that demonstrates strong Object-Oriented Programming principles and robust backend logic. This console-based application simulates real-world banking operations with secure authentication and transaction management.



---

## 📋 Table of Contents

- [Features](#-features)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [System Architecture](#-system-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [Class Structure](#-class-structure)
- [Screenshots](#-screenshots)
- [Security Features](#-security-features)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### Core Banking Operations
- ✅ **Account Creation** - Create Savings or Current accounts with unique account numbers
- ✅ **Secure Login** - Password-based authentication with SHA-256 hashing
- ✅ **Deposit Money** - Add funds to your account with validation
- ✅ **Withdraw Money** - Withdraw funds with balance checks
- ✅ **Balance Inquiry** - Check current account balance
- ✅ **Mini Statement** - View last 5 transactions
- ✅ **Transaction History** - Complete transaction log with timestamps
- ✅ **Multiple Account Types** - Savings accounts with interest, Current accounts with overdraft

### Advanced Features
- 🔒 **Password Hashing** - SHA-256 encryption for secure password storage
- 📊 **Transaction Tracking** - Detailed logs with type, amount, balance, and timestamp
- 💰 **Interest Calculation** - Automatic interest application for savings accounts
- 🏦 **Overdraft Protection** - Overdraft limit for current accounts
- ⚠️ **Input Validation** - Comprehensive error handling and validation
- 📈 **Real-time Balance Updates** - Instant balance updates after each transaction

---

## 🎯 OOP Concepts Demonstrated

This project showcases fundamental and advanced OOP principles:

### 1. **Encapsulation**
```java
private String accountNumber;
private String hashedPassword;
protected double balance;
```
- Private fields with controlled access through public methods
- Data hiding and protection of sensitive information

### 2. **Inheritance**
```java
abstract class Account { }
class SavingsAccount extends Account { }
class CurrentAccount extends Account { }
```
- Abstract base class with concrete implementations
- Code reusability through parent-child relationships

### 3. **Polymorphism**
```java
@Override
public String getAccountType() { }

@Override
public boolean withdraw(double amount) { }
```
- Method overriding for specialized behavior
- Runtime polymorphism through abstract methods

### 4. **Abstraction**
```java
public abstract String getAccountType();
```
- Abstract methods defining common interface
- Hiding implementation details from users

### 5. **Composition**
```java
class Bank {
    private Map<String, Account> accounts;
}
class Account {
    protected List<Transaction> transactions;
}
```
- "Has-a" relationships between objects
- Complex objects built from simpler components

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────┐
│     BankingManagementSystem (Main)      │
│         - User Interface Layer          │
└──────────────────┬──────────────────────┘
                   │
                   ▼
         ┌─────────────────┐
         │      Bank       │
         │  - Account Map  │
         └────────┬────────┘
                  │
         ┌────────┴────────┐
         │                 │
    ┌────▼────┐      ┌────▼────┐
    │ Savings │      │ Current │
    │ Account │      │ Account │
    └────┬────┘      └────┬────┘
         │                │
         └────────┬───────┘
                  │
         ┌────────▼────────┐
         │ Abstract Account│
         │  - Transaction  │
         │     List        │
         └─────────────────┘
                  │
         ┌────────▼────────┐
         │  Transaction    │
         │   - Details     │
         └─────────────────┘
```

---

## 🚀 Installation

### Prerequisites
- **Java JDK 8+** installed on your system
- A terminal/command prompt
- (Optional) An IDE like IntelliJ IDEA, Eclipse, or VS Code

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/banking-management-system.git
cd banking-management-system
```

2. **Compile the program**
```bash
javac BankingManagementSystem.java
```

3. **Run the application**
```bash
java BankingManagementSystem
```

---

## 💻 Usage

### Main Menu Options

```
┌─────────────────────────────┐
│        MAIN MENU            │
├─────────────────────────────┤
│ 1. Create Account           │
│ 2. Login to Account         │
│ 3. Display All Accounts     │
│ 4. Exit                     │
└─────────────────────────────┘
```

### Creating an Account

1. Select option `1` from the main menu
2. Enter your full name
3. Create a secure password
4. Choose account type:
   - `1` for Savings Account (3.5% interest rate)
   - `2` for Current Account ($1000 overdraft limit)
5. Receive your unique account number

### Logging In

1. Select option `2` from the main menu
2. Enter your account number (format: ACC1001)
3. Enter your password
4. Access account operations menu

### Account Operations

```
┌─────────────────────────────┐
│     ACCOUNT OPERATIONS      │
├─────────────────────────────┤
│ 1. Deposit                  │
│ 2. Withdraw                 │
│ 3. Check Balance            │
│ 4. Mini Statement           │
│ 5. Transaction History      │
│ 6. Apply Interest (Savings) │
│ 0. Logout                   │
└─────────────────────────────┘
```

---

## 📦 Class Structure

### 1. **Transaction Class**
Represents individual banking transactions
```java
- type: String (DEPOSIT, WITHDRAW, INTEREST)
- amount: double
- balanceAfter: double
- timestamp: LocalDateTime
```

### 2. **Abstract Account Class**
Base class for all account types
```java
- accountNumber: String
- accountHolder: String
- hashedPassword: String
- balance: double
- transactions: List<Transaction>

+ deposit(double): boolean
+ withdraw(double): boolean
+ verifyPassword(String): boolean
+ printMiniStatement(): void
+ printTransactionHistory(): void
```

### 3. **SavingsAccount Class**
Extends Account with interest features
```java
- interestRate: double

+ applyInterest(): void
+ getAccountType(): String
```

### 4. **CurrentAccount Class**
Extends Account with overdraft features
```java
- overdraftLimit: double

+ withdraw(double): boolean (overridden)
+ getAccountType(): String
```

### 5. **Bank Class**
Manages all accounts and banking operations
```java
- accounts: Map<String, Account>
- accountCounter: int

+ createAccount(String, String, String): String
+ login(String, String): Account
+ displayAllAccounts(): void
```

### 6. **BankingManagementSystem Class**
Main class with user interface
```java
+ main(String[]): void
- createAccountMenu(): void
- loginMenu(): void
- accountOperationsMenu(Account): void
```

---

## 📸 Screenshots

### Account Creation
```
--- CREATE NEW ACCOUNT ---
Enter your name: John Doe
Create password: ********
Account type (1-Savings / 2-Current): 1

✅ Account created successfully!
Account Number: ACC1001
Account Holder: John Doe
Account Type: SAVINGS ACCOUNT
```

### Mini Statement
```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                    MINI STATEMENT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Account: ACC1001 | Holder: John Doe
Type: SAVINGS ACCOUNT
Current Balance: $5500.00
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Last 5 Transactions:
WITHDRAW     | $500.00    | Balance: $5500.00  | 2024-12-02 14:30:45
DEPOSIT      | $2000.00   | Balance: $6000.00  | 2024-12-02 14:25:12
INTEREST     | $140.00    | Balance: $4000.00  | 2024-12-02 14:20:00
DEPOSIT      | $3000.00   | Balance: $3860.00  | 2024-12-02 14:15:30
DEPOSIT      | $860.00    | Balance: $860.00   | 2024-12-02 14:10:15
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

---

## 🔒 Security Features

### Password Security
- **SHA-256 Hashing**: Passwords are hashed using SHA-256 cryptographic algorithm
- **No Plain Text Storage**: Passwords never stored in readable format
- **Secure Verification**: Password comparison done via hash matching

```java
private String hashPassword(String password) {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(password.getBytes());
    // Convert to hexadecimal string
}
```

### Input Validation
- Amount validation (positive values only)
- Balance checks before withdrawal
- Account number format verification
- Type checking for all user inputs

### Data Protection
- Private fields with controlled access
- Encapsulated sensitive information
- Transaction immutability

---

## 🔮 Future Enhancements

- [ ] **Database Integration** - MySQL/PostgreSQL for persistent storage
- [ ] **Account Transfer** - Money transfer between accounts
- [ ] **GUI Interface** - JavaFX or Swing-based graphical interface
- [ ] **Multi-Currency Support** - Support for different currencies
- [ ] **Loan Management** - Loan application and EMI calculation
- [ ] **Credit/Debit Cards** - Virtual card generation
- [ ] **Transaction Limits** - Daily withdrawal/deposit limits
- [ ] **Account Statements** - PDF/CSV export functionality
- [ ] **Email Notifications** - Transaction alerts via email
- [ ] **Two-Factor Authentication** - Enhanced security with 2FA
- [ ] **Admin Panel** - Bank manager dashboard
- [ ] **RESTful API** - Web service integration
- [ ] **Mobile App** - Android/iOS application

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Contribution Guidelines
- Follow Java coding conventions
- Add comments for complex logic
- Update README if adding new features
- Test thoroughly before submitting PR

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Your Name

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```



## 🙏 Acknowledgments

- Inspired by real-world banking systems
- Built as a demonstration of OOP principles in Java
- Thanks to the Java community for excellent documentation

---

## 📊 Project Stats

- **Lines of Code**: ~450
- **Classes**: 6
- **Methods**: 20+
- **OOP Concepts**: 5 core principles
- **Security**: SHA-256 encryption

---

## 🐛 Bug Reports

Found a bug? Please open an issue with:
- Bug description
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)

---



<div align="center">

**Happy Banking! 🏦💰**



</div>
