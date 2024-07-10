package minishopper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import minishopper.dto.AddressDto;
import minishopper.entity.Address;
import minishopper.entity.User;

public interface AddressRepository extends JpaRepository<Address, Integer>{
      List<Address> findByUser(User user);
}
