package minishopper.service;

import java.util.List;

import minishopper.dto.UserDto;
import minishopper.entity.User;

public interface UserService {

	public User saveUser(User user);

	public User checkUserId(String userId);

	public UserDto fetchUserDetailsById(String userId);

	public UserDto updateUser(String userId, UserDto userDto);
}
