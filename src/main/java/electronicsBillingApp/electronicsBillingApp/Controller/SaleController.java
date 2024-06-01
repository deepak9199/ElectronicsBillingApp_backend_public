package ElectronicsBillingApp.ElectronicsBillingApp.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ElectronicsBillingApp.ElectronicsBillingApp.Constant.DeleteCode;
import ElectronicsBillingApp.ElectronicsBillingApp.Constant.GstType;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Sale;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.SaleEntry;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.SaleItem;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.SaleItemReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.SaleReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Request.DeleteRequest;
import ElectronicsBillingApp.ElectronicsBillingApp.Request.SaleEntryRequest;
import ElectronicsBillingApp.ElectronicsBillingApp.Request.SaleItemRequest;
import ElectronicsBillingApp.ElectronicsBillingApp.Response.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SaleController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SaleReposiroty saleReposiroty;

	@Autowired
	private SaleItemReposiroty saleitemReposiroty;

	// get sale details for role Super
	@GetMapping("/Sale")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ApiResponse<?> get() {
		List<Sale> Sale = saleReposiroty.findAll();
		List<SaleItem> SaleItem = saleitemReposiroty.findAll();
		List<SaleEntry> SaleItemArraylist = new ArrayList<SaleEntry>();
		if (!Sale.isEmpty()) {
			int i = 0;
			for (i = 0; i < Sale.size(); i++) {
				SaleEntry saleEntry = new SaleEntry();
				saleEntry.setSale(Sale.get(i));
				Long saleId = Sale.get(i).getId();
				List<SaleItem> saleItemList = SaleItem.stream().filter(employee -> employee.getReferenceid() == saleId)
						.collect(Collectors.toList());
				saleEntry.setSaleitem(saleItemList.toArray(new SaleItem[saleItemList.size()]));
				SaleItemArraylist.add(saleEntry);
			}
			if (i >= Sale.size()) {
				return ApiResponse.success(SaleItemArraylist);
			} else {
				return ApiResponse.failure("Sale not found enternal Error Occoured");
			}
		} else {
			return ApiResponse.failure("Sale not found");
		}
	}

	@GetMapping("/Sale/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> getbyuserid(@PathVariable Long id) {
		List<Sale> Sale = saleReposiroty.findByUserid(id);
		List<SaleItem> SaleItem = saleitemReposiroty.findByUserid(id);
		List<SaleEntry> SaleItemArraylist = new ArrayList<SaleEntry>();
		if (!Sale.isEmpty()) {
			int i = 0;
			for (i = 0; i < Sale.size(); i++) {
				SaleEntry saleEntry = new SaleEntry();
				saleEntry.setSale(Sale.get(i));
				Long saleId = Sale.get(i).getId();
				List<SaleItem> saleItemList = SaleItem.stream().filter(employee -> employee.getReferenceid() == saleId)
						.collect(Collectors.toList());
				saleEntry.setSaleitem(saleItemList.toArray(new SaleItem[saleItemList.size()]));
				SaleItemArraylist.add(saleEntry);
			}
			if (i >= Sale.size()) {
				return ApiResponse.success(SaleItemArraylist);
			} else {
				return ApiResponse.failure("Sale not found enternal Error Occoured");
			}
		} else {
			return ApiResponse.failure("Sale not found");
		}

	}

	@DeleteMapping("/Sale")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> delete(@Valid @RequestBody DeleteRequest deleteRequest) {
		Sale sale = saleReposiroty.findById(deleteRequest.getBillnoid()).get();
		List<Sale> saleList = saleReposiroty.findByUserid(deleteRequest.getUserid());
		List<SaleItem> saleItemList = saleitemReposiroty.findByReferenceid(deleteRequest.getBillnoid());
		// delete sale
		saleReposiroty.deleteById(deleteRequest.getBillnoid());
		// delete sale Item
		for (int i = 0; i < saleItemList.size(); i++) {
			saleitemReposiroty.deleteById(saleItemList.get(i).getId());
		}
		// make Sequence of bill no
		for (int i = 0; i < saleList.size(); i++) {
			if (sale.getInvoice_no() < saleList.get(i).getInvoice_no()) {

				Sale existingSale = saleList.get(i);
				Sale Sale = modelMapper.map(saleList.get(i), Sale.class);
				Sale.setInvoice_no((saleList.get(i).getInvoice_no() - 1));
				Sale.setId(existingSale.getId());
				saleReposiroty.save(Sale);
			}
		}
		// return success
		return ApiResponse.success("sale Delete Successfully");
	}

	// update sale
	@PutMapping("/Sale/{id}")
	public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody SaleEntryRequest saleEntryRequest) {
		if (saleEntryRequest.getSaleitem().length != 0 && saleEntryRequest.getSale() != null) {
			List<Sale> saleListByUserID = saleReposiroty.findByUserid(saleEntryRequest.getSale().getUserid());
			SaleEntry saleEntry = new SaleEntry();
			SaleItemRequest[] saleItemList = saleEntryRequest.getSaleitem();
			System.out.println(saleEntryRequest);
			List<SaleItem> SaleItemArraylist = new ArrayList<SaleItem>();
			if (!saleListByUserID.isEmpty()) {
				List<Sale> saleList = saleListByUserID.stream()
						.filter(employee -> employee.getInvoice_no().equals(saleEntryRequest.getSale().getInvoice_no()))
						.collect(Collectors.toList());
				List<SaleItem> existingSaleItem = saleitemReposiroty.findByReferenceid(saleList.get(0).getId());
				if (!saleList.isEmpty()) {
					Sale existingSale = saleReposiroty.findById(saleList.get(0).getId()).get();
					Sale sale = new Sale();
					sale.setAddress(saleEntryRequest.getSale().getAddress());
					sale.setAmount_in_word(saleEntryRequest.getSale().getAmount_in_word());
					sale.setDate(saleEntryRequest.getSale().getDate());
					sale.setGsttin_no(saleEntryRequest.getSale().getGsttin_no());
					sale.setInvoice_no(saleEntryRequest.getSale().getInvoice_no());
					sale.setIsdelete(DeleteCode.NO);
					sale.setName(saleEntryRequest.getSale().getName());
					sale.setSession(saleEntryRequest.getSale().getSession());
					sale.setState(saleEntryRequest.getSale().getState());
					sale.setAddress(saleEntryRequest.getSale().getAddress());
					sale.setState_code(saleEntryRequest.getSale().getState_code());
					sale.setTotal_amount(saleEntryRequest.getSale().getTotal_amount());
					sale.setTotal_rate_value(saleEntryRequest.getSale().getTotal_rate_value());
					sale.setTotal_tax_value(saleEntryRequest.getSale().getTotal_tax_value());
					sale.setFinanceby(saleEntryRequest.getSale().getFinanceby());
					sale.setFinancedownpayment(saleEntryRequest.getSale().getFinancedownpayment());
					if (saleEntryRequest.getSale().getType().equals("NONE_GST")) {
						sale.setType(GstType.NONE_GST);
					} else if (saleEntryRequest.getSale().getType().equals("GST")) {
						sale.setType(GstType.GST);
					}
					sale.setUserid(saleEntryRequest.getSale().getUserid());
					sale.setVehicalno(saleEntryRequest.getSale().getVehicalno());
					sale.setId(existingSale.getId());
					saleEntry = updateSale(existingSaleItem, SaleItemArraylist, saleItemList, sale);
					return ApiResponse.success(saleEntry);
				} else {
					return ApiResponse.failure("Bill no Not Found");
				}
			} else {
				return ApiResponse.failure("user id not found");
			}
		} else {
			return ApiResponse.failure("sale Entry Update Data is Not Valid");
		}

	}

	// create Sale
	@PostMapping("/Sale")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> create(@Valid @RequestBody SaleEntryRequest saleEntryRequest) {
		if (saleEntryRequest.getSaleitem().length != 0 && saleEntryRequest.getSale() != null) {
			Sale sale = new Sale();
			sale.setAddress(saleEntryRequest.getSale().getAddress());
			sale.setAmount_in_word(saleEntryRequest.getSale().getAmount_in_word());
			sale.setDate(saleEntryRequest.getSale().getDate());
			sale.setGsttin_no(saleEntryRequest.getSale().getGsttin_no());
			sale.setInvoice_no(saleEntryRequest.getSale().getInvoice_no());
			sale.setName(saleEntryRequest.getSale().getName());
			sale.setSession(saleEntryRequest.getSale().getSession());
			sale.setState(saleEntryRequest.getSale().getState());
			sale.setAddress(saleEntryRequest.getSale().getAddress());
			sale.setState_code(saleEntryRequest.getSale().getState_code());
			sale.setTotal_amount(saleEntryRequest.getSale().getTotal_amount());
			sale.setTotal_rate_value(saleEntryRequest.getSale().getTotal_rate_value());
			sale.setTotal_tax_value(saleEntryRequest.getSale().getTotal_tax_value());
			sale.setFinanceby(saleEntryRequest.getSale().getFinanceby());
			sale.setFinancedownpayment(saleEntryRequest.getSale().getFinancedownpayment());
			if (saleEntryRequest.getSale().getType().equals("NONE_GST")) {
				sale.setType(GstType.NONE_GST);
			} else if (saleEntryRequest.getSale().getType().equals("GST")) {
				sale.setType(GstType.GST);
			}
			sale.setIsdelete(DeleteCode.NO);
			sale.setUserid(saleEntryRequest.getSale().getUserid());
			sale.setVehicalno(saleEntryRequest.getSale().getVehicalno());
			SaleEntry saleEntry = new SaleEntry();
			SaleItemRequest[] saleItemList = saleEntryRequest.getSaleitem();
			List<SaleItem> SaleItemArraylist = new ArrayList<SaleItem>();
			if (saleItemList.length != 0) {
				List<Sale> saleList = saleReposiroty.findByUserid(saleEntryRequest.getSale().getUserid());
				if (!saleList.isEmpty()) {
					int index = saleList.size();
					long sequence = saleList.get(index - 1).getInvoice_no() + 1;
					if (sequence == sale.getInvoice_no()) {
						saleEntry = saveSale(SaleItemArraylist, saleItemList, sale);
						return ApiResponse.success(saleEntry);
					} else {
						return ApiResponse.failure("Sequence not Found");
					}

				} else {
					saleEntry = saveSale(SaleItemArraylist, saleItemList, sale);
					return ApiResponse.success(saleEntry);
				}

			} else {
				return ApiResponse.failure("sale Item is Empty");
			}
		} else {
			return ApiResponse.failure("sale Entry Data is Not Valid");
		}

	}

	// function for saveSale
	private SaleEntry saveSale(List<SaleItem> SaleItemArraylist, SaleItemRequest[] saleItemList, Sale sale) {
		SaleEntry saleEntry = new SaleEntry();
		int i = 0;
		saleEntry.setSale(saleReposiroty.save(sale));
		for (i = 0; i < saleItemList.length; i++) {
			SaleItem saleItem = new SaleItem();
			saleItem.setReferenceid(sale.getId());
			saleItem.setUserid(sale.getUserid());
			saleItem.setInvoice_no(null);
			saleItem.setSl_no((long) (i+1));
			saleItem.setItem(saleItemList[i].getItem());
			saleItem.setModel_no(saleItemList[i].getModel_no());
			saleItem.setSl_no_and_emie_no(saleItemList[i].getSl_no_and_emie_no());
			saleItem.setQuantity(saleItemList[i].getQuantity());
			saleItem.setRate(saleItemList[i].getRate());
			saleItem.setHsn_code(saleItemList[i].getHsn_code());
			saleItem.setCgst_per(saleItemList[i].getCgst_per());
			saleItem.setSgst_per(saleItemList[i].getSgst_per());
			saleItem.setIgst_per(saleItemList[i].getIgst_per());
			saleItem.setGst_tax_value(saleItemList[i].getGst_tax_value());
			saleItem.setItem_amount(saleItemList[i].getItem_amount());
			SaleItemArraylist.add(saleitemReposiroty.save(saleItem));
		}
		if (i >= saleItemList.length) {
			saleEntry.setSaleitem(SaleItemArraylist.toArray(new SaleItem[SaleItemArraylist.size()]));
			return saleEntry;
		} else {
			return saleEntry;
		}
	}

	// function for UpdateSale
	private SaleEntry updateSale(List<SaleItem> existingSaleItem, List<SaleItem> SaleItemArraylist,
			SaleItemRequest[] saleItemList, Sale sale) {
		SaleEntry saleEntry = new SaleEntry();
		int i = 0;
		saleEntry.setSale(saleReposiroty.save(sale));
		if (!existingSaleItem.isEmpty()) {
			int j = 0;
			for (j = 0; j < existingSaleItem.size(); j++) {
				saleitemReposiroty.deleteById(existingSaleItem.get(j).getId());
			}
			if (j >= existingSaleItem.size()) {

				for (i = 0; i < saleItemList.length; i++) {
					SaleItem saleItem = new SaleItem();
					saleItem.setReferenceid(sale.getId());
					saleItem.setUserid(sale.getUserid());
					saleItem.setInvoice_no(null);
					saleItem.setSl_no((long) (i+1));
					saleItem.setItem(saleItemList[i].getItem());
					saleItem.setModel_no(saleItemList[i].getModel_no());
					saleItem.setSl_no_and_emie_no(saleItemList[i].getSl_no_and_emie_no());
					saleItem.setQuantity(saleItemList[i].getQuantity());
					saleItem.setRate(saleItemList[i].getRate());
					saleItem.setHsn_code(saleItemList[i].getHsn_code());
					saleItem.setCgst_per(saleItemList[i].getCgst_per());
					saleItem.setSgst_per(saleItemList[i].getSgst_per());
					saleItem.setIgst_per(saleItemList[i].getIgst_per());
					saleItem.setGst_tax_value(saleItemList[i].getGst_tax_value());
					saleItem.setItem_amount(saleItemList[i].getItem_amount());
					SaleItemArraylist.add(saleitemReposiroty.save(saleItem));
				}
				if (i >= saleItemList.length) {
					saleEntry.setSaleitem(SaleItemArraylist.toArray(new SaleItem[SaleItemArraylist.size()]));
					return saleEntry;
				} else {
					return saleEntry;
				}

			} else {
				return saleEntry;
			}
		} else {
			for (i = 0; i < saleItemList.length; i++) {
				SaleItem saleItem = new SaleItem();
				saleItem.setReferenceid(sale.getId());
				saleItem.setUserid(sale.getUserid());
				saleItem.setInvoice_no(null);
				saleItem.setSl_no(null);
				saleItem.setItem(saleItemList[i].getItem());
				saleItem.setModel_no(saleItemList[i].getModel_no());
				saleItem.setSl_no_and_emie_no(saleItemList[i].getSl_no_and_emie_no());
				saleItem.setQuantity(saleItemList[i].getQuantity());
				saleItem.setRate(saleItemList[i].getRate());
				saleItem.setHsn_code(saleItemList[i].getHsn_code());
				saleItem.setCgst_per(saleItemList[i].getCgst_per());
				saleItem.setSgst_per(saleItemList[i].getSgst_per());
				saleItem.setIgst_per(saleItemList[i].getIgst_per());
				saleItem.setGst_tax_value(saleItemList[i].getGst_tax_value());
				saleItem.setItem_amount(saleItemList[i].getItem_amount());
				SaleItemArraylist.add(saleitemReposiroty.save(saleItem));
			}
			if (i >= saleItemList.length) {
				saleEntry.setSaleitem(SaleItemArraylist.toArray(new SaleItem[SaleItemArraylist.size()]));
				return saleEntry;
			} else {
				return saleEntry;
			}
		}

	}

}
