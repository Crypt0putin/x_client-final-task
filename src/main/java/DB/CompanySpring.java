package DB;
import Model.CompanyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanySpring extends CrudRepository<CompanyEntity, Integer> {

    List<CompanyEntity> findAll();
    List<CompanyEntity> findAllByIsActive(boolean isActive);
    List<CompanyEntity> findAllByIsActiveAndDeletedAtIsNotNull(boolean isActive);
    List<CompanyEntity> findAllByDeletedAtNull();
    void deleteByNameStartingWith(String name);
    CompanyEntity findFirstByOrderByIdDesc();

}