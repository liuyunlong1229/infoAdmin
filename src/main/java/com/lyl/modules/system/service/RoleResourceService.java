package com.lyl.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysRole;


@Service
public class RoleResourceService {

	@Autowired
	private BaseDao	baseDao;

	public List<SysRole> findRolesByResource(SysResource resource) {
		String hql = "select rr.role from RoleResource rr where rr.resource.id=:rsid";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("rsid", resource.getId());
		List<SysRole> roleList = baseDao.findByHQL(hql, args);
		return roleList;

	}

	public List<SysResource> findResourcesByRole(SysRole role) {
		String hql = "select rr.resource from RoleResource rr where rr.role.id=:rid";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("rid", role.getId());
		List<SysResource> resourceList = baseDao.findByHQL(hql, args);
		return resourceList;

	}

}
