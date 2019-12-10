package pl.koszela.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Points extends AppCompatActivity {

    private TextView mPoints;
    private TextView idUSer;
    private String result;
    private String str_id;
    private String str_points;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        mPoints = findViewById(R.id.points);
        idUSer = findViewById(R.id.idUser);
        mAuth = FirebaseAuth.getInstance();

        result = getIntent().getStringExtra("message_key");
        if (result != null) {
            int login = result.indexOf("points");
            str_id = result.substring(0, login);
            String textID = str_id;
            idUSer.setText(textID);

            int login1 = result.indexOf('s');
            int login2 = result.indexOf("login");
            str_points = result.substring(login1 + 1, login2);
            int brakuje = 1000 - Integer.parseInt(str_points);
            String pointsText = "Aktualnie zdobyłes : " + str_points + " Brakuję Ci " + brakuje;
            mPoints.setText(pointsText);
        } else {
            Toast.makeText(this, "Nie mogę wstawić id. Nic nie ma", Toast.LENGTH_LONG).show();
        }
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
                Intent intent = new Intent(Points.this, SaveCustomer.class);
                intent.putExtra("message_key", result);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void OnLogout1(View view) {
        mAuth.signOut();
        getIntent().removeExtra("message_key");
        startActivity(new Intent(Points.this, MainActivity.class));
    }
}



