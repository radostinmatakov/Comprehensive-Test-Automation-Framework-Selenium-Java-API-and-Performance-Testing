package weare.testing;

import org.openqa.selenium.WebDriver;

public class NewPostPage extends BaseWeArePage {


    public NewPostPage(WebDriver driver) {
        super(driver, "newPost.page");
    }

    public void createPost(String content, String visibility) {
        actions.waitForElementVisible("weAre.newPostPage.postVisibilityDropdown");
        actions.clickElement("weAre.newPostPage.postVisibilityDropdown");
        if (visibility.equals("Public")) {
            actions.clickElement("weAre.newPostPage.selectPublic");
        }else {
            actions.clickElement("weAre.newPostPage.selectPrivate");
        }
        actions.waitForElementPresent("weAre.newPostPage.messageField");
        actions.typeValueInField(content, "weAre.newPostPage.messageField");
        actions.clickElement("weAre.newPostPage.submitButton");
    }



}
