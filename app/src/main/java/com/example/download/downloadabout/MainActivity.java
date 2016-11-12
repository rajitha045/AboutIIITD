package com.example.download.downloadabout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;


public class MainActivity extends AppCompatActivity {

    TextView  about;
    private static final String TAG="MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        about = (TextView)findViewById(R.id.about_txt);
        View b = findViewById(R.id.about_txt);
        b.setVisibility(View.INVISIBLE);
    }

    public void onAboutClick(View view)
    {
        View b = findViewById(R.id.about_txt);
        b.setVisibility(View.VISIBLE);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            new downloadPageAbout().execute();
        }
        else
        {
            Toast.makeText(this,"Please Check Network connection",Toast.LENGTH_SHORT).show();
        }

    }

    public static String downloadPage(String url) throws IOException {

        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        InputStream htmlInputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(htmlInputStream));
        StringBuilder htmlString = new StringBuilder();
        for (String line; (line = bufferedReader.readLine()) != null; ) {
            htmlString.append(line);

        }
        htmlInputStream.close();
        return htmlString.toString();
    }

    private class downloadPageAbout extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... paramses) {
            String about = "No About";
            try
            {
                about = downloadPage("https://www.iiitd.ac.in/about");
            }
            catch (Exception e)
            {
                System.out.println("Something Wrong with site");
                e.printStackTrace();
            }
            return about;

        }
        public void printBackGround(String string,int initial,int last){

            for(int i=initial;i<last;i=i+100){
                if(i==last-1){
                    System.out.println(string.substring(i,i+75));
                }
                else {
                    System.out.println(string.substring(i,i+100));
                }

            }
        }

        @Override
        protected void onPostExecute(String aboutString) {

                String removeTags = aboutString.replaceAll("<[^>]*>", "");

                int firstIndraprastha=removeTags.indexOf("Indraprastha");


                String pragraph1=removeTags.substring(firstIndraprastha+15,removeTags.length());
                int secondIndraprastha=pragraph1.indexOf("Indraprastha");
                int lastIndex=pragraph1.indexOf("First");
                String str=pragraph1.substring(secondIndraprastha,lastIndex+70);
                lastIndex=pragraph1.indexOf("far");
                printBackGround(pragraph1,secondIndraprastha,lastIndex);
//                int lastBackInde=pragraph1.indexOf("events");
//                Log.d("",pragraph1.substring(lastIndex,lastIndex+100));
//                Log.d("",pragraph1.substring(lastIndex+100,lastIndex+200));
//                Log.d("",pragraph1.substring(lastIndex+300,lastIndex+400));
//                Log.d("",pragraph1.substring(lastIndex+500,lastIndex+600));
                View b = findViewById(R.id.about_btn);
                b.setVisibility(View.GONE);
                View c=findViewById(R.id.imageView);
                c.setVisibility(View.GONE);
                about.setText(str);
            

        }




    }
}