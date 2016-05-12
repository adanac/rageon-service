package com.adanac.tool.rageon;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Unit test for simple App.
 */
public class AppTest   extends BaseTest
{
	
	/*  请自行修改成实际的java bean
	  @Autowired
	 App app;
	 
	 */


    /**
     * Rigourous Test :-)
     */
	@Test
    public void testApp()
    {
		System.out.println("Test 1");
        assert true : "dfd";
    }
	@Test
    public void testApp1()
    {
		System.out.println("Test 2");
        assert( true );
    }
}
