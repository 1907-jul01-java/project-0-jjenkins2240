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
public class Account extends bankaccounts{
    Scanner sc=new Scanner(System.in);
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
  
          System.out.println("\n"+"Account: "+results.getInt("acctnum"));
          System.out.println("Status: "+results.getString("status"));
          System.out.println("Type of Account: "+results.getString("accttype"));
          System.out.println("Account Balance: "+results.getDouble("balance")+"\n");
          
          }
        }catch(SQLException e){
            
        }
    }

    @Override
   public void change()throws IOException {
         //this class will accept user input for what kind of changes they want to make
       System.out.println("Enter the Account Number: ");
       acctid=sc.nextInt();
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
              System.out.println("\n"+"Account: "+results.getInt("acctnum"));
          System.out.println("Status: "+results.getString("status"));
          System.out.println("Type of Account: "+results.getString("accttype"));
          System.out.println("Account Balance: "+results.getDouble("balance")+"\n");
          if(results.getString("status").toLowerCase().equals("open")){
              System.out.println("What changes would like to make?");
              System.out.println("1."+"\t"+"Deposit funds");
              System.out.println("2."+"\t"+"Withdraw funds");
              System.out.println("3."+"\t"+"Close account");
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
                      break;
                  case 3:
                      this.closeacct(acctid);
                      System.out.println("Do you wish to make anymore changes?"+"\t"+"1. Yes"+"\t"+"2. No");
                      innerchoice=sc.nextInt();
                      break;
              }
          }
          }
              
           
           
           
       }catch(SQLException e){
           
       }
       
       
    }

    @Override
   public void deposit(int id) {
       double b;
       double ammount;
         try{
            PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=?");
            pstate.setInt(1, id);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            System.out.println("How much do you wish to deposit? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            b+=ammount;
            PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=?");
            input.setDouble(1, b);
            input.setInt(2, id);
            
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
            PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=?");
            pstate.setInt(1, id);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            overdraft=results.getDouble("overdraft");
            System.out.println("How much do you wish to withdraw? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            b-=ammount;
            double draft=overdraft-ammount;
            
            if(b<0){
                if(draft>=0){
                System.out.println("Withdrawing this much will result in an overdraft which will charge a $35 fee. Do you wish to make this withdrawl?");
                System.out.println("1. Yes"+"\t"+"2. No");
                choice=sc.nextInt();
                if(choice==1){
                    PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=?");
            input.setDouble(1, b);
            input.setInt(2, id);
            input.executeQuery();
                    PreparedStatement over=connect.prepareCall("Update bankaccount set overdraft=? Where acctowner=?");
                    over.setDouble(1, draft);
                    over.setInt(2,id);
                    over.executeQuery();
                    System.out.println("Withdrawl Successful.");
                }
                if(choice==2){
                    System.out.println("No money was withdawn");
                }
            }else{
                    System.out.println("Cannot withdraw the specified ammount money. You have insufficent bank account funds and/or overdraw funds");
                }
            }else{
                
                 PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=?");
            input.setDouble(1, b);
            input.setInt(2, id);
            input.executeQuery();
            System.out.println("Withdrawl Successful");
            }
            
            
        }catch(SQLException e){
            
        }
        
    }

    @Override
    public void closeacct(int id) throws IOException {
        double b;
        int anum;
          try{
            PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=?");
            pstate.setInt(1, id);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            anum=results.getInt("acctnum");
         b=results.getDouble("balance");
         if(b!=0.0){
             System.out.println("Cannot delete account. The balance must be $0.0 to delete account.");
             System.out.println("Your current balance for Account: "+results.getInt("acctnum")+" is "+b);
         }else{
             PreparedStatement delete=connect.prepareStatement("delete from bankaccount where acctnum=? AND where acctowner=?");
             delete.setInt(1,anum);
             delete.setInt(2, id);
             delete.executeQuery();
             System.out.println("The account has been deleted");
         }
             
         
        }catch(SQLException e){
            
        }
        
    }
    }

   

