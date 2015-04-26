package com.example.aman.housing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Display extends ActionBarActivity {

    TextView details;
    TextView videos;
    TextView room;
    TextView mates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        setTitle("Property Details");

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        String budget = intent.getStringExtra("budget");

        details = (TextView)findViewById(R.id.fillDetails);
        videos = (TextView)findViewById(R.id.videoLinks);
        room = (TextView)findViewById(R.id.room);
        mates = (TextView)findViewById(R.id.mates);

        if(Transfer.selections[position].rentable.equals("0")) {
            room.setVisibility(View.INVISIBLE);
            mates.setVisibility(View.INVISIBLE);
        }

        else {

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();

            params.put("controller", "user");
            params.put("action", "getRoommates");
            params.put("propid", Transfer.selections[position].propid);
            params.put("budget", budget);
            params.put("lat", Transfer.selections[position].lat);
            params.put("lon", Transfer.selections[position].lon);
            params.put("userid", Transfer.userid);

            client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    try {
                        JSONObject object = new JSONObject(new String(response));
                        Log.e("Videos",new String(response));
                        JSONArray array = object.getJSONArray("result");
                        if(array.length()>0){
                            String links = "\n";
                            for(int i=0; i<array.length(); ++i) {
                                JSONObject obj = array.getJSONObject(i);
                                String name = obj.getString("name");
                                String phone = obj.getString("phno");
                                String fin = "Name: "+name+"\tPhone: "+phone+"\n";
                                links = links + fin;
                            }
                            mates.setText(links);
                        }
                        else
                            mates.setText("No users with similar budget");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    Log.e("PROP", "failmax : " + e.toString());
                }

                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });


        }

        StringBuilder sn = new StringBuilder("\n");
        sn.append("Property ID: ").append(Transfer.selections[position].propid).append("\n");
        sn.append("Owner ID: ").append(Transfer.selections[position].ownerid).append("\n");
        sn.append("Price: ").append(Transfer.selections[position].price).append("\n");
        sn.append("BHK: ").append(Transfer.selections[position].bhk).append("\n");
        sn.append("Latitude: ").append(Transfer.selections[position].lat).append("\n");
        sn.append("Longitude: ").append(Transfer.selections[position].lon).append("\n");
        sn.append("Houses (people): ").append(Transfer.selections[position].numppl).append("\n");
        sn.append("For Rent: ").append(Transfer.selections[position].rentable).append("\n");
        sn.append("Description: ").append(Transfer.selections[position].numppl).append("\n");
        sn.append("Address: ").append(Transfer.selections[position].rentable).append("\n");

        details.setText(sn.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("controller", "propvideo");
        params.put("action", "getpropvideo");
        params.put("propid", Transfer.selections[position].propid);

        client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    JSONObject object = new JSONObject(new String(response));
                    Log.e("Videos",new String(response));
                    JSONArray array = object.getJSONArray("result");
                    if(array.length()>0){
                        String links = "\n";
                        for(int i=0; i<array.length(); ++i) {
                            JSONObject obj = array.getJSONObject(i);
                            String url = obj.getString("url");
                            Log.e("OLDURL",url);
                            url = url.replace("\\/","/");
                            Log.e("NEWURL",url);
                            links = links + url + "\n";
                        }
                        videos.setText(links);
                    }
                    else
                        videos.setText("\nNone available");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("PROP", "failmax : " + e.toString());
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
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
}
