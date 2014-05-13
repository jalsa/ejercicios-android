package com.jon.ejerciciointents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button botonForm, botonCam, botonContact;
	private TextView editor, label;
	private String valorDeVuelta;
	private static final int CODIGO_FOTO = 1;
	private ImageView imagen;
	static String mCurrentPhotoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		OnClickListener formulario = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editor = (TextView)findViewById(R.id.editorMain);
				String valor = editor.getText().toString();
				Intent intent = new Intent(MainActivity.this, ActividadSecundaria.class);
				intent.putExtra("cadena", valor);
				startActivity(intent);
			}
		};
		
		botonForm = (Button)findViewById(R.id.botonFormulario);
		botonForm.setOnClickListener(formulario);
		
		OnClickListener camara = new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//startActivityForResult(intent, CODIGO_FOTO);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    // Ensure that there's a camera activity to handle the intent
			    if (intent.resolveActivity(getPackageManager()) != null) {
			        // Create the File where the photo should go
			        File photoFile = null;
			        try {
			            photoFile = createImageFile();
			        } catch (IOException ex) {
			            // Error occurred while creating the File
			        }
			        // Continue only if the File was successfully created
			        if (photoFile != null) {
			            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			            startActivityForResult(intent, CODIGO_FOTO);
			        }
			    }
			}
		};
		
		botonCam = (Button)findViewById(R.id.botonCamara);
		botonCam.setOnClickListener(camara);
		
		OnClickListener contacto = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		};
		
		botonContact = (Button)findViewById(R.id.botonContacto);
		botonContact.setOnClickListener(contacto);
		
		Intent intent = getIntent();
		valorDeVuelta = intent.getStringExtra("cadena1");
		label = (TextView)findViewById(R.id.labelMain);
		label.setText(valorDeVuelta);
		
	}
	
	public static File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    File image = File.createTempFile(imageFileName, ".jpg", storageDir);
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CODIGO_FOTO && resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen = (ImageView)findViewById(R.id.imagen);
            imagen.setImageBitmap(photo);
        }  
    }
	
}
