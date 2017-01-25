package info.ItemServiceHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import infocancer.nyesteventure.com.infocancer.Reply;

/**
 * Created by lalu on 1/3/2017.
 */

public class ReplyItem {

    @SerializedName("replyuser")
    @Expose
    private List<ReplyItem.Replyuser> replyuser = new ArrayList<>();

    public List<ReplyItem.Replyuser> getReplyuser() {
        return replyuser;
    }

    public void setReplyuser(List<ReplyItem.Replyuser> replyuser) {
        this.replyuser = replyuser;
    }

    public class Replyuser {

        @SerializedName("reply")
        @Expose
        private String reply;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("doctorname")
        @Expose
        private String doctorname;
        @SerializedName("doctorphoto")
        @Expose
        private String doctorphoto;
        @SerializedName("type")
        @Expose
        private String type;

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDoctorname() {
            return doctorname;
        }

        public void setDoctorname(String doctorname) {
            this.doctorname = doctorname;
        }

        public String getDoctorphoto() {
            return doctorphoto;
        }

        public void setDoctorphoto(String doctorphoto) {
            this.doctorphoto = doctorphoto;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }}
