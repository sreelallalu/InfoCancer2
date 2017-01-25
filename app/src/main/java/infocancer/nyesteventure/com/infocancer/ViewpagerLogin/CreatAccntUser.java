package infocancer.nyesteventure.com.infocancer.ViewpagerLogin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import info.CameraClick.CameraPhoto;
import info.CameraClick.GalleryPhoto;
import info.Database.DATABASE_C;
import info.Database.MainDataBAse;
import info.Database.UserDbItem;
import info.Holder.DataCheckHolder;
import info.Holder.DataUserAge;
import info.Holder.DataUserEmail;
import info.Holder.DataUserName;
import info.Holder.DataUserPhone;
import info.Holder.DataUserUrl;
import info.Holder.DataUserUserId;
import info.ItemServiceHolder.AccntCreatItem;
import info.ItemServiceHolder.VarificationItem;
import info.NavItem.AlertBox_;
import info.NavItem.AlertBox_Inner;
import info.NavItem.AlertBox_outer;
import info.WebService.RestBuilderPro;
import infocancer.nyesteventure.com.infocancer.CreateAccount;
import infocancer.nyesteventure.com.infocancer.LoginActivity;
import infocancer.nyesteventure.com.infocancer.R;
import infocancer.nyesteventure.com.infocancer.UserChoicePage;
import retrofit2.Call;
import retrofit2.Response;

import static info.NetWorkCheck.NetworkChecker.isConnected;
import static info.NetWorkCheck.NetworkChecker.isConnectedMobile;
import static info.NetWorkCheck.NetworkChecker.isConnectedWifi;

/**
 * Created by lalu on 1/4/2017.
 */

