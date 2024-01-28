package weare.api.testing.connection;

import api.ConnectionController;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.RECEIVER_NAME_MISMATCH_MESSAGE;
import static utils.Constants.SENDER_NAME_MISMATCH_MESSAGE;

public class SendRequestTest extends BaseConnectionSetup {

    @BeforeClass
    public void setup() {
        sendRequestToUser = ModelGenerator.generateSendRequestModel(receiverUserId, receiverUsername);
    }

    @Test
    public void sendRequest() {
        authenticateAndFetchCookies(senderUsername, senderPassword);
        Response response = ConnectionController.sendRequest(sendRequestToUser, cookies, senderUsername);
        isResponse200(response);
        System.out.println(response.asString());

        String responseBody = response.getBody().asString();
        String[] parts = responseBody.split(" ");
        String senderNameInResponse = parts[0];
        String receiverNameInResponse = parts[parts.length - 1];

        Assert.assertEquals(senderUsername, senderNameInResponse, SENDER_NAME_MISMATCH_MESSAGE);
        Assert.assertEquals(receiverUsername, receiverNameInResponse, RECEIVER_NAME_MISMATCH_MESSAGE);

    }

}
