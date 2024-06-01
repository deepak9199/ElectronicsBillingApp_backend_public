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
@Table(name = "sale_customer_item")
@Data
@NoArgsConstructor
public class SaleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "userid must not be Null")
	@Min(value = 1, message = "userid must not be 0")
	private Long userid;

	@NotNull
	@Min(value = 1, message = "referenceid must not be 0")
	private Long referenceid;

	private Long invoice_no;

	private Long sl_no;

	@NotBlank(message = "item must not be empty")
	@Size(max = 500)
	private String item;

	@Size(max = 500)
	private String hsn_code;

	@NotBlank(message = "model_no must not be empty")
	@Size(max = 500)
	private String model_no;

	@NotBlank(message = "sl_no_and_emie_no must not be empty")
	@Size(max = 500)
	private String sl_no_and_emie_no;

	@NotNull(message = "quantity  must not be 0")
	private Double quantity;

	@NotNull(message = "rate must not be 0")
	private Double rate;

	@NotNull(message = "cgst_per must not be 0")
	private Double cgst_per;

	@NotNull(message = "sgst_per must not be 0")
	private Double sgst_per;

	@NotNull(message = "igst_per must not be 0")
	private Double igst_per;

	@NotNull(message = "gst_tax_value must not be 0")
	private Double gst_tax_value;

	@NotNull(message = "item_amount must not be 0")
	private Double item_amount;

}
