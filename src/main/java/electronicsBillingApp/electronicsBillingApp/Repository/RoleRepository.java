package ElectronicsBillingApp.ElectronicsBillingApp.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ElectronicsBillingApp.ElectronicsBillingApp.Constant.ERole;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
