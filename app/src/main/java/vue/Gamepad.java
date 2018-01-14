package vue;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.i162174.robot.R;

import autres.Robot;

public class Gamepad extends GridLayout {

    public Gamepad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(final Context context){
        setColumnCount(3);
        setRowCount(3);

        ImageView btn_up = new ImageView(context);
        ImageView btn_down = new ImageView(context);
        ImageView btn_left = new ImageView(context);
        ImageView btn_right = new ImageView(context);

        btn_up.setImageResource(R.drawable.icon_up);
        btn_down.setImageResource(R.drawable.icon_down);
        btn_left.setImageResource(R.drawable.icon_left);
        btn_right.setImageResource(R.drawable.icon_right);

        btn_up.setOnTouchListener(new OnTouchListener() { // Avancer

            private Handler mHandler;

            Runnable actionAvancer = new Runnable() {
                @Override public void run() {
                    Robot.envoyerCommande(context, Robot.AVANCER);
                    mHandler.postDelayed(this, 10);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler = new Handler();
                        mHandler.postDelayed(actionAvancer, 10);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(actionAvancer);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        mHandler = null;
                        return true;
                }
                return false;
            }
        });

        btn_down.setOnTouchListener(new OnTouchListener() { // Avancer

            private Handler mHandler;

            Runnable actionReculer = new Runnable() {
                @Override public void run() {
                    Robot.envoyerCommande(context, Robot.RECULER);
                    mHandler.postDelayed(this, 10);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler = new Handler();
                        mHandler.postDelayed(actionReculer, 10);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(actionReculer);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        mHandler = null;
                        return true;
                }
                return false;
            }
        });

        btn_left.setOnTouchListener(new OnTouchListener() { // Avancer

            private Handler mHandler;

            Runnable actionTournerGauche = new Runnable() {
                @Override public void run() {
                    Robot.envoyerCommande(context, Robot.TOURNER_A_GAUCHE);
                    mHandler.postDelayed(this, 1000);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler = new Handler();
                        mHandler.postDelayed(actionTournerGauche, 1000);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(actionTournerGauche);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        mHandler = null;
                        return true;
                }
                return false;
            }
        });

        btn_right.setOnTouchListener(new OnTouchListener() { // Avancer

            private Handler mHandler;

            Runnable actionTournerDroite = new Runnable() {
                @Override public void run() {
                    Robot.envoyerCommande(context, Robot.TOURNER_A_DROITE);
                    mHandler.postDelayed(this, 1000);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler = new Handler();
                        mHandler.postDelayed(actionTournerDroite, 1000);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(actionTournerDroite);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        Robot.envoyerCommande(context, Robot.ARRETER);
                        mHandler = null;
                        return true;
                }
                return false;
            }
        });


        addView(btn_up, new LayoutParams(GridLayout.spec(0),  GridLayout.spec(1)));
        addView(btn_down, new LayoutParams(GridLayout.spec(2),  GridLayout.spec(1)));
        addView(btn_left, new LayoutParams(GridLayout.spec(1),  GridLayout.spec(0)));
        addView(btn_right, new LayoutParams(GridLayout.spec(1),  GridLayout.spec(2)));
    }

}
