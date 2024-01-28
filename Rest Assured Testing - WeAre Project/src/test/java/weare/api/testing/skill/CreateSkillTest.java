package weare.api.testing.skill;

import api.SkillController;
import base.BaseTestSetup;
import io.restassured.response.Response;
import models.Skill;
import models.UserRegister;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ModelGenerator;

public class CreateSkillTest extends BaseTestSetup {
    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
        }
    }

    @Test
    public void createSkill() {
        skillToCreated = ModelGenerator.generateSkillModel(155);

        Response response = SkillController.createSkill(cookies, skillToCreated);
        isResponse200(response);

        createdSkill = response.as(Skill.class);
        Assert.assertEquals(createdSkill.skill, skillToCreated.skill);
        Assert.assertEquals(createdSkill.category.id, skillToCreated.category.id);
    }

    @AfterClass
    public void tearDown() {
        if (createdSkill != null) {
            SkillController.deleteSkill(createdSkill.skillId);
        }
    }
}
