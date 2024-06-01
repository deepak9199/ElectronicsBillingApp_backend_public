package ElectronicsBillingApp.ElectronicsBillingApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.SaleItem;

public interface SaleItemReposiroty extends JpaRepository<SaleItem, Long> {
	List<SaleItem> findByReferenceid(Long referenceid);

	List<SaleItem> findByUserid(Long userid);
}
