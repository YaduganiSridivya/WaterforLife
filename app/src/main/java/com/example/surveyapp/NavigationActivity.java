package com.example.surveyapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url ="https://api.androidhive.info/contacts/" ;


    Dialog myDialog;
    GoogleSignInClient mGoogleSignInClient;
    RadioGroup radioGroup;

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionNoView;
    private TextView mQuestionView;
    private RadioButton mChoice1;
    private RadioButton mChoice2;
    private RadioButton mChoice3;
    private RadioButton mChoice4;
    private Button next,prev;
    String name,email;

    private int mQuestionNoValue = 1;
    private int mQuestionValue = 0;
    public String options="";
    public String value="";
    public String optionsArray[] = new String[4];

    //VARIABLE USED TO STORE USERID PASSED FROM OAUTH ACTIVITY
    public String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


       new Questions().execute();

        //dialog intialization
        myDialog=new Dialog(this);
        //set the connection of radiogroup
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        //STORING USERID PASSED FROM OAUTH ACTIVITY
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             userId = extras.getString("key");
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(NavigationActivity.this);
        if (acct != null) {
           name = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            //nameTV.setText("Name: "+personName);
            //emailTV.setText("Email: "+personEmail);
            //idTV.setText("ID: "+personId);
            //Glide.with(this).load(personPhoto).into(photoIV);
        }





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

       // updateQuestion();


        mChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice1.getText().toString();

                //options+=mChoice1.getText()+",";
                //Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();


            }
        });
        mChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice2.getText().toString();
               // options+=mChoice2.getText()+",";
               // mQuestionNoValue++;
               // Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });
        mChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice3.getText().toString();
               // options+=mChoice3.getText()+",";
                //Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });
        mChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=mChoice4.getText().toString();
                //options+=mChoice4.getText()+",";
              //  mQuestionNoValue++;
                //Toast.makeText(NavigationActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();

            }
        });

//Radiogroup code for radiobuttons



}


    private void updateQuestion()
    {

        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionValue));
        mChoice1.setText(mQuestionLibrary.getChoice1(mQuestionValue));
        mChoice2.setText(mQuestionLibrary.getChoice2(mQuestionValue));
        mChoice3.setText(mQuestionLibrary.getChoice3(mQuestionValue));
        mChoice4.setText(mQuestionLibrary.getChoice4(mQuestionValue));
        value=" ";
        mChoice1.setChecked(false);
        mChoice2.setChecked(false);
        mChoice3.setChecked(false);
        mChoice4.setChecked(false);


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

        else if(id == R.id.action_demography)
        {
            startActivity(new Intent(NavigationActivity.this,DemoComparsion.class));
        }
        else if(id==R.id.residents)
        {
            //ShowPopup();
            TextView exit;
            TextView name,email;
            myDialog.setContentView(R.layout.custompopup);
            exit = (TextView) myDialog.findViewById(R.id.exit);
           name = (TextView) myDialog.findViewById(R.id.namecp);
            email = (TextView) myDialog.findViewById(R.id.emailcp);

            name.setText(" "+name);
            email.setText("Email: "+email);
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

        }
        else if (id == R.id.nav_share)
        {
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


if(value!=" ")
{
    options = options + value + ",";
    mQuestionNoValue++;
    mQuestionValue++;
    if (mQuestionNoValue == 5) {
        optionsArray = options.split(",");
        Intent i = new Intent(NavigationActivity.this, DemographyActivity.class);
        i.putExtra("key", options);
        NavigationActivity.this.startActivity(i);

    } else {
        updateQuestionNo(mQuestionNoValue);
        updateQuestion();
    }
}
else
{
    Toast.makeText(this,"select an option",Toast.LENGTH_SHORT).show();
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

    /**
     * Async task class to get json by making HTTP call
     */
    private class Questions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NavigationActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeGetServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //JSONArray questions = jsonObj.getJSONArray("questions");
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    //for (int i = 1; i <= questions.length(); i++) {
                    for (int i = 0; i <= 3; i++) {
                        // JSONObject c = questions.getJSONObject(i);
                        JSONObject c = contacts.getJSONObject(i);

                        //int qid = c.getInt("qid");
                        // String question = c.getString("question");
                        String question = c.getString("address");
                        /*
                        JSONArray options =new JSONArray("options");
                        for(int j=1;j<=options.length();j++)
                        {
                            mQuestionLibrary.mChoices[i][j]= options.getString(j);
                        }*/
                        mQuestionLibrary.mChoices[i][0]=c.getString("id");
                        mQuestionLibrary.mChoices[i][1]=c.getString("name");
                        mQuestionLibrary.mChoices[i][2]=c.getString("email");
                        mQuestionLibrary.mChoices[i][3]=c.getString("gender");
                        mQuestionLibrary.mQuestions[i]=question;

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            //post call to update the answers
            sh.makePostServiceCall(url,optionsArray,userId);

            //get call to get users answer
            // HERE TWO SUBMISSION STRINGS MUST BE COLLECTED FROM THE BACKEND AND WRITE A LOGIC SUCH THAT IF THE USER HAS ONLY ONE SUBMISSION
            //ONLY ONE SUBMISSION AFTER THE DEMOGRAPHIC ACTIVITY DEMOCOMPARISION ACTIVITY MUST NOT BE DISPLAYED ELSE MUST BE DISPLAYED.
            String jsonStr1 = sh.makeGetServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr1);

            if (jsonStr1 != null) {
                try {

                    //here write code according to the way we want to parse the json data
                    JSONObject jsonObj = new JSONObject(jsonStr1);
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    for (int i = 0; i <= 3; i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String question = c.getString("address");
                        mQuestionLibrary.mChoices[i][0]=c.getString("id");
                        mQuestionLibrary.mChoices[i][1]=c.getString("name");
                        mQuestionLibrary.mChoices[i][2]=c.getString("email");
                        mQuestionLibrary.mChoices[i][3]=c.getString("gender");
                        mQuestionLibrary.mQuestions[i]=question;

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }

    }
}

