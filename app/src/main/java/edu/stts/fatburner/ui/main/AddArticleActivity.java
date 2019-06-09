package edu.stts.fatburner.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import androidx.annotation.NonNull;
import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.stts.fatburner.R;
import edu.stts.fatburner.data.network.API;
import edu.stts.fatburner.data.network.ApiClient;
import edu.stts.fatburner.data.network.body.AddArticleBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticleActivity extends AppCompatActivity implements View.OnClickListener {
    private API mApiInterface;
    private Button btnAddImage,btnSave,btnRemove;
    private EditText etJudul,etIsi;
    private ImageView img;
    private Bitmap chosenImage;
    private SharedPreferences pref;
    private SweetAlertDialog pDialog;
    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        btnAddImage = findViewById(R.id.btn_addarticle_image);
        btnRemove = findViewById(R.id.btn_addarticle_remove);
        img = findViewById(R.id.iv_addarticle_image);
        btnSave = findViewById(R.id.btn_addarticle);
        etJudul = findViewById(R.id.et_addarticle_title);
        etIsi = findViewById(R.id.et_addarticle_isi);

        pref = getSharedPreferences("FatBurnerPrefs",Context.MODE_PRIVATE);
        btnAddImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        mApiInterface = ApiClient.getClient().create(API.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add New Article");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_addarticle_image){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureDirectoryPath = pictureDirectory.getPath();
            Uri data = Uri.parse(pictureDirectoryPath);
            photoPickerIntent.setDataAndType(data, "image/*");
            startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
        }else if(id == R.id.btn_addarticle){
            if(isAllowed()) saveArticle(chosenImage);
        }else if(id == R.id.btn_addarticle_remove){
            img.setImageBitmap(null);
            img.setVisibility(View.GONE);
            btnRemove.setVisibility(View.GONE);
        }
    }

    private boolean isAllowed(){
        if(etJudul.getText().toString().equals("")||etIsi.getText().toString().equals("")){
            Toast.makeText(AddArticleActivity.this, "Title and content must be filled in!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(Bitmap image){
        MultipartBody.Part body;
        if(image != null){
            File file = createTempFile(image);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("imageurl", file.getName(), reqFile);
        }else body = null;
        int user = pref.getInt("userID",-1);

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user));
        RequestBody judul = RequestBody.create(MediaType.parse("multipart/form-data"), etJudul.getText().toString());
        RequestBody isi = RequestBody.create(MediaType.parse("multipart/form-data"), etIsi.getText().toString());
        String token = pref.getString("token","");

        Call<InsertResponse> call = mApiInterface.uploadImage(token,userid,judul,isi,body);
        call.enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> res) {
                InsertResponse response = res.body();
                Toast.makeText(AddArticleActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
                if (response != null && !response.isError()) finish();
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(AddArticleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void saveArticle(final Bitmap bitmap){
        pDialog = new SweetAlertDialog(AddArticleActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Uploading your article..");
        pDialog.setCancelable(false);
        pDialog.show();

        new AsyncTask<Void,Void,File>(){

            @Override
            protected File doInBackground(Void... voids) {
                save(bitmap);
                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
            }
        }.execute();
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.png");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,0, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    chosenImage = image;
                    img.setVisibility(View.VISIBLE);
                    img.setImageBitmap(image);
                    btnRemove.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(AddArticleActivity.this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
