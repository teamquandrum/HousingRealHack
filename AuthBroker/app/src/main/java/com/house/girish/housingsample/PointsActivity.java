package com.house.girish.housingsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class PointsActivity extends ActionBarActivity {

    Bundle extras;
    String url, brokerid="1";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        final String propid;

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                propid= null;
            } else {
                propid= extras.getString("propid");
            }
        } else {
            propid= (String) savedInstanceState.getSerializable("propid");
        }


        tv = (TextView)findViewById(R.id.textView6);
        tv.setText("Points for broker number " + brokerid);


        final EditText et = (EditText)findViewById(R.id.editText);


        Button btn = (Button)findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                url = et.getText().toString();
                if(url==null||url.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter a valid url",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    registerNow(propid, url, brokerid);

                    //startActivity(new Intent(P.this, RegList.class));
                }

            }
        });
        Button btn2 = (Button)findViewById(R.id.button3);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                getPoints();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_points, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPoints()
    {
        //Register with the Server
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("controller", "broker");
        params.put("action", "getpoints");

        params.put("brokerid", brokerid);

        client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "Sending",
                        Toast.LENGTH_SHORT).show();
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast.makeText(getApplicationContext(), new String(response),
                        Toast.LENGTH_SHORT).show();

                try {
                    JSONObject json = new JSONObject(new String(response));
                    JSONObject j = json.getJSONObject("result");
                    String points = j.getString("points");
                    tv.setText("Points for broker " + brokerid + ": " + points);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Intent intent = new Intent(AskActivity.this, QAActivity.class);
                //startActivity(intent);
                //finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), e.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                Toast.makeText(getApplicationContext(), "Retrying",
                        Toast.LENGTH_SHORT).show();
                // called when request is retried
            }
        });

    }

    public void registerNow(String propid, String url, String brokerid) {


        //Register with the Server
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("controller", "propvideo");
        params.put("action", "newpropvideo");
        params.put("propid", propid);
        params.put("url", url);
        params.put("brokerid", brokerid);


        client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "Sending",
                        Toast.LENGTH_SHORT).show();
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Toast.makeText(getApplicationContext(), new String(response),
                        Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(AskActivity.this, QAActivity.class);
                //startActivity(intent);
                //finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), e.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                Toast.makeText(getApplicationContext(), "Retrying",
                        Toast.LENGTH_SHORT).show();
                // called when request is retried
            }
        });

    }
}
