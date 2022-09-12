package com.example.prc;

import static com.example.prc.ExtKt.log;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class MainActivity extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}




//    private synchronized void initUI() {
//        int[] int1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        new Thread(() -> {
//            for (int i : int1) {
//                log(i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        int[] int2 = new int[]{11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
//        new Thread(() -> {
//            for (int i : int2) {
//
//                log(i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
