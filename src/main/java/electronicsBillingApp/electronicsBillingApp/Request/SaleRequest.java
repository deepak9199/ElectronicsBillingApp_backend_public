package ElectronicsBillingApp.ElectronicsBillingApp.Request;

import java.util.Date;

import lombok.Data;

@Data
public class SaleRequest {

	private Long userid;

	private Long invoice_no;

	private Date date;

	private String name;

	private String address;

	private Double total_amount;

	private String amount_in_word;

	private String state;

	private String state_code;

	private Double total_tax_value;

	private Double total_rate_value;

	private String gsttin_no;

	private String vehicalno;

	private String session;

	private String type;

	private String financeby;

	private Double financedownpayment;

}
