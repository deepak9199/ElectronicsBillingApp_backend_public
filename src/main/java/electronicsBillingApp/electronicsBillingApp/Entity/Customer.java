package ElectronicsBillingApp.ElectronicsBillingApp.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "userid must not be Null")
	@Min(value = 1, message = "userid must not be 0")
	private Long userid;

	@NotBlank(message = "name must not be empty")
	@Size(max = 500)
	private String name;

	@NotBlank(message = "address must not be empty")
	@Size(max = 500)
	private String address;

	@Size(max = 50)
	private String gst;
}
