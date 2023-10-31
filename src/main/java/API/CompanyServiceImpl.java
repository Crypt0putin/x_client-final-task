package API;

import Model.Company;
import java.util.List;
import com.github.javafaker.Faker;
import io.restassured.common.mapper.TypeRef;
import static io.qameta.allure.Allure.step;

import static io.restassured.RestAssured.given;

public class CompanyServiceImpl implements CompanyService {

      String uri = "https://x-clients-be.onrender.com";
      String path = "/company";
      String prefix = "AK-";
      String deletePath = "/company/delete";

    Faker faker = new Faker();

    @Override
    public Company getRandomCompany() {

        int companyId = faker.random().nextInt(1000, 10000);
        String companyName = prefix + faker.twinPeaks().character();
        String companyDescription = prefix + faker.twinPeaks().quote();

        return new Company(companyId, companyName, companyDescription, true);
    }


    @Override
    public List<Company> getAll() {
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .extract()
                .response()
                .then()
                .extract()
                .body().as(new TypeRef<List<Company>>() {
                });
    }

    @Override
    public List<Company> getAll(boolean isActive) {
        step("Получить список всех компаний isActive");
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .param("active", isActive)
                .when()
                .get()
                .then()
                .extract()
                .response()
                .then()
                .extract()
                .body().as(new TypeRef<List<Company>>() {
                });
    }

    @Override
    public Company getById(int id) {
        return null;
    }


    @Override
    public int create(String name, String description, String token) {
        step("Создать компанию");
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .header("x-client-token", token)
                .log().ifValidationFails()
                .contentType("application/json")
                .body("{\"name\": \"" + name + "\",\"description\": \"" + description + "\"}")
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
    }

    @Override
    public void deleteById(int id, String token) {
        step("Удалить компанию по ID", ()->{
            given()
                    .log().ifValidationFails()
                    .baseUri(uri + path + id)
                    .header("accept", "application/json")
                    .header("x-client-token", token)
                    .log().ifValidationFails()
                    .contentType("application/json")
                    .when()
                    .get()
                    .then()
                    .log().ifValidationFails()
                    .statusCode(200);
        });
    }
}