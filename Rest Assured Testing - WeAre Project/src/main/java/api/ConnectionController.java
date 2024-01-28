package api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import models.SendRequest;
import utils.Serializer;

import static utils.Constants.APPLICATION_JSON;
import static utils.Endpoints.GET_REQUESTS_ENDPOINT;
import static utils.Endpoints.SEND_REQUEST_ENDPOINT;
public class ConnectionController {

    public static Response sendRequest(SendRequest sendRequestToUser, Cookies cookies, String senderUsername) {
        String bodySendRequest = Serializer.convertObjectToJsonString(sendRequestToUser);

        return RestAssured.given()
                .cookies(cookies)
                .header("name", senderUsername)
                .contentType(APPLICATION_JSON)
                .body(bodySendRequest)
                .when()
                .post(SEND_REQUEST_ENDPOINT);
    }

    public static Response getUserRequests(Cookies cookies, int receiverUserId){
        return RestAssured.given()
                .cookies(cookies)
                .pathParam("receiverUserId", receiverUserId)
                .when()
                .get(GET_REQUESTS_ENDPOINT);
    }

    public static Response approveRequest(Cookies cookies, int receiverUserId, int requestId){
        return  RestAssured.given()
                .cookies(cookies)
                .pathParam("receiverUserId", receiverUserId)
                .queryParam("requestId", requestId)
                .when()
                .post(GET_REQUESTS_ENDPOINT);
    }
}
