package DB;

import Model.Employee;
import Model.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository {

    List<EmployeeEntity> getAll();

    EmployeeEntity getById(int id);

    int create(Employee employee);

    int update(EmployeeEntity e);

    void deleteById(int id);
}