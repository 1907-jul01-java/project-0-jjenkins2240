/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.employee;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tinma
 */
public class EmpMenuIT {
    
    public EmpMenuIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConnect method, of class EmpMenu.
     */
    @Test
    public void testGetConnect() {
        System.out.println("getConnect");
        EmpMenu instance = new EmpMenu();
        Connection expResult = null;
        Connection result = instance.getConnect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConnect method, of class EmpMenu.
     */
    @Test
    public void testSetConnect() {
        System.out.println("setConnect");
        Connection connect = null;
        EmpMenu instance = new EmpMenu();
        instance.setConnect(connect);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstname method, of class EmpMenu.
     */
    @Test
    public void testGetFirstname() {
        System.out.println("getFirstname");
        EmpMenu instance = new EmpMenu();
        String expResult = "";
        String result = instance.getFirstname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFirstname method, of class EmpMenu.
     */
    @Test
    public void testSetFirstname() {
        System.out.println("setFirstname");
        String firstname = "";
        EmpMenu instance = new EmpMenu();
        instance.setFirstname(firstname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMiddlename method, of class EmpMenu.
     */
    @Test
    public void testGetMiddlename() {
        System.out.println("getMiddlename");
        EmpMenu instance = new EmpMenu();
        String expResult = "";
        String result = instance.getMiddlename();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMiddlename method, of class EmpMenu.
     */
    @Test
    public void testSetMiddlename() {
        System.out.println("setMiddlename");
        String middlename = "";
        EmpMenu instance = new EmpMenu();
        instance.setMiddlename(middlename);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastname method, of class EmpMenu.
     */
    @Test
    public void testGetLastname() {
        System.out.println("getLastname");
        EmpMenu instance = new EmpMenu();
        String expResult = "";
        String result = instance.getLastname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastname method, of class EmpMenu.
     */
    @Test
    public void testSetLastname() {
        System.out.println("setLastname");
        String lastname = "";
        EmpMenu instance = new EmpMenu();
        instance.setLastname(lastname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAdmin method, of class EmpMenu.
     */
    @Test
    public void testGetAdmin() {
        System.out.println("getAdmin");
        EmpMenu instance = new EmpMenu();
        char expResult = ' ';
        char result = instance.getAdmin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAdmin method, of class EmpMenu.
     */
    @Test
    public void testSetAdmin() {
        System.out.println("setAdmin");
        char admin = ' ';
        EmpMenu instance = new EmpMenu();
        instance.setAdmin(admin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class EmpMenu.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        EmpMenu instance = new EmpMenu();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class EmpMenu.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        EmpMenu instance = new EmpMenu();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
