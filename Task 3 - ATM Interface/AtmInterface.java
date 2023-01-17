import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class BankAccount{
    int userId;   
    private int pin;
    int balance = 1000;
    int deposit = 0;
    int withdraw = 0;
    int transfer = 0;
    int recipientId=0;//userid of user to whom money has to transfer
    List<String> history = new ArrayList<String>();
    static ArrayList<BankAccount> acc = new ArrayList<BankAccount>();

    BankAccount(int userId,int pin){
        this.userId = userId;
        this.pin = pin;
    }
    
    public int getUserId() {
        return userId;
    }
    @Override
    public String toString() {
        return ("userId:"+ userId+ ",pin:" + pin);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null
                || this.getClass() != obj.getClass())
            return false;

        BankAccount p1 = (BankAccount) obj; 
        return this.userId==p1.userId && this.pin == p1.pin;
    }
    

    boolean checkUser(BankAccount b) {
        for (BankAccount bankAccount : acc) {
            if (bankAccount.equals(b)) {
                System.out.println("User logged in Successfully!\n\n");
                return true;
            }
        }
        System.out.println("Wrong Credential.Please enter correct credential!\n\n");
        return false;
    }
    boolean showMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println(); 
        System.out.println("What you want to do?");
        System.out.println("1.Check Balance");
        System.out.println("2.Deposit Money");
        System.out.println("3.Withdraw Money");
        System.out.println("4.Transfer Money");
        System.out.println("5.Transaction History");
        System.out.println("6.Quit");
        System.out.print(">>");
        int option = sc.nextInt();
        System.out.println();
        switch (option) {
            case 1:
                checkBalance();
                return true;
            case 2:
                System.out.println("Enter the amount you want to deposit");
                deposit= sc.nextInt();
                depositMoney(deposit);
                saveTransaction(option);
                return true;
            case 3:
                System.out.println("Enter the amount you want to withdraw");
                withdraw = sc.nextInt();
                boolean check=withdrawMoney(withdraw);
                if(check){
                    saveTransaction(option);
                }
                return true;
            case 4:
                System.out.println("Enter the userId of a Bank Account to whom you want to transfer the money?");
                recipientId = sc.nextInt();
                System.out.println("Enter the amount you want to transfer");
                transfer = sc.nextInt();
                check=transferMoney(recipientId,transfer);
                if (check) {
                    saveTransaction(option);
                }
                return true;
            case 5:
                showTransactionHistory();
                return true;
        
            default:
                signOut();
                return false;
        }
    }
    private void saveTransaction(int option) {
        if (option==2) {
            history.add("Balance was '"+(balance-deposit)+"' and Deposit Amount was '"+deposit+"' so Closing Balance becomes '"+balance+"'");
        }
        else if (option==3) {
            history.add("Balance was '"+(balance+withdraw)+"' and Withdrawn Amount was '"+withdraw+"' so Closing Balance becomes '"+balance+"'");
        }
        else if (option==4) {
            history.add("Balance was '"+(balance+ transfer)+"' after transferring '"+ transfer +"' to '"+recipientId+"' Closing Balance becomes '"+balance+"'");
        }
    }
    private void signOut() {
        System.out.println("User SignOut successfully");
    }
    private void showTransactionHistory() {
        if (history.isEmpty()){
            System.out.println("No Transaction History to show");
        }
        else{
            for (String h : history) {
                System.out.println(h);   
            }  
        }
    }
    private boolean withdrawMoney(int amount) {
        if (amount>balance) {
            System.out.println("Insufficient Balance.Your current balance is "+balance+".Please try again with lower amount");
            return false;
        }else{
            balance-=amount;
            System.out.printf("%d is withdrawed from your account.\nCurrent Balance:%d\n",amount,balance);
            return true;
        }
    }

    private void depositMoney(int amount) {
        balance +=amount;
        System.out.println(amount + " is deposited from You.\nCurrent Balance:"+balance);
    }
    private boolean transferMoney(int userId,int amount) {
        if (amount > balance) {
            System.out.println("Insufficient Balance.Your current balance is " + balance + ".Please try again with lower amount");
            return false;
        }
        else if(userId==this.userId){
            System.out.println("You can't transfer to your own account");
            return false;
        }
        else{
            for (BankAccount bankAccount : acc) {
                if (bankAccount.getUserId()==userId) {
                    this.balance -= amount;
                    bankAccount.balance += amount;
                    System.out.printf("%d amount is transferred from your account to %d account.\nCurrent Balance:%d\n",amount,userId,balance);
                    return true;
                }   
            }
            System.out.println("User with "+userId+" does not exist");
            return false;
        }
        
    }
    private void checkBalance() {
        System.out.println("Balance of account "+userId+" is "+balance);
    }
}

public class AtmInterface {
    static List<Integer> prompt(){
        System.out.println("Please enter your credential");
        Scanner sc = new Scanner(System.in);
        System.out.print("User Id:");
        int userId = sc.nextInt();
        System.out.print("Pin:");
        int pin = sc.nextInt();
        return Arrays.asList(userId,pin);
    }
    
    public static void main(String[] args) {
        BankAccount.acc.add(new BankAccount(1, 1));
        BankAccount.acc.add(new BankAccount(2, 2));
        BankAccount.acc.add(new BankAccount(3, 3));


        System.out.println("--------Welcome to ATM Machine--------");
        List<Integer> cred=prompt();
        BankAccount b=new BankAccount(cred.get(0),cred.get(1));
        boolean user=b.checkUser(b);
        boolean menu=true;
        while(true){

            while(!user){
               cred=prompt();
               b = new BankAccount(cred.get(0), cred.get(1));
               user=b.checkUser(new BankAccount(cred.get(0),cred.get(1)));
               menu=true;
               
            }
            System.out.println("Welcome "+b.getUserId());
            while (menu) {
                menu=b.showMenu();  
             }
             user=false;
        }
    }
}
