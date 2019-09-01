package com.example.surveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.content.Intent;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OAuthActivity extends AppCompatActivity  {
    private static String url ="https://api.androidhive.info/contacts/" ;
    public String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView t;
    public String optionsArray[] = new String[4];
   // private FirebaseAuth firebaseAuth;
    //int[] nameList={R.drawable.wl5,R.drawable.icon,R.drawable.wl5,R.drawable.icon};

    private int index=0;

    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    //A VARIABLE IS DECLARED HERE TO STORE THE USERID THAT IS SENT AS A REPSOPNSE FROM THE BACKEND ON CLICKING THE SIGN IN BUTTON
    //USERID VARIABLE MUST BE INITIALIZED BY THE RESPONSE OF THE POST CALL
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);

     /*   final ImageSwitcher imageSwitcher=findViewById(R.id.imageswitcher);

        if(imageSwitcher!=null)
        {
            imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView imageView=new ImageView(getApplicationContext());

                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                    return imageView;
                }
            });

            imageSwitcher.setImageResource(nameList[index]);

            Animation in= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
            imageSwitcher.setInAnimation(in);

            Animation out= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
            imageSwitcher.setInAnimation(out);

        }*/

      /*  Button b=findViewById(R.id.buttonswitcher);

        if(b!=null)
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index=(++index<nameList.length)?index:0;

                    if(imageSwitcher!=null)
                        imageSwitcher.setImageResource(nameList[index]);
                }
            });
        }
*/
        t=(TextView)findViewById(R.id.query);



        //Initializing Views
        signInButton = findViewById(R.id.sign_in);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

            //PASSING THE USERID VALUE TO THE NAVIGATION ACTIVITY
            Intent i= new Intent(OAuthActivity.this,NavigationActivity.class);
            i.putExtra("key",userId);
            startActivity(i);

            startActivity(new Intent(OAuthActivity.this,NavigationActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(OAuthActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            startActivity(new Intent(OAuthActivity.this,NavigationActivity.class));
        }
        super.onStart();
    }


    private class Questions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OAuthActivity.this);
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
                   /*     mQuestionLibrary.mChoices[i][0]=c.getString("id");
                        mQuestionLibrary.mChoices[i][1]=c.getString("name");
                        mQuestionLibrary.mChoices[i][2]=c.getString("email");
                        mQuestionLibrary.mChoices[i][3]=c.getString("gender");
                        mQuestionLibrary.mQuestions[i]=question;*/

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
                     /*   mQuestionLibrary.mChoices[i][0]=c.getString("id");
                        mQuestionLibrary.mChoices[i][1]=c.getString("name");
                        mQuestionLibrary.mChoices[i][2]=c.getString("email");
                        mQuestionLibrary.mChoices[i][3]=c.getString("gender");
                        mQuestionLibrary.mQuestions[i]=question;*/

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