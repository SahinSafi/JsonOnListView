package com.cyberwith.jsononlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewID);
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();

    }

    public class JsonTask extends AsyncTask<String, String, List<StudentModel>> {

        @Override
        protected List<StudentModel> doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;


            try {
                URL url = new URL("https://api.myjson.com/bins/1h1lzy");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();    // to get jeson byte code
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // to convert byte code to string
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";

                while ( (line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }
                String jsonFile = stringBuffer.toString();
                JSONObject mainObject = new JSONObject(jsonFile);
                JSONArray studentArray = mainObject.getJSONArray("student");
                List<StudentModel> studentModelList = new ArrayList<>();

                for (int i = 0; i<studentArray.length(); i++){
                    JSONObject jsonObject = studentArray.getJSONObject(i);
                    StudentModel studentModel = new StudentModel();
                    studentModel.setName(jsonObject.getString("name"));
                    studentModel.setDepartment(jsonObject.getString("department"));
                    studentModel.setCountry(jsonObject.getString("country"));
                    studentModelList.add(studentModel);
                }

                return studentModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                try {
                    httpURLConnection.disconnect();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<StudentModel> s) {
            super.onPostExecute(s);

            customAdapter = new CustomAdapter(getApplicationContext(),R.layout.sample_layout, s);
            listView.setAdapter(customAdapter);
        }
    }
}
