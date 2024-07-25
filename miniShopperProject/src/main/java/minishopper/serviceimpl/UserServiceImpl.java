package minishopper.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import minishopper.dto.AddressDto;
import minishopper.dto.UserDto;
import minishopper.entity.Address;
import minishopper.entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.repository.AddressRepository;
import minishopper.repository.UserRepository;
import minishopper.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(UserDto userDto) {
		// TODO Auto-generated method stub
		Address address = modelMapper.map(userDto.getAddress().get(0), Address.class);
		User user = modelMapper.map(userDto, User.class);
		address.setUser(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		addressRepository.save(address);
		return savedUser;
	}

	@Override
	public User checkUserId(String userId) {
		// TODO Auto-generated method stub
		return userRepository.findByUserId(userId);
	}

	@Override
	public UserDto fetchUserDetailsById(String userId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
		List<Address> allAddress = addressRepository.findByUser(user);
		List<AddressDto> allAddressDto = new ArrayList<>();
		for (int i = 0; i < allAddress.size(); i++) {
			allAddressDto.add(modelMapper.map(allAddress.get(i), AddressDto.class));
		}
		UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.setAddress(allAddressDto);
		return userDto;
	}

	@Override
	public UserDto updateUser(String userId, AddressDto addressDto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
		addressRepository.updateAddressById(addressDto.getAddressId(), addressDto.getAddressLine(),
				addressDto.getAddressType(), addressDto.getCity(), addressDto.getPinCode(), addressDto.getState(),
				addressDto.getStreet(), addressDto.getPhoneNumber());
		List<AddressDto> listOfAddress = new ArrayList<>();
		listOfAddress.add(addressDto);
		UserDto savedUser = modelMapper.map(user, UserDto.class);
		savedUser.setAddress(listOfAddress);
		return savedUser;
	}

	@Override
	public UserDto addAddress(String userId, AddressDto addressDto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
		Address address = modelMapper.map(addressDto, Address.class);
		address.setUser(user);
		Address addedAddress = addressRepository.save(address);

		AddressDto addedAddressDto = modelMapper.map(addedAddress, AddressDto.class);
		List<AddressDto> listOfAddress = new ArrayList<>();
		listOfAddress.add(addedAddressDto);
		UserDto savedUser = modelMapper.map(user, UserDto.class);
		savedUser.setAddress(listOfAddress);

		return savedUser;
	}

}
