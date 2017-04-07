package com.zone.caritukang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
import java.util.List;

import static com.zone.caritukang.DataURL.ROOT_URL;

public class DetailSwipe extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    private final static int NUM_PAGES = 4;
    private List<ImageView> dots;

    String gambar1 = "";
    String gambar2 = "";
    String gambar3 = "";
    String gambar4 = "";
    String gambar5 = "";

    String id = "";

    String nama = "";
    String nama_jasa = "";
    String phone = "";
    String detail = "";
    String deskripsi = "";
    String foto = "";
    String foto_ktp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_swiper);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);


        // Fungsi Cek data Apakah User telah ada
        Bundle extras = getIntent().getExtras();
        gambar1 = extras.getString("foto");
        gambar2 = extras.getString("foto");
        gambar3 = extras.getString("foto");
        gambar4 = extras.getString("foto");
        id = extras.getString("id");
        phone = id;

        Toast.makeText(getApplicationContext(), "foto " + gambar1, Toast.LENGTH_SHORT).show();


        new JSONAsyncTask().execute(ROOT_URL + "/carijasa/detail_tukang.php?phone=" + id);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        addDots();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();
                String  phone = extras.getString("id");

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(DetailSwipe.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);

            }
        });


    }



    //jika back button di klik
    public boolean onOptionsItemSelected(MenuItem item) {
         finish();

        return true;
    }

    //Asyc Task Ambil Data Detail User
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DetailSwipe.this);
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
                        id        = (object.getString("id"));
                        nama      = (object.getString("nama"));
                        nama_jasa = (object.getString("nama_jasa"));
                        detail    = (object.getString("detail"));
                        deskripsi = (object.getString("deskripsi"));
                        foto      = (object.getString("foto"));
                        foto_ktp  = (object.getString("foto_ktp"));

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

            TextView txtDeskripsi = (TextView)findViewById(R.id.deskripsiJasa);
            TextView detailJasa = (TextView)findViewById(R.id.detailJasa);
            TextView namaJasa = (TextView)findViewById(R.id.namaJasa);
            ImageView imgJasa = (ImageView)findViewById(R.id.imgJasa);

            namaJasa.setText(nama+"\n"+nama_jasa);
            txtDeskripsi.setText(deskripsi);
            detailJasa.setText(detail);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef1 = storage.getReference(foto);

            Glide.with(DetailSwipe.this).using(new FirebaseImageLoader()).load(storageRef1).into(imgJasa);



            //Toast.makeText(DetailSwipe.this, "Deksripsi " +deskripsi, Toast.LENGTH_LONG).show();

            if(result == false)
                Toast.makeText(DetailSwipe.this, "Unable to fetch data from server", Toast.LENGTH_LONG).show();


        }
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail_swipe, menu);
//        return true;
//    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String gambar) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("gambar", gambar);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_activity_swipe, container, false);
            ImageView gambarFragment = (ImageView)rootView.findViewById(R.id.gambarFragment);

            String gambar            = getArguments().getString("gambar");
            FirebaseStorage storage  = FirebaseStorage.getInstance();

            System.out.println("GAMBARNYA "+ gambar);

            StorageReference storageRef1 = storage.getReference(gambar);
            Glide.with(this).using(new FirebaseImageLoader()).load(storageRef1).into(gambarFragment);
           // Glide.with(this).load("http://static.republika.co.id/uploads/images/inpicture_slide/proses-pengelasan-ilustrasi-_140908133122-690.jpg").into(gambarFragment);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            String gbr = gambar1;

            if(position == 0){
                gbr = gambar1;
            }else if(position == 1){
                gbr = gambar2;
            }else if(position == 2){
                gbr = gambar3;
            }else if(position == 3){
                gbr = gambar4;
            }else {
                gbr = gambar1;
            }

            return PlaceholderFragment.newInstance((position + 1) , gbr);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }



    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout)findViewById(R.id.layoutDots);

        for(int i = 0; i < NUM_PAGES; i++) {
            ImageView dot = new ImageView(this);
            if(i==0){
                dot.setImageDrawable(getResources().getDrawable(R.drawable.round_view_white));
            }else{
                dot.setImageDrawable(getResources().getDrawable(R.drawable.round_view_grey));
            }


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10, 10, 10, 10);
            dotsLayout.addView(dot, params);

            dots.add(dot);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for(int i = 0; i < NUM_PAGES; i++) {
            int drawableId = (i==idx)?(R.drawable.round_view_white):(R.drawable.round_view_grey);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);


        }
    }

}
