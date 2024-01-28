package weare.api.testing.users;

import api.UserController;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.REGISTER_SUCCESS_MESSAGE;

public class RegisterUserTest extends BaseUserSetup {
    @Test
    public void RegisterUser() {
        userToRegister = ModelGenerator.generateUserRegisterModel();
        Response response = UserController.registerUser(userToRegister);
        isResponse200(response);

        String[] responseString = response.asString().split(" ");
        Assert.assertEquals(responseString[3], userToRegister.username);
        currentUsername = userToRegister.username;
        currentUserId = Integer.parseInt(responseString[6]);
        System.out.println(REGISTER_SUCCESS_MESSAGE);
    }
}
