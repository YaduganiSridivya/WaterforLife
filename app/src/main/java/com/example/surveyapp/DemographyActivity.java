package com.example.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DemographyActivity extends AppCompatActivity {



    BarChart barChart;
    Button okButton;
    TextView text;
    String values;
    String valuesArray[];
    String myUrl = "http://myApi.com/get_some_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demography);

        okButton = (Button)findViewById(R.id.button);
        //text=(TextView)findViewById(R.id.textView);
        barChart = (BarChart)findViewById(R.id.mp_groupBarChart);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DemographyActivity.this,DemoComparsion.class);
                startActivity(i);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            values=extras.getString("key");
            valuesArray = values.split(",");
        }


        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Your usage");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Standard usage");
        barDataSet1.setColor(Color.BLUE);

        BarData data = new BarData(barDataSet1,barDataSet2);
        barChart.setData(data);
        String[] xAxisValues = new String[]{"Question1","Question2","Question3","Question4","Question5"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        float barSpace = 0.05f;
        float groupSpace = 0.58f;
        data.setBarWidth(0.16f);

        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*5);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.groupBars(0,groupSpace,barSpace);
        barChart.invalidate();



    }

    private ArrayList<BarEntry> barEntries1()
    {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        //CHANGED THE PREVIOUS STATEMENTS TO TAKE THE VALUES FOR THE BAR GRAPH DYNAMICALLY

         //barEntries.add(new BarEntry(1,Integer.parseInt(valuesArray[0])));
         barEntries.add(new BarEntry(1,1500));
         //barEntries.add(new BarEntry(2,Integer.parseInt(valuesArray[1])));
        barEntries.add(new BarEntry(2,1200));
        // barEntries.add(new BarEntry(3,Integer.parseInt(valuesArray[2])));
        barEntries.add(new BarEntry(3,500));
        // barEntries.add(new BarEntry(4,Integer.parseInt(valuesArray[3])));
        barEntries.add(new BarEntry(4,1800));

        return barEntries;

    }
    private ArrayList<BarEntry> barEntries2()
    {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1,2000));
        barEntries.add(new BarEntry(2,900));
        barEntries.add(new BarEntry(3,1000));
        barEntries.add(new BarEntry(4,1500));

        return barEntries;

    }

    public void restart(View view) {
        startActivity(new Intent(DemographyActivity.this,NavigationActivity.class));
    }
/*
    private  class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            okButton.setText(result);
        }
    }

    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }
    private  JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("Question1", valuesArray[0]);
        jsonObject.accumulate("Question2", valuesArray[1]);
        jsonObject.accumulate("Question3", valuesArray[2]);
        jsonObject.accumulate("Question4", valuesArray[3]);

        return jsonObject;
    }

    private  void setPostRequestContent(HttpURLConnection conn,
                                        JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(DemographyActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }




    private  class HTTPAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpGet(urls[0]);
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid.";
                }
            }catch (JSONException e) {
                e.printStackTrace();
                return "Error!";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            text.setText(result);
        }

    }

    private  String HttpGet(String myUrl) throws IOException, JSONException {
        InputStream inputStream = null;
        String result = "";

        URL url = new URL(myUrl);

        // create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // make GET request to the given URL
        conn.connect();

        // receive response as inputStream
        inputStream = conn.getInputStream();


        // convert inputstream to string
        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        else
            result = "Did not work!";

        return result;
    }

    private  String convertInputStreamToString(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        //ans = result.split("[{:,]");
        newJson = new JSONObject(result);

        inputStream.close();
        return result;

    }
    */
}

