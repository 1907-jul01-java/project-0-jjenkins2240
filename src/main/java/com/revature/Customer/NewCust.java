package com.revature.Customer;

import java.io.*;
import java.util.*;

public class NewCust{
   private static String fullname;
    private static boolean success=false;
   private static Scanner sc=new Scanner(System.in);
  private static  int found=0;
   private static String un=""; 
   Scanner reader;
 FileWriter fw= new FileWriter("C:\\Users\\tinma\\Desktop\\Revature Class\\test.in",true);
 FileWriter fwtxt=new FileWriter("C:\\Users\\tinma\\Desktop\\Revature Class\\test.txt",true);
     PrintWriter print=new PrintWriter(fw);
     PrintWriter printtxt=new PrintWriter(fwtxt); 
     ArrayList <String> unlist=new <String>ArrayList();
public NewCust(String first, String middle, String last)throws IOException{
        this.reader = new Scanner(new File("C:\\Users\\tinma\\Desktop\\Revature Class\\test.in"));

    NewCust.fullname=first+" "+middle+" "+last;
 this.NewAccount();

}

public void NewAccount()throws IOException{
    
   
  

    String line="";
    
   System.out.println("Enter your Username:");
 while(reader.hasNextLine()){
          unlist.add(reader.nextLine());
      }

do{      
    un=sc.nextLine();

     for( String item : unlist){
         String [] examin = new String[5];
         examin=item.split(" ");

                
         if(examin[1].equals(un)){
             found=1;
         }
 
     }
     if(found==0){
             success=true;
             System.out.println("The Username was successfully Created.");
             this.un=un;
         }
     if(found==1){
             System.out.println("That Username is not available. Please enter a different Username");
             found=0;
         } 
     
   }while(success==false);

System.out.println("Please create your password now.");
String pw=sc.nextLine();
line="Username: "+un+" Password: "+pw+" Customer Name: "+fullname+ "Account Status: Pending";
print.println(line);
printtxt.println(line);   
      print.close();
      printtxt.close();
       fw.close();
       fwtxt.close();
       reader.close();



   }
      


 


}
