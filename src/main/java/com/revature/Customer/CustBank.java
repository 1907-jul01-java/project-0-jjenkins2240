package com.revature.Customer;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
public class CustBank {

  public static void main(String args[])throws IOException{
     
        ConnectionUtil connectionUtil = new ConnectionUtil();
        
       
      Menu m=new Menu(connectionUtil.getConnection());
      m.startmenu();
        connectionUtil.close();
                
      
    
  }
 
}
