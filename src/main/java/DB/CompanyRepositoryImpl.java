package DB;

import Model.CompanyEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    CompanySpring springCompany;


    @Override
    public List<CompanyEntity> getAll() {
        return springCompany.findAllByDeletedAtNull();
    }

    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        return springCompany.findAllByIsActive(isActive);
    }

    @Override
    public CompanyEntity getLast() {
        return null;
    }

    @Override
    public CompanyEntity getById(int id) {
        return null;
    }

    @Override
    public int create(String name) {
        return 0;
    }

    @Override
    public int create(String name, String description) {
        return 0;
    }

    @Override
    public void deleteById(int id) {

    }
}