package ElectronicsBillingApp.ElectronicsBillingApp.Controller;

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

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Customer;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.CustomerReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Request.CustomerRequest;
import ElectronicsBillingApp.ElectronicsBillingApp.Response.ApiResponse;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CustomerController {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerReposiroty customerReposiroty;

	@PostMapping("/customer")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> create(@Valid @RequestBody CustomerRequest customerrequest) {
		List<Customer> customerListByUserId = customerReposiroty.findByUserid(customerrequest.getUserid());
		if (!customerListByUserId.isEmpty()) {
			List<Customer> customerlist = customerListByUserId.stream()
					.filter(employee -> employee.getName().equals(customerrequest.getName()))
					.collect(Collectors.toList());
			if (customerlist.isEmpty()) {
				Customer customer = modelMapper.map(customerrequest, Customer.class);
				System.out.println(customer);
				return ApiResponse.success(customerReposiroty.save(customer));
			} else {
				List<Customer> filteredList;
				filteredList = customerlist.stream()
						.filter(employee -> employee.getAddress().equals(customerrequest.getAddress()))
						.collect(Collectors.toList());
				if (filteredList.isEmpty()) {
					Customer customer = modelMapper.map(customerrequest, Customer.class);
					System.out.println(customer);
					return ApiResponse.success(customerReposiroty.save(customer));
				} else {
					return ApiResponse.failure("customer data already present");
				}
			}
		} else {
			Customer customer = modelMapper.map(customerrequest, Customer.class);
			System.out.println(customer);
			return ApiResponse.success(customerReposiroty.save(customer));
		}

	}

	@GetMapping("/customer")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ApiResponse<?> get() {
		List<Customer> customer = customerReposiroty.findAll();
		return ApiResponse.success(customer);
	}

	@GetMapping("/customer/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> getbyuserid(@PathVariable Long id) {
		List<Customer> customer = customerReposiroty.findByUserid(id);
		return ApiResponse.success(customer);
	}

	@DeleteMapping("/customer/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> delete(@PathVariable Long id) {
		customerReposiroty.deleteById(id);
		return ApiResponse.success();
	}

	@PutMapping("/customer/{id}")
	public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerrequest) {
		Customer existingcustomer = customerReposiroty.findById(id).get();

		Customer customer = modelMapper.map(customerrequest, Customer.class);
		customer.setId(existingcustomer.getId());
		return ApiResponse.success(customerReposiroty.save(customer));
	}

}
