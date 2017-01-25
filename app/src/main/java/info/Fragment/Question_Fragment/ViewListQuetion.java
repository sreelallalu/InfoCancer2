package info.Fragment.Question_Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import info.Constant.CONSTANTS;
import info.Fragment.Prevension.BackFragment;
import info.NavAdapter.CommentAdapter;
import info.NavItem.AlertBox_;
import info.NavItem.CommentItem;
import info.WebService.RestBuilderPro;
import infocancer.nyesteventure.com.infocancer.Questions;
import infocancer.nyesteventure.com.infocancer.R;
import infocancer.nyesteventure.com.infocancer.Reply;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static infocancer.nyesteventure.com.infocancer.UserChoicePage.MY_PREFS_NAME;


/**
 * Created by SLR on 10/18/2016.
 */
public class ViewListQuetion extends Fragment implements BackFragment,SwipeRefreshLayout.OnRefreshListener{


    private static final String TAG ="tag" ;

    private TextView q_title,q_content,q_time,ur_name,q_catg;
    private EditText comment_box;
    private ImageView comment_button,user_image;
private SwipeRefreshLayout swipeview;
    private ListView listView;
    private View comment;
   private String q_id;
    private String PPop,PPP;
    private ArrayList<info.ItemServiceHolder.CommentItem.Comt> arrayList=new ArrayList<>();
    TextView u_name,u_time,u_comment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.viewlistquestion,container,false);
        ((Questions) getActivity()).setTitle("Comments");
        q_title=(TextView)rootview.findViewById(R.id.id_question_view_sub2u2);
        q_content=(TextView)rootview.findViewById(R.id.id_question_view_content2u2);
        q_time=(TextView)rootview.findViewById(R.id.id_question_view_time2u2);
        q_catg=(TextView)rootview.findViewById(R.id.q_categoryu2);
        user_image=(ImageView)rootview.findViewById(R.id.u_propic_viewu2);

        ur_name=(TextView)rootview.findViewById(R.id.u_user_nameu2);

      //  comment=(View)rootview.findViewById(R.id.id_comment_linear_buttonu2);

        swipeview=(SwipeRefreshLayout)rootview. findViewById(R.id.comment_refresh_idu2);
        swipeview.setOnRefreshListener(this);


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

             String _name = sharedPref.getString(CONSTANTS.intent_send.Intent_U_NAME,"name");
        String _time = sharedPref.getString(CONSTANTS.intent_send.Intent_TIME,"time");

        String _title = sharedPref.getString(CONSTANTS.intent_send.Intent_TITLE,"title");
        final String _qid = sharedPref.getString(CONSTANTS.intent_send.Intent_Q_ID,"qid");

        String _conten = sharedPref.getString(CONSTANTS.intent_send.Intent_CONTENT,"content");
        String _categ = sharedPref.getString(CONSTANTS.intent_send.Intent_CAT,"category");

        String _uimg= sharedPref.getString(CONSTANTS.intent_send.Intent_U_IMAGE,"uimag");
        String _qimg = sharedPref.getString(CONSTANTS.intent_send.Intent_Q_IMAGE,"qimag");

        listView=(ListView)rootview.findViewById(R.id.xlistview_idu2);
        /*comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_listview(_qid);
            }
        });*/
        viewquestions(_name,_time,_title,_qid,_conten,_categ,_uimg,_qimg);
       // viewquestion(time,content,title,catg,q_image,u_image,u_name);
        return rootview;
    }

    private void viewquestions(String name, String time, String title, String qid, String conten, String categ, String uimg, String qimg) {


        q_title.setText(title);
        q_content.setText(conten);

        q_time.setText(time);
        q_catg.setText(categ);

        ur_name.setText(name);
        q_id=qid;

        user_image.setImageResource(R.drawable.save_user1);
        PostAsyn(qid);


    }


   /* protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent loop=getIntent();
            String time=loop.getStringExtra(CONSTANTS.intent_send.Intent_TIME);
        String content=loop.getStringExtra(CONSTANTS.intent_send.Intent_CONTENT);
        String title=loop.getStringExtra(CONSTANTS.intent_send.Intent_TITLE);
        String catg=loop.getStringExtra(CONSTANTS.intent_send.Intent_CAT);

         q_id=loop.getStringExtra(CONSTANTS.intent_send.Intent_Q_ID);
        String q_image=loop.getStringExtra(CONSTANTS.intent_send.Intent_Q_IMAGE);

        String u_name=loop.getStringExtra(CONSTANTS.intent_send.Intent_U_NAME);
        String u_image=loop.getStringExtra(CONSTANTS.intent_send.Intent_U_IMAGE);
        setContentView(R.layout.viewlistquestion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarkk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            find_userid();
            viewquestion(time,content,title,catg,q_image,u_image,u_name);
    }*/

    private void comment_butt() {



    }



    private void viewquestion(String time, String content, String title, String catg, String q_image, String u_image, String u_name) {





    }

