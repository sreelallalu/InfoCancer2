package info.Holder;

/**
 * Created by lalu on 12/26/2016.
 */


     

public class USerPicDatabase {
    private static USerPicDatabase  dataObject = null;

    private USerPicDatabase () {
        // left blank intentionally
    }

    public static USerPicDatabase  getInstance() {
        if (dataObject == null)
            dataObject = new USerPicDatabase ();
        return dataObject;
    }
    private String distributor_id;;

    public String getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(String distributor_id) {

        this.distributor_id = distributor_id;
    }
}


