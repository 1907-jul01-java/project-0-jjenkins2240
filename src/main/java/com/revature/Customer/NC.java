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
  private static String middle;
  private static String last;
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
      try{
          PreparedStatement pstate=connection.prepareStatement("insert into customers(firstname, midname, lastname) values(?,?,?)");
          pstate.setString(1, first);
          if(middle.toLowerCase()=="none")
              middle=null;
          pstate.setString(2, middle);
          pstate.setString(3, last);
      } catch(SQLException e){
          System.out.println(e);
      }
      
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
