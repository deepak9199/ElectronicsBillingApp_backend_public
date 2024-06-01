package ElectronicsBillingApp.ElectronicsBillingApp.Request;

import lombok.Data;

@Data
public class DeleteRequest {
	private Long billnoid;
	private Long userid;
}
