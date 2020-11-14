package com.example.saveme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterInforActivity extends AppCompatActivity {
    EditText num1,num2,num3,num4,num5,name;
    Button setNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        num1= findViewById(R.id.num1);
        num2= findViewById(R.id.num2);
        num3= findViewById(R.id.num3);
        num4= findViewById(R.id.num4);
        num5= findViewById(R.id.num5);
        name=findViewById(R.id.name);
        setNum= findViewById(R.id.setNumbers);
        setNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Num1,Num2,Num3,Num4,Num5,mName;
                Num1=num1.getText().toString();
                Num2=num2.getText().toString();
                Num3=num3.getText().toString();
                Num4=num4.getText().toString();
                Num5=num5.getText().toString();
                mName=name.getText().toString();
                if(Num1.equals("")||Num2.equals("")||Num3.equals("")||Num4.equals("")||Num5.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill up all the fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent2=new Intent(EnterInforActivity.this,MainActivity.class);
                    intent2.putExtra("NUM1",Num1);
                    intent2.putExtra("NUM2",Num2);
                    intent2.putExtra("NUM3",Num3);
                    intent2.putExtra("NUM4",Num4);
                    intent2.putExtra("NUM5",Num5);
                    intent2.putExtra("NAME",mName);
                    startActivity(intent2);
                    Toast.makeText(EnterInforActivity.this,"Information is Set Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
