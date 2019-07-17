/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.employee;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.revature.Accounts.*;

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
    public void viewtransactions()throws IOException{
        try{this.getcustdata();
        this.getcustacct();
            PreparedStatement pstate=connect.prepareStatement("select* from transactions where transactions.acctnum=? AND transactions.custid=?");
            pstate.setInt(1,custacctnum);
            pstate.setInt(2, custid);
            ResultSet results=pstate.executeQuery();
            if(!results.isBeforeFirst()){
                System.out.println("The account number specified does not match any account numbers for this user.");
            }else{
          while(results.next()){
          System.out.println("\n"+"Account number: "+results.getString("acctnum"));
          System.out.println("Transaction: "+results.getString("t_type"));
          System.out.println("Time: "+results.getTime("t_time"));
          System.out.println("Starting balance: "+results.getDouble("startbalance"));
          System.out.println("Ending balance: "+results.getString("endbalance")+"\n");

          
          }}
        }catch(SQLException e){
            System.out.println(e);
        }
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
        System.out.println("4. "+"\t"+"Close account");
        System.out.println("5. "+"\t"+"transfer funds");
        System.out.println("6. "+"\t"+"Reset Overdraft");
        System.out.println("7. "+"\t"+"Reset Password");
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
            case 4:
                this.closeacct();
                break;
            case 5:
                this.transfer();
                break;
            case 6:
                this.resetover();
                break;
            case 7:
                this.changepass();
                break;
        }
    }
    public void changepass()throws IOException{
       String oldpass;
       String newpass;
        
        try{
            this.getcustdata();
            PreparedStatement pstate=connect.prepareStatement("select password from custdata Where custdata.customerid=?");
            pstate.setInt(1, custid);
            ResultSet results=pstate.executeQuery();
            results.next();
            System.out.println("Enter the customers current password;");
            oldpass=sc.next();
            String currentpass=results.getString("password");
            if(!oldpass.equals(currentpass)){
                System.out.println("Passwords do not match");
            }else{
                System.out.println("Enter your new password: ");
        newpass=sc.next();
        PreparedStatement updatepass=connect.prepareStatement("update custdata set password=? Where customerid=?");
        updatepass.setString(1, newpass);
        updatepass.setInt(2,custid);
        updatepass.executeUpdate();
        System.out.println("Password Successfully updated.");
            }
        }catch(SQLException e){
            System.out.println(e);
            
        }
    }
    public void resetover()throws IOException{
        this.getcustdata();
        this.getcustacct();
        try{
             PreparedStatement pstate=connect.prepareStatement("update bankaccount set overdraft=? Where bankaccount.acctnum=? AND bankaccount.acctowner=?");
             pstate.setDouble(1,500.00);
             pstate.setInt(2, custacctnum);
             pstate.setInt(3,custid);
             pstate.executeUpdate();
        }catch(SQLException e){
            
        }
       
    }
    public void changestatus()throws IOException{
        this.getcustdata();
        this.getcustacct();
        
        try{
            System.out.println("Enter the Account's new status: ");
            String status=sc.next();
         PreparedStatement pstate=connect.prepareStatement("Update bankaccount set status=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
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
       if(admin.toLowerCase().equals("y")){
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
            if(ammount<0){
                System.out.println("You cannot deposit a negative ammount");
                this.deposit();
            }else{
            b+=ammount;
            PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2, custid);
            input.setInt(3,custacctnum);
           input.executeQuery();
            }
        }catch(SQLException e){
            
        }System.out.println("Account Updated");}else
            System.out.println("You do not have suffecient admin rights to perform this action.");
    }public void withdraw()throws IOException{
        int choice;
        double b;
       double ammount;
       double overdraft;
       if(admin.toLowerCase().equals("y")){
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
                    PreparedStatement over=connect.prepareStatement("Update bankaccount set overdraft=? Where bankaccount.acctowner=? AND bankaccount.acctnum=?");
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
                
                 PreparedStatement input=connect.prepareStatement("Update bankaccount set balance=? Where acctowner=? And bankaccount.acctnum=?");
            input.setDouble(1, b);
            input.setInt(2,custid);
            input.setInt(3, custacctnum);
            input.executeQuery();
            
            }
            
            
        }catch(SQLException e){
        }
     
            System.out.println("Withdrawl Successful");
        }else
            System.out.println("You do not have suffecient admin rights to perform this action.");
    }
    public void closeacct()throws IOException{
           double b;
           if(admin.toLowerCase().equals("y")){
           this.getcustdata();
           this.getcustacct();
          try{
             PreparedStatement pstate=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=? ");
            pstate.setInt(1,custacctnum);
            pstate.setInt(2,custid);
            ResultSet results=pstate.executeQuery();
            results.next();
          
         b=results.getDouble("balance");
         if(b!=0.0){
             System.out.println("Cannot delete account. The balance must be $0.0 to delete account.");
             System.out.println("Your current balance for Account: "+results.getInt("acctnum")+" is "+b);
             this.change();
         }else{
             PreparedStatement delete=connect.prepareStatement("delete from bankaccount where  acctnum=? AND acctowner=?");
             delete.setInt(1,custacctnum);
             delete.setInt(2, custid);
             delete.executeUpdate();
             System.out.println("The account has been deleted");
         }
             
         
        }catch(SQLException e){
            System.out.println(e);
        }
    }else
               System.out.println("You do not have suffecient admin rights to perform this action.");
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
     public void transfer()throws IOException{
       int acct1;
       int acct2;
       double b1;
       double b2;
       double transfer;
       String status1="";
       String status2;
       if(admin.toLowerCase().equals("y")){
           this.getcustdata();

       try{System.out.println("Enter the Account number for the account you will be transfering funds to");
       acct1=sc.nextInt();
           PreparedStatement acc1=connect.prepareStatement("select * from bankaccount where bankaccount.acctnum=? AND bankaccount.acctowner=?");
           acc1.setInt(1,acct1);
           acc1.setInt(2,custid);
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
               acc2.setInt(2,custid);
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
                             double balance1=b1-transfer;
                           double balance2=b2+transfer;
                           PreparedStatement update2=connect.prepareStatement("update bankaccount set balance=? Where bankaccount.acctnum=?");
                           update2.setDouble(1,(b2));
                           update2.setInt(2, acct2);
                           update2.executeUpdate();
                           
                           PreparedStatement update1=connect.prepareStatement("update bankaccount set balance=? Where bankaccount.acctnum=?");
                           update1.setDouble(1,(b1));
                           update1.setInt(2,acct1);
                           update1.executeUpdate();
                            PreparedStatement transaction=connect.prepareStatement("insert into transactions(acctnum,startbalance,endbalance,custid,t_type) values(?,?,?,?,?)");
                                transaction.setInt(1,acct2);
                                transaction.setDouble(2,balance2);
                                transaction.setDouble(3,b2);
                                transaction.setInt(4, custid);
                                transaction.setString(5,"Transfer");
                                transaction.executeUpdate();
                             
                           
                        
                           PreparedStatement transaction2=connect.prepareStatement("insert into transactions(acctnum,startbalance,endbalance,custid,t_type) values(?,?,?,?,?)");
                                 transaction2.setInt(1,acct1);
                                 transaction2.setDouble(2,balance1);
                                 transaction2.setDouble(3,b1);
                                 transaction2.setInt(4, custid);
                                 transaction2.setString(5, "Transfer");
                                 transaction2.executeUpdate();
                           
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
    }else
           System.out.println("You do not have suffecient admin rights to perform this action.");
           }
    }


