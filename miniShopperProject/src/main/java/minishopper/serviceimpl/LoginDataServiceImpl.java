package minishopper.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minishopper.entity.LoginData;
import minishopper.repository.LoginDataRepository;
import minishopper.service.LoginDataService;

@Service
public class LoginDataServiceImpl implements LoginDataService {

	@Autowired
	private LoginDataRepository loginDataRepo;

	@Override
	public LoginData saveLoginData(LoginData loginData) {

		return loginDataRepo.save(loginData);
	}

}
