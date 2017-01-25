package info.ItemServiceHolder;

/**
 * Created by lalu on 1/7/2017.
 */


import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class  AccntCreatItem
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("errormsg")
    @Expose
    private String errormsg;

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

}