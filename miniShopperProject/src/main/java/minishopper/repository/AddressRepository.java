package minishopper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import minishopper.entity.Address;
import minishopper.entity.User;

public interface AddressRepository extends JpaRepository<Address, Integer>{
      List<Address> findByUser(User user);
      
  	@Transactional
  	@Modifying
  	@Query("update address a set a.addressLine = :addressLine, a.addressType = :addressType, a.city = :city, a.pinCode = :pinCode, a.state = :state, a.street = :street,  a.phoneNumber = :phoneNumber where a.addressId = :addressId")
  	void updateAddressById(@Param("addressId") int addressId, @Param("addressLine") String addressLine,@Param("addressType") String addressType,  
  			                        @Param("city") String city, @Param("pinCode") String pinCode, @Param("state") String state,@Param("street") String street,@Param("phoneNumber") String phoneNumber); 


}
