package com.syx.sboot.webback.service.extend;

import com.syx.sboot.common.access.SqlUtil;
import com.syx.sboot.common.access.sql.SqlQuery;
import com.syx.sboot.common.utils.Digests;
import com.syx.sboot.common.utils.Encodes;
import com.syx.sboot.common.utils.MD5Util02;
import com.syx.sboot.webback.entity.Sysstaff;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


@Service("sysUserService")
public class SysUserService implements InitializingBean {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;



	public Sysstaff getUserByLoginName(String username, String userpwd) {
		SqlQuery query = new SqlQuery();
		query.appendSql(" and loginname = ? and loginpwd = ?");
		query.addParam(username);
		query.addParam(MD5Util02.getMD5_32(userpwd));
		Sysstaff staff = SqlUtil.getOnly("", "sysstaff", Sysstaff.class, query);
		return staff;
	}
	
	public Sysstaff getUserById(String userid) {
		SqlQuery query = new SqlQuery();
		query.appendSql(" and id = ? and deleteflag='0' and loginflag = '1' ");
		query.addParam(userid);
		Sysstaff staff = SqlUtil.getOnly("", "sysstaff", Sysstaff.class, query);
		return staff;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
}