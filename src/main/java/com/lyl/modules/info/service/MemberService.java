package com.lyl.modules.info.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.modules.info.entity.Member;
import com.lyl.modules.info.entity.QrCode;
@Service
public class MemberService {

	@Autowired
	private BaseDao			baseDao;


	public PageData findMemberWithPage(Member member,PageParam pageParam) {
		
		StringBuffer hqlBuffer = new StringBuffer("select u from Member u where 1=1");
		Map<String,Object> args=new HashMap<String,Object>();
 		if(StringUtils.isNotBlank(member.getName())){
			hqlBuffer.append(" and u.name=:name ");
			args.put("name", member.getName());
		}
 		if(StringUtils.isNotBlank(member.getGender())){
			hqlBuffer.append(" and u.gender=:gender ");
			args.put("gender", member.getGender());
		}
		return baseDao.findByHQL(hqlBuffer.toString(),args, pageParam);
	}


	public void saveMember(Member member) {
		baseDao.save(member);
	}


	public Member getMemberById(String mid) {
		return baseDao.getById(Member.class, mid);
	}


	public PageData findQrCodeWithPage(QrCode qrCode, PageParam pageParam) {
		
		StringBuffer hqlBuffer = new StringBuffer("select q from Qrcode q where 1=1");
		Map<String,Object> args=new HashMap<String,Object>();
 		if(StringUtils.isNotBlank(qrCode.getCode())){
			hqlBuffer.append(" and q.code=:code ");
			args.put("code", qrCode.getCode());
		}
		return baseDao.findByHQL(hqlBuffer.toString(),args, pageParam);
	}


	public void sendMoney(String id, String sendMoney) {
		Member member=baseDao.getById(Member.class, id);
		member.setSendMoney(Double.valueOf(sendMoney));
	}

}
