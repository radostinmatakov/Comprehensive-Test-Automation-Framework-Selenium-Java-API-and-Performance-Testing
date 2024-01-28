package weare.api.testing.connection;

import api.ConnectionController;
import io.restassured.response.Response;
import models.ApproveRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.STATUS_CODE_MESSAGE;

public class ApproveRequestTest extends BaseConnectionSetup {
    int requestId;

    @BeforeClass
    public void setupTest() {
        sendRequestToUser = ModelGenerator.generateSendRequestModel(receiverUserId, receiverUsername);
        senderCookies = authenticateAndFetchCookies(senderUsername, senderPassword);
        Response response = ConnectionController.sendRequest(sendRequestToUser, senderCookies, senderUsername);

        receiverCookies = authenticateAndFetchCookies(receiverUsername, receiverPassword);
        Response response2 = ConnectionController.getUserRequests(receiverCookies, receiverUserId);


        isResponse200(response2);
        ApproveRequest[] approveRequestList = response2.as(ApproveRequest[].class);
        requestId = approveRequestList[0].id;
    }

    @Test
    public void approveRequest() {

        authenticateAndFetchCookies(receiverUsername, receiverPassword);
        ConnectionController.approveRequest(cookies, receiverUserId, requestId);
        Response response = ConnectionController.approveRequest(cookies, receiverUserId, requestId);

        int statusCode = response.getStatusCode();
        System.out.println(String.format(STATUS_CODE_MESSAGE, statusCode));
    }
}
