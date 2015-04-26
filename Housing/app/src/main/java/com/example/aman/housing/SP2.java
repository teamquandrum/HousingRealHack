package com.example.aman.housing;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SP2 extends ActionBarActivity {
    TextView txtV;
    Context cntx;
    String newString;
    Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp2);

        setTitle("Personality Calculator");

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("status");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("status");
        }

        try {
            Log.e("extra", "" + extraversion());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lo = 0, ho = 0, ha = 0, la = 0, e = 0, i = 0, n = 0, es = 0, hc = 0, lc = 0;
        try {
            lo = lowopenness();
            ho = highopenness();
            ha = highagreeableness();
            la = lowagreeableness();
            e = extraversion();
            i = introversion();
            n = neuroticism();
            es = emotionalstability();
            hc = highconscientiousness();
            lc = lowconscientiousness();
            System.out.println((lo) + " " + (ho) + " " + (la) + " " + (ha) + " " + (i) + " " + (e) + " " + (es) + " " + (n) + " " + (lc) + " " + (hc));
        } catch (IOException ee) {
            ee.printStackTrace();
        }


        final int openness, extraversion, concentiousness, agreeableness, neuroticism;

        if (ho > lo) {
            System.out.println("Openness: " + (ho * 100 / (ho + lo)) + "% ");
            openness = (ho * 100 / (ho + lo));
        } else {
            System.out.println("Openness: " + (ho * 100 / (ho + lo)) + "% ");
            openness = (ho * 100 / (ho + lo));
        }
        if (i > e) {
            System.out.println("Extraversion: " + (e * 100 / (i + e)) + "% ");
            extraversion = (e * 100 / (i + e));
        } else {
            System.out.println("Extraversion " + (e * 100 / (i + e)) + "% ");
            extraversion = (e * 100 / (i + e));
        }
        if (hc > lc) {
            System.out.println("Conscientiousness: " + (hc * 100 / (hc + lc)) + "% ");
            concentiousness = (hc * 100 / (hc + lc));
        } else {
            System.out.println("Conscientiousness: " + (hc * 100 / (hc + lc)) + "% ");
            concentiousness = (hc * 100 / (hc + lc));
        }
        if (ha > la) {
            System.out.println("Agreeableness: " + (ha * 100 / (ha + la)) + "% ");
            agreeableness = (ha * 100 / (ha + la));
        } else {
            System.out.println("Agreeableness: " + (ha * 100 / (ha + la)) + "% ");
            agreeableness = (ha * 100 / (ha + la));
        }
        if (n > es) {
            System.out.println("Neuroticism: " + (n * 100 / (n + es)) + "% ");
            neuroticism = (n * 100 / (n + es));
        } else {
            System.out.println("Neuroticism: " + (n * 100 / (n + es)) + "% ");
            neuroticism = (n * 100 / (n + es));
        }

        Log.e("the final shit", openness + " " + concentiousness + " " + extraversion + " " + agreeableness + " " + neuroticism);

        registerNow(openness, concentiousness, extraversion, agreeableness, neuroticism);
    }

    public void registerNow(int o, int c, int e, int a, int n) {


        //Register with the Server
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("controller", "user");
        params.put("action", "newUser");
        params.put("name", "Fill value obtained from user");
        params.put("gender", "M");

        params.put("openness", o);
        params.put("conscience", c);
        params.put("extroverted", e);
        params.put("agreeable", a);
        params.put("neurotic", n);

        client.get("http://housinghack.gear.host/index.php/manager", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "Uploading Information",
                        Toast.LENGTH_SHORT).show();
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    JSONObject object = new JSONObject(new String(response));
                    object = object.getJSONObject("result");
                    Transfer.userid = object.getString("userid");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), new String(response),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SP2.this, Parameter.class);
                startActivity(intent);
                finish();
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

    public int extraversion() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.extra);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }

    public int introversion() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.intro);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }


    public int lowopenness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.lowopenness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count;
    }

    public int highopenness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.highopenness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count;
    }

    public int neuroticism() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.neuroticism);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count;
    }

    public int emotionalstability() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.emotionalstability);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count;
    }

    public int lowagreeableness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.lowagreeableness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }

    public int highagreeableness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.highagreeableness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }

    public int highconscientiousness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.highconscientiousness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }

    public int lowconscientiousness() throws IOException {
        String everything, everything2;
        ArrayList<String> list = new ArrayList<String>();

        final Resources resources = this.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.lowconscientiousness);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder strBuild = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                strBuild.append(line);
                strBuild.append("\n");
            }
        } finally {
            reader.close();
        }

        everything = strBuild.toString();
        //Log.d("everything", everything);
        String[] words = everything.split("\n");
        int[] weights = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            String sweight = words[i].substring(0, 2);
            weights[i] = Integer.parseInt(sweight);
            words[i] = words[i].substring(3);
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(weights[i] + words[i]);
        }


        everything2 = newString;
        //Log.e("everytihng2", everything2);
        //System.out.println(everything2);
        int count1 = 0;
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, words[i].length() - 1);
            //System.out.println(words[i]+"\n");
            if (everything2.toLowerCase().contains(words[i].toLowerCase())) {
                count1++; //System.out.println(words[i]);
                count = count + weights[i];
            }
        }


        System.out.println(count1);

        everything2 = everything2.replace("\n", "");
        String[] no = everything2.split(" ");
        int count2 = no.length;

        //System.out.println(count2);

        return count1;
    }

}