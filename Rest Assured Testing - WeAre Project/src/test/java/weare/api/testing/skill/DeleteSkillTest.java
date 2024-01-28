package weare.api.testing.skill;

import api.SkillController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.Skill;
import models.UserRegister;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

public class DeleteSkillTest extends BaseTestSetup {
    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }
        skillToCreated = ModelGenerator.generateSkillModel(155);
        createdSkill = SkillController.createSkill(cookies, skillToCreated).as(Skill.class);
    }

    @Test
    public void deleteSkill() {
        Response response = SkillController.deleteSkill(createdSkill.skillId);
        isResponse200(response);
    }
}
