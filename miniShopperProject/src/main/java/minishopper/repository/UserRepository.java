package minishopper.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import minishopper.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUserId(String userId);
	

}
