package com.zone.caritukang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlphoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olphone);

        Bundle extras = getIntent().getExtras();
        String id  = extras.getString("id");
        String phone = extras.getString("phone");


        Button btnUbah = (Button)findViewById(R.id.btnSubmit);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();
                String id  = extras.getString("id");
                String phone = extras.getString("phone");

                EditText edLama = (EditText) findViewById(R.id.edLama);
                EditText edReLama = (EditText) findViewById(R.id.edReLama);


                String lama = edLama.getText().toString();
                String relama = edReLama.getText().toString();

                if(lama==relama){

                    if(lama==phone){

                        Intent x = new Intent(OlphoneActivity.this, ChangePhone.class);
                        x.putExtra("phone",lama);
                        x.putExtra("id",id);
                        startActivity(x);


                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(), "Nomor Ponsel Yang dimasukkan Berbeda Dengan Nomor Lama", Toast.LENGTH_SHORT).show();

                    }



                }else{

                    Toast.makeText(getApplicationContext(), "Nomor Ponsel Yang dimasukkan Berbeda", Toast.LENGTH_SHORT).show();

                }







            }
        });


    }
}
