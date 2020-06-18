package com.example.veekaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.validation.Validator;

public class knowTheOwner extends AppCompatActivity {
    TextView carNumberPreDisplayed , CarNumber , Contact , FlatNumber , OwnerName;
    Button alertingTheOwner;
    String carnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_the_owner);
        carNumberPreDisplayed = findViewById(R.id.textView2);
        Intent intent= getIntent();
         carnumber = intent.getStringExtra("Car_No");
        carNumberPreDisplayed.setText(carnumber);

        CarNumber = findViewById(R.id.Car_No);
        Contact = findViewById(R.id.contact);
        FlatNumber = findViewById(R.id.Flat);
        OwnerName = findViewById(R.id.name);

        fetchingDetailsFromTheDB();

    //    ------------------------------------------------------------Sending the notifications--------------------------------------------
        alertingTheOwner = findViewById(R.id.button);



        alertingTheOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                OneSignal.startInit(knowTheOwner.this).init();
                OneSignal.setSubscription(true);

                OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);

                Toast.makeText(knowTheOwner.this, "Sending your message... :) ", Toast.LENGTH_SHORT).show();

                new SendNotification("Your vehicle No." + carnumber + "is wrongly parked. Please check!" , "Veerkaar Notification",null);
            }
        });

    }

    private void fetchingDetailsFromTheDB() {

         final String carnumber1 = carnumber.trim();
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("carNumber").equalTo(carnumber1);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String carNumberFromDB= dataSnapshot.child(carnumber1).child("carNumber").getValue(String.class) ;
                    
                        String Name = dataSnapshot.child(carnumber1).child("name").getValue(String.class) ;
                        String Flat_Number = dataSnapshot.child(carnumber1).child("flat").getValue(String.class) ;
                        String ContactNo =dataSnapshot.child(carnumber1).child("contact").getValue(String.class) ;

                        CarNumber.setText(carNumberFromDB);
                        Contact.setText(ContactNo);
                        FlatNumber.setText(Flat_Number);
                        OwnerName.setText(Name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}