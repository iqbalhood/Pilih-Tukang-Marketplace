package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

import java.io.IOException;

import static com.zone.caritukang.DataURL.ROOT_URL;

public class GaleriProdukActivity extends AppCompatActivity implements View.OnClickListener {

    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int PICK_IMAGE_REQUEST2 = 234;
    private static final int PICK_IMAGE_REQUEST3 = 234;
    private static final int PICK_IMAGE_REQUEST4 = 234;
    private static final int PICK_IMAGE_REQUEST5 = 234;


    //Buttons
    private Button buttonChoose;
    private Button buttonChoose2;
    private Button buttonChoose3;
    private Button buttonChoose4;
    private Button buttonChoose5;
    private Button btnSubmit;

    //ImageView
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;

    //a Uri object to store file path
    private Uri filePath;
    private Uri filePath2;
    private Uri filePath3;
    private Uri filePath4;
    private Uri filePath5;

    //firebase storage reference
    private StorageReference storageReference;

    String stat = "";

    String stat1 = "1";
    String stat2 = "2";
    String stat3 = "3";
    String stat4 = "4";
    String stat5 = "5";


    String URL1 =   "";
    String URL2 =   "";
    String URL3 =   "";
    String URL4 =   "";
    String URL5 =   "";

    String HP  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri_produk);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fungsi Cek data Apakah User telah ada
        Bundle extras = getIntent().getExtras();
        HP = extras.getString("phone");
        String g1 = extras.getString("foto1");
        String g2 = extras.getString("foto2");
        String g3 = extras.getString("foto3");
        String g4 = extras.getString("foto4");
        String g5 = extras.getString("foto5");

        System.out.println(" G1 "+ g1);


        //Setup
        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonChoose2 = (Button) findViewById(R.id.buttonChoose2);
        buttonChoose3 = (Button) findViewById(R.id.buttonChoose3);
        buttonChoose4 = (Button) findViewById(R.id.buttonChoose4);
        buttonChoose5 = (Button) findViewById(R.id.buttonChoose5);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);

        FirebaseStorage storage = FirebaseStorage.getInstance();


        if(!empty(g1)){
            StorageReference storageRef1 = storage.getReference(g1);
            URL1 = g1 ;
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef1).into(imageView);
        }else{
            imageView.setVisibility(View.GONE);
        }

        if(!empty(g2)){
            StorageReference storageRef2 = storage.getReference(g2);
            URL2 = g2 ;
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef2).into(imageView2);
        }else{
            imageView2.setVisibility(View.GONE);
        }


        if(!empty(g3)){
            StorageReference storageRef3 = storage.getReference(g3);
            URL3 = g3 ;
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef3).into(imageView3);
        }else{
            imageView3.setVisibility(View.GONE);
        }


        if(!empty(g4)){
            StorageReference storageRef4 = storage.getReference(g4);
            URL4 = g4 ;
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef4).into(imageView4);
        }else{
            imageView4.setVisibility(View.GONE);
        }


        if(!empty(g5)){
            StorageReference storageRef5 = storage.getReference(g5);
            URL5 = g5 ;
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef5).into(imageView5);
        }else{
            imageView5.setVisibility(View.GONE);
        }


        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonChoose2.setOnClickListener(this);
        buttonChoose3.setOnClickListener(this);
        buttonChoose4.setOnClickListener(this);
        buttonChoose5.setOnClickListener(this);
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

    //method to show file chooser
    private void showFileChooser3() {

        stat = stat3;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST3);

        imageView3.setVisibility(View.VISIBLE);


    }

    //method to show file chooser
    private void showFileChooser4() {

        stat = stat4;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST4);

        imageView4.setVisibility(View.VISIBLE);


    }

    //method to show file chooser
    private void showFileChooser5() {

        stat = stat5;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST5);

        imageView5.setVisibility(View.VISIBLE);


    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Toast.makeText(getApplicationContext(), "FUPLOAD DILAKUKAN " + stat, Toast.LENGTH_LONG).show();

        if ( stat == stat1){
            Toast.makeText(getApplicationContext(), "WIDIW " + stat, Toast.LENGTH_LONG).show();
            if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


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

        if ( stat == stat3){
            Toast.makeText(getApplicationContext(), "WADAW " + stat, Toast.LENGTH_LONG).show();
            if (requestCode == PICK_IMAGE_REQUEST3 && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath3 = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath3);
                    imageView3.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            uploadFile3();


        }


        if ( stat == stat4){
            Toast.makeText(getApplicationContext(), "WADAW " + stat, Toast.LENGTH_LONG).show();
            if (requestCode == PICK_IMAGE_REQUEST4 && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath4 = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath4);
                    imageView4.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            uploadFile4();


        }


        if ( stat == stat5){
            Toast.makeText(getApplicationContext(), "WADAW " + stat, Toast.LENGTH_LONG).show();
            if (requestCode == PICK_IMAGE_REQUEST5 && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath5 = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath5);
                    imageView5.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            uploadFile5();


        }


    }

    //this method will upload the file1
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("galeri/1foto"+ts+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();


                            URL1 = String.valueOf("galeri/1foto"+ts+".jpg");

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

    //this method will upload the file2
    private void uploadFile2() {
        //if there is a file to upload
        if (filePath2 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("galeri/2foto"+ts+".jpg");
            riversRef.putFile(filePath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();



                            URL2 = String.valueOf("galeri/2foto"+ts+".jpg");


                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " File Foto Uploaded ", Toast.LENGTH_LONG).show();

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

    //this method will upload the file3
    private void uploadFile3() {
        //if there is a file to upload
        if (filePath3 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("galeri/3foto"+ts+".jpg");
            riversRef.putFile(filePath3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();



                            URL3 = String.valueOf("galeri/3foto"+ts+".jpg");


                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " File Foto Uploaded ", Toast.LENGTH_LONG).show();

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

    //this method will upload the file4
    private void uploadFile4() {
        //if there is a file to upload
        if (filePath4 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("galeri/4foto"+ts+".jpg");
            riversRef.putFile(filePath4)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();



                            URL4 = String.valueOf("galeri/4foto"+ts+".jpg");


                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " File Foto Uploaded ", Toast.LENGTH_LONG).show();

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

    //this method will upload the file5
    private void uploadFile5() {
        //if there is a file to upload
        if (filePath5 != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            Long tsLong = System.currentTimeMillis()/1000;
            final String ts = tsLong.toString();

            StorageReference riversRef = storageReference.child("galeri/5foto"+ts+".jpg");
            riversRef.putFile(filePath5)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();



                            URL5 = String.valueOf("galeri/5foto"+ts+".jpg");


                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), " File Foto Uploaded ", Toast.LENGTH_LONG).show();

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
        }else  if (view == buttonChoose3) {
            System.out.println("Button 3 di klik ");
            stat = stat3;
            showFileChooser3();
        }else  if (view == buttonChoose4) {
            System.out.println("Button 4 di klik ");
            stat = stat4;
            showFileChooser4();
        }else  if (view == buttonChoose5) {
            System.out.println("Button 5 di klik ");
            stat = stat5;
            showFileChooser5();
        }else  if (view == btnSubmit) {
            new JSONAsyncTask().execute(ROOT_URL+"/carijasa/submit_data_foto.php?phone="+HP+
                    "&foto1="+URL1+
                    "&foto2="+URL2+
                    "&foto3="+URL3+
                    "&foto4="+URL4+
                    "&foto5="+URL5
            );
        }
    }



    //Class to Sumbit Data

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GaleriProdukActivity.this);
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

            Toast.makeText(GaleriProdukActivity.this, "Proses Update Berhasil", Toast.LENGTH_LONG).show();
            finish();





            if(result == false)
                Toast.makeText(GaleriProdukActivity.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }
}
