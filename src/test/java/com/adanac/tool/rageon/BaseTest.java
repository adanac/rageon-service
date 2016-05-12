package com.adanac.tool.rageon;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 */



/**
 * @author Johnny
 *
 */
@ContextConfiguration(locations={"classpath:conf/spring/spring-impl.xml","classpath:conf/spring/spring-da.xml",
		"classpath:conf/spring/jms-test.xml","classpath:conf/spring/res-test.xml"})
@TransactionConfiguration( defaultRollback=false)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

}
