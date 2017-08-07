package com.lyl.modules.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.base.vo.ResourceVO;
import com.lyl.base.vo.TreeVo;
import com.lyl.modules.system.entity.SysResource;

@Service
public class ResourceService {

	@Autowired
	private BaseDao	baseDao;

	public SysResource findById(String Id) {
		return baseDao.getById(SysResource.class, Id);
	}

	public void saveResource(SysResource resource) {
		if(StringUtils.isNotBlank(resource.getPid())){
			SysResource parent=baseDao.getById(SysResource.class, resource.getPid());
			parent.setIsLeaf(false);
		}
		baseDao.save(resource);
	}

	public void updateResource(SysResource resource) {
		SysResource rs=baseDao.getById(SysResource.class,resource.getId());
		rs.setName(resource.getName());
		rs.setLabel(resource.getLabel());
		rs.setResourcePath(resource.getResourcePath());
		
	}

	@SuppressWarnings("unchecked")
	public List<SysResource> findResourcesByLoginName(String loginName) {
		String hql = "select rs from SysResource rs ,RoleResource rr ,SysRole r,UserRole ur,SysUser u where rs.id=rr.rsid and rr.rid=r.id and r.id=ur.rid and ur.uid=u.id and u.loginName =:loginName";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("loginName", loginName);
		List<SysResource> resouceList = baseDao.findByHQL(hql, args);
		return resouceList;

	}

	public List<SysResource> findAll() {
		return baseDao.findAll(SysResource.class);
	}

	public List<SysResource> findByName(String resName) {
		String hql = "from SysResource rs where rs.name like:rsName ";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("rsName", "%" + resName + "%");
		List<SysResource> resouceList = baseDao.findByHQL(hql, args);
		return resouceList;

	}

	public void delResById(String rsid) {
		    SysResource rs=baseDao.getById(SysResource.class, rsid);
		    
			baseDao.execSQL("delete from  T_SYS_ROLE_RESOURCE where rsid= '"+rsid+"'",null); //角色资源表
			baseDao.execSQL("delete from  T_SYS_ORG_RESOURCE where rsid='"+rsid+"'" ,null); //组织资源表
			baseDao.execSQL("delete from  T_SYS_RESOURCE where id='"+rsid+"'",null); //资源表
			
			//查询父节点，判断父节点下面还有没有子节点，如果没有，修改是否是叶子节点的标识位
			if(rs.getPid()  != null){
				 SysResource parent=baseDao.getById(SysResource.class,  rs.getPid());
				    List<SysResource> childList= getDirectChilds(parent);
				    if(childList.isEmpty()){
				    	parent.setIsLeaf(true);
				    }
			}
		   
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TreeVo> findResourceTreeByRid(String rid) {
		List<TreeVo> resourceTree=new ArrayList<TreeVo>();
		List selectIds=new ArrayList();

		if(StringUtils.isNotBlank(rid)){
			selectIds=baseDao.findBySQL("SELECT  rs.rsid FROM T_SYS_ROLE_RESOURCE rs WHERE rs.rid='"+rid+"'", null);
		}
		
		List<SysResource> allResources=baseDao.findAll(SysResource.class);
		
		for(SysResource rs:allResources){
			if(StringUtils.isBlank(rs.getPid())){ //从根节点开始遍历
				TreeVo tv=construct(rs,allResources,selectIds);
				resourceTree.add(tv);
			}
		}
		
		return resourceTree;
		
		
	}
	
	
	public TreeVo construct(SysResource child,List<SysResource> allResources,List<String> selectedIds){
		TreeVo tv=bulidTreeVo(child,selectedIds);
		for(SysResource rs:allResources){
			if(child.getId().equals(rs.getPid())){ //非叶子节点
				TreeVo t=construct(rs,allResources,selectedIds);  //递归
				tv.getChildren().add(t);//没有子节点后，加入集合
			}
		}
	    return tv;
		
	}
	


	public TreeVo bulidTreeVo(SysResource rs,List<String> roleResourceIds){
		TreeVo tv=new TreeVo();
		tv.setId(rs.getId());
		tv.setText(rs.getName());
		tv.setChecked(roleResourceIds.contains(rs.getId()));
		return tv;
		
	}

	public List<ResourceVO> findAllResourceVos() {

		ResourceVO root = new ResourceVO("0", "系统资源", "", "根节点", "", "", "",false,1);
		List<SysResource> allResource = baseDao.findAll(SysResource.class);
		List<ResourceVO> allResourceVos = new ArrayList<ResourceVO>();
		ResourceVO rv = null;
		for (SysResource rs : allResource) {
			if(StringUtils.isBlank(rs.getPid())){
				rs.setPid("0");//虚拟根节点的id
			}
			rv = new ResourceVO();
			rv.setId(rs.getId().toString());
			rv.setName(rs.getName());
			rv.setLabel(rs.getLabel());
			rv.setIcon(rs.getIcon());
			rv.set_parentId(rs.getPid());
			rv.setResourcePath(rs.getResourcePath());
			rv.setResourceType(rs.getResourceType().getText());
			rv.setIsLeaf(rs.getIsLeaf());
			allResourceVos.add(rv);
		}
		allResourceVos.add(0, root);
		return allResourceVos;

	}

	
	
	/**
	 * 获取当前资源的各级子节点
	 * @param resource
	 * @return
	 */
	public SysResource getChildListCascade(SysResource resource) {
		List<SysResource> childs=getDirectChilds(resource);
		if(childs.isEmpty()){ 
			for(SysResource rs:childs){
				 getChildListCascade(rs) ;
			}
		}
		Set<SysResource> childSet=new HashSet<SysResource>();
		childSet.addAll(childs);
		resource.getSubList().addAll(childSet);
		return resource;
		
	}
	
	/**
	 * 获取一级子节点，
	 * @param resource
	 * @return
	 */
	public List<SysResource> getDirectChilds(SysResource resource){
		String hql="select rs from SysResource rs where rs.pid='"+resource.getId()+"'";
		List<SysResource> resourceList=baseDao.findByHQL(hql, null);
		return resourceList;
	}
	
	



}
