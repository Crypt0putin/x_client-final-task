package test;

import API.CompanyService;
import Model.Company;
import Model.CompanyEntity;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import API.AuthorizeServiceImpl;
import API.EmployeeService;
import API.EmployeeServiceImpl;
import DB.CompanyRepository;
import DB.EmployeeRepository;
import Model.Employee;
import Model.EmployeeEntity;
import org.springframework.boot.test.context.SpringBootTest;

import static io.qameta.allure.Allure.step;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


//Проверить, что список компаний фильтруется по параметру active
//Проверить, что неактивный сотрудник не отображается в списке
//Проверить, что у удаленной компании проставляется в БД поле deletedAt
//@ExtendWith({PropertiesResolver.class, EmployeeRepositoryJPAResolver.class,
//        CompanyRepositoryJPAResolver.class, JDBCConnectionResolver.class, EmployeeServiceResolver.class})
@SpringBootTest
public class XClientsFinalTest {


    private CompanyRepository companyService;
    private EmployeeRepository employeeService;
    private CompanyService companyApiService;
    private EmployeeService employeeAPIService;

    static Faker faker = new Faker();
    private static int companyId;
    private static int employeeId;
    //Properties properties = PropertyProvider.getInstance().getProps();
    private EmployeeService employeeServiceImp = new EmployeeServiceImpl();
    private String token = new AuthorizeServiceImpl().getToken();

    @BeforeAll
    public static void setUp(CompanyRepository companyRepository) {
        companyId = companyRepository.create("AK-" + faker.company().name(), "AK-" + faker.twinPeaks().location());
    }

//    @AfterAll
//    public static void tearDown(CompanyRepository companyRepository) {
//        companyRepository.deleteById(companyId);
//    }
//
//    @AfterEach
//    public void cleanData(EmployeeRepository employeeRepository) {
//        employeeRepository.deleteById(employeeId);
//    }

//    @Test
//    @DisplayName("Добавление нового сотрудника и проверка записи его в БД")
//    public void shouldCreateEmployeeAndCheckSavingToDB(EmployeeRepository repository) {
//        step("Добавление сотрудника", () -> {
//            Employee createEmployee = employeeServiceImp.getRandomEmployee(companyId);
//            employeeId = employeeServiceImp.create(createEmployee, token);
//            EmployeeEntity employeeInDb = repository.getById(employeeId);
//            assertEquals(employeeId, employeeInDb.getId());
//        });
//
//    }

    @Test
    @DisplayName("1. Проверить, что список компаний фильтруется по параметру active")
    public void filterByIsActive() {
        step("Получить список компаний");
        List<Company> companiesA = companyApiService.getAll();
        step("Получить список компаний БД");
        List<CompanyEntity> companiesB = companyService.getAll();
        step("Сравнить результат", () -> {
            Assertions.assertEquals(companiesA.size(), companiesB.size());
        });
        step("Получить cписок компаний isActive");
        List<Company> companiesIsActiveA = companyApiService.getAll(true);
        step("Получить cписок компаний isActive");
        List<CompanyEntity> companiesIsActiveB = companyService.getAll(true);
        step("Сравнить результат");
        Assertions.assertEquals(companiesIsActiveA.size(), companiesIsActiveB.size());

    }

    @Test
    @DisplayName("2. Проверить, что неактивный сотрудник не отображается в списке")
    public void addEmployeeOfNotExistingCompany() {
        step("Создаем компанию в БД");
        //CompanyEntity company = companyService.create("AK-" + faker.company().name(), "AK-" + faker.twinPeaks().location());
        step("Создаем неактивного сотрудника в БД");
        CompanyEntity companiesIsActiveB = companyService.getById(2);
        Employee employeE = employeeAPIService.getRandomEmployee(2);


    }

    @Test
    @DisplayName("3. Проверить, что у удаленной компании проставляется в БД поле deletedAt")
    public void shouldFillDeletedAtToDeletedCompany() throws SQLException {

    }

}