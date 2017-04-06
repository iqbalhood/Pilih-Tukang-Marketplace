package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.zone.caritukang.adapter.JasaAdapter;
import com.zone.caritukang.setget.Jasa;

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

import static com.zone.caritukang.DataURL.ROOT_URL;

public class SearchResult extends AppCompatActivity {


    ArrayList<Jasa> jasaList;

    JasaAdapter adapter;

    ImageButton btnTopHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        jasaList = new ArrayList<Jasa>();
        new JSONAsyncTask().execute(ROOT_URL+"/carijasa/list_jasa.php");

        ListView listview = (ListView)findViewById(R.id.lv_item);
        adapter = new JasaAdapter(SearchResult.this, R.layout.lsv_item_jasa, jasaList);

        listview.setAdapter(adapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String idnya = String.valueOf(jasaList.get(position).getId());
                String namanya = String.valueOf(jasaList.get(position).getNama());
                String fotonya = String.valueOf(jasaList.get(position).getFproduk());
                String detailnya = String.valueOf(jasaList.get(position).getDetail());

                Log.v("IDnya :", idnya);

                Intent k = new Intent(SearchResult.this, DetailSwipe.class);
                k.putExtra("id", idnya );
                k.putExtra("nama", namanya );
                k.putExtra("foto", fotonya );
                k.putExtra("detail", detailnya );
                startActivity(k);

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
            dialog = new ProgressDialog(SearchResult.this);
            dialog.setMessage("Sedang Mengambil Data...");
//            dialog.setTitle("Connecting server");
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

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Jasa jasa = new Jasa();
                        jasa.setId(object.getString("id"));
                        jasa.setNama(object.getString("nama"));
                        jasa.setDetail(object.getString("detail"));
                        jasa.setFoto(object.getString("foto"));
                        jasa.setFproduk(object.getString("foto1"));
                        jasaList.add(jasa);
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
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(SearchResult.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
