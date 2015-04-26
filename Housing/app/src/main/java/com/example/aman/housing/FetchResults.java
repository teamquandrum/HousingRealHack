package com.example.aman.housing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FetchResults extends ActionBarActivity {

    public String latstring;
    public String lonstring;
    public String budget;
    private List<String> mItems;
    private List<Double> latitudes;
    private List<Double> longitudes;
    private CompleteListAdapter mListAdapter;
    private ListView mCompleteListView;
    Land listings[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_results);

        setTitle("The perfect houses");

        Intent intent = getIntent();
        latstring = intent.getStringExtra("lats");
        lonstring = intent.getStringExtra("lons");
        budget = intent.getStringExtra("budget");

        mCompleteListView = (ListView) findViewById(R.id.placesList);
        mItems = new ArrayList<String>();
        mListAdapter = new CompleteListAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);

        latitudes = new ArrayList<Double>();
        longitudes = new ArrayList<Double>();

        mCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(getBaseContext(),Display.class);
                intent.putExtra("position",position);
                intent.putExtra("budget",budget);
                startActivity(intent);

            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("controller", "property");
        params.put("action", "getproperty");
        params.put("userid", "2");
        params.put("budget", budget);
        params.put("lats", latstring);
        params.put("lons", lonstring);

        client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.e("PROP", new String(response));
                try {
                    JSONObject object = new JSONObject(new String(response));
                    JSONArray array = object.getJSONArray("result");
                    listings = new Land[array.length()];
                    for (int i = 0; i < array.length(); ++i) {
                        JSONObject obj = array.getJSONObject(i);
                        listings[i] = new Land(obj.getString("propid"), obj.getString("ownerid"), obj.getString("price"), obj.getString("bhk"), obj.getString("lat"), obj.getString("lon"), obj.getString("numppl"), obj.getString("rentable"), obj.getString("description"), obj.getString("address"));
                    }
                    Transfer.selections = listings;
                    for(int i=0; i<listings.length; ++i) {
                        String n= listings[i].description.substring(0,listings[i].description.indexOf("."));
                        n=n+"\nLand for "+listings[i].bhk+" BHK home.";
                        if(listings[i].rentable.equals("1"))
                            n=n+"\nFor rent";
                        n=n+"\nAddress: "+ listings[i].address;
                        n=n+"\nPrice: "+ listings[i].price + "\n";
                        mItems.add(n);
                    }
                    mListAdapter.notifyDataSetChanged();

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
        getMenuInflater().inflate(R.menu.menu_fetch_results, menu);
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
