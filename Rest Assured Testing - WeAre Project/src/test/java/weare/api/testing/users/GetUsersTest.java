package weare.api.testing.users;

import api.UserController;
import io.restassured.response.Response;
import models.Page;
import models.UserProfile;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.EMPTY_USER_LIST_MESSAGE;
import static utils.Constants.USER_NOT_FOUND_MESSAGE;

public class GetUsersTest extends BaseUserSetup {


    @Test()
    public void GetUsersTest() {
        Page page = ModelGenerator.generatePageModel(1500);
        Response response = UserController.getUsers(page);

        isResponse200(response);

        userProfileList = response.then().extract().as(UserProfile[].class);
        Assert.assertTrue(userProfileList.length > 0, EMPTY_USER_LIST_MESSAGE);
        Assert.assertTrue(assertUserIsPresented(userProfileList, currentUsername));

        userProfile = returnUserProfile(userProfileList, currentUsername);
        currentUserProfile = userProfile;
    }

    private boolean assertUserIsPresented(UserProfile[] userProfileList, String username) {
        for (UserProfile userProfile : userProfileList) {
            if (userProfile.username.equals(username)) {
                return true;
            }
        }
        Assert.fail(String.format(USER_NOT_FOUND_MESSAGE, username));
        return false;
    }

    private UserProfile returnUserProfile(UserProfile[] userProfileList, String username) {
        for (UserProfile userProfile : userProfileList)
            if (userProfile.username.equals(username)) {
                return userProfile;
            }
        return null;
    }
}
