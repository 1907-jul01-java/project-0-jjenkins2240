/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.employee;

import java.io.*;
import java.util.*;
import java.sql.*;

public class Empoptions {
    int custid;
    String custfirst;
    String custlast;
    int custsocial;
    int acctnum;
    String admin;
    int custacctnum;
    Connection connect;
    Scanner sc=new Scanner(System.in);
   
    public Empoptions(Connection connect){
        this.connect=connect;
    }
    public void view()throws IOException{
      try{
          this.getcustdata();
          
            PreparedStatement pstate=connect.prepareStatement("select * from bankaccount Where bankaccount.acctowner=?");
            pstate.setInt(1, custid);

            ResultSet results= pstate.executeQuery();
          while(results.next()){
  
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
    public void change()throws IOException{
        System.out.println("Account Options");
        System.out.println("1. "+"\t"+"Change Status");
        System.out.println("2. "+"\t"+"Deposit funds");
        System.out.println("3. "+"\t"+"Remove funds");
        int choice=sc.nextInt();
        switch(choice){
            case 1:
                this.changestatus();
                break;
            case 2:
                this.deposit();
                break;
            case 3:
                this.withdraw();
                break;
        }
    }
    public void changestatus()throws IOException{
        this.getcustacct();
        this.getcustdata();
        try{
            System.out.println("Enter the Account's new status: ");
            String status=sc.next();
         PreparedStatement pstate=connect.prepareStatement("Update bankaccount set status=? Where customers.id=? AND bankaccount.acctnum=?");
            pstate.setString(1,status);
            pstate.setInt(2, custid);
            pstate.setInt(3, custacctnum);

           pstate.executeQuery();
            
        }catch(SQLException e){
            
        }
        
    }
    public void deposit()throws IOException{
         double b;
       double ammount;
       if(admin.toLowerCase()=="y"){
           this.getcustdata();
           this.getcustacct();
         try{
           PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1, custacctnum);
            pstate.setInt(2,custid);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            System.out.println("How much do you wish to deposit? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            b+=ammount;
            PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2, custid);
            input.setInt(3,custacctnum);
            input.executeQuery();
        }catch(SQLException e){
            
        }}
    }public void withdraw()throws IOException{
        int choice;
        double b;
       double ammount;
       double overdraft;
       if(admin.toLowerCase()=="y"){
           this.getcustdata();
           this.getcustacct();
        try{
          PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1, custacctnum);
            pstate.setInt(2,custid);
            ResultSet results=pstate.executeQuery();
            results.next();
            b=results.getDouble("balance");
            overdraft=results.getDouble("overdraft");
            System.out.println("How much do you wish to withdraw? Please include the ammount of cents even if it is 0. (Ex: 25.00");
            ammount=sc.nextDouble();
            b-=ammount;
            System.out.println(b);
            double draft=overdraft-b;
            
            if(b<0){
                if(draft>=0){
                System.out.println("Withdrawing this much will result in an overdraft which will charge a $35 fee. Do you wish to make this withdrawl?");
                System.out.println("1. Yes"+"\t"+"2. No");
                choice=sc.nextInt();
                if(choice==1){
                    System.out.println("Withdrawl Successful.");
                    PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2, custid);
            input.setInt(3,custacctnum);
            input.executeQuery();
                    PreparedStatement over=connect.prepareCall("Update bankaccount set overdraft=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
                    over.setDouble(1, draft);
                    over.setInt(2,custid);
                    over.setInt(3,custacctnum);
                    over.executeQuery();
                    
                }
                if(choice==2){
                    System.out.println("No money was withdawn");
                }
            }else{
                    System.out.println("Cannot withdraw the specified ammount money. You have insufficent bank account funds and/or overdraw funds");
                }
            } if(b>=0){
                System.out.println("Withdrawl Successful");
                 PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=? And bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2,custid);
            input.setInt(3, custacctnum);
            input.executeQuery();
            
            }
            
            
        }catch(SQLException e){
        }
        }
    }
    public void getcustdata()throws IOException{
        System.out.println("Enter the Customers first name: "+"\t");
        this.custfirst=sc.next();
        System.out.println("Enter the Customers last name: "+"\t");
        this.custlast=sc.next();
        System.out.println("Enter the last 4 of customer's SSN");
        this.custsocial=sc.nextInt();
        try{
            PreparedStatement pstate = connect.prepareStatement("select * from customers where customers.firstname=? AND customers.lastname=? AND customers.ssn=?");
            pstate.setString(1,custfirst);
            pstate.setString(2,custlast);
            pstate.setInt(3, custsocial);
            ResultSet results=pstate.executeQuery();
            if(!results.isBeforeFirst()){
                System.out.println("The Customer you entered does not exist in the database");
                this.getcustdata();
            }else{
            results.next();
            this.custid=results.getInt("id");
            }
        }catch(SQLException e){
            System.out.println(e);
        }

   
    }
    public void getcustacct(){ 
       System.out.println("Enter the Customers Account number");
       custacctnum=sc.nextInt();
        try{
        PreparedStatement pstate=connect.prepareStatement("select * from bankaccount inner join customers on bankaccount.acctowner=customers.id Where customers.id=? AND bankaccount.acctnum=?");
           pstate.setInt(1, custid);
           pstate.setInt(2,custacctnum);
           ResultSet results=pstate.executeQuery();
          if(!results.isBeforeFirst()){
              System.out.println("There is not account for the customer matching: "+custacctnum);
              System.out.println("Enter a different number and try again."+"\n");
              this.getcustacct();
    }
       }catch(SQLException e){
           
       }}
    public void setAdmin(String s){
        this.admin=s;
    }
}
