package weare.api.testing.users;

import api.UserController;
import io.restassured.response.Response;
import models.ExpertiseProfile;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.*;

public class UpdateUserExpertiseTest extends BaseUserSetup {

    @BeforeClass
    public void setup() {
        if (isRegistered == false) {
            userToRegister = ModelGenerator.generateUserRegisterModel();
            register(userToRegister);
            isRegistered = true;

        }
    }

    @Test
    public void UpdateUserExpertise() {
        ExpertiseProfile expertiseProfile = ModelGenerator.generateUserExpertiseProfile(158, "Market", 33.66);
        authenticateAndFetchCookies();
        Response response = UserController.updateExpertiseProfile(cookies, expertiseProfile, currentUserId);
        System.out.println(response.asString());

        ExpertiseProfile updatedProfile = response.as(ExpertiseProfile.class);
        isResponse200(response);

        Assert.assertEquals(expertiseProfile.category.id, updatedProfile.category.id, CATEGORY_ID_MISMATCH_MESSAGE);
        Assert.assertEquals(expertiseProfile.category.name, updatedProfile.category.name, CATEGORY_NAME_MISMATCH_MESSAGE);
        Assert.assertEquals(expertiseProfile.availability, updatedProfile.availability, AVAILABILITY_MISMATCH_MESSAGE);
        System.out.println(PROFILE_UPDATED_SUCCESS_MESSAGE);
    }
}
