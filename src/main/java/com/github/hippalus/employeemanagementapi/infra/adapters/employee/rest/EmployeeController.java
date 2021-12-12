package com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeActivate;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeApprove;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeCreate;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeInCheck;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRecheck;
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
@RequestMapping("/api/v1/employees")
public class EmployeeController extends BaseController {

  private final UseCaseHandler<Page<Employee>, EmployeeRetrieveAll> employeeRetrieveAllUseCaseHandler;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Response<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest employeeCreateRequest) {
    final EmployeeCreate employeeCreate = employeeCreateRequest.toUseCase();
    final Employee employee = publish(Employee.class, employeeCreate);
    return respond(EmployeeResponse.fromModel(employee));
  }

  @GetMapping
  public Response<EmployeeResponse> retrieve(@RequestParam("id") Long employeeId) {
    final Employee employee = publish(Employee.class, EmployeeRetrieve.valueOf(employeeId));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @GetMapping("/all")
  public Response<DataResponse<EmployeeResponse>> retrieveAll(@RequestParam("page") Integer page,
      @RequestParam("size") Integer size) {
    final Page<Employee> employees = employeeRetrieveAllUseCaseHandler.handle(new EmployeeRetrieveAll(size, page));
    final List<EmployeeResponse> responseList = employees.stream().map(EmployeeResponse::fromModel).collect(Collectors.toList());
    return respond(responseList, page, responseList.size(), employees.getTotalElements());
  }

  @PostMapping("/{id}/command/inCheck")
  public Response<EmployeeResponse> inCheckEmployee(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeInCheck.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/activate")
  public Response<EmployeeResponse> activateEmployee(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeActivate.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/approve")
  public Response<EmployeeResponse> approveEmployee(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeApprove.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/recheck")
  public Response<EmployeeResponse> recheckEmployee(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeRecheck.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

/*
  @PostMapping("/{id}/command/finishSecurityCheck")
  public Response<EmployeeResponse> finishSecurityCheck(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeFinishSecurityCheck.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }

  @PostMapping("/{id}/command/finishWorkPermitCheck")
  public Response<EmployeeResponse> finishWorkPermitCheck(@PathVariable Long id) {
    final Employee employee = publish(Employee.class, EmployeeFinishWorkPermitCheck.valueOf(id));
    return respond(EmployeeResponse.fromModel(employee));
  }
*/


}
