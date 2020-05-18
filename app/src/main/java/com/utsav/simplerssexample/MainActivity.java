package com.utsav.simplerssexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.AdapterView.*;


public class MainActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   btn=findViewById(R.id.btn);
   btn.setOnClickListener(new OnClickListener() {
       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
       @Override
       public void onClick(View view) {
           updatetextview();
       }
   });


        }
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public void updatetextview(){
    String link="https://feed2json.org/convert?url=https%3A%2F%2Ftimesofindia.indiatimes.com%2Frssfeedstopstories.cms&minify=on";

   try{
       makenetworkcall(link);
   }catch(IOException e){
       e.printStackTrace();
   }

}
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
 void makenetworkcall(String url)throws IOException{
    OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result =response.body().string();

                Gson gson =new Gson();
                Apiresult apiResult=gson.fromJson(result,Apiresult.class);

                 final newsitemadapter gitHubAdapter=new newsitemadapter(apiResult.getItems());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        RecyclerView recyclerView = findViewById(R.id.rv_itemlist);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        recyclerView.setAdapter(gitHubAdapter);
                    }
                });

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }
    class NetworkTask extends AsyncTask<String,Void,String> implements com.utsav.simplerssexample.NetworkTask {
        @Override
        protected String doInBackground(String... strings) {
            String stringurl  =strings[0];
            URL url= null;
            try {
                url = new URL(stringurl);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    String s=scanner.next();
                    return s;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return "failed to reload";
        }

        public void onPostExecue(String s){
            super.onPostExecute(s);

            ArrayList<newsitem> users=parsejson(s);
           // Log.e(TAG,"started"+ users.size());
            newsitemadapter gitHubAdapter=new newsitemadapter(users);
            RecyclerView recyclerView=findViewById(R.id.rv_itemlist);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView.setAdapter(gitHubAdapter);
        }
    }
    ArrayList<newsitem>parsejson(String s){
        ArrayList<newsitem> listitem= new ArrayList<>();
        try {
            JSONObject root=new JSONObject(s);
            JSONArray items= root.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject object=  items.getJSONObject(i);
                String title=object.getString("title");
                String guid= object.getString("guid");
                String url=object.getString("url");
                String content_html=object.getString("content_html");
                String summary =object.getString("summary");

                newsitem newsitems=new newsitem(title,url,guid,content_html,summary);
                listitem.add(newsitems);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listitem;
    }
    public void Onclick(){

    }



}
