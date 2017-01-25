package info.Holder;

public class DataUserPPP {
    private static DataUserPPP  dataObject = null;

    private DataUserPPP () {
        // left blank intentionally
    }

    public static DataUserPPP  getInstance() {
        if (dataObject == null)
            dataObject = new DataUserPPP ();
        return dataObject;
    }
    private boolean distributor_id;;

    public boolean getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(boolean distributor_id) {

        this.distributor_id = distributor_id;
    }
}
