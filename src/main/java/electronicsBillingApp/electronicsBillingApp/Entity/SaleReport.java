package ElectronicsBillingApp.ElectronicsBillingApp.Entity;

import lombok.Data;

@Data
public class SaleReport {

	private Long userid;

	private Long invoice_no;

	private String date;

	private String name;

	private String gsttin_no;

	private String session;

	private String gsttype;

	private String hsn_code;

	private Double rate;

	private Double cgst_per;
	
	private Double cgst_per_value;

	private Double sgst_per;
	
	private Double sgst_per_value;

	private Double igst_per;
	
	private Double igst_per_value;

	private Double gst_tax_value;

	private Double item_amount;
	
	private String isdeleted;

}
