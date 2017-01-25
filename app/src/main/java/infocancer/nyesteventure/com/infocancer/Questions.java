package infocancer.nyesteventure.com.infocancer;

import android.content.Intent;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import info.Fragment.Prevension.BackFragmentHelper;
import info.Fragment.Question_Fragment.My_Questions;
import info.Fragment.Question_Fragment.QuestionsList;
import info.NavAdapter.Question_Adapter;
import info.NavItem.QuestionItem;

import static info.NetWorkCheck.NetworkChecker.isConnected;
import static info.NetWorkCheck.NetworkChecker.isConnectedMobile;
import static info.NetWorkCheck.NetworkChecker.isConnectedWifi;


public class Questions extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView name_u, email_u;
    ListView listView;
    ImageView user_pic, editpic;
    RecyclerView.LayoutManager layoutManager;
    boolean refreshToggle = true;


    SwipeRefreshLayout swipeView;


    private ArrayList<QuestionItem> itemList = new ArrayList<>();
    private ArrayList<QuestionItem> arrayList = new ArrayList<>();
    private TextView category_text;
    private FloatingActionButton fab;

    int totalsize, end_value;
    private Spinner spinner;
    private Toolbar toolbar;
    private String categoryString;
    private String category_temp;
    RecyclerView recyclerView;

    Question_Adapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        toolbar = (Toolbar) findViewById(R.id.toolbarkk);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            QuestionsList fragment = new QuestionsList();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_question, fragment);
            fragmentTransaction.commit();
        }
        spinnersetting();

    }




    private void spinnersetting() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_coolop);
        navigationView.setNavigationItemSelectedListener(this);

        /*List<String> list= DataHolderClass.getInstance().getDistributor_id();

        ArrayAdapter dri=new ArrayAdapter<String>(Questions.this,
                R.layout.spinner_item,list);

     dri.setDropDownViewResource(R.layout.spinner_compo);
        spinner.setAdapter(dri);
      categoryString=spinner.getSelectedItem().toString();
      category_text.setText(categoryString);
        spinner.setOnItemSelectedListener(new SelectedSpinner());*/


