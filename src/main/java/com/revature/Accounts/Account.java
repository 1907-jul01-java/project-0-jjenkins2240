/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Accounts;
import java.util.*;
import java.io.*;
import java.sql.*;
import com.revature.Customer.*;
import java.util.logging.Logger;
public class Account extends bankaccounts{
    Scanner sc=new Scanner(System.in);
    private static final Logger LOG = Logger.getLogger(Account.class.getName());
    Connection connect;
    int customerid;
    int acctid;
    int type;
    public Account(Connection connect, int id){
        this.connect=connect; 
        this.customerid=id;
    }
    @Override
    public void create()throws IOException{
        System.out.println("What kind of account do you wish to create?");
        System.out.println("1. Checking"+"\t"+"2. Savings");
        type=sc.nextInt();
        switch(type){
            case 1:
                try{
                    PreparedStatement pstate=connect.prepareStatement("insert into bankaccount(status, accttype, balance, acctowner,overdraft) values(?,?,?,?,?)");
                    pstate.setString(1, "pending");
                    pstate.setString(2,"Checking");
                    pstate.setDouble(3,1.00);
                    pstate.setInt(4, customerid);
                    pstate.setDouble(5,500.00);
                    pstate.executeQuery();
                    
                }catch(SQLException e){
                    
                }
                break;
            case 2:
                try{
                    PreparedStatement pstate=connect.prepareStatement("insert into bankaccount(status, accttype, balance, acctowner) values(?,?,?,?)");
                    pstate.setString(1, "pending");
                    pstate.setString(2,"Savings");
                    pstate.setDouble(3,1.00);
                    pstate.setInt(4, customerid);
                    pstate.executeQuery();
                    
                }catch(SQLException e){
                    
                }
                break;
        }
    }
    
    public void view(){
        try{
            PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=?");
            pstate.setInt(1, customerid);
            ResultSet results= pstate.executeQuery();
          while(results.next()){
          System.out.println("Name on Account: "+results.getString("firstname")+" "+results.getString("lastname"));
          System.out.println("Social: ***-***-"+results.getInt("ssn"));
          System.out.println("\n"+"Account: "+results.getInt("acctnum"));
          System.out.println("Status: "+results.getString("status"));
          System.out.println("Type of Account: "+results.getString("accttype"));
          System.out.println("Account Balance: "+results.getDouble("balance"));
          System.out.println("Overdraft Ammount:"+results.getDouble("overdraft")+"\n");
          
          }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
   public void change()throws IOException {

         //this class will accept user input for what kind of changes they want to make
       System.out.println("Enter the Account Number: ");
       this.acctid=sc.nextInt();
       int choice;
       int innerchoice;
       try{
           PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=? AND bankaccount.acctnum=?");
           pstate.setInt(1, customerid);
           pstate.setInt(2,acctid);
           ResultSet results=pstate.executeQuery();
          if(!results.isBeforeFirst()){
              System.out.println("You do not have an account matching the account number: "+acctid);
              System.out.println("Enter a different number and try again."+"\n");
              this.change();
          }else{
              results.next();
              System.out.println("\n"+"Account: "+results.getInt("acctnum"));
          System.out.println("Status: "+results.getString("status"));
          System.out.println("Type of Account: "+results.getString("accttype"));
          System.out.println("Account Balance: "+results.getDouble("balance"));
          System.out.println("Overdraft Balance: "+results.getDouble("overdraft")+"\n");
          if(results.getString("status").toLowerCase().equals("open")){
              System.out.println("What changes would like to make?");
              System.out.println("1."+"\t"+"Deposit funds");
              System.out.println("2."+"\t"+"Withdraw funds");
              System.out.println("3."+"\t"+"Transfer funds");
              System.out.println("4."+"\t"+"Close account");
              choice=sc.nextInt();
              switch(choice){
                  case 1:
                      this.deposit(acctid);
                      System.out.println("Do you wish to make anymore changes?"+"\t"+"1. Yes"+"\t"+"2. No");
                      innerchoice=sc.nextInt();
                      if(innerchoice==1)
                          this.change();                    
                      break;
                  case 2:
                      this.withdraw(acctid);
                      System.out.println("Do you wish to make anymore changes?"+"\t"+"1. Yes"+"\t"+"2. No");
                      innerchoice=sc.nextInt();
                      if(innerchoice==1)
                          this.change();
                      break;
                  case 3:
                     this.transfer();
                     System.out.println("Do you wish to make anymore changes?"+"\t"+"1. Yes"+"\t"+"2. No");
                      innerchoice=sc.nextInt();
                      if(innerchoice==1)
                          this.change();
                      break;
                  case 4:
                       this.closeacct(acctid);
                      System.out.println("Do you wish to make anymore changes?"+"\t"+"1. Yes"+"\t"+"2. No");
                      innerchoice=sc.nextInt();
                      if(innerchoice==1)
                          this.change();
              }
          }else
              System.out.println("This account is not yet been opened by the bank.");
          }
              
           
           
           
       }catch(SQLException e){
           System.out.println(e);
       }
       
       
    }

    @Override
   public void deposit(int id) {
       double b;
       double ammount;
  
         try{
           PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1, id);
            pstate.setInt(2,customerid);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");          
            System.out.println("How much do you wish to deposit? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            if(ammount<0){
                System.out.println("You cannot deposit an ammount less than 0.00");
                this.deposit(id);
            }
            b+=ammount;
            PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2, customerid);
            input.setInt(3,id);
            input.executeQuery();
        }catch(SQLException e){
            
        }
    }

