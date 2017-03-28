package com.yonyou.iuap.example.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.example.service.SysUserService;
import com.yonyou.iuap.iweb.exception.ValidException;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

/**
 * 用户校验器 Created by gu
 */
@Component
public class SysUserValidator {

    @Autowired
    private SysUserService sysUserService;

    /**保存时候校验 */
    public void validAdd( SysUser  user) {
        StringBuilder builder = new StringBuilder();
        List<SysUser> res = sysUserService.findByUserCode(user.getUsercode()) ;
        if ( res!= null && res.size()>0 ) {
            builder.append(user.getUsercode()).append(",");
        }
        if (builder.toString().length() > 0) {
            String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
            StringBuilder msgBuilder = new StringBuilder("编码为");
            msgBuilder.append(codeStr).append("的记录已经存在!");
            throw new ValidException(msgBuilder.toString());
        }
        this.validNotNull(user);
    }
    
    /**更新时候校验 */
    public void validUpdate( SysUser  user) {
    	StringBuilder builder = new StringBuilder();
    	List<SysUser> res = sysUserService.findByUserCode(user.getUsercode(),user.getId() ) ;
    	if (res != null && res.size()>0) {
    		builder.append(user.getUsercode()).append(",");
    	}
    	if (builder.toString().length() > 0) {
    		String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
    		StringBuilder msgBuilder = new StringBuilder("编码为");
    		msgBuilder.append(codeStr).append("的记录已经存在!");
    		throw new ValidException(msgBuilder.toString());
    	}
    	this.validNotNull(user);
    }
    
     
    private void validNotNull(SysUser  user){
    	StringBuilder builder = new StringBuilder();
    	List<SysUserJob> jobList = user.getIdSysUserJob() ;
    	if(jobList!=null && jobList.size()>0){
    		for(SysUserJob job : jobList){
    			if( StringUtil.isEmpty( job.getDeptid() )  ){
    				builder.append("用户任职的部门不能为空").append(",");
    			}if(StringUtil.isEmpty(job.getOrgid() )  ){
    				builder.append("用户任职的组织不能为空").append(",");
    			}
    			if(builder.toString().length() > 0){
    				throw new ValidException(builder.toString());
    			}
    		}
    	}
    }
    
    
}
