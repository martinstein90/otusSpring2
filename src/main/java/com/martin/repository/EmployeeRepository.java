package com.martin.repository;

import com.martin.domain.Department;
import com.martin.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "employee")
public interface EmployeeRepository  extends CrudRepository<Employee, Integer>{
}

