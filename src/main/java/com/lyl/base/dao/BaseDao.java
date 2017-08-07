package com.lyl.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;


public class BaseDao {
	
	private static Logger	LOG	= Logger.getLogger(BaseDao.class);
	
	SessionFactory sessionFactory;
	
	
	/**
	 * 获取session
	 * @return
	 */
	protected Session getSession() {
		Session session=sessionFactory.getCurrentSession();
		if(session==null){
		    session=sessionFactory.openSession();
		}
		return session;
	}

	/**
	 * 保存方法
	 * @param obj 需要保存的实体
	 */
	public Serializable save(Object obj){
		return getSession().save(obj);
	}
	
	/**
	 * 删除方法
	 * @param obj 需要删除的实体
	 */
	public void delete(Object obj){
		getSession().delete(obj);
	}
	
	/**
	 * 更新方法
	 * @param obj 需要更新是实体
	 */
	public void update(Object obj){
		getSession().update(obj);
	}
	
	
	/**
	 * 根据主键查询单个对象
	 * @param clazz 类对象
	 * @param id 主键
	 * @return 查询的单个对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T getById(Class<T> clazz,Serializable id){
		return (T) getSession().get(clazz, id);
	}
	
	/**
	 * 根据类对象查询所有结果
	 * @param clazz 类对象
	 * @return 类对象代表的所有结果集
	 */
	public List findAll(Class  clazz ){
		String hql=" from "+clazz.getName();
		return findByHQL(hql,null,null,null);
		
	}
	

	/**
	 * 根据HQL查询结果集
	 * @param hql hibernate 查询语句
	 * @param paramMap 命名参数条件
	 * @return 符合条件的结果集
	 */
	public List findByHQL(String hql,Map<String,Object> paramMap) {
		return findByHQL(hql,paramMap,null,null);
		
	}
	
