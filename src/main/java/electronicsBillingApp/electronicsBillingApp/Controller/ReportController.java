package ElectronicsBillingApp.ElectronicsBillingApp.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ElectronicsBillingApp.ElectronicsBillingApp.Constant.SaleAttribute;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Sale;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.SaleItem;
import ElectronicsBillingApp.ElectronicsBillingApp.Entity.SaleReport;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.SaleItemReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.SaleReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Response.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ReportController {

	@Autowired
	private SaleReposiroty saleReposiroty;

	@Autowired
	private SaleItemReposiroty saleitemReposiroty;

	@GetMapping("/Report/Sale/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> get(@PathVariable Long id) {
		List<Sale> saleList = saleReposiroty.findByUserid(id);
		List<SaleItem> saleItemList = saleitemReposiroty.findByUserid(id);
		List<SaleReport> saleReportLIst = new ArrayList<SaleReport>();
		if (!saleList.isEmpty()) {
			if (!saleItemList.isEmpty()) {
				int i = 0;
				for (i = 0; i < saleItemList.size(); i++) {
					SaleReport saleReport = new SaleReport();
					saleReport.setCgst_per(saleItemList.get(i).getCgst_per());
					saleReport.setDate(SaleSearchData(SaleAttribute.DATE, saleList, saleItemList.get(i)));
					saleReport.setGst_tax_value(saleItemList.get(i).getGst_tax_value());
					saleReport.setGsttin_no(SaleSearchData(SaleAttribute.GST, saleList, saleItemList.get(i)));
					saleReport.setGsttype(SaleSearchData(SaleAttribute.GST_TYPE, saleList, saleItemList.get(i)));
					saleReport.setHsn_code(saleItemList.get(i).getHsn_code());
					saleReport.setIgst_per(saleItemList.get(i).getIgst_per());
					saleReport.setInvoice_no(
							Long.parseLong(SaleSearchData(SaleAttribute.INVOICE_NO, saleList, saleItemList.get(i))));
					saleReport.setItem_amount(saleItemList.get(i).getItem_amount());
					saleReport.setName(SaleSearchData(SaleAttribute.NAME, saleList, saleItemList.get(i)));
					saleReport.setRate(saleItemList.get(i).getRate());
					saleReport.setSession(SaleSearchData(SaleAttribute.SESSION, saleList, saleItemList.get(i)));
					saleReport.setSgst_per(saleItemList.get(i).getSgst_per());
					saleReport.setUserid(saleItemList.get(i).getUserid());
					saleReport.setIsdeleted(SaleSearchData(SaleAttribute.IS_DELETED, saleList, saleItemList.get(i)));
					saleReport.setCgst_per_value(Double
							.parseDouble(SaleSearchData(SaleAttribute.CGST_VALUE, saleList, saleItemList.get(i))));
					saleReport.setSgst_per_value(Double
							.parseDouble(SaleSearchData(SaleAttribute.SGST_VALUE, saleList, saleItemList.get(i))));
					saleReport.setIgst_per_value(Double
							.parseDouble(SaleSearchData(SaleAttribute.IGST_VALUE, saleList, saleItemList.get(i))));
					saleReportLIst.add(saleReport);
				}
				System.out.println(i);
				System.out.println(saleItemList.size());
				if (i >= saleItemList.size()) {
					return ApiResponse.success(saleReportLIst);
				}

			} else {
				return ApiResponse.failure("Sale item not found");
			}
		} else {
			return ApiResponse.failure("Sale not found");
		}
		return ApiResponse.failure("Sale not found");
	}

	// function for get data sale
	private String SaleSearchData(SaleAttribute Datatype, List<Sale> sale, SaleItem saleitem) {
		String result = "";
		DecimalFormat df = new DecimalFormat("#.###");
		List<Sale> saleList = sale.stream().filter(employee -> employee.getId().equals(saleitem.getReferenceid()))
				.collect(Collectors.toList());
		switch (Datatype) {
		case NAME: {
			result = saleList.get(0).getName();
			break;
		}
		case IS_DELETED: {
			result = saleList.get(0).getIsdelete().toString();
			break;
		}
		case CGST_VALUE: {
			if (saleitem.getIgst_per() == 0) {
				Double gstValue = saleitem.getGst_tax_value() / 2;
				result = df.format(gstValue).toString();
			} else {
				result = "0";
			}
			break;
		}
		case SGST_VALUE: {
			if (saleitem.getIgst_per() == 0) {
				Double gstValue = saleitem.getGst_tax_value() / 2;
				result = df.format(gstValue).toString();
			} else {
				result = "0";
			}
			break;
		}
		case IGST_VALUE: {
			if (saleitem.getIgst_per() != 0) {
				Double gstValue = saleitem.getGst_tax_value() / 2;
				result = df.format(gstValue).toString();
			} else {
				result = "0";
			}
			break;
		}
		case SESSION: {
			result = saleList.get(0).getSession();
			break;
		}
		case GST: {
			result = saleList.get(0).getGsttin_no();
			break;
		}
		case GST_TYPE: {
			result = saleList.get(0).getType().toString();
			break;
		}
		case DATE: {
			result = saleList.get(0).getDate().toString();
			break;
		}
		case INVOICE_NO: {
			result = saleList.get(0).getInvoice_no().toString();
			break;
		}
		default: {
			result = "";
		}
		}
		return result;
	}
}