public class CreatAccntUser extends AppCompatActivity {
    private Button _save;
    private ImageView _edit_pic,camarapicp;
    private EditText _name,_email,_age,_mobile,_pass;
    private Spinner _geneder;
  private  CameraPhoto cameraPhoto;
    private int CAMERACLICk=12;
   private GalleryPhoto galleryPhoto;
    private static final int GALLERY_DATAD =11 ;
    private static final int CROPE = 712;
    private int CROPEx=60;
    private String photostring;
    private boolean checkf;
    private  EditText validno,emailvali;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        Intent intent=getIntent();
        checkf=intent.getBooleanExtra("checkfirst",true);
        findID();

    }

    private void findID() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarkk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        _save=(Button)findViewById(R.id.user_profile_save);

        //  _save.setVisibility(View.INVISIBLE);
        camarapicp=(ImageView)findViewById(R.id.user_profile_imageview);
        _edit_pic=(ImageView)findViewById(R.id.userpr_floting);
        _edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeapic();
            }
        });
        cameraPhoto=new CameraPhoto(getApplicationContext());
        galleryPhoto=new GalleryPhoto(getApplicationContext());
        _name=(EditText)findViewById(R.id.user_proid_name);
        _email=(EditText)findViewById(R.id.user_proid_email);
        _age=(EditText)findViewById(R.id.user_profile_age);
        _mobile=(EditText)findViewById(R.id.user_profile_mbno);
        _pass=(EditText)findViewById(R.id.user_profile_password);
        _geneder=(Spinner)findViewById(R.id.user_profile_gender);
        progressDialog = new ProgressDialog(CreatAccntUser.this);
        String gender[]={"male","female"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,gender);
        _geneder.setAdapter(adapter);
        if(!checkf)
        {
         String   h_name= DataUserName.getInstance().getDistributor_id();
        //String    h_email= DataUserEmail.getInstance().getDistributor_id();
         String   h_age= DataUserAge.getInstance().getDistributor_id();
         String   h_phone= DataUserPhone.getInstance().getDistributor_id();
          String  h_url= DataUserUrl.getInstance().getDistributor_id();
            _email.setVisibility(View.INVISIBLE);
            _name.setText(h_name);
           // _email.setText(h_email);
            _age.setText(h_age);
            _mobile.setText(h_phone);
            if(h_url.contains("nourl")){
                camarapicp.setImageResource(R.drawable.save_user1);
            }
            else{
                picassaurl(h_url);
            }




        }
        _save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkf){

                    CreatUserACCNT(v);}
                else{UpdateUser(v);}
            }
        });
        /*_*/
        
        
        
    }

    private void UpdateUser(View v) {

        if (isConnectedWifi(CreatAccntUser.this) || isConnectedMobile(CreatAccntUser.this)) {
            if (isConnected(CreatAccntUser.this)) {
                v.setClickable(false);
               updateUser(v);


            } else {
                new AlertBox_Inner(CreatAccntUser.this);
            }
        } else {
            new AlertBox_outer(CreatAccntUser.this);
        }


    }

    private void updateUser(View v) {
        if (!UserUpdate()) {
            v.setClickable(true);
            return;
        }
        progressDialog.show();
        String  _gname = _name.getText().toString();
       // String _gemail = _email.getText().toString();
        String  _gage = _age.getText().toString();
        String   _gmobile = _mobile.getText().toString();
        String _gpass= _pass.getText().toString();
        String gender =_geneder.getSelectedItem().toString();
        String idf= DataUserUserId.getInstance().getDistributor_id();

        onUdateUser(_gname ,_gage, _gmobile, _gpass, gender,v,idf);

    }

    private boolean UserUpdate() {
        boolean valid = true;
        String name=_name.getText().toString();
       // String email = _email.getText().toString();
        String password = _pass.getText().toString();
        String age = _age.getText().toString();
        String mob=_mobile.getText().toString();
        String gender =_geneder.getSelectedItem().toString();



//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _email.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _email.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _pass.setError(null);
        }

        if (name.isEmpty() || name.length() < 3 || name.length() > 18) {
            _name.setError("between 3 and 18 alphanumeric characters");
            valid = false;
        } else {
            _name.setError(null);
        }
        if (age.isEmpty() || age.length() < 2 || age.length() >2) {
            _age.setError("between 10 and 99 ");
            valid = false;
        } else {
            _age.setError(null);
        }
        if (mob.isEmpty() || mob.length() < 10 || mob.length() > 14) {
            _mobile.setError("between 10 and 14");
            valid = false;
        } else {
            _mobile.setError(null);
        }
        if (gender.isEmpty() ) {
            // Toast.makeText(CreateAccount.this,"genderempty", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;


    }

    private void onUdateUser(final String gname, final String gage, final String gmobile, final String gpass, String gender, View v, final String idf) {

        String encodeImage="";
        Bitmap bitmap = null;
        if(photostring!=null){
            try {
                bitmap = ImageLoader.init().from(photostring).requestSize(200, 200).getBitmap();
                encodeImage = ImageBase64.encode(bitmap);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        RestBuilderPro.getService().useraccntCreatupdate("userupdate",gname,gage,gmobile,gpass,gender,encodeImage,idf).enqueue(new retrofit2.Callback<AccntCreatItem>() {
            @Override
            public void onResponse(Call<AccntCreatItem> call, Response<AccntCreatItem> response) {
              progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    AccntCreatItem item=response.body();
                    int succ=item.getSuccess();
                    if(succ==1)
                    {
                        String url=item.getUrl();
                        try {
                        MainDataBAse dataBAse=new MainDataBAse(CreatAccntUser.this);
                            dataBAse.open();
                        if(url.contains("nourl"))
                        {
                            url= dataBAse.getNameUser(DATABASE_C.TABLE_NAME.T_USER,DATABASE_C.COLUMN_NAME_USER.U_url,1);
                        }
                           String email=DataUserEmail.getInstance().getDistributor_id();
                            dataBAse.updateuserTable(DATABASE_C.TABLE_NAME.T_USER, new UserDbItem(gname, email, gpass,idf,gmobile,gage, url), 1, "userlogin");
                            dataBAse.close();
                            DataCheckHolder.getInstance().setDistributor_id(true);


                            Intent intent = new Intent(CreatAccntUser.this, UserChoicePage.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else
                        if(succ==303)
                        {
                            String error=item.getErrormsg();
                            new AlertBox_(error,CreatAccntUser.this);
                        }

                }
                else {
                    new AlertBox_("error_connecting",CreatAccntUser.this);
                }
            }

            @Override
            public void onFailure(Call<AccntCreatItem> call, Throwable t) {
                new AlertBox_("error_connecting",CreatAccntUser.this);
                progressDialog.dismiss();
            }
        });


    }

    private void CreatUserACCNT(View v) {
        if (isConnectedWifi(CreatAccntUser.this) || isConnectedMobile(CreatAccntUser.this)) {
            if (isConnected(CreatAccntUser.this)) {
                 v.setClickable(false);

                createuser(v);


            } else {
                new AlertBox_Inner(CreatAccntUser.this);
            }
        } else {
            new AlertBox_outer(CreatAccntUser.this);
        }


    }

    private void createuser(View v) {
        if (!useronValid()) {
             v.setClickable(true);
            return;
        }
        progressDialog.show();
      String  _gname = _name.getText().toString();
       String _gemail = _email.getText().toString();
       String  _gage = _age.getText().toString();
      String   _gmobile = _mobile.getText().toString();
       String _gpass= _pass.getText().toString();
      String gender =_geneder.getSelectedItem().toString();

        onLoginSuccesUser(_gname, _gemail, _gage, _gmobile, _gpass, gender,v);



    }

    private void onLoginSuccesUser(String gname, String gemail, String gage, String gmobile, String gpass, String gender, final View v) {

        String encodeImage="";
        Bitmap bitmap = null;
        if(photostring!=null){
            try {
                bitmap = ImageLoader.init().from(photostring).requestSize(200, 200).getBitmap();
                encodeImage = ImageBase64.encode(bitmap);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        RestBuilderPro.getService().useraccntCreat("userregister",gname,gemail,gage,gmobile,gpass,gender,encodeImage).enqueue(new retrofit2.Callback<AccntCreatItem>() {
            @Override
            public void onResponse(Call<AccntCreatItem> call, Response<AccntCreatItem> response) {
                v.setClickable(true);
                progressDialog.dismiss();
                if(response.isSuccessful())
                {

                    AccntCreatItem accnt=response.body();
                    int ch=accnt.getSuccess();
                    if (ch == 1) {

                    varification("verifyuserkey");




                    }else if(ch==404)
                    {
                        String errormsg= accnt.getErrormsg();
                        new AlertBox_(errormsg, CreatAccntUser.this);
                    }
                    else if (ch == 303) {

                        String errormsg= accnt.getErrormsg();
                        new AlertBox_(errormsg, CreatAccntUser.this);
                    }
                }


            }

            @Override
            public void onFailure(Call<AccntCreatItem> call, Throwable t) {
             v.setClickable(true);
            }
        });



    }

    private void varification(String verifyuserkey) {
        setContentView(R.layout.email_valid_number);
       validno=(EditText)findViewById(R.id.edit_valid_noqe);
        emailvali=(EditText)findViewById(R.id.edit_valid_noqeemail);
        Button click=(Button)findViewById(R.id.email_valid_okqe);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnectedWifi(CreatAccntUser.this) || isConnectedMobile(CreatAccntUser.this)) {
                    if (isConnected(CreatAccntUser.this)) {

                        if(!valdate()){
                            v.setClickable(true);
                            return;
                        }

                        progressDialog.show();
                        String _validno=validno.getText().toString();
                        String _emailvali=emailvali.getText().toString();

                            v.setClickable(false);
                            POstSend(v,_validno,_emailvali);



                    }else{
                        new AlertBox_Inner(CreatAccntUser.this);
                    }
                }else{
                    new AlertBox_outer(CreatAccntUser.this);
                }
            }

        });

    }

    private boolean valdate() {
        boolean check=true;
        String _validno=validno.getText().toString();
        String _emailvali=emailvali.getText().toString();
        if(_emailvali.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(_emailvali).matches()){

            check=false;
            emailvali.setError("not valid");
        }
        else
        if(_validno.isEmpty())
        {
            check=false;
            validno.setError("null value");
        }
       return check;
    }

    private void POstSend(final View v, String valid, final String email) {

        RestBuilderPro.getService().userkey("verifyuserkey",email,valid).enqueue(new retrofit2.Callback<VarificationItem>() {
            @Override
            public void onResponse(Call<VarificationItem> call, Response<VarificationItem> response) {
             progressDialog.dismiss();
                v.setClickable(true);
                if(response.isSuccessful())
                {
                    VarificationItem vaif=response.body();
                    int ch=vaif.getSuccess();
                    if(ch==1) {

                        String url = vaif.getUrl();
                        String user_id = vaif.getUserid();

                       String uname=vaif.getUsername();
                        String age=vaif.getAge();

                        String mobile=vaif.getMobile();
                        String password=vaif.getPassword();


                        MainDataBAse dataBase=new MainDataBAse(CreatAccntUser.this);
                        dataBase.open();
                        try {
                            dataBase.createuserTable(DATABASE_C.TABLE_NAME.T_USER, new UserDbItem(uname,email, password, user_id,mobile,age, url),"userlogin");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dataBase.close();

                        DataCheckHolder.getInstance().setDistributor_id(true);
                        Intent intent = new Intent(CreatAccntUser.this, UserChoicePage.class);

                        startActivity(intent);
                        finish();
                    }else
                    if(ch==303){

                        new AlertBox_(vaif.getErrormsg(),CreatAccntUser.this);

                    }
                    else
                        {
                            new AlertBox_("error connecting",CreatAccntUser.this);
                }
                }
            }

            @Override
            public void onFailure(Call<VarificationItem> call, Throwable t) {
                new AlertBox_("error connecting",CreatAccntUser.this);
                v.setClickable(true);
            }
        });

    }


    private boolean useronValid() {

        boolean valid = true;
        String name=_name.getText().toString();
        String email = _email.getText().toString();
        String password = _pass.getText().toString();
        String age = _age.getText().toString();
        String mob=_mobile.getText().toString();
        String gender =_geneder.getSelectedItem().toString();



        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("enter a valid email address");
            valid = false;
        } else {
            _email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _pass.setError(null);
        }

        if (name.isEmpty() || name.length() < 3 || name.length() > 18) {
            _name.setError("between 3 and 18 alphanumeric characters");
            valid = false;
        } else {
            _name.setError(null);
        }
        if (age.isEmpty() || age.length() < 2 || age.length() >2) {
            _age.setError("between 10 and 99 ");
            valid = false;
        } else {
            _age.setError(null);
        }
        if (mob.isEmpty() || mob.length() < 10 || mob.length() > 14) {
            _mobile.setError("between 10 and 14");
            valid = false;
        } else {
            _mobile.setError(null);
        }
        if (gender.isEmpty() ) {
           // Toast.makeText(CreateAccount.this,"genderempty", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;


    }



    private void takeapic() {
        ActivityCompat.requestPermissions(CreatAccntUser.this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE},
                1);


        String choice[]={"Take a Picture","Select From Gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreatAccntUser.this,android.R.layout.select_dialog_item, choice);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
//cameratestdemo();
                    callcameraxc();
                }
                if (which == 1) {
                    try {
                        gallareydf();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        final AlertDialog dialog = builder.create();
        dialog.show();


    }


    private void picassaurl(String h) {
        try {
            Picasso picasso=new Picasso.Builder(CreatAccntUser.this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                }
            }).build();




            picasso.load(h)

                    .into(camarapicp, new Callback() {
                        @Override
                        public void onSuccess() {

                           // Log.d(TAG,"sucseess");
                        }

                        @Override
                        public void onError()
                        {
                           // Log.d(TAG,"error");
                        }
                    });
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    private void gallareydf()throws Exception {
        startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_DATAD);
    }

    private void callcameraxc() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERACLICk);
        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(CreatAccntUser.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==CreatAccntUser.RESULT_OK) {

            if (requestCode == CAMERACLICk)
            {
                try { String photoPaths = cameraPhoto.getPhotoPath();
                    File op = new File(photoPaths);
                    Uri uricv = Uri.fromFile(op);
                    //  Log.e("crop",photoPaths);

                    performCrop(uricv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == GALLERY_DATAD)
            {
                try {
                    Uri uri = data.getData();
                    galleryPhoto.setPhotoUri(uri);
                    String   photoPaths = galleryPhoto.getPath();
                    File op = new File(photoPaths);
                    Uri uricv = Uri.fromFile(op);
                    Log.e("crop",photoPaths);
                    try {
                        performCropnm(uricv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            else
            if (requestCode==CROPE)

            {
                String photoPaths = cameraPhoto.getPhotoPath();
                photostring=photoPaths;
                Log.e("path",photoPaths);
                try{
                    File op = new File(photoPaths);

                    if(op.exists()){

                        Picasso.with(CreatAccntUser.this).load(op).into(camarapicp);

                    }}catch (Exception e){
                    e.printStackTrace();
                }
            }
            else
            if (requestCode==CROPEx)

            {
                String photoPaths = cameraPhoto.getPhotoPath();
                photostring=photoPaths;
                Log.e("path",photoPaths);
                try{
                    File op = new File(photoPaths);

                    if(op.exists()){

                        Picasso.with(CreatAccntUser.this).load(op).into(camarapicp);


                    }}catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    private void performCrop(Uri picUri) {

        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            File photoFile = cameraPhoto.createImageFile();
            cropIntent.putExtra("output",Uri.fromFile(photoFile));
            startActivityForResult(cropIntent,CROPE);
        }

        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){e.printStackTrace();}
    }
    private void performCropnm(Uri picUri) {

        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            File photoFile = cameraPhoto.createImageFile();
            cropIntent.putExtra("output",Uri.fromFile(photoFile));

            startActivityForResult(cropIntent,CROPEx);

        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(checkf){

            Intent intent=new Intent(CreatAccntUser.this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        else
        {
            Intent intent=new Intent(CreatAccntUser.this,UserChoicePage.class);
            startActivity(intent);
            this.finish();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                if(checkf){

                    Intent intent=new Intent(CreatAccntUser.this,LoginActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                else
                {
                    Intent intent=new Intent(CreatAccntUser.this,UserChoicePage.class);
                    startActivity(intent);
                    this.finish();
                }
                return true;

        }

        return super.onOptionsItemSelected(item);




    }
}
