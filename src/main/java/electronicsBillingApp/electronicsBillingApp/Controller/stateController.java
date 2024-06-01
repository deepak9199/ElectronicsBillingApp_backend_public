package ElectronicsBillingApp.ElectronicsBillingApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ElectronicsBillingApp.ElectronicsBillingApp.Entity.State;
import ElectronicsBillingApp.ElectronicsBillingApp.Repository.StateReposiroty;
import ElectronicsBillingApp.ElectronicsBillingApp.Response.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class stateController {

	@Autowired
	private StateReposiroty stateReposiroty;

	@GetMapping("/State")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<?> get() {
		List<State> state = stateReposiroty.findAll();
		return ApiResponse.success(state);
	}

}
