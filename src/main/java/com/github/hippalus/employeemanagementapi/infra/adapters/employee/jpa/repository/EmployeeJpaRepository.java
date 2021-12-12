package com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa.repository;

import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {

  @Modifying
  @Query("update EmployeeEntity e set e.state=:state where e.id=:id")
  void updateState(@Param("id") Long employeeId, @Param("state") EmployeeState employeeState);

  @Query("select e.state from EmployeeEntity e where e.id=:id")
  EmployeeState getStateById(@Param("id") Long employeeId);
}
