package ElectronicsBillingApp.ElectronicsBillingApp.Request;

import lombok.Data;

@Data
public class SaleEntryRequest {
	private SaleRequest sale;
	private SaleItemRequest saleitem[];
}
