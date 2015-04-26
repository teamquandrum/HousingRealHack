package com.example.aman.housing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Main extends ActionBarActivity {

    CallbackManager callbackManager;
    String sup;

    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.house.girish.housingsample", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_status", "email", "user_tagged_places", "user_likes"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("Login success", loginResult.getAccessToken().getToken() + " + " + loginResult.toString());
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.e("TAG", object.optString("name") + object.optString("likes") + object.optString("tagged_places") + object.optString("email") + object.optString("id") + object.optString("statuses"));

                                        Log.e("statuses", object.optString("statuses"));
                                        try {
                                            JSONObject j = new JSONObject(object.optString("statuses"));
                                            JSONArray array = j.getJSONArray("data");
                                            StringBuilder sb = null;
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject status = array.getJSONObject(i);
                                                String message = status.getString("message");
                                                Log.e("status", message);
                                                sb = new StringBuilder();
                                                sb.append(message);
                                                sb.append("\n");
                                            }
                                            sup = sb.toString();

                                            JSONObject json = new JSONObject(object.optString("likes"));
                                            JSONArray data = json.getJSONArray("data");
                                            for (int i = 0; i < data.length(); i++) {
                                                JSONObject like = data.getJSONObject(i);
                                                String id = like.getString("id");
                                                String category = like.getString("category");
                                                String name = like.getString("name");

                                                if (category.equals("Food/beverages") || category.equals("Restaurant/cafe")) {
                                                    Log.e("restaurant", name);
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email,statuses, tagged_places, likes");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("Login error", exception.toString());
                    }
                });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // App code
                Log.d("access token tracker", currentAccessToken.toString());
            }
        };

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                Intent i = new Intent(Main.this, SP2.class);
                String keyIdentifer = null;
                i.putExtra("status", sup);
                startActivity(i);

            }
        });

        showHashKey(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
