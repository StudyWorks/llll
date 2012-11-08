package com.learning;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;


@ContextConfiguration({ "classpath:applicationContext.xml" })
public class BaseTestCase extends AbstractTransactionalTestNGSpringContextTests{
	
}
