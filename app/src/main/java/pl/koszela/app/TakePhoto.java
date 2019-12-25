package pl.koszela.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;

public class TakePhoto extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String id;
    private String str_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        if (getIntent().getStringExtra("str_points") != null) {
            str_points = getIntent().getStringExtra("str_points");
        }
        mAuth = FirebaseAuth.getInstance();
    }

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
                return true;
            case R.id.item2:
                getIntent().removeExtra("message_key");
                id = getIntent().getStringExtra("id");
                Intent intent = new Intent(TakePhoto.this, SaveCustomer.class);
                if (getIntent().getStringExtra("str_points") != null) {
                    str_points = getIntent().getStringExtra("str_points");
                    intent.putExtra("str_points", str_points);
                }
                intent.putExtra("id", id);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void OnLogout1(View view) {
        mAuth.signOut();
        getIntent().removeExtra("message_key");
        startActivity(new Intent(TakePhoto.this, MainActivity.class));
    }


}

