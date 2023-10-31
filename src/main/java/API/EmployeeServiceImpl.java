package API;

import com.github.javafaker.Faker;
import io.restassured.common.mapper.TypeRef;
import Model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EmployeeServiceImpl implements EmployeeService {
    private static final String path = "/employee";
    private final static String prefix = "AK-";
    Faker faker = new Faker();
    private Map<String, String> headers = new HashMap<>();

    private String uri = "https://x-clients-be.onrender.com";

//    public EmployeeServiceImpl(String uri) {
//        this.uri = uri;
//    }
//
//    @Override
//    public void setURI(String uri) {
//        this.uri = uri;
//    }

    @Override
    public Employee getRandomEmployee(int companyId) {
        int id = 0;
        String firstName = prefix + faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String url = faker.internet().url();
        String phone = String.valueOf(faker.number().digits(10));
        String birthDate = faker.date().birthday().toString();
        return new Employee(id, firstName, lastName, companyId, email, url, phone, birthDate, false);
    }

    @Override
    public List<Employee> getAll(int companyId) {
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .param("company", companyId)
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .extract()
                .response()
                .then()
                .extract().body().as(new TypeRef<List<Employee>>() {
                });
    }

    @Override
    public Employee getById(int id) {
        return given()
                .baseUri(uri + path + "/" + id)
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().body().as(Employee.class);
    }

    @Override
    public int create(Employee employee, String token) {
        return given()
                .baseUri(uri + path)
                .log().ifValidationFails()
                .header("accept", "application/json")
                .contentType("application/json; charset=utf-8")
                .header("x-client-token", token)
                .body(employee)
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
    }

    @Override
    public Employee update(Employee employee, String token) {
        return given()
                .log().ifValidationFails()
                .contentType("application/json; charset=utf-8")
                .header("x-client-token", token)
                .body(employee)
                .when()
                .patch(uri + path + "/{id}", employee.getId())
                .then().log().ifValidationFails()
                .extract()
                .body().as(Employee.class);
    }
}