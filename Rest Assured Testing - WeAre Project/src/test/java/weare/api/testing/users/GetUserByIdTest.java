package weare.api.testing.users;

import api.UserController;
import io.restassured.response.Response;
import models.UserPersonal;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.*;

public class GetUserByIdTest extends BaseUserSetup {

    @BeforeClass
    public void setup() {
        if(isRegistered == false){
            userToRegister = ModelGenerator.generateUserRegisterModel();
            register(userToRegister);
            isRegistered = true;
        }
    }

    @Test
    public void GetUserById() {
        Response response = UserController.getUserById(userToRegister.username, currentUserId);
        isResponse200(response);

        userPersonal = response.as(UserPersonal.class);
        Assert.assertEquals(userPersonal.username, currentUsername, USERNAME_MISMATCH_MESSAGE);
        Assert.assertEquals(userPersonal.email, currentEmail, EMAIL_MISMATCH_MESSAGE);
        Assert.assertEquals(userPersonal.id, currentUserId, ID_MISMATCH_MESSAGE);

    }
}
