package minishopper.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import minishopper.dto.AddressDto;
import minishopper.dto.UserDto;
import minishopper.entity.User;

public interface UserService {

	@Cacheable(value = "saveUsers", key = "#userDto.userId")
	public User saveUser(UserDto userDto);

	@Cacheable(value = "checkUser", key = "#userId")
	public User checkUserId(String userId);

	@Cacheable(value = "userDetails", key = "#userId")
	public UserDto fetchUserDetailsById(String userId);

	@Caching(evict = { @CacheEvict(value = "users"), @CacheEvict(value = "checkUser", key = "#userId"),
			@CacheEvict(value = "userDetails", key = "#userId") })
	public UserDto addAddress(String userId, AddressDto address);

	@Caching(evict = { @CacheEvict(value = "users", key = "#userId"), @CacheEvict(value = "checkUser", key = "#userId"),
			@CacheEvict(value = "userDetails", key = "#userId") })
	public UserDto updateUser(String userId, AddressDto address);
}
