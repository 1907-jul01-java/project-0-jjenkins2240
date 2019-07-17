package com.revature.Customer;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.sql.*;

public class NC{
   private static Scanner sc=new Scanner(System.in);
   private static String fullname;
    private static boolean success=false;
  private static String first;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        NC.id = id;
    }
private static int id;
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
  private static String middle;
  private static String last;
  private String uname;
  private String pass;
  private int social;
  private Connection connect;
  public NC(){
      
  }
  public NC(String first, String middle, String last){
      this.first=first;
      this.middle=middle;
      this.last=last;
  }
  
  public void CreateUser(Connection connection)throws IOException{
      this.connect=connection;
      System.out.println("Enter your First Name: "+"\t"+"\n");
      first=sc.next();
      System.out.println("Enter your Middle Name: (If you wish to omit your middle name or do not have one then type 'none') "+"\t"+"\n");
      middle=sc.next();
      System.out.println("Enter your Last Name: "+"\t"+"\n");
      last=sc.next();
      System.out.println("Enter the last 4 digits of your social sercurity number");
      this.ssn();
      
      
      try{
          PreparedStatement pstate=connection.prepareStatement("insert into customers(firstname, midname, lastname, ssn) values(?,?,?,?)");
          pstate.setString(1, first);
          if(middle.toLowerCase()=="none")
              middle=null;
          pstate.setString(2, middle);
          pstate.setString(3, last);
          pstate.setInt(4, social);
          pstate.executeQuery();
          
      } catch(SQLException e){
          
      }
      System.out.println("Create your username now: ");
        this.createusername();
        System.out.println("Enter your password.");
        pass=sc.next();
        int x;
         try{
             PreparedStatement getid=connection.prepareStatement("select * from customers where customers.ssn=?");
             getid.setInt(1, this.social);
             
             ResultSet result=getid.executeQuery();
             result.next();
             x=result.getInt("id");
             System.out.println(x);
          PreparedStatement pstate=connection.prepareStatement("insert into custdata(username, password, customerid) values(?,?,?)");
          pstate.setString(1, uname);
          pstate.setString(2, pass);
        pstate.setInt(3, x);
       pstate.executeQuery();
          
      } catch(SQLException e){
       
      }
      Menu mu=new Menu(this.connect);
      System.out.println("Please login with your new credentials");
      mu.login();
  }
  public void createusername(){
      uname=sc.next();
        Authenticate a = new Authenticate(uname,connect);
      boolean success = a.validateusername();
      if(success==false){
          System.out.println("That username is already in use. Please use a different username.");
          this.createusername();
      }
      
  }
  
    public void ssn(){
      int x=sc.nextInt();
        if(x<1000||x>9999){
            System.out.println("Invalid ssn. Please re-enter your last 4 digits of your social");
            this.ssn();
        }
            this.social=x;
    }

    public static String getFullname() {
        return fullname;
    }

    public static String getFirst() {
        return first;
    }

    public static String getMiddle() {
        return middle;
    }

    public static String getLast() {
        return last;
    }

    public static void setFullname() {
        NC.fullname = NC.first+" "+NC.middle+" "+NC.last;
    }

    public static void setFirst(String first) {
        NC.first = first;
    }

    public static void setMiddle(String middle) {
        if(middle==null)
            NC.middle="";
        else
        NC.middle = middle;
    }

    public static void setLast(String last) {
        NC.last = last;
    }

    @Override
    public String toString() {
        return fullname;
    }
 
  
 


}
