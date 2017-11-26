package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.i162174.robot.R;

import autres.Robot;

public class ControlleurActivity extends ActivityAvecMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlleur);
        initialisationMenu();

        Button btn_deconnexion = (Button) findViewById(R.id.btn_deconnexion);
        btn_deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Robot.deconnectionRobot(ControlleurActivity.this);
            }
        });
    }

}