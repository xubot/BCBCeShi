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
import com.example.bckj.projectbcb.Bean.ReActiveUserBean;
import com.example.bckj.projectbcb.Bean.SensitizeBean;
import com.example.bckj.projectbcb.Bean.SensitizelistBean;
import com.example.bckj.projectbcb.Bean.StatusBean;
import com.example.bckj.projectbcb.Bean.UpdateInfoBean;
import com.example.bckj.projectbcb.Moude.MoudelLayer;
import com.example.bckj.projectbcb.Utils.net.Api;
import com.example.bckj.projectbcb.ViewLayer.ForgetView;
import com.example.bckj.projectbcb.ViewLayer.LogView;
import com.example.bckj.projectbcb.ViewLayer.LoginView;
import com.example.bckj.projectbcb.ViewLayer.MadifyDataView;
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
    private MadifyDataView madifyDataView;

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
        Log.d("zzz", "log    ");
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
                        Log.d("zzz", "log  的信息"+logBean.getMsg());
                        logView.log(logBean);
                        Log.d("zzz", "log  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "log 错误信息：" + e.toString());
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
        Log.d("zzz", "login    ");
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
                        Log.d("zzz", "login  注册的信息："+loginBean.getMsg());
                        loginView.login(loginBean);
                        Log.d("zzz", "login  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "login  错误的信息"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    //发起注册请求(无手机号)
    public void setLoginTwe(String login_usre,String login_email,String login_pwd,String login_repwd) {
        Log.d("zzz", "login   twe");
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
                        Log.d("zzz", "logintwe  注册的回馈信息："+loginBean.getMsg());
                        loginView.login(loginBean);
                        Log.d("zzz", "logintwe 成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "logintwe  错误的信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起登录请求
    public void setPersonDataLog(String usre,String pwd) {
        Log.d("zzz", "login  persondatalog");
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
                        Log.d("zzz", "login  persondatalog的信息："+logBean);
                        loginView.log(logBean);
                        Log.d("zzz", "login  persondatalog成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "login  persondatalog错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //得到SensitizeView对象
    public void SensitizeView(SensitizeView sensitizeView){
        this.sensitizeView = sensitizeView;
    }
    //发起激活自身请求
    public void setStatus(String token) {
        Log.d("zzz", "status ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<StatusBean> List_course = api.status(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusBean statusBean) {
                        Log.d("zzz", "status  激活自身的信息"+statusBean.getMsg());
                        sensitizeView.status(statusBean);
                        Log.d("zzz", "status  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "status的错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起重新激活邮件
    public void setreActive(String token){
        Log.d("zzz", "treActive");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<ReActiveUserBean> List_course = api.reActiveUser(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReActiveUserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReActiveUserBean reActiveUserBean) {
                        Log.d("zzz", "treActive 重新激活的信息："+reActiveUserBean.getMsg());
                        sensitizeView.reActiveData(reActiveUserBean);
                        Log.d("zzz", "treActive  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "treActive错误信息："+e.toString());
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
    public void setData(String token){
        Log.d("zzz", "maindata");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<DataNameBean> List_course = api.mainDetails(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataNameBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataNameBean dataNameBean) {
                        Log.d("zzz", "maindata  main得到信息："+dataNameBean.getMsg());
                        mainView.getDataName(dataNameBean);
                        Log.d("zzz", "maindata  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "maindata错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起登录请求
    public void setMainLog(String usre,String pwd) {
        Log.d("zzz", "mainlog");
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
                        Log.d("zzz", "mainlog  main登录的信息情况："+logBean.getMsg());
                        mainView.log(logBean);
                        Log.d("zzz", "mainlog 成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "mainlog错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起得到激活列表
    public void setSensitizelist(String token){
        Log.d("zzz2", "sensitzelist ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<SensitizelistBean> List_course = api.sensitizelist(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SensitizelistBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SensitizelistBean sensitizelistBean) {
                        Log.d("zzz2", "sensitzelist  激活列表的信息："+sensitizelistBean.getMsg());
                        mainView.getsensitizelistBean(sensitizelistBean);
                        Log.d("zzz2", "sensitzelist  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz2", "sensitzelist错误信息：" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起激活请求
    public void setSensitize(String type,int atype,String password ,String account,String token) {
        Log.d("zzz1", "sensitize  ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<SensitizeBean> List_course = api.sensitize(type,atype,password,account,token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SensitizeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SensitizeBean sensitizeBean) {
                        Log.d("zzz", "sensitize  的信息情况："+sensitizeBean.getMsg());
                        sensitizeView.getSensitize(sensitizeBean);
                        Log.d("zzz", "sensitize 成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz1", "sensitize 错误信息："+e.toString());
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
    public void setModifyPwd(String oldpwd,String newpwd, String newrepwd,String token){
        Log.d("zzz", "setModifyPwd   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<Modifypwd> List_course = api.modifypwd(oldpwd,newpwd,newrepwd,token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Modifypwd>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Modifypwd modifypwd) {
                        Log.d("zzz", "setModifyPwd  修改的信息情况："+modifypwd.getMsg());
                        modifyView.getModify(modifypwd);
                        Log.d("zzz", "setModifyPwd  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "setModifyPwd 错误信息："+e.toString());
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
        Log.d("zzz", "Person     ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<PersonDataBean> List_course = api.personDetails(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonDataBean personDataBean) {
                        Log.d("zzz", "Person  得到个人信息的情况："+personDataBean.getMsg() + "vddddddddd");
                        personDataView.personData(personDataBean);
                        Log.d("zzz", "Person  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "Person错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起重新激活邮件
    public void setreActiveUser(String token){
        Log.d("zzz", "reActiveUser   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<ReActiveUserBean> List_course = api.reActiveUser(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReActiveUserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReActiveUserBean reActiveUserBean) {
                        Log.d("zzz", "reActiveUser   信息情况："+reActiveUserBean.getMsg());
                        personDataView.reActiveData(reActiveUserBean);
                        Log.d("zzz", "reActiveUser 成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "reActiveUser错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起退出登录
    public void setLogout(String token){
        Log.d("zzz", "logout   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<LogoutBean> List_course = api.logout(token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LogoutBean logoutBean) {
                        Log.d("zzz", "logout  的信息状况："+logoutBean.getMsg());
                        personDataView.getLogout(logoutBean);
                        Log.d("zzz", "logout  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "logout 错误信息："+e.toString());
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
    public void setForget_Code(String emal,int type){
        Log.d("zzz", "Forget_Code   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<CodeBean> List_course = api.getcode(emal,2,type);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        Log.d("zzz", "Forget_Code   的信息情况："+codeBean.getMsg());
                        forgetView.code(codeBean);
                        Log.d("zzz", "Forget_Code  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "Forget_Code 错误的信息"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //发起找回
    public void setForget_Back(String emal,String code,String pwd,String repwd){
        Log.d("zzz", "Forget_Back    ");
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
                        Log.d("zzz", "Forget_Back   的信息情况："+backBean.getMsg());
                        forgetView.back(backBean);
                        Log.d("zzz", "Forget_Back   成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "Forget_Back 错误的信息"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //修改个人信息的view对象
    public void setModifyDataView(MadifyDataView madifyDataView) {
        this.madifyDataView = madifyDataView;
    }
    //发起得到修改信息
    public void setUpdataInfo(String username,String phone,String token){
        Log.d("zzz", "UpdataInfoTwe   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<UpdateInfoBean> List_course = api.updateInfo(username,phone,token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateInfoBean updateInfoBean) {
                        Log.d("zzz", "UpdataInfoTwe  的信息情况："+updateInfoBean.getMsg());
                        madifyDataView.getUpdataInfoTwe(updateInfoBean);
                        Log.d("zzz", "UpdataInfoTwe  成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "UpdataInfoTwe错误信息："+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /*//发起得到修改信息
    public void setUpdataInfoAll(String username,String mobile,String token){
        Log.d("zzz", "UpdataInfo   ");
        Retrofit retrofit = moudelLayer.getData();
        Api api = retrofit.create(Api.class);
        Observable<UpdateInfoBean> List_course = api.updateInfo(username,mobile,token);
        List_course.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateInfoBean updateInfoBean) {
                        Log.d("zzz", "UpdataInfo   的信息情况："+updateInfoBean.getMsg());
                        madifyDataView.getUpdataInfoTwe(updateInfoBean);
                        Log.d("zzz", "UpdataInfo 成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("zzz", "UpdataInfo 错误信息"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/
}
