package info.WebService;

import android.util.Log;

import info.ItemServiceHolder.LogInUser;

/**
 * Created by lalu on 12/21/2016.
 */

public class RestBuilderPro {
    private static LoginApi service;
    public static   LoginApi getService(){
        service=ServiceGeneratorpro.createService(LoginApi.class);
        return service;
    }


}