	/**
	 * 根据HQL分页查询结果集
	 * @param hql hibernate查询语句
	 * @param paramMap 命名参数条件
	 * @param start 开始行
	 * @param pageSize 每页数量
	 * @return 符合条件的结果集
	 */
	public List findByHQL(String hql,Map<String,Object> paramMap,Integer start,Integer pageSize){
		Query query=getSession().createQuery(hql);
		applyNamedParameterToQuery(query,paramMap);
		if(start !=null){
			query.setFirstResult(start);
		}
		if(pageSize !=null){
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	
	/**
	 * 根据条件hql查询单个对象
	 * @param hql hibernate查询语句
	 * @param paramMap 命名参数
	 * @return 符合条件的单个对象
	 */
	public Object findUniqueResultByHQL(String hql,Map<String,Object> paramMap){
		Query query=getSession().createQuery(hql);
		applyNamedParameterToQuery(query,paramMap);
		return query.uniqueResult();
	}
	
	
	/**
	 * 根据条件sql查询结果集
	 * @param sql 原生sql语句
	 * @param paramMap 命名参数
	 * @return 符合条件的结果集
	 */
	public List findBySQL(String sql,Map<String,Object> paramMap){
		return findBySQL(sql,paramMap,null,null);
	}
	
	/**
	 * 根据条件sql分页查询结果集
	 * @param sql 原生sql语句
	 * @param paramMap 命名参数
	 * @param start 开始行
	 * @param pageSize 每页记录数
	 * @return 符合条件的结果集
	 */
	public List findBySQL(String sql,Map<String,Object> paramMap,Integer start,Integer pageSize){
		SQLQuery  query=getSession().createSQLQuery(sql);
		applyNamedParameterToQuery(query,paramMap);
		if(start !=null){
			query.setFirstResult(start);
		}
		if(pageSize !=null){
			query.setFetchSize(pageSize);
		}
		return query.list();
	}
	
	
	/**
	 * 根据条件sql查询单个结果
	 * @param sql 原生sql语句
	 * @param paramMap 命名参数
	 * @return 单个结果
	 */
	public Object findUniqueResultBySQL(String sql,Map<String,Object> paramMap){
		SQLQuery query=getSession().createSQLQuery(sql);
		applyNamedParameterToQuery(query,paramMap);
		return query.uniqueResult();
	}
	
	
	/**
	 * 执行原生sql语句
	 * @param sql 原生sql
	 * @param paramMap 命名参数
	 * @return 影响记录数
	 */
	public int execSQL(String sql,Map<String,Object> paramMap){
		SQLQuery  query=getSession().createSQLQuery(sql);
		applyNamedParameterToQuery(query,paramMap);
		return query.executeUpdate();
	}
	
	/**
	 * 批量执行原生sql语句
	 * @param sql 原生sql
	 * @return 影响总记录数
	 */
	public int execSQLWithBatch(String sql[]){
		int result=0;
		for(int i=0;i<sql.length;i++){
			SQLQuery  query=getSession().createSQLQuery(sql[i]);
			applyNamedParameterToQuery(query,null);
			result+=query.executeUpdate();
		}
		return result;
		
	}
	
	/**
	 * 设置命名参数
	 * @param queryObject 查询query
	 * @param paramMap	参数map
	 * @throws HibernateException
	 */
	protected void applyNamedParameterToQuery(Query queryObject, Map<String,Object> paramMap)
			throws HibernateException {

		if(paramMap ==null || paramMap.isEmpty()){
			return;
		}
		
		Iterator<Entry<String, Object>> it=paramMap.entrySet().iterator();
		String paramName=null;
		Object value=null;
		while(it.hasNext()){
			Entry<String, Object>  en=it.next();
			paramName=en.getKey();
			value=en.getValue();
			if (value instanceof Collection) {
				queryObject.setParameterList(paramName, (Collection<?>) value);
			}
			else if (value instanceof Object[]) {
				queryObject.setParameterList(paramName, (Object[]) value);
			}
			else {
				queryObject.setParameter(paramName, value);
			}
			//开启查询缓存，将会把把hql语句和参数一起作为参数，查询结果作为值，放入缓存，并不需要开启二级缓存
			//但是必须设置hibernate.cache.provider_class,否则会启动报错，提示无法使用
			//queryObject.setCacheable(true); 
		}
		
	}
	
	/**
	 *  分页查询结果集
	 * @param hql 查询hql语句
	 * @param paramMap 参数map
	 * @param pageParam 页面参数
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData findByHQL (String hql,Map<String,Object> paramMap,PageParam pageParam){
		PageData pageData=new PageData();
		Integer start=pageParam.getStart();
		Integer pageSize=pageParam.getPageSize();
		List result=findByHQL(hql, paramMap, start, pageSize);
		pageData.setRows(result);
		StringBuffer strBuff = new StringBuffer("select count(id) ");
		strBuff.append(hql.substring(hql.indexOf("from")));
		hql=strBuff.toString();
		//LOG.info("construct hql to count total rows === "+hql);
		Integer totalRows=Integer.valueOf(findUniqueResultByHQL(hql, paramMap).toString());
		pageData.setTotal(totalRows);
		return pageData;
		
	}
	
	/**
	 *  分页查询结果集
	 * @param sql 查询sql语句
	 * @param paramMap 参数map
	 * @param pageParam 页面参数
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData findBySQL (String sql,Map<String,Object> paramMap,PageParam pageParam){
		PageData pageData=new PageData();
		Integer start=pageParam.getStart();
		Integer pageSize=pageParam.getPageSize();
		List result=findBySQL(sql, paramMap, start, pageSize);
		pageData.setRows(result);
		StringBuffer strBuff = new StringBuffer("select count(id) ");
		strBuff.append(sql.substring(sql.indexOf("from")));
		sql=strBuff.toString(); 
		LOG.info("construct hql to count total rows=== "+sql);
		Integer totalRows=Integer.valueOf(findUniqueResultBySQL(sql, paramMap).toString());
		pageData.setTotal(totalRows);
		return pageData;
		
	}
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
