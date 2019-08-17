package com.example.surveyapp;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Dialog myDialog;
    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView nameTV;
    TextView emailTV;
    Radiobutton ans;
    RadioGroup radioGroup;
    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionNoView;
    private TextView mQuestionView;
    private RadioButton mChoice1;
    private RadioButton mChoice2;
    private RadioButton mChoice3;
    private RadioButton mChoice4;
    private Button next,prev;


    private int mQuestionNoValue = 1;
    private int mQuestionValue = 0;
    String options=" ";
    String value=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //dialog intialization
        myDialog=new Dialog(this);
        //set the connection of radiogroup
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);






        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //questions logic starts from here
        mQuestionNoView = (TextView)findViewById(R.id.number);
        mQuestionView = (TextView)findViewById(R.id.Question);
        mChoice1 = (RadioButton) findViewById(R.id.choiceb1);
        mChoice2 = (RadioButton)findViewById(R.id.choiceb2);
        mChoice3 = (RadioButton) findViewById(R.id.choiceb3);
        mChoice4 = (RadioButton) findViewById(R.id.choiceb4);
       next = (Button)findViewById(R.id.next);
       prev = (Button)findViewById(R.id.prev);

        updateQuestion();

        mChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice1.getText().toString();

                //options+=mChoice1.getText()+",";
                Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();


            }
        });
        mChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice2.getText().toString();
               // options+=mChoice2.getText()+",";
               // mQuestionNoValue++;
                Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });
        mChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice3.getText().toString();
               // options+=mChoice3.getText()+",";
                Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });
        mChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice4.getText().toString();
                //options+=mChoice4.getText()+",";
              //  mQuestionNoValue++;
                Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });

//Radiogroup code for radiobuttons



}


    private void updateQuestion() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionValue));
        mChoice1.setText(mQuestionLibrary.getChoice1(mQuestionValue));
        mChoice2.setText(mQuestionLibrary.getChoice2(mQuestionValue));
        mChoice3.setText(mQuestionLibrary.getChoice3(mQuestionValue));
        mChoice4.setText(mQuestionLibrary.getChoice4(mQuestionValue));


    }
    private void updateQuestionNo(int number)
    {
        mQuestionNoView.setText(""+mQuestionNoValue);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //connect the xml page with java  using id's


            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            signOut();
        }
        else if(id == R.id.action_login)
        {
            startActivity(new Intent(NavigationActivity.this,OAuthActivity.class));
        }
        else if(id==R.id.residents)
        {
            //ShowPopup();
            TextView exit;
            myDialog.setContentView(R.layout.custompopup);
            exit = (TextView) myDialog.findViewById(R.id.exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            // Handle the camera action
            signOut();
        }  else if (id == R.id.nav_send) {
            Intent i=new Intent(Intent.ACTION_VIEW);
            //inner parameter is uri pattern
            //address of the app
            i.setData(Uri.parse("market://details?id=com.tutorial.personal.androidstudiopro"));

            if(!isActivityStarted(i))
            {
                i.setData(Uri.parse("https://play.google.com"));
            }

        } else if (id == R.id.nav_share) {
            Intent i=new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_SUBJECT,"android studio pro");
            i.putExtra(Intent.EXTRA_TEXT,"https://play.google.com");

            i.setType("text/plain");
            startActivity(i);


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NavigationActivity.this, OAuthActivity.class));
                        finish();
                    }
                });
    }
    private boolean isActivityStarted(Intent aIntent)
    {
        try {
            startActivity(aIntent);
            return true;
        }
        catch(ActivityNotFoundException e)
        {
            return false;
        }
    }

    public void next(View view) {

        //int selectedId = radioGroup.getCheckedRadioButtonId();
       //ans= (Radiobutton) findViewById(selectedId);

           options=options+value+",";
            mQuestionNoValue++;
            mQuestionValue++;
            if (mQuestionNoValue == 5) {
                Intent i = new Intent(NavigationActivity.this, DemographyActivity.class);
                i.putExtra("key", options);
                NavigationActivity.this.startActivity(i);

            } else {
                updateQuestionNo(mQuestionNoValue);
                updateQuestion();
            }

        }




    public void prev(View view) {
        mQuestionNoValue--;
        mQuestionValue--;
        if(mQuestionNoValue==0)
        {
            Toast.makeText(NavigationActivity.this, "This is the first question ", Toast.LENGTH_SHORT).show();
            mQuestionNoValue++;
            mQuestionValue++;
        }

        else {
            updateQuestionNo(mQuestionNoValue);
            updateQuestion();
        }

    }

   /* public void ShowPopup(View v)
    {
        TextView exit;
        myDialog.setContentView(R.layout.custompopup);
        exit = (TextView) myDialog.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }*/
}

