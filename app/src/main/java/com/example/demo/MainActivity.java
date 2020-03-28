package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    int j = 0;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleProgressView circlePercentView1 = findViewById(R.id.cpv1);
        final CircleProgressView2 circlePercentView2 = findViewById(R.id.cpv2);
        final CircleProgressView circlePercentView = findViewById(R.id.cpv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i <= 100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circlePercentView1.setPercent(i+=1);
                            circlePercentView2.setPercent(j+=3);
                            circlePercentView.setPercent(k+=5);
                        }
                    });
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
