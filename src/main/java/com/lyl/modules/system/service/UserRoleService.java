package com.lyl.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.modules.system.entity.SysRole;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.entity.UserRole;

@Service
public class UserRoleService {

	@Autowired
	private BaseDao	baseDao;

	public void saveUserRole(UserRole ur) {
		baseDao.save(ur);

	}
	
	public List<SysRole> findRolesByUser(SysUser user){
		String hql="select r from  SysRole r ,UserRole ur where ur.rid=r.id and  ur.uid=:uid";
		Map<String,Object> args=new HashMap<String,Object>();
		args.put("uid", user.getId());
		List<SysRole> roleList=baseDao.findByHQL(hql, args);
		return roleList;
		
	}
	
	public List<SysRole> findRolesByUserId(String  userId){
		SysUser user=new SysUser();
		user.setId(userId);
		List<SysRole> roleList=findRolesByUser(user);
		return roleList;
		
	}
}
