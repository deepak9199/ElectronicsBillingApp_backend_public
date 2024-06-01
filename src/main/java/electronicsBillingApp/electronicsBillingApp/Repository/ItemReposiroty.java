package ElectronicsBillingApp.ElectronicsBillingApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Item;



public interface ItemReposiroty extends JpaRepository<Item, Long> {
	List<Item> findByUserid(Long userid);
}
