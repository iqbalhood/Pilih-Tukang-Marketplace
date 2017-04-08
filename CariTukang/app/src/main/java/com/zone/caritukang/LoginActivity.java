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


public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CQg5yb7Mh6qIva2zrnc7J2SoY";
    private static final String TWITTER_SECRET = "VMh0vLBOPHIRMasE3pEnMOCF2T77NK5wVqs7FQvptcKp3lAy5l";

    String id = "";
    String nama = "";
    String nama_jasa = "";
    String phone = "";
    String detail = "";
    String foto = "";
    String foto_ktp = "";
    String lokasi = "";
    String kategori = "";
    String sub = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());


        setContentView(R.layout.activity_login);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);

        digitsButton.setText("Masukkan No. Ponsel");

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {


                // We need an Editor object to make preference changes.
// All objects are from android.context.Context

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ALREADY_AUTHENTICATED", true /** or false */);
                editor.putString("PHONE", phoneNumber);

// Commit the edits!
                editor.commit();

                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();


                new JSONAsyncTask().execute(ROOT_URL+"/carijasa/tukang.php?phone="+phoneNumber);
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
            dialog = new ProgressDialog(LoginActivity.this);
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




                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("jasa");


                    System.out.println("jasa //------------------>> "+ data);



                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        id          = (object.getString("id"));
                        nama        = (object.getString("nama"));
                        detail      = (object.getString("detail"));
                        foto        = (object.getString("foto"));
                        foto_ktp    = (object.getString("foto_ktp"));
                        lokasi      = (object.getString("lokasi"));
                        kategori    = (object.getString("kategori"));
                        sub         = (object.getString("sub"));
                        nama_jasa   = (object.getString("nama_jasa"));

                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            // adapter.notifyDataSetChanged();

            if(nama.isEmpty()){

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ID", id);
                editor.commit();

                System.out.println("Kosong Barang Tu");
                Intent y = new Intent(LoginActivity.this, DataUserActivity.class);
                y.putExtra("id",id);
                y.putExtra("phone",phone);
                y.putExtra("phone",phone);
                y.putExtra("id",id);
                y.putExtra("nama",nama);
                y.putExtra("detail",detail);
                y.putExtra("foto",foto);
                y.putExtra("foto_ktp",foto_ktp);
                y.putExtra("lokasi","0");
                y.putExtra("kategori","0");
                y.putExtra("sub","0");
                y.putExtra("nama_jasa",nama_jasa);
                startActivity(y);

                finish();






            }else{

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ID", id);
                editor.commit();

                Intent x = new Intent(LoginActivity.this, PengaturanActivity.class);
                x.putExtra("phone",phone);
                x.putExtra("id",id);
                x.putExtra("nama",nama);
                x.putExtra("detail",detail);
                x.putExtra("foto",foto);
                x.putExtra("foto_ktp",foto_ktp);
                x.putExtra("lokasi",lokasi);
                x.putExtra("kategori",kategori);
                x.putExtra("sub",sub);
                x.putExtra("nama_jasa",nama_jasa);
                startActivity(x);
                finish();
            }




            if(result == false)
                Toast.makeText(LoginActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }













}
