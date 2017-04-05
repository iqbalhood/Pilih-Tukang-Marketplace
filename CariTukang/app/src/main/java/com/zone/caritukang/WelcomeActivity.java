package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digits.sdk.android.Digits;

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

import static android.R.attr.phoneNumber;
import static com.zone.caritukang.DataURL.ROOT_URL;


public class WelcomeActivity extends AppCompatActivity {


    String nama = "";
    String phone = "";
    String detail = "";
    String foto = "";
    String foto_ktp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        Button buttonCari = (Button)findViewById(R.id.buttonCari);
        Button buttonPasang = (Button)findViewById(R.id.buttonPasang);



        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(WelcomeActivity.this, SearchActivity.class);
                startActivity(k);
            }
        });

        buttonPasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences   = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this);
                boolean isAuthenticated = preferences.getBoolean("ALREADY_AUTHENTICATED", false);
                String  phoneNumber = preferences.getString("PHONE", null);

                Toast.makeText(WelcomeActivity.this, "NOMOR HP " + phoneNumber, Toast.LENGTH_LONG).show();

                if(isAuthenticated && !(phoneNumber.isEmpty())){
                    new JSONAsyncTask().execute(ROOT_URL+"/carijasa/tukang.php?phone="+phoneNumber);
                }else{

                    Intent k = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(k);

                }




            }
        });



    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(WelcomeActivity.this);
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

            if(nama.isEmpty()){


                SharedPreferences preferences   = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this);
                String  phoneNumber = preferences.getString("PHONE", null);

                System.out.println("Kosong Barang Tu");
                Intent y = new Intent(WelcomeActivity.this, DataUserActivity.class);
                y.putExtra("phone",phoneNumber);
                startActivity(y);






            }else{


                SharedPreferences preferences   = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this);
                String  phoneNumber = preferences.getString("PHONE", null);


                Intent x = new Intent(WelcomeActivity.this, PengaturanActivity.class);
                x.putExtra("phone",phoneNumber);
                x.putExtra("nama",nama);
                x.putExtra("detail",detail);
                x.putExtra("foto",foto);
                x.putExtra("foto_ktp",foto_ktp);
                startActivity(x);
            }




            if(result == false)
                Toast.makeText(WelcomeActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }

}
