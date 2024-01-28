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

import static utils.Constants.EMPTY_SKILLS_LIST_MESSAGE;
import static utils.Constants.SKILL_NOT_FOUND_MESSAGE_FORMAT;

public class GetAllSkillsTest extends BaseTestSetup {

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
    public void getAllSkills() {

        Response response = SkillController.getAllSkills(cookies);
        isResponse200(response);
        Skill[] skillsList = response.as(Skill[].class);
        Assert.assertTrue(skillsList.length > 0, EMPTY_SKILLS_LIST_MESSAGE);
        Assert.assertTrue(assertCreatedSkillIdIsPresent(skillsList, createdSkill.skillId));

    }

    @AfterClass
    public void tearDown() {
        Response response = SkillController.deleteSkill(createdSkill.skillId);
        isResponse200(response);
    }

    private boolean assertCreatedSkillIdIsPresent(Skill[] skillsList, int id) {
        for (Skill skill : skillsList) {
            if (skill.skillId == id) {
                return true;
            }
        }
        Assert.fail(String.format(SKILL_NOT_FOUND_MESSAGE_FORMAT, id));
        return false;
    }
}

