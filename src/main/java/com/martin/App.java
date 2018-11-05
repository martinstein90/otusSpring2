package com.martin;

import com.martin.domain.Department;
import com.martin.domain.Employee;
import com.martin.repository.DepartmentRepository;
import com.martin.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebMvc
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Component
    public class MyHealthIndicator implements HealthIndicator {

        @Override
        public Health health() {
            if(employeeRepository.count() > 2)
                return Health.up().build();
            return Health.down().build();
        }
    }

    @PostConstruct
    public void init() {
        Department sales = departmentRepository.save(new Department("sales"));
        Department developed = departmentRepository.save(new Department("developed"));
        Department economic = departmentRepository.save(new Department("economic"));

        employeeRepository.save(new Employee("Kirill", sales));
        employeeRepository.save(new Employee("Alex", developed));
        employeeRepository.save(new Employee("Petr", developed));
        employeeRepository.save(new Employee("Olga", economic));
    }
}