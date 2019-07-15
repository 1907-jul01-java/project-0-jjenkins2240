/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Customer;

import java.io.*;
import java.util.*;
import java.sql.*;
        
public class Authenticate {
    private String user;
    private String pass;
    private String datauser;
    private String datapass;
    private Connection connect;
    private boolean success;
    public Authenticate(String user, String pass, Connection connect){
        this.user=user;
        this.pass=pass;
       this.connect=connect;
    }
    public Authenticate(String user, Connection connect){
        this.user=user;
        this.connect=connect;
    }
    
    public boolean validate(String uname, String upass){
        //use a prepare statement and join with custdata and customer table to find the user associated with the username and password.
      this.user=uname;
      this.pass=upass;
        NC datas;
        List<NC> data= new ArrayList<>();
        try{
  
      PreparedStatement pstate=connect.prepareStatement("select * from customers inner join custdata ON customers.id=custdata.customerid Where custdata.username=? AND custdata.password=?");
      pstate.setString(1, user);
      pstate.setString(2, pass);
      ResultSet results=pstate.executeQuery();
      while(results.next()){
         datas = new NC();
         datas.setId(results.getInt("id"));
          datas.setFirst(results.getString("firstname"));
          datas.setMiddle(results.getString("midname"));
          datas.setLast(results.getString("lastname"));
          datas.setFullname();
          data.add(datas);
          
      }
      
    }catch(SQLException e){
    System.out.println(e);}
 
        if(data.size()==0){
            return false;
        }else
            return true;

}
    public boolean validateusername(){
      
    String s = null;
      

        try{
  
      PreparedStatement pstate=connect.prepareStatement("select * from custdata Where custdata.username=?");
      pstate.setString(1, this.user);
       ResultSet results=pstate.executeQuery();
       if(!results.isBeforeFirst()){
           this.success=true;
       }else
           this.success=false;
      
     
    }catch(SQLException e){
    System.out.println(e);}

    return success;
        
    }

}