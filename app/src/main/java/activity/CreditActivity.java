package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.i162174.robot.R;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);


        Button btn_retour = (Button) findViewById(R.id.btn_retour);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activity = getIntent().getStringExtra("From");
                Class callerClass = null;
                try {
                    callerClass = Class.forName(activity);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(CreditActivity.this, callerClass));
                finish();
            }
        });
    }

}