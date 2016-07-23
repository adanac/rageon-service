package com.adanac.tool.rageon;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 */

/**
 * @author Johnny
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring/spring-impl.xml", "classpath:conf/spring/spring-config.xml",
		"classpath:conf/spring/spring-res-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

}
