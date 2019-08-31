package com.example.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import org.json.JSONObject;
import java.util.ArrayList;


public class DemoComparsion extends AppCompatActivity {

    BarChart barChart;
    Button okButton;
    TextView text;
    String values;
    String valuesArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_comparsion);

        okButton = (Button)findViewById(R.id.button);
        text=(TextView)findViewById(R.id.textView);
        barChart = (BarChart)findViewById(R.id.mp_groupBarChart);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DemoComparsion.this,LastActivity.class);
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
        barEntries.add(new BarEntry(1,2000));
        // barEntries.add(new BarEntry(1,Integer.parseInt(valuesArray[0])));
        barEntries.add(new BarEntry(2,900));
        // barEntries.add(new BarEntry(1,Integer.parseInt(valuesArray[1])));
        barEntries.add(new BarEntry(3,1000));
        // barEntries.add(new BarEntry(1,Integer.parseInt(valuesArray[2])));
        barEntries.add(new BarEntry(4,1500));
        // barEntries.add(new BarEntry(1,Integer.parseInt(valuesArray[3])));

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

}

