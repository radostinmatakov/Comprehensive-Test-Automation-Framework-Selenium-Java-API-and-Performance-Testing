package weare.testing;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.telerikacademy.testframework.Utils.getUIMappingByKey;

public class LatestPostsPage extends BaseWeArePage{
    public LatestPostsPage(WebDriver driver) {
        super(driver, "latestPosts.page");
    }

    public void likeDislikePost(int id) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.latestPostsPage.likeButton"), id));
        actions.clickElement(String.format(getUIMappingByKey("weAre.latestPostsPage.likeButton"), id));
    }

    public void assertPostIsLiked(int id){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(String.format(getUIMappingByKey("weAre.latestPostsPage.spanLikeCount"), id)), "Likes: 1"));
        WebElement spanLikes = actions.getElement(String.format(getUIMappingByKey("weAre.latestPostsPage.spanLikeCount"), id));
        Assertions.assertEquals("Likes: 1", spanLikes.getText(), "Post is not liked");
    };
    public void assertPostDisliked(int id){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(String.format(getUIMappingByKey("weAre.latestPostsPage.spanLikeCount"), id)), "Likes: 0"));
        WebElement spanLikes = actions.getElement(String.format(getUIMappingByKey("weAre.latestPostsPage.spanLikeCount"), id));
        Assertions.assertEquals("Likes: 0", spanLikes.getText(), "Post is liked");
    };

    public void assertPostIsPresent(String postContent) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.latestPostsPage.postContent"), postContent));
        assertElementPresent(String.format(getUIMappingByKey("weAre.latestPostsPage.postContent"), postContent));
    }

}
