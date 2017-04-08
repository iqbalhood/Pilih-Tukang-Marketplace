package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

public class DataUserActivity extends AppCompatActivity implements View.OnClickListener {
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_IMAGE_REQUEST2 = 234;

    //Buttons
    private Button buttonChoose;
    private Button buttonChoose2;
    private Button btnSubmit;

    //ImageView
    private ImageView imageView;
    private ImageView imageView2;

    //a Uri object to store file path
    private Uri filePath;
    private Uri filePath2;

    //firebase storage reference
    private StorageReference storageReference;

    String stat = "";

    String stat1 = "1";
    String stat2 = "2";


    String URL1 =   "";
    String URL2 =   "";

    String HP  = "";

    String id = "";

    //Untuk Spinner
    ArrayList<String> worldlist;
    ArrayList<String> idlist;


    ArrayList<String> worldkotalist;
    ArrayList<String> idkotalist;

    ArrayList<String> worldsublist;
    ArrayList<String> idsublist;

    String kategori = "0";
    String namakategori = "0";

    String kota = "0";
    String namakota = "0";

    String sub = "0";
    String namasub = "0";

    String Xlokasi      = "";
    String Xkategori    = "";
    String Xsub         = "";
    String XnamaJasa    = "";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fungsi Cek data Apakah User telah ada
        Bundle extras = getIntent().getExtras();
        HP = extras.getString("phone");
        id = extras.getString("id");
        String nama = extras.getString("nama");
        String detail = extras.getString("detail");
        String g1 = extras.getString("foto");
        String g2 = extras.getString("foto_ktp");
        Xlokasi = extras.getString("lokasi");
        Xkategori = extras.getString("kategori");
        XnamaJasa = extras.getString("nama_jasa");
        Xsub = extras.getString("sub");

        System.out.println(" G1 "+ g1);


        //Setup
        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonChoose2 = (Button) findViewById(R.id.buttonChoose2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        EditText edNama = (EditText)findViewById(R.id.edNama);
        EditText edAlamat = (EditText)findViewById(R.id.edAlamat);
        EditText edDetail = (EditText)findViewById(R.id.edDetail);
        EditText edNamaJasa = (EditText)findViewById(R.id.edNamaJasa);

        edNama.setText(nama);
        edDetail.setText(detail);
        edAlamat.setText(detail);
        edNamaJasa.setText(XnamaJasa);

        if(!empty(g1)){

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef1 = storage.getReference(g1);
            StorageReference storageRef2 = storage.getReference(g2);

            URL1 = g1 ;
            URL2 = g2 ;


            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef1).into(imageView);
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef2).into(imageView2);

            System.out.println("G2 INFO "+ g2);




        }else{
            imageView.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
        }

        Toast.makeText(getApplicationContext(), "SUB"+sub, Toast.LENGTH_LONG).show();

        new SPINNERAsyncTask().execute(ROOT_URL+"/carijasa/kategori.php");
        new KOTAAsyncTask().execute(ROOT_URL+"/carijasa/kota.php");






        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonChoose2.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        //getting firebase storage reference
        storageReference = FirebaseStorage.getInstance().getReference();









    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }

    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    //method to show file chooser
    private void showFileChooser() {

        stat = stat1;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        imageView.setVisibility(View.VISIBLE);



    }



    //method to show file chooser
    private void showFileChooser2() {

        stat = stat2;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST2);

        imageView2.setVisibility(View.VISIBLE);


    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Toast.makeText(getApplicationContext(), "FUPLOAD DILAKUKAN " + stat, Toast.LENGTH_LONG).show();

        if ( stat == stat1){
            Toast.makeText(getApplicationContext(), "WIDIW " + stat, Toast.LENGTH_LONG).show();
            if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {



            //uploadFile();

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            }

            uploadFile();

        }

        if ( stat == stat2){
            Toast.makeText(getApplicationContext(), "WADAW " + stat, Toast.LENGTH_LONG).show();
            if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath2 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                imageView2.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
         }

            uploadFile2();


        }
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

             StorageReference riversRef = storageReference.child("images/pic"+ts+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();


                            URL1 = String.valueOf("images/pic"+ts+".jpg");

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Foto Uploaded", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


    //this method will upload the file
    private void uploadFile2() {
        //if there is a file to upload
        if (filePath2 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("ktp/ktp"+ts+".jpg");
            riversRef.putFile(filePath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();



                            URL2 = String.valueOf("ktp/ktp"+ts+".jpg");


                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " File KTP Uploaded ", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }




    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            stat = stat1;
            showFileChooser();
        }else  if (view == buttonChoose2) {
            System.out.println("Button 2 di klik ");
            stat = stat2;
            showFileChooser2();
        }
        else  if (view == btnSubmit) {


            if(URL1.isEmpty()&&URL2.isEmpty()){

                Toast.makeText(this, "KOSONG CUYYY ", Toast.LENGTH_LONG);

            }else {


                Bundle extras = getIntent().getExtras();

                String phone = extras.getString("phone");

                System.out.println(" PHONE USERNYA " + HP);


                EditText edNama = (EditText) findViewById(R.id.edNama);
                EditText edAlamat = (EditText) findViewById(R.id.edAlamat);
                EditText edDetail = (EditText) findViewById(R.id.edDetail);
                EditText edNamaJasa = (EditText) findViewById(R.id.edNamaJasa);


                new JSONAsyncTask().execute(ROOT_URL + "/carijasa/submit_data_tukang.php?phone=" + HP +
                        "&nama=" + edNama.getText().toString() +
                        "&nama_jasa=" + edNamaJasa.getText().toString() +
                        "&alamat=" + edAlamat.getText().toString() +
                        "&detail=" + edDetail.getText().toString()+
                        "&lokasi=" + kota +
                        "&kategori=" + kategori+
                        "&sub=" + sub +
                        "&foto=" + URL1 +
                        "&foto_ktp=" + URL2);


               // Toast.makeText(this, "kategroir "+kategori +"kota "+kota+" SUB "+sub, Toast.LENGTH_LONG).show();


            }






        }


    }

    //Class to Sumbit Data

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DataUserActivity.this);
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




