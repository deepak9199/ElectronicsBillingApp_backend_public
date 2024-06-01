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

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.Item;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.ItemReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Request.ItemRequest;
import ElectronicsBillingApp.ElectronicsBillingApp.Response.ApiResponse;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class itemController {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ItemReposiroty itemReposiroty;

	@PostMapping("/Item")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> create(@Valid @RequestBody ItemRequest Itemrequest) {
		List<Item> ItemList = itemReposiroty.findByUserid(Itemrequest.getUserid());
		if (!ItemList.isEmpty()) {
			List<Item> filteredList;
			filteredList = ItemList.stream().filter(employee -> employee.getName().equals(Itemrequest.getName()))
					.collect(Collectors.toList());
			if (filteredList.isEmpty()) {
				Item Item = modelMapper.map(Itemrequest, Item.class);
				System.out.println(Item);
				return ApiResponse.success(itemReposiroty.save(Item));
			} else {
				return ApiResponse.failure("Item already present");
			}
		} else {
			Item Item = modelMapper.map(Itemrequest, Item.class);
			System.out.println(Item);
			return ApiResponse.success(itemReposiroty.save(Item));
		}

	}

	@GetMapping("/Item")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ApiResponse<?> get() {
		List<Item> Item = itemReposiroty.findAll();
		return ApiResponse.success(Item);
	}

	@GetMapping("/Item/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> getbyuserid(@PathVariable Long id) {
		List<Item> Item = itemReposiroty.findByUserid(id);
		return ApiResponse.success(Item);
	}

	@DeleteMapping("/Item/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> delete(@PathVariable Long id) {
		itemReposiroty.deleteById(id);
		return ApiResponse.success();
	}

	@PutMapping("/Item/{id}")
	public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody ItemRequest Itemrequest) {
		Item existingItem = itemReposiroty.findById(id).get();

		Item Item = modelMapper.map(Itemrequest, Item.class);
		Item.setId(existingItem.getId());
		return ApiResponse.success(itemReposiroty.save(Item));
	}

}
