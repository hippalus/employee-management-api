package com.github.hippalus.employeemanagementapi.domain.employee.event;

public enum EmployeeEventType {
  EMPLOYEE_CREATED,
  READY_TO_CHECK,
  SECURITY_CHECK_STARTED,
  SECURITY_CHECK_FINISHED,
  WORK_PERMIT_CHECK_STARTED,
  WORK_PERMIT_CHECK_FINISHED,
  APPROVED, ACTIVATED
}
