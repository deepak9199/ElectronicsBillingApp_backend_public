package ElectronicsBillingApp.ElectronicsBillingApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Customer;



public interface CustomerReposiroty extends JpaRepository<Customer, Long> {
	List<Customer> findByUserid(Long userid);

	List<Customer> findByName(String name);
}
