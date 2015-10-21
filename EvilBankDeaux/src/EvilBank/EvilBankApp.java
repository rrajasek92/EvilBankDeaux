package EvilBank;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class EvilBankApp {
	public static int index=1;
	protected static ArrayList<Transaction> transHistory=new ArrayList<Transaction>();
	protected static ArrayList<Account> acctList=new ArrayList<Account>();
	protected static ArrayList<Account> acctList1=new ArrayList<Account>();
	protected static ArrayList<Account> acctList2=new ArrayList<Account>();
	protected static ArrayList<String> ssnList1=new ArrayList<String>();
	protected static ArrayList<String> ssnList2=new ArrayList<String>();
	protected static LinkedHashMap<String,Account> accChecking=new LinkedHashMap<String,Account>();
	protected static LinkedHashMap<String,Account> accSavings=new LinkedHashMap<String,Account>();

	public static void main(String[] args){
		
		//InitializeDatabase.initDB();
		boolean loop1=true,loop2=true;
		String acctNum="",fName="",lName="",ssn="",in="",accType="";
		float balPass=0;
		Scanner keyboard=new Scanner(System.in);
		System.out.println("Welcome to Evil Bank!\nPlease create the user account(s)");
		Account accct = new Account();
		while(loop1){
			loop2=true;
			Account acct= new Account();
			System.out.println("Enter Checking or Savings, or -1 to stop entering accounts: ");
			in=keyboard.next();
			
		if (in.equals("-1")){
			loop1 = false;
		} else {
			accType=in;
			while(loop2){
			System.out.println("Enter the SSN: ");
			ssn=keyboard.next();
			int c=0;
			if (in.equals("Checking")){
			for (int i=0;i<ssnList1.size();i++){
				if (ssn.equals(ssnList1.get(i))) {
					c++;
					break;
				}
			}
				if(c!=0) {
					System.out.println("The SSN already exists."); 
					loop2=false;	
				}
				else ssnList1.add(ssn);
			}
			else if(in.equals("Savings")){
			for (int i=0;i<ssnList2.size();i++){
					if (ssn.equals(ssnList2.get(i))) {
						c++;
						break;
					}
				}
					if(c!=0) {
						System.out.println("The SSN already exists."); 
						loop2=false;	
					}
					else ssnList2.add(ssn);
			}
			if (!loop2) {loop2=true; continue;}
			break;
			}
			while(loop2){
			System.out.println("Enter account #: ");
			acctNum=keyboard.next();
			int c=0;
			if (in.equals("Checking")){
			for (Account Acct : EvilBankApp.acctList1){
				if (acctNum.equals(Acct.getAcctNo())) {
					accct = Acct;
					c++;
					break;
				}
			}
				if(c!=0) {
					System.out.println("The account already exists."); 
					loop2=false;
					
				}
			}
			else if(in.equals("Savings")){
			for (Account Acct : EvilBankApp.acctList2){
					if (acctNum.equals(Acct.getAcctNo())) {
						accct = Acct;
						c++;
						break;
					}
				}
					if(c!=0) {
						System.out.println("The account already exists."); 
						loop2=false;
						
					}
			}
				
			if (!loop2){loop2=true; continue;}
			break;
			}
			acct.setAcctNo(acctNum);
			System.out.println("Enter the first name for acct # "+acctNum+": ");
			fName=keyboard.next();
			System.out.println("Enter the last name for acct # "+acctNum+": ");
			lName=keyboard.next();
			
			acct.setAcctName(fName+" "+lName);
			System.out.println("Enter the balance for acct # "+acctNum+": ");
			balPass=keyboard.nextFloat();
			acct.setAcctBalance(balPass);
			accountDB.sendToDatabase(ssn, fName, lName, acctNum, balPass);
			if(in.equals("Checking"))
			{acctList1.add(acct);
			 accChecking.put(ssn, acct);}
			else if(in.equals("Savings"))
			{acctList2.add(acct);
			 accSavings.put(ssn, acct);}
			
			}
		}
		
		for (Account oneAcct : EvilBankApp.acctList1){
			System.out.println("Checking accounts: "+oneAcct.toString());
}
		for (Account oneAcct: acctList2){
			System.out.println("Savings accounts: "+oneAcct.toString());
		}
		
		//looping for transactions
			double transCheck=0.0;
			String ltype="",laccount="",ldate="";
			float lamount=0;
			Account theAcct = new Account();
			while(loop2){
				System.out.println("Enter a transaction type(Check, Debit Card, Deposit, Withdrawal, or Transfer) or -1 to finish: ");
				ltype=keyboard.next();
				if (ltype.equals("-1")) break;
				System.out.println("Enter the account #: ");
				laccount=keyboard.next();
				int c=0;
				for (Account oneAcct : EvilBankApp.acctList1){
					if (laccount.equals(oneAcct.getAcctNo())) {
						theAcct = oneAcct;
						c++;
						break;
					}
				}
				for (Account oneAcct : EvilBankApp.acctList2){
					if (laccount.equals(oneAcct.getAcctNo())) {
						theAcct = oneAcct;
						c++;
						break;
					}
				}
					if(c==0) {
						System.out.println("The account does not exist."); 
						loop2=false;
						
					}
				
				if (!loop2){ loop2=true;
							 continue;}
				System.out.println("Enter the amount of transaction: ");
				lamount=keyboard.nextFloat();
				if (ltype.equals("Withdrawal")&&lamount>theAcct.getAcctBalance()){
					System.out.println("Insufficient funds! Withdrawal declined!");}
				else{
				System.out.println("Enter the date of the transaction (YYYY-MM-DD): ");
				ldate=keyboard.next();
				Transaction trans=new Transaction(laccount,lamount,ltype,ldate);
				transHistory.add(trans); // grand transaction history, not even in date order
				trans.writeToFile(laccount); // transaction for individual account, not in date order 
				theAcct.processTrans(trans);
				transactionDB.sendToDatabase(laccount, lamount, ltype, ldate, index);
				index++;
				}
				}
			System.out.println("Checking Accounts\n-------------------------");
			
			for (Account anAcct : EvilBankApp.acctList1) {
				System.out.println("The balance for account "+anAcct.getAcctNo()+" is "+anAcct.getAcctBalance());
			}
			
			System.out.println("Savings Accounts\n-------------------------");

			for (Account anAcct : EvilBankApp.acctList2) {
				System.out.println("The balance for account "+anAcct.getAcctNo()+" is "+anAcct.getAcctBalance());
			}
			System.out.println("\nTransaction History: \n-----------------------\n");
			Retrieve.retrieveDB();
			System.out.println("\nClosing Program.....");
			
	}
	
}
