package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digits.sdk.android.Digits;
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

import io.fabric.sdk.android.Fabric;

import static com.zone.caritukang.DataURL.ROOT_URL;



public class PengaturanActivity extends AppCompatActivity {

    String nama ="";
    String detail = "";
    String foto ="";
    String foto_ktp = "";

    String foto1 ="";
    String foto2 = "";
    String foto3 = "";
    String foto4 = "";
    String foto5 = "";



    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "CQg5yb7Mh6qIva2zrnc7J2SoY";
    private static final String TWITTER_SECRET = "VMh0vLBOPHIRMasE3pEnMOCF2T77NK5wVqs7FQvptcKp3lAy5l";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());



        setContentView(R.layout.activity_pengaturan);


        Bundle extras = getIntent().getExtras();

        String nama = extras.getString("nama");



        // Fungsi Cek data Apakah User telah ada


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(nama);

        Button profilUser = (Button)findViewById(R.id.profilUser);
        Button btnLogout = (Button)findViewById(R.id.btnLogout);
        Button Galeri = (Button)findViewById(R.id.Galeri);
        Button ubahHP = (Button)findViewById(R.id.ubahHP);



        profilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();

                String phoneNumber = extras.getString("phone");


                new JSONAsyncTask().execute(ROOT_URL+"/carijasa/tukang.php?phone="+phoneNumber);



            }
        });


        Galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();

                String phoneNumber = extras.getString("phone");


                new GaleriAsyncTask().execute(ROOT_URL+"/carijasa/galeri.php?phone="+phoneNumber);



            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PengaturanActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ALREADY_AUTHENTICATED", false /** or true */);
                editor.putString("PHONE", "");
                editor.commit();

                Digits.clearActiveSession();
                Digits.logout();
                finish();

            }
        });


        ubahHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Digits.clearActiveSession();
                Digits.logout();

                Bundle extras = getIntent().getExtras();

                String phoneNumber = extras.getString("phone");
                String id = extras.getString("id");

                Intent x = new Intent(PengaturanActivity.this, OlphoneActivity.class);
                x.putExtra("phone",phoneNumber);
                x.putExtra("id",id);
                startActivity(x);


                finish();

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
            dialog = new ProgressDialog(PengaturanActivity.this);
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

                        nama     = (object.getString("nama"));
                        detail   = (object.getString("detail"));
                        foto     = (object.getString("foto"));
                        foto_ktp = (object.getString("foto_ktp"));

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


            Bundle extras = getIntent().getExtras();

            String HP = extras.getString("phone");


            Intent x = new Intent(PengaturanActivity.this, DataUserActivity.class);
            x.putExtra("phone",HP);
            x.putExtra("nama",nama);
            x.putExtra("detail",detail);
            x.putExtra("foto",foto);
            x.putExtra("foto_ktp",foto_ktp);
            startActivity(x);




            if(result == false)
                Toast.makeText(PengaturanActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }

    class GaleriAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PengaturanActivity.this);
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

                        foto1     = (object.getString("foto1"));
                        foto2     = (object.getString("foto2"));
                        foto3     = (object.getString("foto3"));
                        foto4     = (object.getString("foto4"));
                        foto5     = (object.getString("foto5"));

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


            Bundle extras = getIntent().getExtras();

            String HP = extras.getString("phone");


            Intent x = new Intent(PengaturanActivity.this, GaleriProdukActivity.class);
            x.putExtra("phone",HP);
            x.putExtra("foto1",foto1);
            x.putExtra("foto2",foto2);
            x.putExtra("foto3",foto3);
            x.putExtra("foto4",foto4);
            x.putExtra("foto5",foto5);
            startActivity(x);




            if(result == false)
                Toast.makeText(PengaturanActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }

}
