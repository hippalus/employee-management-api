package com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeActivate;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeApprove;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeCreate;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeInCheck;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRetrieve;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRetrieveAll;
import com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest.dto.EmployeeCreateRequest;
import com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest.dto.EmployeeResponse;
import com.github.hippalus.employeemanagementapi.infra.common.rest.BaseController;
import com.github.hippalus.employeemanagementapi.infra.common.rest.DataResponse;
import com.github.hippalus.employeemanagementapi.infra.common.rest.Response;
import com.github.hippalus.employeemanagementapi.infra.common.rest.ResponseBuilder;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController extends BaseController {

  private final UseCaseHandler<Employee, EmployeeCreate> createEmployeeUseCaseHandler;
  private final UseCaseHandler<Employee, EmployeeRetrieve> employeeEmployeeRetrieveUseCaseHandler;
  private final UseCaseHandler<Page<Employee>, EmployeeRetrieveAll> employeeRetrieveAllUseCaseHandler;
  private final UseCaseHandler<Employee, EmployeeActivate> employeeEmployeeActivateUseCaseHandler;
  private final UseCaseHandler<Employee, EmployeeApprove> employeeEmployeeApproveUseCaseHandler;
  private final UseCaseHandler<Employee, EmployeeInCheck> employeeEmployeeInCheckUseCaseHandler;


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Response<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest employeeCreateRequest) {
    final Employee employee = createEmployeeUseCaseHandler.handle(employeeCreateRequest.toUseCase());
    return respond(EmployeeResponse.fromModel(employee));
  }

  @GetMapping
  public Response<EmployeeResponse> retrieve(@RequestParam("id") Long employeeId) {
    final Employee employee = employeeEmployeeRetrieveUseCaseHandler.handle(new EmployeeRetrieve(employeeId));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @GetMapping("/all")
  public Response<DataResponse<EmployeeResponse>> retrieveAll(
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size) {
    final Page<Employee> employees = employeeRetrieveAllUseCaseHandler.handle(new EmployeeRetrieveAll(size, page));
    final List<EmployeeResponse> responseList = employees.stream().map(EmployeeResponse::fromModel).collect(Collectors.toList());
    return ResponseBuilder.build(responseList, page, responseList.size(), employees.getTotalElements());
  }

  @PostMapping("/{id}/command/inCheck")
  public Response<EmployeeResponse> inCheckEmployee(@PathVariable Long id) {
    final Employee employee = employeeEmployeeInCheckUseCaseHandler.handle(new EmployeeInCheck(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/activate")
  public Response<EmployeeResponse> activateEmployee(@PathVariable Long id) {
    final Employee employee = employeeEmployeeActivateUseCaseHandler.handle(new EmployeeActivate(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/approve")
  public Response<EmployeeResponse> approveEmployee(@PathVariable Long id) {
    final Employee employee = employeeEmployeeApproveUseCaseHandler.handle(new EmployeeApprove(id));
    return respond(EmployeeResponse.fromModel(employee));
  }


}
