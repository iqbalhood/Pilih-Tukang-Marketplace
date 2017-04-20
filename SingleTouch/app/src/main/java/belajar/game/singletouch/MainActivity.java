package belajar.game.singletouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAngka  ;
    Button btnJalur  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAngka = (Button)findViewById(R.id.btnAngka);
        btnJalur = (Button)findViewById(R.id.btnJalur);


        btnAngka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent x = new Intent(MainActivity.this, SingleTouchActivity.class);
                startActivity(x);

            }
        });

        btnJalur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent x = new Intent(MainActivity.this, JalurActivity.class);
                startActivity(x);

            }
        });




    }
}
