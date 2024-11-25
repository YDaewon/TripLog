package com.triplog.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.triplog.user.model.UserDto;

public interface UserService {
	int idCheck(String userId) throws SQLException;
	int nicknameCheck(String nickname);
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(Map<String, String> map) throws SQLException;
	UserDto getUser(String userId) throws SQLException; // Detail
	void updateUser(UserDto UserDto) throws SQLException;
	void deleteUser(String userId) throws SQLException;
	
	//JWT
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	
	/* Admin */
	List<UserDto> listUser() throws SQLException;
	void changePwd(String userId, String pwd);

}
