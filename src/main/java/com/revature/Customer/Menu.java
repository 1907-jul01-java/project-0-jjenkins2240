/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Customer;
import java.io.*;
import java.util.*;
public class Menu {
    private static int option;
    private Scanner sc= new Scanner(System.in);
    private String first;
    private String middle;
    private String last;
    private String un;
    private String pw;
    private int found;
    private boolean success;
    private String name;
    String account="";
    Scanner reader=new Scanner(new File("C:\\Users\\tinma\\Desktop\\Revature Class\\test.in"));
    ArrayList <String> unlist=new <String>ArrayList();
    public Menu() throws IOException{
        
        System.out.println("Welcome to the Revature Banking App."+"\n");
        System.out.println("Are you a Customer or Employee? Enter the number next to your choice."+"\n");
        System.out.println("1------> Customer"+"\t"+"2------> Employee");
        this.Options(sc.nextInt());
        
    }
    public void Options(int x)throws IOException{
        switch(x){
            case 1:
                this.Customer();
                break;
            case 2:
                this.Employee();
                break;
        }
        
    }
    public void Customer()throws IOException{
        System.out.println("Please make a Selection from the menu");
        System.out.println("1."+"\t"+"Create account");
        System.out.println("2."+"\t"+"Login to account");
        System.out.println("3."+"\t"+"Check status of account");String [] examin = new String[11];
        System.out.println("4."+"\t"+"Exit");
        option=sc.nextInt();
        switch(option){
            case 1: System.out.println("Enter your first name");
                    this.first=sc.next();
                    System.out.println("Enter your middle name");
                    this.middle=sc.next();
                    System.out.println("Enter your Last name");
                    this.last=sc.next();
                     NewCust customer=new NewCust(first, middle, last);
                     this.Customer();
                    break;
                
            case 2: System.out.println("Enter Username");
                    this.un=sc.next();
                    this.UNCheck(un);
                    break;
            
            case 3: System.out.println("Enter your Username");
                    this.un=sc.next();
                    this.UNCheck(un);
                    this.AccStatus(un);
                    this.Customer();
                    break;
             
            case 4: System.out.println("Thank you for your visit. We look forward to seeing you next time.");
                    break;
        }
}
    public void afterloginmenu(){
            
}
    public void Employee(){
        
}
    public void UNCheck(String user){
        while(reader.hasNextLine()){
            unlist.add(reader.nextLine());
        }
        do{      

     for( String item : unlist){
         String [] examin = new String[11];
         examin=item.split(" ");

                
         if(examin[1].equals(user)){
             found=1;
             name=examin[5]+" "+examin[6]+" "+examin[7];
         }
 
     }
     if(found==0){
             success=false;
             System.out.println("Username not found. Please try again");
             this.UNCheck(sc.nextLine());
         }
     if(found==1){
             System.out.println("Welcome back "+name);
             found=0;
             success=true;
         } 
     
   }while(success==false);
        
        
       
    }
    public void AccStatus(String user){
         while(reader.hasNextLine()){
            unlist.add(reader.nextLine());
        }
        do{      

     for( String item : unlist){
         String [] examin = new String[11];
         examin=item.split(" ");
         
                
         if(examin[1].equals(user)){
             found=1;
             account=examin[10];
         }
 
     }
     

     if(found==1){
             System.out.println("Your Account status is: "+account);
             found=0;
             success=true;
         } 
     
   }while(success==false);
        
        
       
    }
    
   
}
