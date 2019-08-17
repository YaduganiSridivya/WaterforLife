package com.example.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {


    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionNoView;
    private TextView mQuestionView;
    private Button mChoice1;
    private Button mChoice2;
    private Button mChoice3;
    private Button mChoice4;


    private int mQuestionNoValue = 1;
    private int mQuestionValue = 0;
    String options=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mQuestionNoView = (TextView)findViewById(R.id.number);
        mQuestionView = (TextView)findViewById(R.id.Question);
        mChoice1 = (Button)findViewById(R.id.choice1);
        mChoice2 = (Button)findViewById(R.id.choice2);
        mChoice3 = (Button)findViewById(R.id.choice3);
        mChoice4 = (Button)findViewById(R.id.choice4);

        updateQuestion();

        mChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options+=mChoice1.getText()+",";
                mQuestionNoValue++;
                if(mQuestionNoValue==5)
                {
                    Intent i =new Intent(HomeActivity.this,DemographyActivity.class);
                    i.putExtra("key",options);
                    HomeActivity.this.startActivity(i);
                }
                else
                {
                    updateQuestionNo(mQuestionNoValue);
                    updateQuestion();
                }
            }
        });
        mChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options+=mChoice1.getText()+",";
                mQuestionNoValue++;
                if(mQuestionNoValue==5)
                {
                    Intent i =new Intent(HomeActivity.this,DemographyActivity.class);
                    i.putExtra("key",options);
                    HomeActivity.this.startActivity(i);
                }
                else {
                    updateQuestionNo(mQuestionNoValue);
                    updateQuestion();
                }
            }
        });
        mChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options+=mChoice1.getText()+",";
                mQuestionNoValue++;
                if(mQuestionNoValue==5)
                {
                    Intent i =new Intent(HomeActivity.this,DemographyActivity.class);
                    i.putExtra("key",options);
                    HomeActivity.this.startActivity(i);

                }
                else {
                    updateQuestionNo(mQuestionNoValue);
                    updateQuestion();
                }
            }
        });
        mChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options+=mChoice1.getText()+",";
                mQuestionNoValue++;
                if(mQuestionNoValue==5)
                {
                    Intent i =new Intent(HomeActivity.this,DemographyActivity.class);
                    i.putExtra("key",options);
                    HomeActivity.this.startActivity(i);

                }
                else {
                    updateQuestionNo(mQuestionNoValue);
                    updateQuestion();
                }
            }
        });

    }
    private void updateQuestion() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionValue));
        mChoice1.setText(mQuestionLibrary.getChoice1(mQuestionValue));
        mChoice2.setText(mQuestionLibrary.getChoice2(mQuestionValue));
        mChoice3.setText(mQuestionLibrary.getChoice3(mQuestionValue));
        mChoice4.setText(mQuestionLibrary.getChoice4(mQuestionValue));
        mQuestionValue++;

    }
    private void updateQuestionNo(int number)
    {
        mQuestionNoView.setText(""+mQuestionNoValue);
    }



}



