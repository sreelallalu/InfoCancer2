package info.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import info.Database.DATABASE_C;
import info.Database.MainDataBAse;
import info.Holder.DataUserAge;
import info.Holder.DataUserEmail;
import info.Holder.DataUserName;
import info.Holder.DataUserPPP;
import info.Holder.DataUserPhone;
import info.Holder.DataUserUrl;
import infocancer.nyesteventure.com.infocancer.CreateAccount;
import infocancer.nyesteventure.com.infocancer.R;
import infocancer.nyesteventure.com.infocancer.UserChoicePage;
import infocancer.nyesteventure.com.infocancer.ViewpagerLogin.CreatAccntDoctor;
import infocancer.nyesteventure.com.infocancer.ViewpagerLogin.CreatAccntUser;


/**
 * Created by SLR on 12/3/2016.
 */

public class UserProfile extends AppCompatActivity {
   private ImageView imgv,edit;
    private TextView _name,_email,_age,_phone;
    private boolean b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_view);

        imgv=(ImageView)findViewById(R.id.u_p_img);
        edit=(ImageView)findViewById(R.id.u_p_edit);

        _name=(TextView) findViewById(R.id.u_p_name);
        _age=(TextView) findViewById(R.id.u_p_age);
        _phone=(TextView) findViewById(R.id.u_p_phone);
        _email=(TextView) findViewById(R.id.u_p_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarkk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            String nameq = DataUserName.getInstance().getDistributor_id();
            String emailq = DataUserEmail.getInstance().getDistributor_id();
            String phoneq = DataUserPhone.getInstance().getDistributor_id();
            String ageq = DataUserAge.getInstance().getDistributor_id();
            String urlq = DataUserUrl.getInstance().getDistributor_id();
            b = DataUserPPP.getInstance().getDistributor_id();
            boolean kcheck=urlq.contains("nourl") ? false : true;

            _name.setText(nameq);
            _age.setText(ageq);
            _phone.setText(phoneq);
            _email.setText(emailq);

          if(b){
              //user
             if(kcheck){Post_Picassa(urlq);}else{imgv.setImageResource(R.drawable.save_user1);}
          }else{
              //doctor
              if(kcheck){Post_Picassa(urlq);}else{imgv.setImageResource(R.drawable.save_doctor1);}
          }


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    change(b);

                }
            });


        }catch (Exception e){e.printStackTrace();}



    }

    private void change(boolean b) {

      if(b){
          Intent ui = new Intent(UserProfile.this, CreatAccntUser.class);
          ui.putExtra("checkfirst", false);
          UserProfile.this.startActivity(ui);
          UserProfile.this.finish();

        }else
      {

          Intent ui = new Intent(UserProfile.this, CreatAccntDoctor.class);
          ui.putExtra("checkfirst", false);
          UserProfile.this.startActivity(ui);
          UserProfile.this.finish();
        }
    }

    private void Post_Picassa(String url) {

        try {
            Picasso picasso=new Picasso.Builder(UserProfile.this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                }
            }).build();

                picasso.load(url)
                        .into(imgv, new Callback() {
                            @Override
                            public void onSuccess() {
                               // Log.d(TAG, "sucseess");
                            }

                            @Override
                            public void onError() {

                                //Log.d(TAG, "error");
                            }
                        });


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(UserProfile.this,UserChoicePage.class);
        startActivity(intent);
        this.finish();
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                Intent intent=new Intent(UserProfile.this,UserChoicePage.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
