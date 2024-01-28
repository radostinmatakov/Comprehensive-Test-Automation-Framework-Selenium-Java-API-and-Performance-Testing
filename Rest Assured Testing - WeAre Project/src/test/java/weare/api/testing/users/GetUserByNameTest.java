package weare.api.testing.users;

import api.UserController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.SearchUser;
import models.UserPersonal;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

import static utils.Constants.*;

public class GetUserByNameTest extends BaseTestSetup {

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
    }
    @Test
    public void GetUserByName (){
        SearchUser searchUser = ModelGenerator.generateSearchUserModel(SEARCH_USER_NAME);
        Response response = UserController.getUserByName(cookies, searchUser);

        isResponse200(response);

        UserPersonal[] userPersonalList = response.as(UserPersonal[].class);
        Assert.assertEquals(userPersonalList[0].username, EXPECTED_USERNAME, USERNAME_DOES_NOT_MATCH_MESSAGE);
    }
}