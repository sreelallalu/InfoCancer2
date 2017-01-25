package info.ItemServiceHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class LogInDoctor {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("succ")
    @Expose
    private Integer succ;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("photourl")
    @Expose
    private String photourl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("doctorid")
    @Expose
    private String doctorid;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSucc() {
        return succ;
    }

    public void setSucc(Integer succ) {
        this.succ = succ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}







