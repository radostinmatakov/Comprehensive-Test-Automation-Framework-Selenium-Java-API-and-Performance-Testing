package weare.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import utils.Serializer;
import weare.models.SendRequest;

import static utils.Endpoints.*;

public class ConnectionController {

    public static Response sendRequest(SendRequest sendRequestToUser, Cookies cookies, String senderUsername) {
        String bodySendRequest = Serializer.convertObjectToJsonString(sendRequestToUser);

        return RestAssured.given()
                .cookies(cookies)
                .header("name", senderUsername)
                .contentType("application/json")
                .body(bodySendRequest)
                .when()
                .post(SEND_REQUEST_ENDPOINT);
    }

    public static Response getUserRequests(Cookies cookies, int receiverUserId) {
        return RestAssured.given()
                .cookies(cookies)
                .pathParam("receiverUserId", receiverUserId)
                .when()
                .get(GET_USER_REQUESTS_ENDPOINT);
    }

    public static Response approveRequest(Cookies cookies, int receiverUserId, int requestId) {
        return RestAssured.given()
                .cookies(cookies)
                .pathParam("receiverUserId", receiverUserId)
                .queryParam("requestId", requestId)
                .when()
                .post(APPROVE_CONNECTION_REQUEST_ENDPOINT);
    }
}
