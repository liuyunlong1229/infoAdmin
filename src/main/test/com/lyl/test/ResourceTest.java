/**
 * 
 */
package com.lyl.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.service.ResourceService;

public class ResourceTest extends BaseSpringTestCase {

	@Autowired
	ResourceService	resourceService;

	@Test
	public void test() {
		SysResource rs =resourceService.findById("402883e4519540500151954054b10002");
		SysResource resource = resourceService.getChildListCascade(rs);
		System.out.println(resource.getSubList().size());
	}

}
