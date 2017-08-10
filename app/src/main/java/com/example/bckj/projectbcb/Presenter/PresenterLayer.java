package com.example.bckj.projectbcb.Presenter;

import android.util.Log;

import com.example.bckj.projectbcb.Bean.BackBean;
import com.example.bckj.projectbcb.Bean.CodeBean;
import com.example.bckj.projectbcb.Bean.DataNameBean;
import com.example.bckj.projectbcb.Bean.LogBean;
import com.example.bckj.projectbcb.Bean.LoginBean;
import com.example.bckj.projectbcb.Bean.LogoutBean;
import com.example.bckj.projectbcb.Bean.Modifypwd;
import com.example.bckj.projectbcb.Bean.PersonDataBean;
import com.example.bckj.projectbcb.Bean.StatusBean;
import com.example.bckj.projectbcb.Moude.MoudelLayer;
import com.example.bckj.projectbcb.Utils.net.Api;
import com.example.bckj.projectbcb.ViewLayer.ForgetView;
import com.example.bckj.projectbcb.ViewLayer.LogView;
import com.example.bckj.projectbcb.ViewLayer.LoginView;
import com.example.bckj.projectbcb.ViewLayer.MainView;
import com.example.bckj.projectbcb.ViewLayer.ModifyView;
import com.example.bckj.projectbcb.ViewLayer.PersonDataView;
import com.example.bckj.projectbcb.ViewLayer.SensitizeView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/7.
 */

public class PresenterLayer {
    //得到Moudel对象
    private final MoudelLayer moudelLayer;
    private LogView logView;
    private LoginView loginView;
    private SensitizeView sensitizeView;
    private MainView mainView;
    private PersonDataView personDataView;
    private ModifyView modifyView;
    private ForgetView forgetView;

    //添加Presenter构造器
    public PresenterLayer() {
        moudelLayer = new MoudelLayer();
    }

    //得到logView对象
    public void setLogView(LogView logView) {
        this.logView = logView;
    }
    //发起登录请求
    public void setLog(String usre,String pwd) {
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<LogBean> List_course = api.log(usre,pwd);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogBean logBean) {
                        Log.d("zzz", logBean + "vddddddddd");
                        logView.log(logBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到loginView对象
    public void setLoginView(LoginView loginView){
        this.loginView = loginView;
    }
    //发起注册请求(有手机号)
    public void setLoginOne(String login_usre,String login_email,String login_pwd,String login_repwd,String login_phone) {
        Log.d("zzz", "setlogin");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<LoginBean> List_course = api.loginOne(login_email, login_pwd,login_repwd, login_usre, login_phone);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.d("zzz", loginBean + "vddddddddd");
                        loginView.login(loginBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "onError"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("zzz", "onComplete");
                    }
                });
    }
    //发起注册请求(无手机号)
    public void setLoginTwe(String login_usre,String login_email,String login_pwd,String login_repwd) {
        Log.d("zzz", "setlogin");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<LoginBean> List_course = api.loginTwe(login_email, login_pwd,login_repwd, login_usre);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.d("zzz", loginBean + "vddddddddd");
                        loginView.login(loginBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "onError"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("zzz", "onComplete");
                    }
                });
    }

    //得到SensitizeView对象
    public void SensitizeView(SensitizeView sensitizeView){
        this.sensitizeView = sensitizeView;
    }
    //发起激活自身请求
    public void setStatus() {
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<StatusBean> List_course = api.status();
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusBean statusBean) {
                        Log.d("zzz", statusBean + "vddddddddd");
                        sensitizeView.status(statusBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到MainView对象
    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }
    //发起得到信息
    public void setData(){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<DataNameBean> List_course = api.mainDetails();
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataNameBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataNameBean dataNameBean) {
                        Log.d("zzz", dataNameBean.getMsg() + "vddddddddd");
                        mainView.getDataName(dataNameBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起退出登录
    public void setLogout(){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<LogoutBean> List_course = api.logout();
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LogoutBean logoutBean) {
                        Log.d("zzz", logoutBean + "vddddddddd");
                        mainView.getLogout(logoutBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到Modify对象
    public void setModifypwd(ModifyView modifyView){
        this.modifyView = modifyView;
    }
    //发起修改密码
    public void setModifyPwd(String oldpwd,String newpwd, String newrepwd){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<Modifypwd> List_course = api.modifypwd(oldpwd,newpwd,newrepwd);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Modifypwd>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Modifypwd modifypwd) {
                        Log.d("zzz", modifypwd.getMsg()+ "vddddddddd");
                        modifyView.getModify(modifypwd);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到PersonDataView对象
    public void setPersonDataView(PersonDataView personDataView){
        this.personDataView = personDataView;
    }
    //发起得到个人信息
    public void setPersonData(String token){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Log.d("zzz", "Person=" + token);
        Observable<PersonDataBean> List_course = api.personDetails(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonDataBean personDataBean) {
                        Log.d("zzz", personDataBean.getMsg() + "vddddddddd");
                        personDataView.personData(personDataBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到ForgetView对象
    public void setForgetView(ForgetView forgetView){
        this.forgetView = forgetView;
    }
    //发起得到验证码
    public void setForget_Code(String emal){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<CodeBean> List_course = api.getcode(emal,2);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        Log.d("zzz", codeBean.getMsg() + "vddddddddd");
                        forgetView.code(codeBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起得到验证码
    public void setForget_Back(String emal,String code,String pwd,String repwd){
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<BackBean> List_course = api.getBack(emal,code,pwd,repwd);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BackBean backBean) {
                        Log.d("zzz", backBean.getMsg() + "vddddddddd");
                        forgetView.back(backBean);
                        Log.d("zzz", "2");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
