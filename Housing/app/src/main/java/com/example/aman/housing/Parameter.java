package com.example.aman.housing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class Parameter extends ActionBarActivity {

    private List<String> mItems;
    private List<Double> latitudes;
    private List<Double> longitudes;
    private CompleteListAdapter mListAdapter;
    private ListView mCompleteListView;
    private EditText budget;
    String latstring = "";
    String lonstring = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        setTitle("Find Houses");

        budget = (EditText)findViewById(R.id.budgetBox);
        mCompleteListView = (ListView) findViewById(R.id.completeList);
        mItems = new ArrayList<String>();
        mListAdapter = new CompleteListAdapter(this, mItems);
        mCompleteListView.setAdapter(mListAdapter);

        latitudes = new ArrayList<Double>();
        longitudes = new ArrayList<Double>();

        mCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                mItems.remove(position);
                latitudes.remove(position);
                longitudes.remove(position);
                mListAdapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parameter, menu);
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

    public void addListItem(View view) {
        Intent intent = new Intent(this, PlacesPicker.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            String name = data.getStringExtra("name");
            Log.e("NAME", name);
            mItems.add(name);
            latitudes.add(data.getDoubleExtra("lat", 0.0));
            longitudes.add(data.getDoubleExtra("lon", 0.0));
            mListAdapter.notifyDataSetChanged();

        }
    }

    public void contactHome(View view) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();



        for (int i = 0; i < latitudes.size(); ++i) {
            Log.e(i + " lat ", latitudes.get(i) + "");
            Log.e(i + " lon ", longitudes.get(i) + "");
            latstring += latitudes.get(i) + ":";
            lonstring += longitudes.get(i) + ":";
        }

        latstring = latstring.substring(0, latstring.length() - 1);
        lonstring = lonstring.substring(0, lonstring.length() - 1);

        Log.e("LAT", latstring);
        Log.e("LON", lonstring);

        params.put("controller", "property");
        params.put("action", "getcentroid");
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
                launchNext();
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

    private void launchNext() {
        Intent intent = new Intent(this,FetchResults.class);
        intent.putExtra("budget",budget.getText().toString());
        intent.putExtra("lats",latstring);
        intent.putExtra("lons",lonstring);
        startActivity(intent);
    }

}
