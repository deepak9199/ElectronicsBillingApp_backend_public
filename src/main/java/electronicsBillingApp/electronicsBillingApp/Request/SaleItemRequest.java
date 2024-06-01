package ElectronicsBillingApp.ElectronicsBillingApp.Request;

import lombok.Data;

@Data
public class SaleItemRequest {

	private String item;

	private String hsn_code;

	private String model_no;

	private String sl_no_and_emie_no;

	private Double quantity;

	private Double rate;

	private Double cgst_per;

	private Double sgst_per;

	private Double igst_per;

	private Double gst_tax_value;

	private Double item_amount;

}
