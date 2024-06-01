package ElectronicsBillingApp.ElectronicsBillingApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Sale;

public interface SaleReposiroty extends JpaRepository<Sale, Long> {

	List<Sale> findByUserid(Long userid);
}
