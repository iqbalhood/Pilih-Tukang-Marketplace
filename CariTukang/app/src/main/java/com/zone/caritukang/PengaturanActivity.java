package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import static com.zone.caritukang.DataURL.ROOT_URL;

public class PengaturanActivity extends AppCompatActivity {

    String nama ="";
    String detail = "";
    String foto ="";
    String foto_ktp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);



        // Fungsi Cek data Apakah User telah ada


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button profilUser = (Button)findViewById(R.id.profilUser);

        profilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();

                String phoneNumber = extras.getString("phone");


                new JSONAsyncTask().execute(ROOT_URL+"/carijasa/tukang.php?phone="+phoneNumber);



            }
        });



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
}
