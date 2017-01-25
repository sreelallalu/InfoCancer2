package info.ItemServiceHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalu on 1/3/2017.
 */


public class QuestionItems {

    @SerializedName("user")
    @Expose
    private List<User> user =new ArrayList<>();

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public class User {

        @SerializedName("postid")
        @Expose
        private String postid;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("datetime")
        @Expose
        private String datetime;
        @SerializedName("q_image")
        @Expose
        private String qImage;
        @SerializedName("u_name")
        @Expose
        private String uName;
        @SerializedName("u_image")
        @Expose
        private String uImage;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getQImage() {
            return qImage;
        }

        public void setQImage(String qImage) {
            this.qImage = qImage;
        }

        public String getUName() {
            return uName;
        }

        public void setUName(String uName) {
            this.uName = uName;
        }

        public String getUImage() {
            return uImage;
        }

        public void setUImage(String uImage) {
            this.uImage = uImage;
        }

    }
}