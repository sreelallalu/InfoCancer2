package info.ItemServiceHolder;

/**
 * Created by lalu on 1/3/2017.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentItem {

    @SerializedName("comt")
    @Expose
    private List<Comt> comt = new ArrayList<>();

    public List<Comt> getComt() {
        return comt;
    }

    public void setComt(List<Comt> comt) {
        this.comt = comt;
    }
    public class Comt {

        @SerializedName("cmt_id")
        @Expose
        private String cmtId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("q_image")
        @Expose
        private String qImage;
        @SerializedName("datetime")
        @Expose
        private String datetime;
        @SerializedName("fname")
        @Expose
        private String fname;

        public String getCmtId() {
            return cmtId;
        }

        public void setCmtId(String cmtId) {
            this.cmtId = cmtId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getQImage() {
            return qImage;
        }

        public void setQImage(String qImage) {
            this.qImage = qImage;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

}}