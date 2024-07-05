package minishopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.entity.LoginData;

public interface LoginDataRepository extends JpaRepository<LoginData, Integer> {

}
