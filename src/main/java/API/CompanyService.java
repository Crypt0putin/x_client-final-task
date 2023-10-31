package API;
import Model.Company;

import java.util.List;

public interface CompanyService {

    Company getRandomCompany();

    List<Company> getAll();

    List<Company> getAll(boolean isActive);

    Company getById(int id);

    int create(String name, String description, String token);

    void deleteById(int id, String token);
}