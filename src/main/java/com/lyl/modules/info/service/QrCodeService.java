package com.lyl.modules.info.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.lyl.base.dao.BaseDao;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.util.qrCode.MatrixToImageWriter;
import com.lyl.modules.info.entity.QrCode;
@Service
public class QrCodeService {

	@Autowired
	private BaseDao			baseDao;
	
	

	public PageData findQrCodeWithPage(QrCode qrCode, PageParam pageParam) {
		StringBuffer hqlBuffer = new StringBuffer("select q from QrCode q where 1=1");
		Map<String,Object> args=new HashMap<String,Object>();
 		if(StringUtils.isNotBlank(qrCode.getCode())){
			hqlBuffer.append(" and q.code=:code ");
			args.put("code", qrCode.getCode());
		}
 		if(StringUtils.isNoneBlank(qrCode.getName())){
 			hqlBuffer.append(" and q.name like :name ");
			args.put("name", "%"+qrCode.getName()+"%");
 		}
		return baseDao.findByHQL(hqlBuffer.toString(),args, pageParam);
	}

	public void saveQrCode(QrCode qrCode) {
		
		 String content = qrCode.getWebUrl();
		 
		 String path = "E:/qrCode";
		 String picUrl=qrCode.getCode()+".jpg";
		 qrCode.setPicUrl(path+"/"+picUrl);
		 
		 
		 boolean result=true;
		 try {
			 MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			 Map hints = new HashMap();
			 hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			 BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
			 File file1 = new File(path,picUrl);
			 MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
			
		}
		 if(result){
			baseDao.save(qrCode); 
		 }
		
	
		
	}

	public QrCode getQrCodeById(String qrId) {
		return baseDao.getById(QrCode.class, qrId);
	}


}
