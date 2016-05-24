package com.adanac.tool.rageon;

import org.testng.annotations.Test;

import com.adanac.framework.utils.UUIDGenerator;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseTest {

	/*
	 * 请自行修改成实际的java bean
	 * 
	 * @Autowired App app;
	 * 
	 */

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		System.out.println("Test 1");
		assert true : "dfd";
	}

	@Test
	public void testApp1() {
		System.out.println("Test 2");
		assert(true);
	}

	@Test
	public void testUUID() {
		try {
			for (int i = 0; i < 50; i++) {
				String msgId = UUIDGenerator.getInstance().get64BitUUID();
				System.out.println(msgId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 50; i++) {
				String msgId = UUIDGenerator.getInstance().get64BitUUID();
				System.out.println(msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
