package com.triplog.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.triplog.user.model.UserDto;
import com.triplog.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public int idCheck(String userId) throws SQLException {
		return userMapper.idCheck(userId);
	}

	@Override
	public void joinUser(UserDto userDto) throws SQLException {
		userMapper.joinUser(userDto);
	}

	@Override
	public UserDto loginUser(Map<String, String> map) throws SQLException {
		return userMapper.loginUser(map);
	}

	@Override
	public UserDto getUser(int userNo) throws SQLException {
		return userMapper.getUser(userNo);
	}

	@Override
	public void updateUser(UserDto UserDto, int admin_check) throws SQLException {
		userMapper.updateUser(UserDto, admin_check);
	}

	@Override
	public void deleteUser(int userNo) throws SQLException {
		userMapper.deleteUser(userNo);
	}

	@Override
	public List<UserDto> listUser() throws SQLException {
		return userMapper.listUser();
	}

}
