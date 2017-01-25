package infocancer.nyesteventure.com.infocancer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import info.Constant.CONSTANTS;
import info.Database.DATABASE_C;
import info.Database.MainDataBAse;
import info.Fragment.Prevension.About;
import info.Fragment.UserProfile;
import info.Holder.DataUserAge;
import info.Holder.DataUserEmail;
import info.Holder.DataUserName;
import info.Holder.DataUserPPP;
import info.Holder.DataUserPhone;
import info.Holder.DataUserUrl;
import info.Holder.DataUserUserId;
import info.Holder.USerPicDatabase;
import info.NavItem.AlertBox_Inner;
import info.NavItem.AlertBox_outer;
import info.WebService.LoginApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static info.NetWorkCheck.NetworkChecker.isConnected;
import static info.NetWorkCheck.NetworkChecker.isConnectedMobile;
import static info.NetWorkCheck.NetworkChecker.isConnectedWifi;


public class UserChoicePage extends AppCompatActivity implements View.OnClickListener
{
    private static final int USERPROFILE =11 ;
    private static final int NEXTCLASS =10 ;
    private static final String TAG ="error_picassa" ;
    private Menu menus;
    private Button cancertext,imagetext,questiontext;
    private LinearLayout view1,view2,view3,view4;
    TextView view_t1,view_t2,view_t3,view_t4;
    TextView Title;
    private String userpropic;
    ImageView user_image;
    boolean b,mcheck;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String PPop,PPP;
    boolean y;
    private String url_pic;

