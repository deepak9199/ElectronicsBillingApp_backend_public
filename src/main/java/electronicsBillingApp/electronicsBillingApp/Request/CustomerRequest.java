package ElectronicsBillingApp.ElectronicsBillingApp.Request;

import lombok.Data;

@Data
public class CustomerRequest {

	private Long userid;
	
	private String name;

	private String address;

	private String gst;
}