//                    JSONObject jsono = new JSONObject(data);


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

            Toast.makeText(DataUserActivity.this, "Proses Update Berhasil", Toast.LENGTH_LONG).show();
            finish();

            if(result == false)
                Toast.makeText(DataUserActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }




    ///SPINNER FUNCTION

    class SPINNERAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DataUserActivity.this);
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

                worldlist = new ArrayList<String>();
                idlist = new ArrayList<String>();


                worldlist.add("ALL");
                idlist.add("0");


                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);




                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("kategori");



                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        idlist.add(object.getString("id"));
                        worldlist.add(object.getString("nama"));
                        //idlist.add(object.getString("nama"));

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

            kategori = idlist.get(0);
            namakategori = worldlist.get(0);
            Spinner mySpinner = (Spinner) findViewById(R.id.kategori_spinner);


            // Spinner adapter
            mySpinner
                    .setAdapter(new ArrayAdapter<String>(DataUserActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));



            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            System.out.println("ID KATEGORI"+idlist.get(position));

                            kategori = idlist.get(position);
                            namakategori = worldlist.get(position);


                            Spinner subSpinner = (Spinner) findViewById(R.id.sub_spinner);
                            TextView txtSUB = (TextView)findViewById(R.id.txtSUB);



                            if(kategori.equals("0")){
                                subSpinner.setVisibility(View.GONE);
                                txtSUB.setVisibility(View.GONE);
                                Toast.makeText(DataUserActivity.this, "MAENKAN"+ kategori, Toast.LENGTH_LONG).show();
                            }else{
                                subSpinner.setVisibility(View.VISIBLE);
                                txtSUB.setVisibility(View.VISIBLE);
                                Toast.makeText(DataUserActivity.this, "MAENKAN"+ kategori, Toast.LENGTH_LONG).show();
                                new DataUserActivity.SUBAsyncTask().execute(ROOT_URL+"/carijasa/sub.php?id="+kategori);

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });



            if(result == false)
                Toast.makeText(DataUserActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }


    class KOTAAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DataUserActivity.this);
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

                worldkotalist = new ArrayList<String>();
                idkotalist = new ArrayList<String>();


//                worldkotalist.add("ALL");
//                idkotalist.add("0");


                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);




                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("kota");



                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        idkotalist.add(object.getString("id"));
                        worldkotalist.add(object.getString("nama"));
                        //idlist.add(object.getString("nama"));

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

            kota = idkotalist.get(0);
            namakota = worldkotalist.get(0);
            Spinner mySpinner = (Spinner) findViewById(R.id.kota_spinner);


            // Spinner adapter
            mySpinner
                    .setAdapter(new ArrayAdapter<String>(DataUserActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldkotalist));



            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            System.out.println("ID KATEGORI"+idkotalist.get(position));

                            kota = idkotalist.get(position);
                            namakota = worldkotalist.get(position);





                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });



            if(result == false)
                Toast.makeText(DataUserActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }





    class SUBAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DataUserActivity.this);
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

                worldsublist = new ArrayList<String>();
                idsublist = new ArrayList<String>();


//                worldsublist.add("ALL");
//                idsublist.add("0");


                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);




                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("sub");



                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        idsublist.add(object.getString("id"));
                        worldsublist.add(object.getString("nama"));
                        //idlist.add(object.getString("nama"));

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

            if(idsublist.isEmpty()){
                Toast.makeText(DataUserActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                Spinner mySpinner = (Spinner) findViewById(R.id.sub_spinner);
                mySpinner.setVisibility(View.GONE);
            }else{

                sub = idsublist.get(0);
                namasub = worldsublist.get(0);
                Spinner mySpinner = (Spinner) findViewById(R.id.sub_spinner);

                // Spinner adapter
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(DataUserActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                worldsublist));



                // Spinner on item click listener
                mySpinner
                        .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int position, long arg3) {
                                // TODO Auto-generated method stub
                                // Locate the textviews in activity_main.xml
                                System.out.println("ID KATEGORI"+idsublist.get(position));

                                sub = idsublist.get(position);
                                namasub = worldsublist.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }
                        });

                mySpinner.setVisibility(View.VISIBLE);

            }





            if(result == false)
                Toast.makeText(DataUserActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

















}
