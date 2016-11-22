package jianshu.model;

/**
 * Created by zhaokangpan on 2016/11/7.
 */
public class Article {

    private int author_id;
    private String author_nickname;
    private String author_slug;
    private int author_public_notes_count;
    private int author_followers_count;
    private int author_total_likes_count;
    private int article_id;
    private String article_slug;
    private String title;
    private String text;
    private int wordage;
    private int views_count;
    private int comments_count;
    private int likes_count;
    private int rewards_total_count = 0;

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public void setAuthor_slug(String author_slug) {
        this.author_slug = author_slug;
    }

    public int getAuthor_public_notes_count() {
        return author_public_notes_count;
    }

    public void setAuthor_public_notes_count(int author_public_notes_count) {
        this.author_public_notes_count = author_public_notes_count;
    }

    public int getAuthor_followers_count() {
        return author_followers_count;
    }

    public void setAuthor_followers_count(int author_followers_count) {
        this.author_followers_count = author_followers_count;
    }

    public int getAuthor_total_likes_count() {
        return author_total_likes_count;
    }

    public void setAuthor_total_likes_count(int author_total_likes_count) {
        this.author_total_likes_count = author_total_likes_count;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_slug() {
        return article_slug;
    }

    public void setArticle_slug(String article_slug) {
        this.article_slug = article_slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWordage() {
        return wordage;
    }

    public void setWordage(int wordage) {
        this.wordage = wordage;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getRewards_total_count() {
        return rewards_total_count;
    }

    public void setRewards_total_count(int rewards_total_count) {
        this.rewards_total_count = rewards_total_count;
    }
}
