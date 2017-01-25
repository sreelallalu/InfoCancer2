package infocancer.nyesteventure.com.infocancer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.Constant.CONSTANTS;
import info.Fragment.Prevension.BackFragmentHelper;
import info.Fragment.Question_Fragment.Doctor_Q_C;
import info.Fragment.Question_Fragment.Doctor_Q_V;
import info.Fragment.Question_Fragment.QuestionsList;
import info.Holder.DataHolderClass;
import info.ItemServiceHolder.QuestionItems;
import info.NavAdapter.Question_Adapter;
import info.NavItem.QuestionItem;


/**
 * Created by SLR on 11/9/2016.
 */
public class Question_Doctor extends AppCompatActivity implements

        NavigationView.OnNavigationItemSelectedListener{





    private ArrayList<QuestionItems.User> itemList=new ArrayList<>();
    private ArrayList<QuestionItems.User> arrayList=new ArrayList<>();
    private TextView category_text;



    private String categoryString;
    private String category_temp;
    RecyclerView recyclerView;
    boolean refreshToggle = true;
    Question_Adapter mAdapter;
    int totalsize, end_value;
    private Toolbar toolbar;

    private Spinner spinner;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_question);
        toolbar = (Toolbar) findViewById(R.id.toolbarkkdf);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
           Doctor_Q_V fragment = new Doctor_Q_V();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_question_doc, fragment);
            fragmentTransaction.commit();
        }
        spinnersetting();



    }

    private void spinnersetting() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cooldo);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_coolopdo);
        navigationView.setNavigationItemSelectedListener(this);


        //change_propic();

    }








  /*  private void Next_page(int position) {
        String title=arrayList.get(position).get_Title();
        String times= arrayList.get(position).get_Time();

        String content=arrayList.get(position).get_Content();
        String catg=arrayList.get(position).get_catg();

        String q_image= arrayList.get(position).get_q_image();
        String q_id= arrayList.get(position).get_q_id();

        String u_image= arrayList.get(position).get_u_image();
        String u_name= arrayList.get(position).get_u_name();

        Intent ui=new Intent(Question_Doctor.this,ViewQuestion_Doctor.class);

        ui.putExtra(CONSTANTS.intent_send.Intent_TITLE,title);
        ui.putExtra(CONSTANTS.intent_send.Intent_TIME,times);

        ui.putExtra(CONSTANTS.intent_send.Intent_CONTENT,content);
        ui.putExtra(CONSTANTS.intent_send.Intent_CAT,catg);

        ui.putExtra(CONSTANTS.intent_send.Intent_U_NAME,u_name);
        ui.putExtra(CONSTANTS.intent_send.Intent_U_IMAGE,u_image);

        ui.putExtra(CONSTANTS.intent_send.Intent_Q_ID,q_id);
        ui.putExtra(CONSTANTS.intent_send.Intent_Q_IMAGE,q_image);

        startActivity(ui);
    }*/




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cooldo);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!BackFragmentHelper.fireOnBackPressedEvent(this)) {
            // lets do the default back action if fragments don't consume it
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Doctor_Q_V fragment = new Doctor_Q_V();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_question_doc, fragment);
                    fragmentTransaction.commit();
                }
            }, 700);


        } else {

            Intent intent = new Intent(Question_Doctor.this, UserChoicePage.class);
            startActivity(intent);
            this.finish();
        }


    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int op=item.getItemId();
        if (op==R.id.my_questionx_do)
        {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cooldo);
            drawer.closeDrawer(GravityCompat.START);
            Doctor_Q_V fragment = new Doctor_Q_V();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_question_doc, fragment);
            fragmentTransaction.commit();



        }else
       if(op== R.id.my_doctorcomm)
          {
              DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cooldo);
              drawer.closeDrawer(GravityCompat.START);
              Doctor_Q_C fragment = new Doctor_Q_C();
              android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
              fragmentTransaction.replace(R.id.frame_question_doc, fragment);
              fragmentTransaction.commit();

          }



        return false;
    }








   /* private class ListViewClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String title=arrayList.get(position).get_Title();
            String times= arrayList.get(position).get_Time();

            String content=arrayList.get(position).get_Content();
            String catg=arrayList.get(position).get_catg();

            String q_image= arrayList.get(position).get_q_image();
            String q_id= arrayList.get(position).get_q_id();

            String u_image= arrayList.get(position).get_u_image();
            String u_name= arrayList.get(position).get_u_name();

            Intent ui=new Intent(Question_Doctor.this,ViewQuestion_Doctor.class);

            ui.putExtra(CONSTANTS.intent_send.Intent_TITLE,title);
            ui.putExtra(CONSTANTS.intent_send.Intent_TIME,times);

            ui.putExtra(CONSTANTS.intent_send.Intent_CONTENT,content);
            ui.putExtra(CONSTANTS.intent_send.Intent_CAT,catg);

            ui.putExtra(CONSTANTS.intent_send.Intent_U_NAME,u_name);
            ui.putExtra(CONSTANTS.intent_send.Intent_U_IMAGE,u_image);

            ui.putExtra(CONSTANTS.intent_send.Intent_Q_ID,q_id);
            ui.putExtra(CONSTANTS.intent_send.Intent_Q_IMAGE,q_image);

            startActivity(ui);
        }
    }*/

}
