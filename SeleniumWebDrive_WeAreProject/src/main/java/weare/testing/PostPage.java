package weare.testing;

import org.openqa.selenium.WebDriver;
import weare.models.CommentModel;

import static com.telerikacademy.testframework.Utils.getUIMappingByKey;

public class PostPage extends BaseWeArePage {

    public PostPage(WebDriver driver, String urlKey) {
        super(driver, urlKey);
    }

    public void update(String content, String visibility, int postId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.editButton"), postId));
        actions.clickElement(String.format(getUIMappingByKey("weAre.postPage.editButton"), postId));
        actions.waitForElementVisible("weAre.newPostPage.postVisibilityDropdown");
        actions.clickElement("weAre.newPostPage.postVisibilityDropdown");
        if (visibility.equals("Public")) {
            actions.clickElement("weAre.newPostPage.selectPublic");
        } else {
            actions.clickElement("weAre.newPostPage.selectPrivate");
        }
        actions.waitForElementPresent("weAre.newPostPage.messageField");
        actions.clearField("weAre.newPostPage.messageField");
        actions.typeValueInField(content, "weAre.newPostPage.messageField");
        actions.clickElement("weAre.newPostPage.submitButton");
    }

    public void delete(int postId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.deleteButton"), postId));
        actions.clickElement(String.format(getUIMappingByKey("weAre.postPage.deleteButton"), postId));
        actions.waitForElementVisible("weAre.newPostPage.postVisibilityDropdown");
        actions.clickElement("weAre.newPostPage.postVisibilityDropdown");
        actions.clickElement("weAre.postPage.selectDeletePost");
        actions.clickElement("weAre.postPage.submitButton");
    }

    public void createComment(CommentModel commentModel) {
        actions.waitForElementVisible("weAre.postPage.commentField");
        actions.typeValueInField(commentModel.content, "weAre.postPage.commentField");
        actions.clickElement("weAre.postPage.postCommentButton");
    }
    public void deleteComment(int commentId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.deleteCommentButton"), commentId));
        actions.clickElement(String.format(getUIMappingByKey("weAre.postPage.deleteCommentButton"), commentId));

        actions.waitForElementVisible("weAre.postPage.deleteCommentDropdown");
        actions.clickElement("weAre.postPage.deleteCommentDropdown");
        actions.waitForElementVisible("weAre.postPage.optionDeleteComment");
        actions.clickElement("weAre.postPage.optionDeleteComment");

        actions.clickElement("weAre.postPage.submitButton");
    }

    public void likeComment(int commentId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.likeCommentButton"), commentId));
        actions.clickElement(String.format(getUIMappingByKey("weAre.postPage.likeCommentButton"), commentId));
    }
    public void dislikeComment(int commentId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.dislikeCommentButton"), commentId));
        actions.clickElement(String.format(getUIMappingByKey("weAre.postPage.dislikeCommentButton"), commentId));
    }

    public void editComment(int commentId, String content) {
        actions.clickElement( String.format(getUIMappingByKey("weAre.postPage.editCommentButton"), commentId));
        actions.waitForElementVisible("weAre.postPage.commentField");
        actions.typeValueInField(content, "weAre.postPage.commentField");
        actions.clickElement("weAre.postPage.postCommentButton");
    }

    public void assertCommentIsLiked(int commentId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId));
        actions.assertElementPresent(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId));

        actions.getElement(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId)).getText().equals("Likes: 1");
    }
    public void assertCommentIsDisliked(int commentId) {
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId));
        actions.assertElementPresent(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId));

        actions.getElement(String.format(getUIMappingByKey("weAre.postPage.spanLikes"), commentId)).getText().equals("Likes: 0");
    }

    public void assertCommentPresent(CommentModel commentModel) {
        actions.waitForElementClickable("weAre.postPage.showCommentsButton");
        actions.clickElement("weAre.postPage.showCommentsButton");
        actions.waitForElementVisible(String.format(getUIMappingByKey("weAre.postPage.commentContent"), commentModel.content));
        actions.assertElementPresent(String.format(getUIMappingByKey("weAre.postPage.commentContent"), commentModel.content));
    }

    public void assertCommentIsDeleted(){
        actions.waitForElementVisible("weAre.postPage.deleteCommentConformationMessage");
        actions.assertElementPresent("weAre.postPage.deleteCommentConformationMessage");
    }
}
