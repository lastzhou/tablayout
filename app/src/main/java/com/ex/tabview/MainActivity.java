package com.ex.tabview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       TabView tabView= findViewById(R.id.tabview);
       String [] data={"农夫山泉","怡宝","王老吉","可口可乐","哇哈哈",
               "白岁山","百事","康师傅","六个核桃"};

       tabView.fillData(data);
    }
}
