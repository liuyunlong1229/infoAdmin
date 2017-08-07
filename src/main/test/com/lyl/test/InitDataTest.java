package com.lyl.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lyl.modules.info.entity.Member;
import com.lyl.modules.info.service.MemberService;
import com.lyl.modules.system.entity.RoleResource;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysResource.ResourceType;
import com.lyl.modules.system.entity.SysRole;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.entity.UserRole;
import com.lyl.modules.system.service.ResourceService;
import com.lyl.modules.system.service.RoleService;
import com.lyl.modules.system.service.UserService;


public class InitDataTest extends BaseSpringTestCase {

	@Autowired
	private UserService		userService;

	@Autowired
	private RoleService		roleService;

	@Autowired
	private ResourceService	resourceService;
	

	@Autowired
	private MemberService	memberService;

	public void testAddUser() {

		SysUser user = new SysUser();
		user.setLoginName("admin");
		user.setPassword("admin");
		user.setRealName("汤姆");
		user.setCreateDttm(new Date());
		userService.saveUser(user);
		

	}

	public void testAddRole() {

		SysRole role1 = new SysRole();
		role1.setName("管理员");
		role1.setDescription("系统内置的角色");
		role1.setLabel("ADMIN");
		baseDao.save(role1);

	}

	public void testAddResource() {

		SysResource ssr1 = new SysResource();
		ssr1.setName("系统管理");
		ssr1.setLabel("SYSTEM_VIEW");
		ssr1.setResourceType(ResourceType.module);
		baseDao.save(ssr1);

		// *********************************************************************************************//
		SysResource ssr11 = new SysResource();
		ssr11.setName("资源管理");
		ssr11.setLabel("RESOURCE_VIEW");
		ssr11.setResourceType(ResourceType.menu);
		ssr11.setResourcePath("/resource/list");
		ssr11.setPid(ssr1.getIcon());
		baseDao.save(ssr11);

		SysResource ssr111 = new SysResource();
		ssr111.setName("资源新增");
		ssr111.setLabel("RESOURCE_ADD");
		ssr111.setResourceType(ResourceType.button);
		ssr111.setResourcePath("/resource/add");
		ssr111.setPid(ssr11.getId());
		baseDao.save(ssr111);

		SysResource ssr112 = new SysResource();
		ssr112.setName("资源修改");
		ssr112.setLabel("RESOURCE_UPDATE");
		ssr112.setResourceType(ResourceType.button);
		ssr112.setResourcePath("/resource/update");
		ssr112.setPid(ssr11.getId());
		baseDao.save(ssr112);

		SysResource ssr113 = new SysResource();
		ssr113.setName("资源删除");
		ssr113.setLabel("RESOURCE_DEL");
		ssr113.setResourceType(ResourceType.button);
		ssr113.setResourcePath("/resource/delete");
		ssr113.setPid(ssr11.getId());
		baseDao.save(ssr113);

		SysResource ssr12 = new SysResource();
		ssr12.setName("用户管理");
		ssr12.setLabel("USER_VIEW");
		ssr12.setResourcePath("/user/list");
		ssr12.setResourceType(ResourceType.menu);
		ssr12.setPid(ssr1.getId());
		baseDao.save(ssr12);

		SysResource ssr121 = new SysResource();
		ssr121.setName("用户新增");
		ssr121.setLabel("USER_ADD");
		ssr121.setResourceType(ResourceType.button);
		ssr121.setResourcePath("/user/add");
		ssr121.setPid(ssr12.getId());
		baseDao.save(ssr121);

		SysResource ssr122 = new SysResource();
		ssr122.setName("用户修改");
		ssr122.setLabel("USER_UPDATE");
		ssr122.setResourceType(ResourceType.button);
		ssr122.setResourcePath("/user/update");
		ssr122.setPid(ssr12.getId());
		baseDao.save(ssr122);

		SysResource ssr123 = new SysResource();
		ssr123.setName("用户删除");
		ssr123.setLabel("USER_DEL");
		ssr123.setResourceType(ResourceType.button);
		ssr123.setResourcePath("/user/delete");
		ssr123.setPid(ssr12.getId());
		baseDao.save(ssr123);

		// *********************************************************************************************//

		SysResource ssr13 = new SysResource();
		ssr13.setName("角色管理");
		ssr13.setLabel("ROLE_VIEW");
		ssr13.setResourceType(ResourceType.menu);
		ssr13.setResourcePath("/role/list");
		ssr13.setPid(ssr1.getId());
		baseDao.save(ssr13);

		SysResource ssr131 = new SysResource();
		ssr131.setName("角色新增");
		ssr131.setLabel("ROLE_ADD");
		ssr131.setResourceType(ResourceType.button);
		ssr131.setResourcePath("/role/add");
		ssr131.setPid(ssr13.getId());
		baseDao.save(ssr131);

		SysResource ssr132 = new SysResource();
		ssr132.setName("角色修改");
		ssr132.setLabel("ROLE_UPDATE");
		ssr132.setResourceType(ResourceType.button);
		ssr132.setResourcePath("/role/update");
		ssr132.setPid(ssr13.getId());
		baseDao.save(ssr132);

		SysResource ssr133 = new SysResource();
		ssr133.setName("角色删除");
		ssr133.setLabel("ROLE_DEL");
		ssr133.setResourceType(ResourceType.button);
		ssr133.setResourcePath("/role/delete");
		ssr133.setPid(ssr13.getId());
		baseDao.save(ssr133);

		// *********************************************************************************************//

	}

	public void testSetRelation() {

		//建立用户和角色的关联
		SysUser user = userService.findByLoginName("admin");
		SysRole role = roleService.findByName("管理员").get(0);
		UserRole ur = new UserRole();
		ur.setUid(user.getId());
		ur.setRid(role.getId());
		baseDao.save(ur);

		// 建立角色和资源的关联
		List<SysResource> allResources = resourceService.findAll();

		for (SysResource sysResource : allResources) {
			RoleResource rrs = new RoleResource();
			rrs.setRid(role.getId());
			rrs.setRsid(sysResource.getId());
			baseDao.save(rrs);
		}

	}

	@Test
	@Rollback(false)
	public void init() {
		baseDao.execSQL("delete from  T_SYS_ROLE_RESOURCE",null); //角色资源表
		baseDao.execSQL("delete from  T_SYS_ORG_RESOURCE",null); //组织资源表
		baseDao.execSQL("delete from  T_SYS_USER_ROLE",null); //用户角色表
		baseDao.execSQL("truncate table  T_SYS_USER_ORGANIZATION",null); //用户组织表
		baseDao.execSQL("truncate table  T_SYS_RESOURCE",null); //资源表
		baseDao.execSQL("delete from  T_SYS_USER",null); //用户表
		baseDao.execSQL("delete from  T_SYS_ROLE",null); //角色表
		baseDao.execSQL("delete from  T_SYS_ORGANIZATION",null); //组织表

		testAddUser();
		testAddRole();
		testAddResource();
		testSetRelation();

	}
	
	
	@Test
	@Rollback(false)
	public void testAddMember() {

		for(int i=1;i<=100;i++){
			Member member = new Member();
			member.setName("张三"+i);
			member.setGender(i%2==0?"1":"0");
			member.setType(0);
			member.setMobile("13288888888");
			member.setAlipayNum("支付宝号"+i);
			member.setWeChatNum("微信号"+i);
			member.setEmail("邮箱号"+i);
			member.setAddress("地址"+i);
			memberService.saveMember(member);
		}
		
	}

}
