package ElectronicsBillingApp.ElectronicsBillingApp.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ElectronicsBillingApp.ElectronicsBillingApp.Constant.DeleteCode;
import ElectronicsBillingApp.ElectronicsBillingApp.Constant.GstType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_customer")
@Data
@NoArgsConstructor
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "userid must not be Null")
	@Min(value = 1, message = "userid must not be 0")
	private Long userid;

	@NotNull
	@Min(value = 1, message = "billno quantity must not be 0")
	private Long invoice_no;

	@NotNull(message = "date must not be Empty")
	private Date date;

	@NotBlank(message = "name must not be empty")
	@Size(max = 500)
	private String name;

	@NotBlank(message = "address must not be empty")
	@Size(max = 500)
	private String address;

	@NotNull(message = "total_amount quantity must not be 0")
	private Double total_amount;

	@NotBlank(message = "amount_in_word must not be empty")
	@Size(max = 500)
	private String amount_in_word;

	@NotBlank(message = "state must not be empty")
	@Size(max = 500)
	private String state;

	@NotBlank(message = "statecode must not be empty")
	@Size(max = 500)
	private String state_code;

	@NotNull(message = "total_tax_value quantity must not be 0")
	private Double total_tax_value;

	@NotNull(message = "total_rate_value quantity must not be 0")
	private Double total_rate_value;

	@Size(max = 500)
	private String gsttin_no;

	@Size(max = 500)
	private String vehicalno;

	@NotBlank(message = "session must not be empty")
	@Size(max = 500)
	private String session;

	@NotNull
	private GstType type;
	@NotNull

	private DeleteCode isdelete;

	@Size(max = 500)
	private String financeby;

	private Double financedownpayment;

}
