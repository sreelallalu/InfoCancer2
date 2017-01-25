package info.WebService;





import info.ItemServiceHolder.AccntCreatItem;
import info.ItemServiceHolder.AskQuestionItem;
import info.ItemServiceHolder.CommentItem;
import info.ItemServiceHolder.LogInDoctor;
import info.ItemServiceHolder.LogInUser;
import info.ItemServiceHolder.QuestionItems;
import info.ItemServiceHolder.ReplyItem;
import info.ItemServiceHolder.SplashClassItems;
import info.ItemServiceHolder.VarificationItem;
import info.NavItem.QuestionItem;
import infocancer.nyesteventure.com.infocancer.Ask_question_User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface LoginApi{



//splash_screen
    @FormUrlEncoded
    @POST("check.php/")
    Call<SplashClassItems> authenticate
            (@Field("tag") String tag);

    @FormUrlEncoded
    @POST("check.php/")
    Call<SplashClassItems>  log
            (@Field("tag") String tag,
             @Field("usertype") String ppp,
             @Field("email") String email,
             @Field("password") String userpass
             );
    @FormUrlEncoded
    @POST("logincheck.php/")
    Call<LogInUser> loginuser
            (
                    @Field("tag") String tag,
                    @Field("email") String ppp,
                    @Field("password") String email


            );
    @FormUrlEncoded
    @POST("logincheck.php/")
    Call<LogInDoctor> logindoctor
            (
                    @Field("tag") String tag,
                    @Field("email") String ppp,
                    @Field("password") String email


            );
    @FormUrlEncoded
    @POST("question.php")
    Call<QuestionItems> question
            (
                    @Field("tag") String tag,
                    @Field("categoryn") String pp

            );
    @FormUrlEncoded
    @POST("question.php")
    Call<QuestionItems> do_qu_co
            (
                    @Field("tag") String tag,
                    @Field("categoryn") String ppp,
                    @Field("userquestion") String pppf
            );
    @FormUrlEncoded
    @POST("question.php")
    Call<QuestionItems> myquestion
            (
                    @Field("tag") String tag,
                    @Field("categoryn") String ppp,
                    @Field("userquestion") String poq
            );
    //
    @FormUrlEncoded
    @POST("comment.php")
    Call<CommentItem> comment
            (
                    @Field("tag") String tag,
                    @Field("post_Id") String ppp
            );
    @FormUrlEncoded
    @POST("replycomment.php")
    Call<ReplyItem> commentreply
            (
                    @Field("tag") String tag,
                    @Field("allcomment_id") String ppp
            );


    @FormUrlEncoded
    @POST("replycomment.php")
    Call<ReplyItem> commentreplysend
            (@Field("tag") String tag,
                    @Field("message") String p1,
                    @Field("comment_id") String p2,
                   @Field("post_Id") String p3,
                @Field("user_id") String p4,
                  @Field("type") String p5


            );
    @FormUrlEncoded
    @POST("question.php")
    Call<AskQuestionItem> askquestion
            (@Field("tag") String tag,
             @Field("title") String p1,
             @Field("content") String p2,
             @Field("categoryc") String p3,
             @Field("userid") String p4,
             @Field("userpic") String p5


            );

    @FormUrlEncoded
    @POST("comment.php")
    Call<CommentItem> doctorsendcommt
            (@Field("tag") String tag,
             @Field("comment") String p1,
             @Field("post_id1") String p2,
             @Field("doctor_id") String p3



            );

    @FormUrlEncoded
    @POST("register.php")
    Call<AccntCreatItem> useraccntCreat
            (@Field("tag") String tag,
             @Field("username") String p1,
             @Field("useremail") String p2,

             @Field("userage") String p3,
             @Field("usermobile") String p4,
             @Field("userpass") String p5,

             @Field("usergender") String p6,
             @Field("userimage") String p7

            );

    @FormUrlEncoded
    @POST("register.php")
    Call<AccntCreatItem> useraccntCreatupdate
            (@Field("tag") String tag,
             @Field("username") String p1,
             @Field("userage") String p3,
             @Field("usermobile") String p4,
             @Field("userpass") String p5,

             @Field("usergender") String p6,
             @Field("userimage") String p7,
             @Field("user_id") String p8

            );
    @FormUrlEncoded
    @POST("register.php")
    Call<AccntCreatItem> doctoraccntCreat
            (@Field("tag") String tag,
             @Field("doctorname") String p1,
             @Field("doctoremail") String p2,

             @Field("doctorage") String p3,
             @Field("doctormobile") String p4,
             @Field("doctorpass") String p5,

             @Field("doctorgender") String p6,
             @Field("doctorimage") String p7

            );
    @FormUrlEncoded
    @POST("register.php")
    Call<AccntCreatItem> doctoraccntCreatupdate
            (@Field("tag") String tag,
             @Field("doctorname") String p1,


             @Field("doctorage") String p3,
             @Field("doctormobile") String p4,
             @Field("doctorpass") String p5,

             @Field("doctorgender") String p6,
             @Field("doctorimage") String p7,
             @Field("doctor_id") String p8

            );

    @FormUrlEncoded
    @POST("register.php")
    Call<VarificationItem> doctorkey
            (@Field("tag") String tag,
             @Field("email") String p1,
             @Field("key") String p2
            );
    @FormUrlEncoded
    @POST("register.php")
    Call<VarificationItem> userkey
            (@Field("tag") String tag,
             @Field("email") String p1,
             @Field("key") String p2
            );



    public interface RetrofitImageAPI {
        @GET("{imageName}")
        Call<ResponseBody> getImageDetails(@Path("imageName") String imageName);
    }




}