/*
       private void picass0_setuserpic(String h) {

        try {
            Picasso picasso=new Picasso.Builder(ViewListQuetion.this).listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                }
            }).build();



            picasso.load(h).placeholder(getResources().getDrawable(R.drawable.babx)).into(user_image, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG,"sucseess");
                }

                @Override
                public void onError() {
                    Log.d(TAG,"error");
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
*/


    private void PostAsyn(String q_id) {
arrayList.clear();
        swipeview.setRefreshing(true);
        RestBuilderPro.getService().comment("comment",q_id).enqueue(new Callback<info.ItemServiceHolder.CommentItem>() {
            @Override
            public void onResponse(Call<info.ItemServiceHolder.CommentItem> call, Response<info.ItemServiceHolder.CommentItem> response) {
                if(response.isSuccessful())
                { swipeview.setRefreshing(false);
                    info.ItemServiceHolder.CommentItem comment=response.body();

                    arrayList= (ArrayList<info.ItemServiceHolder.CommentItem.Comt>) comment.getComt();
                    CommentAdapter adaptero=new CommentAdapter(getActivity(), R.layout.view_question_list,arrayList);
                    listView.setAdapter(adaptero);

                    listView.invalidateViews();
                    if(arrayList.size()==0)
                    {
                 new AlertBox_("empty comments",getActivity());
                    }
            listView.setOnItemClickListener(new ItemCommentClick());

                }
            }

            @Override
            public void onFailure(Call<info.ItemServiceHolder.CommentItem> call, Throwable t) {

            }
        });

    }

   /* private void PostAsyn(String q_id) {
        arrayList.clear();
        listView.refreshDrawableState();

        listView.invalidateViews();

        swipeview.setRefreshing(true);
        HashMap<String,String> hashmap=new HashMap<>();
        hashmap.put("tag",CONSTANTS.comment_list.TAG_COMMENT);
        hashmap.put(CONSTANTS.comment_list.Comment_TAg_id,q_id);



        PostResponseAsyncTask task=new PostResponseAsyncTask(ViewListQuetion.this, hashmap, new AsyncResponse()
        {
            @Override
            public void processFinish(String s) {
                try {
                    JSONObject qrjson =new JSONObject(s);


                    JSONArray jasonJsonArray=qrjson.getJSONArray(CONSTANTS.comment_list.Comment_responce);
                    if(jasonJsonArray==null)
                    {
                        swipeview.setRefreshing(false);
                    }


                    for (int i =0; i<jasonJsonArray.length(); i++)
                    {
                        JSONObject c = jasonJsonArray.getJSONObject(i);
                        String comment = c.getString(CONSTANTS.comment_list.ID_C);
                        String name = c.getString(CONSTANTS.comment_list.ID_N);

                        String time = c.getString(CONSTANTS.comment_list.ID_T);
                        String u_image=c.getString(CONSTANTS.comment_list.ID_IMG);   String commentid=c.getString(CONSTANTS.comment_list.ID_Cid);



                       arrayList.add(new CommentItem(name,comment,u_image,time,commentid));
                        //Log.e("time",subject);
                    }



                    //CommentAdapter adaptero=new CommentAdapter(getApplicationContext(), R.layout.view_question_list,arrayList);


                    listView.setAdapter(adaptero);
                    //adaptero.notifyAll();
                    adaptero.notifyDataSetChanged();
                    listView.invalidateViews();
                    swipeview.setRefreshing(false);
                    listView.setOnItemClickListener(new ItemCommentClick());
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeview.setRefreshing(false);
                }
            }
        });
        task.execute(CONSTANTS.comment_list.WEBURL);
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                swipeview.setRefreshing(false);
            }
            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                swipeview.setRefreshing(false);
            }
            @Override
            public void handleProtocolException(ProtocolException e) {
               swipeview.setRefreshing(false);
            }
            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
               swipeview.setRefreshing(false);
            }
        });


    }*/


    @Override
    public void onRefresh() {

      PostAsyn(q_id);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getBackPriority() {
        return 0;
    }

    private class ItemCommentClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           String name=arrayList.get(position).getFname();
            String comment=arrayList.get(position).getComment();
            String time=arrayList.get(position).getDatetime();
            String url_o=arrayList.get(position).getQImage();
            String commentid=arrayList.get(position).getCmtId();
            Intent reply=new Intent(getActivity(),Reply.class);

            reply.putExtra("namereply",name);
            reply.putExtra("commentreply",comment);
            reply.putExtra("timereply",time);
            reply.putExtra("urlreply",url_o);
            reply.putExtra("post_id",q_id);
            reply.putExtra("commentid",commentid);
            startActivity(reply);


          /*  AlertDialog.Builder showAlert=new AlertDialog.Builder(ViewListQuetion.this);
            LayoutInflater inflater=getLayoutInflater();
            View alertview=inflater.inflate(R.layout.alertbuildbox,null);
          showAlert.setView(alertview);
             TextView t1=(TextView)alertview.findViewById(R.id.namealert_id);
            TextView t2=(TextView)alertview.findViewById(R.id.comment_alert_id);
            TextView t3=(TextView)alertview.findViewById(R.id.timealert_id);

            t1.setText(name);
            t2.setText(comment);
            t3.setText(time);
            showAlert.setNegativeButton("ok" ,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



                }
            });
            AlertDialog alertDialog =showAlert.create();
            alertDialog.show();*/

        }
    }



}
