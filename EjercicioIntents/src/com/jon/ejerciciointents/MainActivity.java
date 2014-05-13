package com.jon.ejerciciointents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
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
	private static final int CODIGO_CONTACTO = 2;
	private ImageView imagen;
	static String mCurrentPhotoPath;
	private File photoFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		OnClickListener formulario = new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor = (TextView) findViewById(R.id.editorMain);
				String valor = editor.getText().toString();
				Intent intent = new Intent(MainActivity.this, ActividadSecundaria.class);
				intent.putExtra("cadena", valor);
				startActivity(intent);
			}
		};

		botonForm = (Button) findViewById(R.id.botonFormulario);
		botonForm.setOnClickListener(formulario);

		OnClickListener camara = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// startActivityForResult(intent, CODIGO_FOTO);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// Ensure that there's a camera activity to handle the intent
				if (intent.resolveActivity(getPackageManager()) != null) {
					// Create the File where the photo should go
					photoFile = null;
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

		botonCam = (Button) findViewById(R.id.botonCamara);
		botonCam.setOnClickListener(camara);

		OnClickListener contacto = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	            startActivityForResult(intent, CODIGO_CONTACTO);
			}
		};

		botonContact = (Button) findViewById(R.id.botonContacto);
		botonContact.setOnClickListener(contacto);

		Intent intent = getIntent();
		valorDeVuelta = intent.getStringExtra("cadena1");
		label = (TextView) findViewById(R.id.labelMain);
		label.setText(valorDeVuelta);

	}

	public static File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStorageDirectory();
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == CODIGO_FOTO) {
				// La foto en baja calidad
				// Bitmap photo = (Bitmap) data.getExtras().get("data");
				// imagen = (ImageView)findViewById(R.id.imagen);
				// imagen.setImageBitmap(photo);

				imagen = (ImageView) findViewById(R.id.imagen);
				if (photoFile.exists()) {
					Bitmap bmp = decodeFile(photoFile, imagen.getWidth(), imagen.getHeight());
					imagen.setImageBitmap(bmp);
				}
			}
			else if (requestCode == CODIGO_CONTACTO) {
				Uri uriContact = data.getData();
				String contactName = null;
		        // querying contact data store
		        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
		        if (cursor.moveToFirst()) {
		            // DISPLAY_NAME = The display name for the contact.
		            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
		            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		        }
		        cursor.close();
		        label = (TextView) findViewById(R.id.labelMain);
				label.setText(contactName);
			}
		}
	}
	
	public Bitmap decodeFile(File file, int reqWidth, int reqHeight){
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(file.getPath(), options);
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(file.getPath(), options);     
	}
	    
	private int calculateInSampleSize(
	    BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	    if (height > reqHeight || width > reqWidth) {
	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	    return inSampleSize;
	}  

}
