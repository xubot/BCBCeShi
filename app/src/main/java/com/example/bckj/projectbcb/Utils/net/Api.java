package com.example.bckj.projectbcb.Utils.net;

import com.example.bckj.projectbcb.Bean.BackBean;
import com.example.bckj.projectbcb.Bean.CodeBean;
import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.LoginBean;
import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.Modifypwd;
import com.example.bckj.projectbcb.Bean.PersonDataBean;
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Bean.StatusBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by liuhao
 * on 2017/4/11
 * use to :
 */

public interface Api {
    //登录
    @POST("sc/index.php?s=/home/user/login")
    @FormUrlEncoded
    Observable<LogBean> log(@Field("username") String username,
                            @Field("password") String password);

    //退出
    @GET("sc/index.php?s=/home/user/logout/token")
    Observable<LogoutBean> logout(@Query("token")String token);

    //注册（有手机号）
    @POST("sc/index.php?s=/home/user/register")
    @FormUrlEncoded
    Observable<LoginBean> loginOne(@Field("email") String email,
                                @Field("password") String password,
                                @Field("repassword") String repassword,
                                @Field("username") String usersname,
                                @Field("mobile") String mobile);
    //注册（无手机号）
    @POST("sc/index.php?s=/home/user/register")
    @FormUrlEncoded
    Observable<LoginBean> loginTwe(@Field("email") String email,
                                   @Field("password") String password,
                                   @Field("repassword") String repassword,
                                   @Field("username") String usersname);

    //激活自身App
    @GET("sc/index.php?s=/home/user/getStatus/token")
    Observable<StatusBean> status(@Query("token")String token);

    //得到重新激活
    @GET("sc/index.php?s=/home/user/reActiveUser/token")
    Observable<ReActiveUserBean> reActiveUser(@Query("token")String token);


    //得到个人信息(main)
    @GET("sc/index.php?s=/home/user/getInfo/token")
    Observable<DataNameBean> mainDetails(@Query("token")String token);
    //得到个人信息(personData)
    @GET("sc/index.php?s=/home/user/getInfo/token")
    Observable<PersonDataBean> personDetails(@Query("token")String token);

    //得到修改密码
    @POST("sc/index.php?s=/home/user/updatePwd")
    @FormUrlEncoded
    Observable<Modifypwd> modifypwd(@Field("old")String old,
                                    @Field("password")String password,
                                    @Field("repassword")String repassword,
                                    @Field("token")String token);
    //得到发送邮件
    @POST("sc/index.php?s=/home/user/sendMail")
    @FormUrlEncoded
    Observable<CodeBean> getcode(@Field("email")String email,
                                 @Field("type")int type);
    //得到找回
    @POST("sc/index.php?s=/home/user/retrieve")
    @FormUrlEncoded
    Observable<BackBean> getBack(@Field("email")String email,
                                 @Field("code")String code,
                                 @Field("password")String password,
                                 @Field("repassword")String repassword);

    /*//激活服务
    @POST("/home/active/index")
    @FormUrlEncoded
    Observable<Object> sensitize(@Field("type") String type,
                                 @Field("atype") String atype,
                                 @Field("password") String password,
                                 @Field("account") String account);

    //激活服务列表
    @POST("/home/active/index")
    @FormUrlEncoded
    Observable<Object> sensitizelist();*/
}
