package com.example.mice.rss_reader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.nkzawa.emitter.Emitter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class LoginFacebookActivity extends AppCompatActivity {
Button facebook;
    private FacebookCallback<LoginResult> loginResult;
    private CallbackManager callbackFaceBookManager;
    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket("http://123gotest.tk:2000/");
//        } catch (URISyntaxException e) {}
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        Log.d("dsdsd", "onCreate: ");

        initFBLogin();
//        mSocket.connect();
//        mSocket.emit("connection",onNewMessage);
//        Log.d("dat_chat",""+mSocket.toString());


    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }


    };

    private void initFBLogin() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackFaceBookManager = CallbackManager.Factory.create();
       // loginActivity = this;
        validateLoginFaceBook();
        LoginManager.getInstance().registerCallback(callbackFaceBookManager, loginResult);
       // ImageButton loginButton = findViewById(R.id.btnLoginFb);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFaceBook();
            }
        });
        printKeyHash(this);
    }

    public String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);



            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
                Log.d("dsdsd", "printKeyHash: "+key);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchFieldError e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return key;
    }
    public void loginFaceBook() {

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void validateLoginFaceBook() {

        loginResult = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Login thành công xử lý tại đây
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code

                                String name =object.optString("name");
//                                String id = object.optString(getString(R.string.id));
//                                String email = object.optString(getString(R.string.email));
//                                String link = object.optString(getString(R.string.link));
                               // URL imageURL = extractFacebookIcon(id);
                                Log.d("name: ", name);
                                Intent intent = new Intent(LoginFacebookActivity.this,MainActivity.class);
                                intent.putExtra("name",name);
                                startActivity(intent);
//                                Log.d("id: ", id);
//                                Log.d("email: ", email);
//                                Log.d("link: ", link);
//                                Log.d("imageURL: ", imageURL.toString());
                               // validateSocialNetworking(email, name, id, "1");
                            }
                        });
                Bundle parameters = new Bundle();
              //  parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
    }
    public URL extractFacebookIcon(String id) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackFaceBookManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
