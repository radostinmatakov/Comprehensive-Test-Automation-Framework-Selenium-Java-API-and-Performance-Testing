package weare.api.testing.users;

import api.UserController;
import io.restassured.response.Response;
import models.UserPersonal;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.*;

public class UpdateUserPersonalProfileTest extends BaseUserSetup {
    @BeforeClass
    public void setup() {
        if(isRegistered == false){
            userToRegister = ModelGenerator.generateUserRegisterModel();
            register(userToRegister);
            isRegistered = true;
        }
        if (currentUserPersonalProfile == null) {
            currentUserPersonalProfile = ModelGenerator.generateUserPersonalModel();
        }
        authenticateAndFetchCookies();
    }

    @Test
    public void UpdateUserPersonalProfile() {
        Response response = UserController.updatePersonalProfile(cookies, currentUserPersonalProfile, currentUserId);
        isResponse200(response);

        UserPersonal returnedUserPersonalProfile = response.as(UserPersonal.class);
        Assert.assertEquals(returnedUserPersonalProfile.firstName, currentUserPersonalProfile.firstName, FIRST_NAME_MISMATCH_MESSAGE);
        Assert.assertEquals(returnedUserPersonalProfile.lastName, currentUserPersonalProfile.lastName, LAST_NAME_MISMATCH_MESSAGE);
        System.out.println(PROFILE_UPDATED_SUCCESS_MESSAGE);

    }
}
