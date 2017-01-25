package info.WebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lalu on 12/21/2016.
 */

public class ServiceGeneratorpro {

    private final static String BASEURL="http://infocancer.nyesteventuretech.com/Service/";

    public static<S> S createService(Class<S> service){
        Gson gson= new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

     Retrofit adapter=new Retrofit.Builder()
             .baseUrl(BASEURL)
             .addConverterFactory(GsonConverterFactory.create(gson))
             .build();
        return adapter.create(service);
    }
}
