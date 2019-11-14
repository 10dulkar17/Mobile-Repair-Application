package com.example.mobile_repair_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
//
//import com.example.mobile_repair_application.ui.login.LoginActivity;

public class FirstPage extends AppCompatActivity {

    private Button vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Spinner location = (Spinner) findViewById(R.id.spinner);



        ArrayAdapter<String> mylocations = new ArrayAdapter<String>(FirstPage.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.locations));
        mylocations.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        location.setAdapter(mylocations);





        vendor = (Button) findViewById(R.id.vendor);
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }
    public void openLoginActivity(){
        Intent intent = new Intent( this, LoginActivity.class);
        startActivity(intent);
    }
}
