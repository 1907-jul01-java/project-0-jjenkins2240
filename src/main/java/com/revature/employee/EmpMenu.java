/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.employee;
import java.io.*;
import java.util.*;
import com.revature.Accounts.*;
import com.revature.Customer.*;
import java.sql.*;

/**
 *
 * @author tinma
 */
public class EmpMenu {
    Connection connect;
    String firstname;
    String middlename;
    String lastname;
    String fullname;
 String admin;
    int id;
    int choice;
    String user;
    String pass;
    Scanner sc=new Scanner(System.in);
    public EmpMenu(){
    }
    public EmpMenu(String user, String pass, Connection connect)throws IOException{
    
        this.user=user;
        this.pass=pass;
        this.connect=connect;
        this.getdata();
        this.EMenu();
        
    }
    public void getdata(){
        try{
            PreparedStatement pstate=connect.prepareStatement("select * from employee inner join emplogin ON employee.employeid=emplogin.employeid where emplogin.password=? AND emplogin.username=? ");
            pstate.setString(1, pass);
            pstate.setString(2,user);
            ResultSet results=pstate.executeQuery();
            results.next();
            this.firstname=results.getString("firstname");
            this.middlename=results.getString(("middlename"));
            this.lastname=results.getString("lastname");
            this.id=results.getInt("employeid");
            this.admin=results.getString("administrator");
            System.out.println(admin);
            this.setFullname();
            
        }catch(SQLException e){
            
        
    }}
    public void EMenu()throws IOException{
          System.out.println("Welcome Employee "+this.fullname);
        System.out.println("\n"+"\t"+"User Options");
        System.out.println("1."+"\t"+"View Accounts");
        System.out.println("2."+"\t"+"Manage Accounts");
        System.out.println("3."+"\t"+"Logout"+"\n");
        choice=sc.nextInt();
        Empoptions option=new Empoptions(this.connect);
        option.setAdmin(this.admin);
        switch(choice){
            case 1:
                option.view();
                this.EMenu();
                break;
            case 2:
                   option.change();
                   this.EMenu();
                break;
            case 3:
                System.out.println("Goodbye!");
                Menu mu=new Menu(this.connect);
                
                mu.startmenu();
                break;
        }
                
    }
  
    


    
    public String getFullname() {
        return fullname;
    }

    public void setFullname() {
        this.fullname = firstname+" "+middlename+" "+lastname;
    }

    public Connection getConnect() {
        return connect;
    }

    public void setConnect(Connection connect) {
        this.connect = connect;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
}
