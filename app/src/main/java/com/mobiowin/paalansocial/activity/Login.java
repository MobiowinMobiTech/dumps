package com.mobiowin.paalansocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.mobiowin.paalansocial.db.DBAdapter;
import com.mobiowin.paalansocial.payload.request.RequestLogin;
import com.mobiowin.paalansocial.payload.response.ResponseLogin;
import com.mobiowin.paalansocial.services.Device;
import com.mobiowin.paalansocial.services.PaalanServices;
import com.mobiowin.paalansocial.utils.AutoCompleteTextViewOpenSansRegular;
import com.mobiowin.paalansocial.utils.ButtonOpenSansSemiBold;
import com.mobiowin.paalansocial.utils.CommanUtils;
import com.mobiowin.paalansocial.utils.EditTextOpenSansRegular;
import com.mobiowin.paalansocial.utils.NetworkUtil;
import com.mobiowin.paalansocial.utils.PreferenceUtils;
import com.mobiowin.paalansocial.utils.TextViewOpenSansRegular;
import com.mobiowin.paalansocial.R;
import com.mobiowin.paalansocial.helper.Social;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getCanonicalName();

    private AutoCompleteTextViewOpenSansRegular mEmailView;
    private EditTextOpenSansRegular mPasswordView;
    private ButtonOpenSansSemiBold btnSignIn;
    private static final int PERMISSION_READ_STATE = 1;
    private String deviceID = "", email = "", password = "";
    private PreferenceUtils pref;
    private DBAdapter dbAdapter;
    private TextViewOpenSansRegular txtSignUp, txtForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializations();
        clickEventFire();
        //merge comment
    }


    /**
     * components initializing here
     */
    private void initializations() {

        pref = new PreferenceUtils(this);
        dbAdapter = new DBAdapter(this);
        mEmailView = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.email);
        mPasswordView = (EditTextOpenSansRegular) findViewById(R.id.password);

        txtSignUp = (TextViewOpenSansRegular)findViewById(R.id.txtSignup);
        txtForgotPassword = (TextViewOpenSansRegular)findViewById(R.id.txtForgotPassword);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txtSignUp.setText(Html.fromHtml(getString(R.string.sign_up_now),Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtSignUp.setText(Html.fromHtml(getString(R.string.sign_up_now)));
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txtForgotPassword.setText(Html.fromHtml(getString(R.string.login_trouble),Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtForgotPassword.setText(Html.fromHtml(getString(R.string.login_trouble)));
        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        btnSignIn = (ButtonOpenSansSemiBold) findViewById(R.id.btnSignIn);
    }

    /**
     * clicking action event fire here
     */
    private void clickEventFire() {

        txtSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this,RegisterUser.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });

        txtForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPasswordIntent = new Intent(Login.this,ForgotPassword.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(forgotPasswordIntent);
            }
        });


        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            email = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
            if (isValidData())
                getRetrofitCall();

            }
//            }
        });

    }



    private boolean isValidData() {
        if(TextUtils.isEmpty(email)){
            mEmailView.setError(getString(R.string.error_field_required));
            return false;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_empty_password));
            return false;
        } else if(!CommanUtils.isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }



    public void getRetrofitCall() {

        if(NetworkUtil.isInternetConnected(Login.this)) {

            CommanUtils.showDialog(Login.this);
            Device.newInstance(Login.this);
            RequestLogin reqLogin = RequestLogin.get( email, password);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<ResponseLogin> resLogin = mPaalanServices.paalanLogin(reqLogin);

            resLogin.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("success")) {
                            CommanUtils.showToast(Login.this, getResources().getString(R.string.logggedIn));
                            pref.setOrgID(response.body().getData()[0].getOrgregdata()[0].getOrgId().toString());
                            pref.setUserName(response.body().getData()[0].getOrgregdata()[0].getName().toString());
                            dbAdapter.open();

                            if (response.body().getData()[0].getOrgprofiledata().length > 0)
                                dbAdapter.insertProfile(
                                        response.body().getData()[0].getOrgprofiledata()[0].getDpImgLink(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getRole(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getRegistrationNo(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getFbLink(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getLinkedinLink(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getWebsiteLink(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getTwitterLink(),
                                        response.body().getData()[0].getOrgprofiledata()[0].getPresenceArea());
                            dbAdapter.close();
                            pref.setLogin(true);
                            pref.setLoginType(Social.ORG_ENTITY);
                            Intent intent = new Intent(Login.this, ActivityFragmentPlatform.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Login.this.finish();
                            Log.d(TAG, "onResponse: after login success");

                        } else {
                            CommanUtils.showToast(Login.this, response.body().getData()[0].getErrmsg());
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(Login.this,response.body().getData()[0].getErrmsg());
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.showToast(Login.this, getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlert(Login.this,getString(R.string.connection_error),
                    getResources().getString(R.string.network_connectivity));
        }
    }

}




