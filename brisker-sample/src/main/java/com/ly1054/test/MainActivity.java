package com.ly1054.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.ly1054.birsker.R;
import com.ly1054.brisker.annotation.Bind;
import com.ly1054.brisker.annotation.ContentView;
import com.ly1054.brisker.annotation.IntentName;
import com.ly1054.brisker.annotation.Lib_Bind;
import com.ly1054.brisker.annotation.Import_R;
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
        mTextView1.setText("come on,baby!");
    }

}
