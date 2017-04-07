package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

import static com.zone.caritukang.DataURL.ROOT_URL;


public class ChangePhone extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CQg5yb7Mh6qIva2zrnc7J2SoY";
    private static final String TWITTER_SECRET = "VMh0vLBOPHIRMasE3pEnMOCF2T77NK5wVqs7FQvptcKp3lAy5l";

    String id = "";
    String nama = "";
    String phone = "";
    String detail = "";
    String foto = "";
    String foto_ktp = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());


        setContentView(R.layout.activity_change_phone);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);

        digitsButton.setText("Masukkan No. Ponsel");

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {


                // We need an Editor object to make preference changes.
// All objects are from android.context.Context

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangePhone.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ALREADY_AUTHENTICATED", true /** or false */);
                editor.putString("PHONE", phoneNumber);

// Commit the edits!
                editor.commit();

                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();


                Bundle extras = getIntent().getExtras();

                String id = extras.getString("id");


                new JSONAsyncTask().execute(ROOT_URL+"/carijasa/ubahhp.php?id="+id+"&phone="+phoneNumber);
                phone = phoneNumber;



            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });


    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }




    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChangePhone.this);
            dialog.setMessage("Sedang Mengambil Data...");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();




                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);





                    System.out.println("jasa //------------------>> "+ data);




                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            // adapter.notifyDataSetChanged();



                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangePhone.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ID", id);
                editor.putBoolean("ALREADY_AUTHENTICATED", true /** or false */);
                editor.putString("PHONE", phone);
                editor.commit();

                Intent x = new Intent(ChangePhone.this, WelcomeActivity.class);
                x.putExtra("id",id);
                x.putExtra("phone",phone);
                startActivity(x);
                finish();




            if(result == false)
                Toast.makeText(ChangePhone.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }













}
