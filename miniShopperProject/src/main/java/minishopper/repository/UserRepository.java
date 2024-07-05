package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUserId(String userId);

}
