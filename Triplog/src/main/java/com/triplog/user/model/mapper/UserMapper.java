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
	UserDto getUser(int userNo) throws SQLException; // Detail
	void updateUser(UserDto UserDto, @Param("admin_check") int admin_check) throws SQLException;
	void deleteUser(int userNo) throws SQLException;
	
	/* Admin */
	List<UserDto> listUser() throws SQLException;
}
