package com.example.food_delievery;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class FullscreenActivity extends AppCompatActivity {
    private SearchView searchView;
    adapter user_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        updateView();

    }

    private void updateView(){
        // to call google data
        //TextView t1=findViewById(R.id.t1);
        Network task= new Network();
        task.execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=47.6204,-122.3491&radius=2500&type=restaurant&key=AIzaSyAIux_9gVtovYz4EOfxouSI5GXpkcT5KKs");
    }
    class Network extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String stringurl=strings[0];
            try {
                URL url=new URL(stringurl);
                HttpURLConnection httpurlconnection= (HttpURLConnection)url.openConnection();
                InputStream inputStream=httpurlconnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    String s=scanner.next();
                    return s;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "failed to send";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //String Tag="myapp";
            //Log.e(Tag,"OnPostExecutesome");
            ArrayList<UserData> users=parsejson(s);
            user_adapter=new adapter(users);
            RecyclerView recycle=findViewById(R.id.recycle);
            whiteNotificationBar(recycle);
            recycle.setHasFixedSize(true);
            recycle.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recycle.getLayoutManager().setMeasurementCacheEnabled(false);
            recycle.setAdapter(user_adapter);
            user_adapter.notifyDataSetChanged();
            //Log.d(Tag,"OnPostExecute"+users.size());
        }
    }
    ArrayList<UserData> parsejson(String s){
        ArrayList<UserData> userdata=new ArrayList<>();
        //String Tag="myapp1";
        //Log.e(Tag,"OnPostExecutesome1");
        //parse json
        try {
            JSONObject root =new JSONObject(s);
            JSONArray items=root.getJSONArray("results");
            for (int i =0;i<items.length();i++){
                JSONObject object=items.getJSONObject(i);
                String login=object.getString("name");
                int id=object.getInt("user_ratings_total");
                Double score=object.getDouble("rating");
                String url=object.getString("icon");
                UserData userdata1=new UserData(login,id,url,score);
                userdata.add(userdata1);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e(Tag,"Exception1");
        }

        return userdata;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                user_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                user_adapter.getFilter().filter(newText );
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}

