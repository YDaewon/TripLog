package com.triplog.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.triplog.user.model.UserDto;

@Mapper
public interface UserMapper {
	int idCheck(String userId) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(Map<String, String> map) throws SQLException;
	UserDto getUser(String userId) throws SQLException; // Detail
	void updateUser(UserDto UserDto) throws SQLException;
	void deleteUser(String userId) throws SQLException;
	
//	JWT
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

	/* Admin */
	List<UserDto> listUser() throws SQLException;
	void changePwd(String userId, String userPwd);
	int nicknameCheck(String nickname);
}