//change_propic();


    }

    /*@Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PostAsyn(category_temp);
            }
        },1000);

    }
    private ArrayList<QuestionItem> PostAsyn(String position) {
       // final ArrayList<QuestionItem> arrayList=new ArrayList<>();

        final ArrayList<QuestionItem> temp=new ArrayList<>();
        swipeView.setRefreshing(true);
        HashMap<String,String> hashmap=new HashMap<>();

        hashmap.put("tag", CONSTANTS.questions.Question_TAG_ID);
        hashmap.put(CONSTANTS.questions.CAT_TAG,position);

        PostResponseAsyncTask task=new PostResponseAsyncTask(Questions.this, hashmap, new AsyncResponse()
        {
            @Override
            public void processFinish(String s) {
                try {
                    JSONObject qrjson =new JSONObject(s);
                    JSONArray jasonJsonArray=qrjson.getJSONArray(CONSTANTS.questions.TAG_NAME);
                    if(jasonJsonArray==null){
                        swipeView.setRefreshing(false);
                    }
                    for (int i =0; i<jasonJsonArray.length(); i++)
                    {
                        JSONObject c = jasonJsonArray.getJSONObject(i);
                         String u_imag= c.getString(CONSTANTS.questions.ID_U_URL);
                          String u_name= c.getString(CONSTANTS.questions.ID_U_name);
                        String q_imag= c.getString(CONSTANTS.questions.ID_Q_urL);
                        String q_id= c.getString(CONSTANTS.questions.ID_Q_id);
                        String content = c.getString(CONSTANTS.questions.ID_C);
                        String title = c.getString(CONSTANTS.questions.ID_S);
                        String time = c.getString(CONSTANTS.questions.ID_T);
                        String catgr = c.getString(CONSTANTS.questions.ID_CAT);

                             //Log.e("catego",title);
          temp.add(new QuestionItem(title,content,time,catgr,q_id,q_imag,u_name,u_imag));
                       // Log.e("time",subject);
                    }

                    swipeView.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeView.setRefreshing(false);
                }finally {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            arrayList.clear();
                            Questions.this.arrayList=temp;
                            totalsize=temp.size();
                            loadData();
                        }
                    },100);
                }
            }
        });
        task.execute(CONSTANTS.questions.WEBURL);
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                swipeView.setRefreshing(false);
            }
            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                swipeView.setRefreshing(false);
            }
            @Override
            public void handleProtocolException(ProtocolException e) {
                swipeView.setRefreshing(false);
            }
            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                swipeView.setRefreshing(false);
            }
        });
       return temp;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my_comme) {


            if(isConnectedWifi(Questions.this)||isConnectedMobile(Questions.this))
            {
                if(isConnected(Questions.this)) {

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
                    drawer.closeDrawer(GravityCompat.START);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(Questions.this,My_Questions.class);
                            startActivity(intent);
                            finish();
                        }
                    },300);

                }
                else
                {
                    new AlertBox_Inner(Questions.this);

                }
            }else
            {
                new AlertBox_outer(Questions.this);

                // new AlertBox("error",getActivity());
            }







        } else if (id == R.id.ask_qio12) {



                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
                    drawer.closeDrawer(GravityCompat.START);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(Questions.this,Ask_question_User.class);
                            startActivity(intent);
                        }
                    },300);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoadMore() {

        //Log.d("MainActivity_","onLoadMore");
        int start = mAdapter.getItemCount();
        mAdapter.setProgressMore(true);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {

                itemList.clear();
                mAdapter.setProgressMore(false);

                int start = mAdapter.getItemCount();

              //  Log.e("loop","loppp");

                int end =endvalue(start);


                for (int i = start ; i <end; i++) {
                    itemList.add(arrayList.get(i));

                }

                mAdapter.addItemMore(itemList);
                mAdapter.setMoreLoading(false);
            }

        },300);



    }
    private int endvalue(int start) {

        int first_check=totalsize-start;
        if(first_check<20)
        {
            end_value=start+first_check;
        }
        else
        {
            end_value=start+20;
        }
       // Log.e("startvalue",""+start);
       // Log.e("endvalue",""+end_value);
        return end_value;

    }

    @Override
    public void OnClickITEM(final int a) {

        Log.e("int a",""+a);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
               Next_page(a);

            }
        });

    }


    private void Next_page(int position) {
        String title=arrayList.get(position).get_Title();
        String times= arrayList.get(position).get_Time();

        String content=arrayList.get(position).get_Content();
        String catg=arrayList.get(position).get_catg();

        String q_image= arrayList.get(position).get_q_image();
        String q_id= arrayList.get(position).get_q_id();

        String u_image= arrayList.get(position).get_u_image();
        String u_name= arrayList.get(position).get_u_name();

        Intent ui=new Intent(Questions.this,ViewListQuetion.class);

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
    private class SelectedSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String selectedItem = parent.getItemAtPosition(position).toString();

        category_text.setText(selectedItem);
    Questions.this.category_temp=selectedItem;

       new Handler().post(new Runnable() {
                @Override
                public void run() {
                  PostAsyn(selectedItem);
                }
            });


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }*/


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else if(!BackFragmentHelper.fireOnBackPressedEvent(this)) {
            // lets do the default back action if fragments don't consume it
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    QuestionsList fragment = new QuestionsList();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_question, fragment);
                    fragmentTransaction.commit();
                }
            },500);


        }
        else {

        Intent intent = new Intent(Questions.this, UserChoicePage.class);
        startActivity(intent);
        this.finish();}

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (isConnectedWifi(Questions.this) || isConnectedMobile(Questions.this)) {
            if (isConnected(Questions.this)) {

                if(id==R.id.my_questionx)
                {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
                    drawer.closeDrawer(GravityCompat.START);
                    QuestionsList fragment = new QuestionsList();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_question, fragment);
                    fragmentTransaction.commit();
                }
                else
                if (id == R.id.my_comme) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
                    drawer.closeDrawer(GravityCompat.START);
                    My_Questions fragment = new My_Questions();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_question, fragment);
                    fragmentTransaction.commit();
                } else if (id == R.id.ask_qio12) {

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
                    drawer.closeDrawer(GravityCompat.START);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Questions.this, Ask_question_User.class);
                            startActivity(intent);
                        }
                    }, 300);
                }

            } else {

            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.question_drawer_cool);
            drawer.closeDrawer(GravityCompat.START);

        } else {

        }

        return true;
    }


}
/*  private class OnScrollL implements AbsListView.OnScrollListener {
          @Override
          public void onScrollStateChanged(AbsListView view, int scrollState) {
              //fab.setVisibility(VISIBLE);
          }

          @Override
          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ?
                      0 : listView.getChildAt(0).getTop();
              swipeView.setEnabled((topRowVerticalPosition >= 0));


             // fab.setVisibility(INVISIBLE);
          }
      }*/

    /*private class ListViewClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    }*/