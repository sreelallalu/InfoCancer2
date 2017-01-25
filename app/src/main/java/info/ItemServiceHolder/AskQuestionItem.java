package info.ItemServiceHolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lalu on 1/4/2017.
 */

public class AskQuestionItem {


        @SerializedName("succ")
        @Expose
        private String succ;

        public String getSucc() {
            return succ;
        }

        public void setSucc(String succ) {
            this.succ = succ;
        }

    }
