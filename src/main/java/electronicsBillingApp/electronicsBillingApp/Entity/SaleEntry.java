package ElectronicsBillingApp.ElectronicsBillingApp.Entity;

import lombok.Data;

@Data
public class SaleEntry {
	private Sale sale;
	private SaleItem saleitem[];

}
