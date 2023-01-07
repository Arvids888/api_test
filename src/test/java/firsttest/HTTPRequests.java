package firsttest;

import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class HTTPRequests {

    int id;

    @Test(priority = 1)
    void getUsers() {

        given()
                .when()
                    .get("https://reqres.in/api/users?page=2")

                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();


    }
    @Test(priority = 2)
    void createUser() {

        HashMap data = new HashMap();
        data.put("name", "drake");
        data.put("job", "engineer");

        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");

//                .then()
//                    .statusCode(201)
//                    .log().all();

    }
    @Test(priority = 3, dependsOnMethods = {"createUser"})
    void updateUser() {

        HashMap data = new HashMap();
        data.put("name", "drake");
        data.put("job", "engineer");

        given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("https://reqres.in/api/users/" + id)

                .then()
                    .statusCode(201)
                    .log().all();
    }
    @Test(priority = 4)
    void deleteUser() {
        given()

                .when()
                .delete("https://reqres.in/api/users/api/users/" + id)

                .then()
                .statusCode(204)
                .log().all();
    }
}
