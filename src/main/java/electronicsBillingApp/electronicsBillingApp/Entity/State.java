package ElectronicsBillingApp.ElectronicsBillingApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statedetail")
@Data
@NoArgsConstructor
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "statename must not be Empty")
	@Size(max = 500)
	private String statename;
	
	@NotNull(message = "statecode must not be Empty")
	@Size(max = 500)
	private String statecode;

	

}