    @Override
    public void withdraw(int id) {
        int choice;
        double b;
       double ammount;
       double overdraft;
        try{
          PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1, id);
            pstate.setInt(2,customerid);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            overdraft=results.getDouble("overdraft");
            System.out.println("How much do you wish to withdraw? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            if(ammount<0){
                System.out.println("You cannot withdraw an ammount less than 0.00");
                this.withdraw(id);
            }
            b-=ammount;
            System.out.println(b);
            double draft=0;
            
            if(b<0){
                draft=overdraft+b;
                System.out.println(draft);
                if(draft>=0){
                    
                System.out.println("Withdrawing this much will result in an overdraft which will charge a $35 fee. Do you wish to make this withdrawl?");
                System.out.println("1. Yes"+"\t"+"2. No");
                choice=sc.nextInt();
                if(choice==1){
              
                    PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
                    input.setDouble(1, b);
                    input.setInt(2, customerid);
                    input.setInt(3,id);
                    input.executeUpdate();
                    PreparedStatement over=connect.prepareStatement("Update bankaccount set overdraft=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
                    over.setDouble(1, draft);
                    over.setInt(2,customerid);
                    over.setInt(3,id);
                    over.executeUpdate();
                    System.out.println("Withdrawl Successful");
                }
                if(choice==2){
                    System.out.println("No money was withdawn");
                }
            }else{
                    System.out.println("Cannot withdraw the specified ammount money. You have insufficent bank account funds and/or overdraw funds");
                }
            } if(b>=0){
                
                 PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=? And bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2, customerid);
            input.setInt(3, id);
            input.executeUpdate();
            System.out.println("Withdrawl Successful");
            }
            
            
        }catch(SQLException e){
            
        }
        
    }

    @Override
    public void closeacct(int id) throws IOException {
        double b;
       
          try{
             PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1, id);
            pstate.setInt(2,customerid);
            ResultSet results=pstate.executeQuery();
            results.next();
          
         b=results.getDouble("balance");
         if(b!=0.0){
             System.out.println("Cannot delete account. The balance must be $0.0 to delete account.");
             System.out.println("Your current balance for Account: "+results.getInt("acctnum")+" is "+b);
         }else{
             PreparedStatement delete=connect.prepareStatement("delete from bankaccount where  bankaccount.acctnum=? AND bankaccount.acctowner=?");
             delete.setInt(1,id);
             delete.setInt(2, customerid);
             delete.executeQuery();
             System.out.println("The account has been deleted");
         }
             
         
        }catch(SQLException e){
            
        }
        
    }
    public void transfer()throws IOException{
       int acct1;
       int acct2;
       double b1;
       double b2;
       double transfer;
       String status1;
       String status2;
       try{System.out.println("Enter the Account number for the account you will be transfering funds to");
       acct1=sc.nextInt();
           PreparedStatement acc1=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=?");
           acc1.setInt(1,acct1);
           acc1.setInt(2,customerid);
           ResultSet results= acc1.executeQuery();
           if(!results.isBeforeFirst()){
               System.out.println("The account you entered does not exist for the user that was given.");
           }else{
               results.next();
               status1=results.getString("status");
               if(status1.toLowerCase().equals("open")){
                   
              
               
               b1=results.getDouble("balance");
              
               System.out.println("Enter the Account number for the account you are transfering funds from");
               acct2=sc.nextInt();
               PreparedStatement acc2=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=?");
               acc2.setInt(1,acct2);
               acc2.setInt(2,customerid);
               ResultSet second=acc2.executeQuery();
               if(!second.isBeforeFirst()){
                   System.out.println("The account you entered does not exist for the user that was given");
               }else{
                   second.next();
                   status2=second.getString("status");
                   
                   if(status2.toLowerCase().equals("open")){
                   
                   b2=second.getDouble("balance");
                  
                   if(b2<=0){
                       System.out.println("You cannot transfer funds from or to an account that has a 0 or negative balance.");
                   }
                   else
                   {
                       System.out.println("Enter the ammount you wish to transfer: ");
                       transfer=sc.nextDouble();
                       if(transfer<0||(b2-transfer)<0){
                           System.out.println("The ammount of funds you wish to transfer is invalid. Either a negative ammount was entered or it was greater than the current balance of the account you are trying to transfer funds from");
                       }
                       else{
                           b1+=transfer;
                           b2-=transfer;
                           PreparedStatement update2=connect.prepareStatement("update bankaccount set balance=? Where bankaccount.acctnum=?");
                           update2.setDouble(1,(b2));
                           update2.setInt(2, acct2);
                           update2.executeUpdate();
                           
                           PreparedStatement update1=connect.prepareStatement("update bankaccount set balance=? Where bankaccount.acctnum=?");
                           update1.setDouble(1,(b1));
                           update1.setInt(2,acct1);
                           update1.executeUpdate();
                           
                       }
                       
                   }
               }else
                       System.out.println("Cannot make transfers from or to an account that has a 0 or negative balance");
               }
                   
           }else
                   System.out.println("Cannot make transfers to or from account that is not 'open'");
                   }
           
       }catch(SQLException e){
           System.out.println(e);
       }
    }
    }

   

