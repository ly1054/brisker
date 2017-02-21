package com.ly1054.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ly1054.birsker.R;
import com.ly1054.birsker.annotation.Bind;
import com.ly1054.birsker.annotation.ContentView;
import com.ly1054.birsker.annotation.IntentName;
import com.ly1054.birsker.annotation.Lib_Bind;
import com.ly1054.birsker.annotation.Import_R;
import com.ly1054.birsker.annotation.Lib_OnClick;
import com.ly1054.birsker.annotation.OnClick;
import com.ly1054.brisker.Brisker;

@Import_R("com.ly1054.birsker.R")
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text1)
    TextView mTextView;

    @Lib_Bind("R.id.text1")
    TextView mTextView1;

    @IntentName("hello")
    String hello;

    @IntentName("int")
    int i;

    @IntentName("char")
    char c;

    @IntentName("bool")
    boolean b;

    @IntentName("long")
    long l;

    @IntentName("double")
    double d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Brisker.inject(this);
        mTextView1.setText("hello whtaddd+++++中国");
    }

//    @Lib_OnClick({"R.id.text1"})
//    public void text(View v){
//    }
//
    @OnClick(R.id.text1)
    public void text1(View v){
    }

    @OnClick({R.id.text1,R.id.text})
    public void onClick(View v){

    }
}
