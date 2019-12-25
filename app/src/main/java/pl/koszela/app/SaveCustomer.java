package pl.koszela.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class SaveCustomer extends AppCompatActivity {

    EditText name;
    EditText phone;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    Spinner dropdown;
    EditText number;
    //    private TextView mPoints;
    private TextView text1;
    private TextView text2;
    private TextView id;


    private TextView mDetailID;

    private FirebaseAuth mAuth;

    private Button mSignOutButton;
    private String result;
    private String str_id;
    private String str_points;
    private Button save;
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageToUpload;
    private Button btnGoToTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_customer);

        name = (EditText) findViewById(R.id.et_new_customer_name);
        phone = (EditText) findViewById(R.id.et_new_customer_phone);
        number = (EditText) findViewById(R.id.editNumber);
        mSignOutButton = (Button) findViewById(R.id.logout);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        mDetailID = findViewById(R.id.userId);
        text1 = findViewById(R.id.textView7);
        text2 = findViewById(R.id.textView5);
        save = (Button) findViewById(R.id.btnSaveCustomer);
        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        btnGoToTakePhoto = (Button) findViewById(R.id.btnGoToTakePhoto);

        mAuth = FirebaseAuth.getInstance();
        if (getIntent().getStringExtra("str_points") != null) {
            str_points = getIntent().getStringExtra("str_points");
        }
        result = getIntent().getStringExtra("message_key");
        str_id = getIntent().getStringExtra("id");
        if (result != null && !result.equals("")) {
            int login = result.indexOf("points");
            str_id = result.substring(0, login);
            String textID = mDetailID.getText().toString() + " " + str_id;
            mDetailID.setText(textID);
        } else if (str_id != null) {
            String textID = mDetailID.getText().toString() + " " + str_id;
            mDetailID.setText(textID);
        } else {
            Toast.makeText(this, "Nie mogę wstawić id. Nic nie ma", Toast.LENGTH_LONG).show();
        }
        dropdown = findViewById(R.id.comboBox);
        String[] items = new String[]{"dach pierwszy", "dach drugi", "dach trzeci", "dach czwarty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    String res = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                getIntent().removeExtra("message_key");
                Intent intent = new Intent(SaveCustomer.this, Points.class);
                if (getIntent().getStringExtra("str_points") != null && getIntent().getStringExtra("id") != null) {
                    str_points = getIntent().getStringExtra("str_points");
                    str_id = getIntent().getStringExtra("id");
                    intent.putExtra("id", str_id);
                    intent.putExtra("str_points", str_points);
                }else {
                    intent.putExtra("id", str_id);
                    intent.putExtra("str_points", str_points);
                }
                intent.putExtra("message_key", result);
                startActivity(intent);
                return true;
            case R.id.item2:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    String urlImage = "";

    public void saveCustomer(View view) {
        String str_name = name.getText().toString();
        String str_phone = phone.getText().toString();
        String type = "save customer";
        String selectItemFromComboBox = dropdown.getSelectedItem().toString();
        String checked1 = Boolean.toString(checkBox1.isChecked());
        String checked2 = Boolean.toString(checkBox2.isChecked());
        String checked3 = Boolean.toString(checkBox3.isChecked());
        String checked4 = Boolean.toString(checkBox4.isChecked());
        String str_number = number.getText().toString();

        image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


        BackgroundWorker backgroundWorker1 = new BackgroundWorker(SaveCustomer.this, new MyCallback() {
            @Override
            public void onResult(String result) {

                urlImage = result;
            }
        });

        backgroundWorker1.execute("save image", name.getText().toString(), encodedImage);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this, new MyCallback() {
            @Override
            public void onResult(String result) {
                res = result;
            }
        });
        backgroundWorker.execute(type, str_name, str_phone, selectItemFromComboBox, checked1, checked2, checked3, checked4, str_number, str_id, urlImage, str_points);
    }

    Bitmap image;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }

    public void setInvisibleAdvanced() {

        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
    }

    public void setInvisibleBasic() {
        btnGoToTakePhoto.setVisibility(View.INVISIBLE);
    }


    public void OnLogout(View view) {
        mAuth.signOut();
        getIntent().removeExtra("message_key");
        startActivity(new Intent(SaveCustomer.this, MainActivity.class));
    }

    public void TakePhotoMethod(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);


//            Intent intent = new Intent(SaveCustomer.this, TakePhoto.class);
//            intent.putExtra("id", str_id);
//            startActivity(intent);

    }
}
