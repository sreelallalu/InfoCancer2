package info.ItemServiceHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lalu on 1/7/2017.
 */

public class VarificationItem {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("errormsg")
    @Expose
    private String errormsg;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("username")
    @Expose
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
