package info.Fragment.Question_Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import info.Constant.CONSTANTS;
import info.Database.DATABASE_C;
import info.Database.MainDataBAse;
import info.Fragment.Prevension.BackFragment;
import info.NavAdapter.CommentAdapter;
import info.NavItem.AlertBox_;
import info.WebService.RestBuilderPro;
import infocancer.nyesteventure.com.infocancer.Question_Doctor;
import infocancer.nyesteventure.com.infocancer.Questions;
import infocancer.nyesteventure.com.infocancer.R;
import infocancer.nyesteventure.com.infocancer.Reply;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lalu on 1/4/2017.
 */

public class Doctor_ViewlistQuestion extends Fragment implements BackFragment, SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "tag";
    private TextView q_title,q_content,q_time,ur_name,q_catg;
    private EditText comment_box;
    private ImageView user_image;
    private FloatingActionButton comment_button;
    private SwipeRefreshLayout swipeview;
    private ListView listView;
    private View comment;
    private String _userid,_qid;

    private String PPop,PPP;
    private ArrayList<info.ItemServiceHolder.CommentItem.Comt> arrayList=new ArrayList<>();
    TextView u_name,u_time,u_comment;
    InputMethodManager imm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View rootview=inflater.inflate(R.layout.viewquestion2,container,false);
        ((Question_Doctor) getActivity()).setTitle("Comments");
        q_title=(TextView)rootview.findViewById(R.id.id_question_view_sub);
        q_content=(TextView)rootview.findViewById(R.id.id_question_view_content);
        q_time=(TextView)rootview.findViewById(R.id.id_question_view_time);
        q_catg=(TextView)rootview.findViewById(R.id.q_category);
        user_image=(ImageView)rootview.findViewById(R.id.u_propic_view);
        ur_name=(TextView)rootview.findViewById(R.id.u_user_name);



        swipeview=(SwipeRefreshLayout) rootview.findViewById(R.id.comment_refresh_opwe);
        swipeview.setOnRefreshListener(this);
        listView=(ListView)rootview.findViewById(R.id.xlistview_id);

        comment_box=(EditText)rootview.findViewById(R.id.id_question_comment_edittext);
        comment_button=(FloatingActionButton) rootview.findViewById(R.id.comment_button_id);
           comment_button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   v.setClickable(false);
                   sendComment(v);
               }
           });
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        String _name = sharedPref.getString(CONSTANTS.intent_send.Intent_U_NAME,"name");
        String _time = sharedPref.getString(CONSTANTS.intent_send.Intent_TIME,"time");

        String _title = sharedPref.getString(CONSTANTS.intent_send.Intent_TITLE,"title");
     _qid = sharedPref.getString(CONSTANTS.intent_send.Intent_Q_ID,"qid");

        String _conten = sharedPref.getString(CONSTANTS.intent_send.Intent_CONTENT,"content");
        String _categ = sharedPref.getString(CONSTANTS.intent_send.Intent_CAT,"category");

        String _uimg= sharedPref.getString(CONSTANTS.intent_send.Intent_U_IMAGE,"uimag");
        String _qimg = sharedPref.getString(CONSTANTS.intent_send.Intent_Q_IMAGE,"qimag");
        MainDataBAse data=new MainDataBAse(getActivity());
        data.open();

        _userid=data.getNameUser(DATABASE_C.TABLE_NAME.T_USER,DATABASE_C.COLUMN_NAME_USER.U_userid,1);
        data.close();
        viewquestions(_name,_time,_title,_qid,_conten,_categ,_uimg,_qimg);
         imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        return rootview;
    }

    private void sendComment(final View v) {
        if(!validate())
        {
            v.setClickable(true);
            return;
        }
      String msg=comment_box.getText().toString();
       /*   InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        ;*/
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        arrayList.clear();
        swipeview.setRefreshing(true);
        RestBuilderPro.getService().doctorsendcommt("cmtinsert",msg,_qid,_userid).enqueue(new Callback<info.ItemServiceHolder.CommentItem>() {
            @Override
            public void onResponse(Call<info.ItemServiceHolder.CommentItem> call, Response<info.ItemServiceHolder.CommentItem> response) {
                if(response.isSuccessful())
                {
                    swipeview.setRefreshing(false);
                    info.ItemServiceHolder.CommentItem comment=response.body();

                    arrayList= (ArrayList<info.ItemServiceHolder.CommentItem.Comt>) comment.getComt();
                    CommentAdapter adaptero=new CommentAdapter(getActivity(), R.layout.view_question_list,arrayList);
                    listView.setAdapter(adaptero);
                    v.setClickable(true);
                    comment_box.setText("");

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
                swipeview.setRefreshing(false);
            }
        });


    }

    private boolean validate() {
        boolean valid=true;
        String msg=comment_box.getText().toString();
        if(msg.isEmpty())
        {
            comment_box.setError("null value");
            valid=false;
        }
        return valid;
    }

    private void viewquestions(String name, String time, String title, String qid, String conten, String categ, String uimg, String qimg) {


        q_title.setText(title);
        q_content.setText(conten);

        q_time.setText(time);
        q_catg.setText(categ);

        ur_name.setText(name);
        user_image.setImageResource(R.drawable.save_user1);
      PostAsyn(qid);

    }
    private void PostAsyn(String q_id) {
        arrayList.clear();
        swipeview.setRefreshing(true);
        RestBuilderPro.getService().comment("comment",q_id).enqueue(new Callback<info.ItemServiceHolder.CommentItem>() {
            @Override
            public void onResponse(Call<info.ItemServiceHolder.CommentItem> call, Response<info.ItemServiceHolder.CommentItem> response) {
                if(response.isSuccessful())
                {
                    swipeview.setRefreshing(false);
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
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PostAsyn(_qid);
            }
        },1000);

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
            reply.putExtra("post_id",_qid);
            reply.putExtra("commentid",commentid);
            startActivity(reply);
        }
    }
}
