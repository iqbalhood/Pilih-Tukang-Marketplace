package com.zone.caritukang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;


public class WelcomeActivity extends AppCompatActivity {


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
                Intent k = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(k);
            }
        });



    }
}
