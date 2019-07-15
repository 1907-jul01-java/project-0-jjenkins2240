/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.Accounts;

import java.io.*;
import java.sql.*;
abstract class bankaccounts {
    
    Connection connect;
    int customerid;
    abstract public void create()throws IOException;
    abstract  public void view();
    abstract public void change()throws IOException;
    abstract public void deposit(int id)throws IOException;
    abstract public void withdraw(int id)throws IOException;
    abstract public void closeacct(int id)throws IOException;
}
