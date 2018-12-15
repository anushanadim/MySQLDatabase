package com.example.anushanadim.mysqldatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name, num, age, id;
    Button submit,show;
    String idStr,ageStr,numStr;
    String nameStr;
    InputStream inputStream=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        num = findViewById(R.id.num);
        submit = findViewById(R.id.submit);
        show=findViewById(R.id.show);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idStr = (id.getText().toString());
                nameStr = name.getText().toString();
                ageStr = (age.getText().toString());
                numStr = (num.getText().toString());

                if(idStr.isEmpty()||nameStr.isEmpty()||ageStr.isEmpty()||numStr.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please enter all fields" ,Toast.LENGTH_SHORT ).show();
                }
                else {
                    new PostDataTOServer().execute();

                    id.setText("");
                    name.setText("");
                    num.setText("");
                    age.setText("");
                }

            }

        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RetrieveData.class);
                startActivity(intent);
            }
        });

    }

    public class PostDataTOServer extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("ID", idStr));
            nameValuePairs.add(new BasicNameValuePair("Name", nameStr));
            nameValuePairs.add(new BasicNameValuePair("Age", ageStr));
            nameValuePairs.add(new BasicNameValuePair("Number", numStr));

            try {
                //setting up default http client
                HttpClient httpClient = new DefaultHttpClient();
                //setting up the url of php file to connect to database
                HttpPost httpPost = new HttpPost("YOUR REGISTER.PHP URL");
                //passing name value pairs in http post
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //getting response
                HttpResponse httpResponse = httpClient.execute(httpPost);
                //setting up entity
                HttpEntity httpEntity = httpResponse.getEntity();
                //setting up content inside an input stream reader
                inputStream = httpEntity.getContent();



            } catch (ClientProtocolException e) {
                Toast.makeText(MainActivity.this,"ClientProtocolException" ,Toast.LENGTH_SHORT ).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,"IOException" ,Toast.LENGTH_SHORT ).show();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();

        }

    }
}
