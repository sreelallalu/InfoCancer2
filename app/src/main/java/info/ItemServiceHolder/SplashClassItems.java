package info.ItemServiceHolder;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SplashClassItems {

    @SerializedName("cat")
    @Expose
    private List<Cat> cat = new ArrayList<>();
    @SerializedName("succ")
    @Expose
    private Integer succ;

    public List<Cat> getCat() {
        return cat;
    }

    public void setCat(List<Cat> cat) {

        this.cat = cat;
    }

    public Integer getSucc() {
        return succ;
    }

    public void setSucc(Integer succ) {
        this.succ = succ;
    }
    public class Cat {

        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("succ")
        @Expose
        private Integer succ;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getSucc() {
            return succ;
        }

        public void setSucc(Integer succ) {
            this.succ = succ;
        }

    }

}