/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Customer;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;
import com.revature.Accounts.*;
public class Menu {
   private Connection connect;
    Scanner sc=new Scanner(System.in);
    private static int choice=0;  
    private static final Logger LOG = Logger.getLogger(Menu.class.getName());
    private static String user;
    private static String pass;
    private boolean success;
    public Menu(Connection connect)throws IOException{
        this.connect=connect;
        
    }
    public void startmenu()throws IOException{
   
        System.out.println("1."+"\t"+"Login");
        System.out.println("2."+"\t"+"Create new User");
        System.out.println("3."+"\t"+"Exit"+"\n");
        choice=sc.nextInt();
        switch(choice){
            case 1: this.login();
                break;
            case 2:
                NC person=new NC();
               person.CreateUser(this.connect);
                break;
            case 3:
                System.out.println("Exiting Application");
               
                break;
        }
    }
    public void login()throws IOException{
       
        System.out.print("Enter your Username: ");
                user=sc.next();
        System.out.print("\n"+"Enter your Password: ");
                pass=sc.next();
                Authenticate a=new Authenticate(user, pass,connect);
               success= a.validate(user, pass);
               if(success==false){
                   System.out.println("Invalid Username or Password. Please try again");
                   this.login();
               }else
                   this.Usermenu();
    
    }
    public void Usermenu()throws IOException{
        int id=NC.getId();
        Account neww=new Account(this.connect,id);
        System.out.println("Welcome "+NC.getFullname());
        System.out.println("\n"+"\t"+"User Options");
        System.out.println("1."+"\t"+"Create new Account");
        System.out.println("2."+"\t"+"Delete an existing Account");
        System.out.println("3."+"\t"+"View Accounts");
        System.out.println("4."+"\t"+"Manage Accounts");
        System.out.println("5."+"\t"+"Logout"+"\n");
        choice=sc.nextInt();
        switch(choice){
            case 1:
                
                neww.create();
                System.out.println("Your account has been created and is pending. Please wait for a bank employee to validate and open the account");
                this.Usermenu();
                break;
            case 2:
                break;
            case 3:
                neww.view();
                this.Usermenu();
                break;
            case 4:
                neww.change();
                this.Usermenu();
                break;
            case 5:
                System.out.println("Thank you for your business!");
                System.out.println("We look forward to your next visit!");
                this.startmenu();
                break;
        }
                
    }
  
    
}
