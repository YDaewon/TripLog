package com.triplog.user.service;

import java.sql.SQLException;
import java.util.HashMap;
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
	public int nicknameCheck(String nickname) {
		return userMapper.nicknameCheck(nickname);
	}

	@Override
	public void joinUser(UserDto userDto) throws SQLException {
		userMapper.joinUser(userDto);
	}

	@Override
	public UserDto loginUser(Map<String, String> map) throws SQLException {
		UserDto dto = userMapper.loginUser(map);
		if(dto.getDeletedAt() == null) {
			return dto;
		}
		else return null;
	}

	@Override
	public UserDto getUser(String userId) throws SQLException {
		return userMapper.getUser(userId);
	}

	@Override
	public void updateUser(UserDto UserDto) throws SQLException {
		userMapper.updateUser(UserDto);
	}

	@Override
	public void deleteUser(String userId) throws SQLException {
		userMapper.deleteUser(userId);
	}

	@Override
	public List<UserDto> listUser() throws SQLException {
		return userMapper.listUser();
	}
	
//	JWT
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public void changePwd(String userId, String pwd) {
		userMapper.changePwd(userId, pwd);
		
	}


}
