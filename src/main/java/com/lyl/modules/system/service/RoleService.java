package com.lyl.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.modules.system.entity.RoleResource;
import com.lyl.modules.system.entity.SysRole;

@Service
public class RoleService {

	@Autowired
	private BaseDao baseDao;

	public void saveRole(SysRole role) {

		String rid = (String) baseDao.save(role);

		if (StringUtils.isNoneBlank(role.getResourceIds())) {
			String rsids [] =StringUtils.split(role.getResourceIds(), ",");
			for (String rsid : rsids) {
				RoleResource rr = new RoleResource();
				rr.setRid(rid);
				rr.setRsid(rsid);
				baseDao.save(rr);
			}
		}

	}

	public List<SysRole> findByName(String roleName) {
		String hql = "from SysRole sr where sr.name like:roleName ";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("roleName", "%" + roleName + "%");
		List<SysRole> resouceList = baseDao.findByHQL(hql, args);
		return resouceList;

	}

	public void updateRole(SysRole role) {
		
		String rid=role.getId();
		String sql = "delete from T_SYS_ROLE_RESOURCE where rid='"+rid+"'";
		baseDao.execSQL(sql, null);

		if (StringUtils.isNoneBlank(role.getResourceIds())) {
			String rsids [] =StringUtils.split(role.getResourceIds(), ",");
			for (String rsid : rsids) {
				RoleResource rr = new RoleResource();
				rr.setRid(rid);
				rr.setRsid(rsid);
				baseDao.save(rr);
			}
		}
		
		SysRole r=baseDao.getById(SysRole.class, role.getId());
		r.setName(role.getName());
		r.setLabel(role.getLabel());
		r.setDescription(role.getDescription());

	}

	public PageData  findRolesWithPage(PageParam  pageParam) {
		return baseDao.findByHQL("from SysRole " , null, pageParam);
	}
	
	public List<SysRole>  findAll() {
		return baseDao.findAll(SysRole.class);
	}
	
	public List<SysRole> findRolesByUid(String uid){
		String hql="select r from SysRole r,UserRole ur where r.id=ur.rid and ur.uid=:uid";
		Map<String,Object> args=new HashMap<String,Object>();
		args.put("uid",uid);
		return (List<SysRole>)baseDao.findByHQL(hql,args );
		
	}

	public void deleteRole(SysRole role) {
		String sqls[] =new String[2];
		sqls[0]= "delete from T_SYS_ROLE_RESOURCE where rid='"+role.getId()+"'";
		sqls[1]= "delete from T_SYS_ROLE where id= '" + role.getId()+"'";
		baseDao.execSQLWithBatch(sqls);

	}

	public SysRole findRoleById(String id) {
		return baseDao.getById(SysRole.class, id);
	}

}