    private String user_name,user_pic;
    private static final String DATABASETABLE4 ="userprofile" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        view1=(LinearLayout)findViewById(R.id.linear_view1);
        view2=(LinearLayout)findViewById(R.id.linear_view2);
        view3=(LinearLayout)findViewById(R.id.linear_view3);
        view4=(LinearLayout)findViewById(R.id.linear_view4);
     Toolbar toolbar=(Toolbar)findViewById(R.id.choice_toolbar);
        setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       view_t1 = (TextView) findViewById(R.id.view_text1);
        view_t2 = (TextView) findViewById(R.id.view_text2);
        view_t3 = (TextView) findViewById(R.id.view_textq);
        view_t4 = (TextView) findViewById(R.id.view_text4);
        user_image = (ImageView) findViewById(R.id.user_profile_pic_idxc);
        Title = (TextView) findViewById(R.id.id_title);
        view1.setOnClickListener(this);
       view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        user_image.setOnClickListener(this);
        MainDatabase();
        boolean u=DataUserPPP.getInstance().getDistributor_id();
        //String url= DataUserUrl.getInstance().getDistributor_id();
      //=url.contains("nourl") ? false : true;
        if(u){ userLogin(mcheck);} else{doctorLogin(mcheck);}
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/georgia.ttf");
        Title.setTypeface(face);
        view_t1.setTypeface(face);
        view_t2.setTypeface(face);
        view_t3.setTypeface(face);
        view_t4.setTypeface(face);

        }

    private void userLogin(boolean check) {
        if(check){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Post_Picassa(url_pic);


                }
            },100);

        }else{
            user_image.setImageResource(R.drawable.save_user1);
        }
    }
    private void doctorLogin(boolean check) {
        if(check){
            Post_Picassa(url_pic);
          //  Log.e("pic_picass",url);
         new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Post_Picassa(url_pic);

                }
            },100);

        }else{
            user_image.setImageResource(R.drawable.save_doctor1);
        }
    }


    private void MainDatabase() {


            MainDataBAse data = new MainDataBAse(UserChoicePage.this);
            data.open();

            String PPP = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_PPP, 1);
            String userid = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_userid, 1);
            String phone = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_phone, 1);
            String age = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_age, 1);
            String name = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_name, 1);
            url_pic = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_url, 1);
            String email = data.getNameUser(DATABASE_C.TABLE_NAME.T_USER, DATABASE_C.COLUMN_NAME_USER.U_email, 1);
       boolean ch=  data.check(DATABASE_C.TABLE_NAME.TEMP_userPIC);
            data.close();


          y=PPP.contains("userlogin") ? true :false;
         mcheck=url_pic.trim().contains("nourl") ? false :true;
       // Log.e("urlnm",url_pic);

            DataUserPPP.getInstance().setDistributor_id(y);
            DataUserAge.getInstance().setDistributor_id(age);
            DataUserPhone.getInstance().setDistributor_id(phone);
            DataUserName.getInstance().setDistributor_id(name);
            DataUserUserId.getInstance().setDistributor_id(userid);
            DataUserEmail.getInstance().setDistributor_id(email);
        DataUserUrl.getInstance().setDistributor_id(url_pic);
          /*if(ch){
            if(mcheck)
            {
                 String _path=download_image(url,y);
                data.setTEMPpath(DATABASE_C.TABLE_NAME.TEMP_userPIC,_path);
            }

        }else{
      String _path=data.getTEMPpath(DATABASE_C.TABLE_NAME.TEMP_userPIC,1);
        }*/




    }

    private String download_image(String ppp,boolean b) {
        String cat="";
        String FileDownloaded = "";
        if(b){cat="userimages";}else{cat="doctorimages";}

      String CurrentString =ppp;
      String[] separated = CurrentString.split(cat+"/");


String yurl=separated[1];
      Log.e("url_1",yurl);
      String urlio=separated[0].trim();
     // http://infocancer.nyesteventuretech.com/Service/photos/userimages/20_9497571826.jpeg
    //  http://idolosol.com/images/ss-1.jpg
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("http://infocancer.nyesteventuretech.com/Service/photos/"+cat+"/")
              .addConverterFactory(GsonConverterFactory.create())
              .build();

      LoginApi.RetrofitImageAPI service = retrofit.create(LoginApi.RetrofitImageAPI.class);

      Call<ResponseBody> call = service.getImageDetails(yurl);
      call.enqueue(new retrofit2.Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              try {

                //  Log.d("onResponse", "Response came from server");

                  String FileDownloaded = DownloadImage(response.body());

                 // Log.d("onResponse", "Image is downloaded and saved ? " + FileDownloaded);

              } catch (Exception e) {
                  Log.d("onResponse", "There is an error");
                  e.printStackTrace();
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {
              Log.d("onFailure", t.toString());
          }
      });

return FileDownloaded;
  }
    private String DownloadImage(ResponseBody body) {
        try {
            Log.d("DownloadImage", "Reading and writing file");
            InputStream in = null;
            FileOutputStream out = null;
            userpropic=getExternalFilesDir(null) + File.separator + "userpropic.jpg";

            try {
                in = body.byteStream();
                out = new FileOutputStream(userpropic);
                int c;

                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            catch (IOException e) {
                Log.d("DownloadImage",e.toString());
                //return false;
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

            int width, height;

            Bitmap bMap = BitmapFactory.decodeFile(userpropic);
          //  width = 2*bMap.getWidth();
           // height = 6*bMap.getHeight();

            user_image.setImageBitmap(bMap);

          //  USerPicDatabase.getInstance().setDistributor_id(userpropic);

           /* SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_MULTI_PROCESS).edit();
            editor.putString("namecjk", userpropic);
            editor.commit();*/

         return userpropic;

        } catch (IOException e) {
            Log.d("DownloadImage",e.toString());
           // return false;
        }

        return userpropic;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int k=item.getItemId();
        if(k==R.id.menuabout)
        {
            Intent about=new Intent(UserChoicePage.this,About.class);
            startActivity(about);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Post_Picassa(String url) {

            Picasso picasso=new Picasso.Builder(UserChoicePage.this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                }
            }).build();
              picasso.load(url)
            .into(user_image, new Callback() {
                @Override
                public void onSuccess() {
                    //Log.d(TAG, "sucseess");
                }
                @Override
                public void onError() {
                   // Log.d(TAG, "error");
                }
            });

    }

    //http://infocancer.nyesteventuretech.com/Service/photos/userimages/10_8606331796.jpeg
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_view1:

                if(isConnectedWifi(UserChoicePage.this)||isConnectedMobile(UserChoicePage.this))
                {
                    if(isConnected(UserChoicePage.this)) {

                    Intent i=new Intent(UserChoicePage.this,MainSlideMenu.class);

                    startActivity(i);
                    finish();

                    }
                    else
                    {
                        new AlertBox_Inner(UserChoicePage.this);

                    }
                }else
                {
                    new AlertBox_outer(UserChoicePage.this);


                }



                break;
            case R.id.linear_view2:
                if(isConnectedWifi(UserChoicePage.this)||isConnectedMobile(UserChoicePage.this))
                {
                    if(isConnected(UserChoicePage.this)) {

                        Intent it=new Intent(UserChoicePage.this,ImageViews_Class.class);
                        startActivity(it);
                        finish();
                        break;

                    }
                    else
                    {
                        new AlertBox_Inner(UserChoicePage.this);

                    }
                }else
                {
                    new AlertBox_outer(UserChoicePage.this);

                    // new AlertBox("error",getActivity());
                }





           break;

            case R.id.linear_view3:
                if(isConnectedWifi(UserChoicePage.this)||isConnectedMobile(UserChoicePage.this)) {
                    if (isConnected(UserChoicePage.this)) {
                        if (y) {
                            Intent intent = new Intent(UserChoicePage.this, Questions.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Intent intent = new Intent(UserChoicePage.this, Question_Doctor.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        new AlertBox_Inner(UserChoicePage.this);
                    }
                }else
                {
                    new AlertBox_outer(UserChoicePage.this);

                }

                //Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();










                break;
            case R.id.user_profile_pic_idxc:

              Intent nezs=new Intent(UserChoicePage.this, UserProfile.class);

                startActivity(nezs);
                this.finish();


                break;
            case R.id.linear_view4:

                Toast.makeText(this, "under construction", Toast.LENGTH_SHORT).show();
                break;



        }
    }






    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(requestCode==USERPROFILE)
        {
            finish();
        }

    }
}
