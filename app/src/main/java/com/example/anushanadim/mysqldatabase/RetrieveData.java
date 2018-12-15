package com.example.anushanadim.mysqldatabase;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class RetrieveData extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String line="",result="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        listView=findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(RetrieveData.this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        new RetrieveTask().execute();


    }

    private class RetrieveTask extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {

            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet request= new HttpGet();
                request.setURI(new URI("YOUR REGISTER.PHP URL"));
                HttpResponse httpResponse = httpClient.execute(request);
                BufferedReader br=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                StringBuffer stringBuffer=new StringBuffer();
                while((line=br.readLine())!=null)
                {
                    stringBuffer.append(line);
                    break;
                }
                br.close();
                result=stringBuffer.toString();

            }catch(Exception e)
            {
                Toast.makeText(RetrieveData.this,"Exception" ,Toast.LENGTH_SHORT ).show();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject=new JSONObject(result);
                int success=jsonObject.getInt("success");
                if(success==1)
                {
                    JSONArray jsonArray=jsonObject.getJSONArray("Details");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        int id=object.getInt("ID");
                        String name=object.getString("Name");
                        int age=object.getInt("Age");
                        int num=object.getInt("Number");
                        String line=id+"\t\t\t"+name+"\t\t\t"+age+"\t\t\t"+num;
                        adapter.add(line);


                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No records" ,Toast.LENGTH_SHORT ).show();
                }
            }catch(Exception e)
            {
                Toast.makeText(RetrieveData.this,"Exception111" ,Toast.LENGTH_SHORT ).show();
            }

        }
    }
}
