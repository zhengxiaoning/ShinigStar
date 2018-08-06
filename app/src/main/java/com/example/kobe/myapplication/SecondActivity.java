package com.example.kobe.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
private Button button;
private String a;
private String b;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
         a=getIntent().getStringExtra("a");
         b=getIntent().getStringExtra("b");
        button=findViewById(R.id.trun);
         progress= (int) ((3/(double)4)*100);
        Log.d("aa", "onCreate: "+progress+"");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("c","3");
                setResult(2,intent1);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent1=new Intent();
        intent1.putExtra("c","4");
        setResult(2,intent1);
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
