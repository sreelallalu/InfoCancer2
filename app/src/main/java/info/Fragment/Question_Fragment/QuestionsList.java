package info.Fragment.Question_Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.Constant.CONSTANTS;
import info.Fragment.Prevension.BackFragment;
import info.Holder.DataHolderClass;
import info.ItemServiceHolder.QuestionItems;
import info.NavAdapter.Question_Adapter;
import info.NavItem.AlertBox_;
import info.WebService.RestBuilderPro;
import infocancer.nyesteventure.com.infocancer.Ask_question_User;
import infocancer.nyesteventure.com.infocancer.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lalu on 1/2/2017.
 */
public class QuestionsList extends Fragment implements BackFragment, SwipeRefreshLayout.OnRefreshListener,

        Question_Adapter.OnLoadMoreListener  {
    private static final String MY_PREFS_NAME ="qlist" ;
    private ArrayList<QuestionItems.User> itemList=new ArrayList<>();
    private ArrayList<QuestionItems.User> arrayList=new ArrayList<>();
    private TextView category_text;
    private FloatingActionButton fab;
    SwipeRefreshLayout swipeView;
    int totalsize, end_value;
    private Spinner spinner;
    private Toolbar toolbar;
    private String categoryString;
    private String category_temp;
    RecyclerView recyclerView;

    Question_Adapter mAdapter;
    private int MODE_PRIVAT=1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frame_layout1_question, container, false);

        spinner= (Spinner)rootView. findViewById(R.id.questionmode_spinner);
        category_text=(TextView)rootView.findViewById(R.id.infoText);
        swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipelayout);
        recyclerView = (RecyclerView)rootView. findViewById(R.id.rvListuser);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Question_Adapter(this);
        mAdapter.setLinearLayoutManager(mLayoutManager);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);
        recyclerView.setAdapter(mAdapter);
        fab = (FloatingActionButton)rootView. findViewById(R.id.question_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Ask_question_User.class);
                startActivity(intent);
            }
        });
        spinnersetting();



        return rootView;



    }

    private void spinnersetting() {
        List<String> list= DataHolderClass.getInstance().getDistributor_id();

        ArrayAdapter dri=new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,list);

        dri.setDropDownViewResource(R.layout.spinner_compo);
        spinner.setAdapter(dri);
        categoryString=spinner.getSelectedItem().toString();
        category_text.setText(categoryString);
        spinner.setOnItemSelectedListener(new SelectedSpinner());
    }




    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

             PostAsyn(category_temp);
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
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
        //Log.e("int a",""+a);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

               Next_page(a);

            }
        });
    }
    private void Next_page(int a) {

        Context context = getActivity();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(CONSTANTS.intent_send.Intent_U_NAME, arrayList.get(a).getUName());
        editor.putString(CONSTANTS.intent_send.Intent_TIME, arrayList.get(a).getDatetime());

        editor.putString(CONSTANTS.intent_send.Intent_TITLE, arrayList.get(a).getTitle());
        editor.putString(CONSTANTS.intent_send.Intent_Q_ID, arrayList.get(a).getPostid());

        editor.putString(CONSTANTS.intent_send.Intent_CONTENT,arrayList.get(a).getContent());
        editor.putString(CONSTANTS.intent_send.Intent_CAT, arrayList.get(a).getCategory());

        editor.putString(CONSTANTS.intent_send.Intent_U_IMAGE, arrayList.get(a).getUImage());
        editor.putString(CONSTANTS.intent_send.Intent_Q_IMAGE, arrayList.get(a).getQImage());



        editor.commit();
       ViewListQuetion fragment = new ViewListQuetion();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_question, fragment);


        fragmentTransaction.commit();

    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public int getBackPriority() {
        return 0;
    }
    /*private void Next_page(int position) {
        String title=arrayList.get(position).get_Title();
        String times= arrayList.get(position).get_Time();

        String content=arrayList.get(position).get_Content();
        String catg=arrayList.get(position).get_catg();

        String q_image= arrayList.get(position).get_q_image();
        String q_id= arrayList.get(position).get_q_id();

        String u_image= arrayList.get(position).get_u_image();
        String u_name= arrayList.get(position).get_u_name();

        Intent ui=new Intent(getActivity(),ViewListQuetion.class);

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

    private class SelectedSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String selectedItem = parent.getItemAtPosition(position).toString();

            category_text.setText(selectedItem);
           category_temp=selectedItem;

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
    }
    private void loadData() {

        // totalsize=arrayList.size();
        mAdapter.setRecyclerView(recyclerView,totalsize);
        itemList.clear();
        if (totalsize >= 20) {
            for (int i = 0; i < 20; i++) {
                itemList.add(arrayList.get(i));
            }
        } else {
            for (int i = 0; i < totalsize; i++) {
                itemList.add(arrayList.get(i));
            }
        }
        mAdapter.addAll(itemList);
    }
    private ArrayList<QuestionItems.User> PostAsyn(String position) {
        swipeView.setRefreshing(true);
        arrayList.clear();
        RestBuilderPro.getService().question("SelCat", position).enqueue(new Callback<QuestionItems>() {
            @Override
            public void onResponse(Call<QuestionItems> call, Response<QuestionItems> response) {
                if (response.isSuccessful()) {
                    swipeView.setRefreshing(false);
                    QuestionItems questionItems = response.body();
                    arrayList = (ArrayList<QuestionItems.User>) questionItems.getUser();
                //String cool=  arrayList.get(0).getCategory();
                    totalsize=arrayList.size();
                    if (totalsize==0){
                        new AlertBox_("empty post",getActivity());}
                    loadData();
                }else{
                    swipeView.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<QuestionItems> call, Throwable t) {
                swipeView.setRefreshing(false);
            }
        });


     /*   final ArrayList<QuestionItem> temp=new ArrayList<>();
        swipeView.setRefreshing(true);
        HashMap<String,String> hashmap=new HashMap<>();

        hashmap.put("tag", CONSTANTS.questions.Question_TAG_ID);
        hashmap.put(CONSTANTS.questions.CAT_TAG,position);

        PostResponseAsyncTask task=new PostResponseAsyncTask(getActivity(), hashmap, new AsyncResponse()
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
                           arrayList=temp;
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
    }*/
        return arrayList;
    }
}
