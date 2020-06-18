package com.example.veekaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class PhotoCapturing extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button UploadButton , GetDetails, sendingCarNumber ;
    ImageView ImageUploaded;
    FirebaseVisionImage image;
    Bitmap imageBitmap;
    TextView CarNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_capturing);

        UploadButton = findViewById(R.id.uploadImage);
        ImageUploaded = findViewById(R.id.ImageUploaded);
        GetDetails = findViewById(R.id.getDetails);
        CarNumber = findViewById(R.id.textView);
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        GetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage();
            }
        });
        sendingCarNumber = findViewById(R.id.getOwnersDetails);

        sendingCarNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = CarNumber.getText().toString();
                final String Car_Number = s.replaceAll(" " , "");
                Intent intent = new Intent(PhotoCapturing.this, knowTheOwner.class);
                intent.putExtra("Car_No",Car_Number );
                startActivity(intent);
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageUploaded.setImageBitmap(imageBitmap);
        }
    }

    private void detectTextFromImage() {

        image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {

            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                //Get Each block of text i.e (Paragraph)
                for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                    String blockText = block.getText();
                    CarNumber.append(blockText+"\n\n"); //Separate each paragraph by new line
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PhotoCapturing.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}