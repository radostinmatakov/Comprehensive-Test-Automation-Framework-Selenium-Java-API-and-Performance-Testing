package api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.Skill;
import utils.Serializer;

import static utils.Constants.APPLICATION_JSON;
import static utils.Endpoints.*;

public class SkillController {

    public static Response createSkill(Cookies cookies, Skill model) {
        String bodySkillString = Serializer.convertObjectToJsonString(model);
        Response response = RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodySkillString)
                .when()
                .post(SKILL_CREATE_ENDPOINT)
                .then().log().body().extract().response();
        return response;
    }

    public static Response deleteSkill(int skillId) {
        return RestAssured.given().baseUri(BASE_URL)
                .queryParam("skillId", skillId)
                .when()
                .put(SKILL_DELETE_ENDPOINT)
                .then().log().body().extract().response();
    }

    public static Response getAllSkills(Cookies cookies) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam("sorted", "true")
                .when()
                .get(SKILL_ENDPOINT)
                .then().extract().response();

    }

    public static Response getOneSkillById(Cookies cookies, int skillId) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam("skillId", skillId)
                .when()
                .get(SKILL_GET_ONE_ENDPOINT)
                .then().extract().response();
    }

    public static Response updateOneSkill(Cookies cookies, Skill skillToUpdate) {
        return RestAssured.given().baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam("skill", skillToUpdate.skill + " Updated")
                .queryParam("skillId", skillToUpdate.skillId)
                .when()
                .put(SKILL_UPDATE_ENDPOINT)
                .then().log().body().extract().response();
    }
}
