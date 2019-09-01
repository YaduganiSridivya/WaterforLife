package com.example.surveyapp;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeGetServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void makePostServiceCall(String reqUrl,String[] optionsArray,String userId) {
        String response = null;
        try {

            URL object=new URL(reqUrl);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            //con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            // 2. build JSON object
            JSONObject parent = buidJsonObject(userId,optionsArray);


           /* OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
            wr.flush();*/
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(parent.toString());
            Log.i(NavigationActivity.class.toString(),parent.toString());
            writer.flush();
            writer.close();
            os.close();

//display what returns the POST request

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                // System.out.println("" + sb.toString());
                Log.e(TAG,"msg returned from if post request"+ sb.toString());
            } else {
                //System.out.println(con.getResponseMessage());
                Log.e(TAG,"msg returned from else post request"+ con.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

    }
    private  JSONObject buidJsonObject(String userId,String[] optionsArray) throws JSONException {
        //creating a json object of the format corresponding to the backend

        /*JSONObject cred   = new JSONObject();
        JSONObject auth   = new JSONObject();
        JSONObject parent = new JSONObject();

        cred.put("username","adm");
        cred.put("password", "pwd");

        auth.put("tenantName", "adm");
        auth.put("passwordCredentials", cred);

        parent.put("auth", auth);*/


       int q=1;
       //json main object
        JSONObject obj = new JSONObject();
        obj.put("uid",userId);

        //json array to be added to te main object
        JSONArray valuesArray = new JSONArray();

        //sub objects to be added to the array
        JSONObject first  = new JSONObject();
        first.put("qid",q);
        first.put("selectedOption",optionsArray[0]);
        JSONObject second  = new JSONObject();
        second.put("qid",q);
        second.put("selectedOption",optionsArray[1]);
        JSONObject third  = new JSONObject();
        third.put("qid",q);
        third.put("selectedOption",optionsArray[2]);
        JSONObject fourth  = new JSONObject();
        fourth.put("qid",q);
        fourth.put("selectedOption",optionsArray[3]);

        //adding the objects to the array
        valuesArray.put(0,first);
        valuesArray.put(1,second);
        valuesArray.put(2,third);
        valuesArray.put(3,fourth);

        //adding the array to the main object
        obj.put("selectedOptions",valuesArray);



        return obj;
    }


}
