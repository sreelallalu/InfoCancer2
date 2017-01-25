package infocancer.nyesteventure.com.infocancer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import info.CameraClick.CameraPhoto;
import info.CameraClick.GalleryPhoto;
import info.Constant.CONSTANTS;
import info.Holder.DataHolderClass;
import info.Holder.DataUserUserId;
import info.ItemServiceHolder.AskQuestionItem;
import info.NavItem.AlertBox_Inner;
import info.NavItem.AlertBox_outer;
import info.WebService.RestBuilderPro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.NetWorkCheck.NetworkChecker.isConnected;
import static info.NetWorkCheck.NetworkChecker.isConnectedMobile;
import static info.NetWorkCheck.NetworkChecker.isConnectedWifi;


/**
 * Created by SLR on 10/11/2016.
 */
public class Ask_question_User extends AppCompatActivity {


    private static final int GALLERY_DATAD =44 ;
    private TextView attach_file_text;
    private EditText subject,Contentq;
    private Spinner spinner;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    private int CAMERACLICk=43;
    String photostring;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ask_question);
        setupWindowAnimations();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarkk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        find_id();




        List<String> list= DataHolderClass.getInstance().getDistributor_id();

        ArrayAdapter dri=new ArrayAdapter<String>(Ask_question_User.this,
                R.layout.spinner_item,list);


        dri.setDropDownViewResource(R.layout.spinner_compo);

        spinner.setAdapter(dri);



        cameraPhoto=new CameraPhoto(getApplicationContext());
        galleryPhoto=new GalleryPhoto(getApplicationContext());




      //  gototest();



    }


    private void setupWindowAnimations() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
           this.getWindow().setExitTransition(slide);
        }

    }

    private void find_id() {

        attach_file_text=(TextView)findViewById(R.id.attch_file_id);
        subject=(EditText)findViewById(R.id.ask_question_subj);
        Contentq=(EditText)findViewById(R.id.ask_questiopn_content);
        spinner=(Spinner)findViewById(R.id.id_ask_question_ui);




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
                case android.R.id.home:
                    super.onBackPressed();
                    this.finish();
                    return true;
              case R.id.user_ask_attachment:
                takeapic();
                 return true;
              case R.id.user_send_:



                  if(isConnectedWifi(Ask_question_User.this)||isConnectedMobile(Ask_question_User.this))
                  {
                      if(isConnected(Ask_question_User.this)) {
                           item.setCheckable(false);
                          send_server(item);

                      }
                      else
                      {
                          new AlertBox_Inner(Ask_question_User.this);

                      }
                  }else
                  {
                      new AlertBox_outer(Ask_question_User.this);

                      // new AlertBox("error",getActivity());
                  }

                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void send_server(MenuItem item) {

    if (!validate()) {
            onLoginFailed();
            return;
        }
        String sub=subject.getText().toString();
        String cont=Contentq.getText().toString();
        String catg=spinner.getSelectedItem().toString();
        String useri= DataUserUserId.getInstance().getDistributor_id();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");


        Post_Data(sub,cont,catg,useri,item);




    }

    private void Post_Data(String sub, String cont, String catg, String userid, final MenuItem item) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        progressDialog.show();
        String encodeImage = "no pic";
        Bitmap bitmap = null;
        if (photostring != null) {
            try {
                bitmap = ImageLoader.init().from(photostring).requestSize(200, 200).getBitmap();
                encodeImage = ImageBase64.encode(bitmap);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        RestBuilderPro.getService().askquestion("question",sub,cont,catg,userid,encodeImage).enqueue(new Callback<AskQuestionItem>() {
            @Override
            public void onResponse(Call<AskQuestionItem> call, Response<AskQuestionItem> response) {

               if(response.isSuccessful()) {
                   progressDialog.dismiss();
                   AskQuestionItem ask = response.body();
                   String result=ask.getSucc();
                 if(result.contains("succ"))
                 {
                     item.setCheckable(true);
                     Toast.makeText(Ask_question_User.this, "success", Toast.LENGTH_SHORT).show();
                     subject.setText("");
                     Contentq.setText("");
                     attach_file_text.setText("");
                 }
                   else{
                     item.setCheckable(true);
                     Toast.makeText(Ask_question_User.this, "error_uploading", Toast.LENGTH_SHORT).show();
                 }
               }
            }

            @Override
            public void onFailure(Call<AskQuestionItem> call, Throwable t) {
                item.setCheckable(true);
            }
        });
    }





    private boolean validate() {

        boolean valid = true;

        String sub=subject.getText().toString();
        String cont=Contentq.getText().toString();
        String catg=spinner.getSelectedItem().toString();
        if(sub.isEmpty()||sub.length()<4)
        {
            valid=false;
            subject.setError("valid subject");
        }
        else
        if(cont.isEmpty()||sub.length()<4)
        {
            valid=false;
         Contentq.setError("valid content");
        }
        else
        if(catg.isEmpty())
        {
            valid=false;

        }

        return valid;
    }
    private void onLoginFailed() {

    }



    private void takeapic() {
        String choice[]={"Select From Gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, choice);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    gallareydf();
                }
            }

        });
        final AlertDialog dialog = builder.create();
        dialog.show();


    }
    private void gallareydf() {
        try {
            startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_DATAD);
            //gallery.addToGallery();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_DATAD)
        {


            Uri uri=data.getData();
            galleryPhoto.setPhotoUri(uri);
            Bitmap bitmapc=null;
            String photoPath=galleryPhoto.getPath();
          photostring=photoPath;


            File u=new File(photoPath);
            String imagename=u.getName();
            float length = u.length() / 1024;
            attach_file_text.setText(imagename+" "+length+"kb");
            Log.e("size",""+length);


        }



    }
    @Override
    public void onBackPressed() {
        this.finish();
        // Log.d("BACK_BUTTON_DOESNT_WORK", "I will never execute and you will never see me :(");
        super.onBackPressed();


    }



}
