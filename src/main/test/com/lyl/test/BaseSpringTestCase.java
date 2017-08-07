package com.lyl.test;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lyl.base.dao.BaseDao;

/**
 * Spring集成测试通用父类
 */
@ContextConfiguration(locations = { "classpath:/spring/spring-context.xml"})
public class BaseSpringTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	protected BaseDao	baseDao;

	protected Logger	logger	= Logger.getLogger(getClass());

	public BaseSpringTestCase() {

	}

}