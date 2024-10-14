package com.example.semestral;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    private TextView usuario;
    private String nombre;
    private ImageView faceImage;

    private TextView opt1, opt2, opt3;
    private String respuesta;
    private int preguntaActual = 0;

    private String[][] preguntas = {{"nose", "Nose", "Eyes", "Mouth"}, {"eyes", "Mouth", "Eyes", "Ear"}, {"mouth", "Ear", "Nose", "Mouth"}, {"ear", "Mouth", "Ear", "Tongue"}, {"eyebrows", "Nose", "Eyes", "Eyebrows"}};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        usuario = findViewById(R.id.usuario);
        faceImage = findViewById(R.id.faceImage);
        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);

        nombre = getIntent().getStringExtra("USER_NAME");
        usuario.setText("Hi, " + nombre);

        setUpDragListeners();
        cargarPregunta(preguntaActual, nombre);
    }

    private void setUpDragListeners() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    return true;
                }
                return false;
            }
        };

        opt1.setOnTouchListener(touchListener);
        opt2.setOnTouchListener(touchListener);
        opt3.setOnTouchListener(touchListener);

        faceImage.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        TextView droppedText = (TextView) event.getLocalState();
                        verificarRespuesta(droppedText.getText().toString());
                        break;
                }
                return true;
            }
        });
    }

    private void cargarPregunta(int questionIndex, String usuario) {
        if (questionIndex < preguntas.length) {
            respuesta = preguntas[questionIndex][0];
            faceImage.setImageResource(getResources().getIdentifier("face_" + respuesta, "drawable", getPackageName()));
            opt1.setText(preguntas[questionIndex][1]);
            opt2.setText(preguntas[questionIndex][2]);
            opt3.setText(preguntas[questionIndex][3]);
        } else {
            Intent intent = new Intent (GameActivity.this, CongratulationsActivity.class);
            intent.putExtra("USER_NAME", usuario);
            
            startActivity(intent);
            finish ();

        }
    }

    private void verificarRespuesta(String answer) {
        if (answer.equalsIgnoreCase(respuesta)) {
            Intent intent = new Intent(GameActivity.this, CorrectActivity.class);
            intent.putExtra("CURRENT_QUESTION", preguntaActual);
            startActivityForResult(intent, 1);
        } else {
            Intent intent = new Intent(GameActivity.this, IncorrectActivity.class);
            intent.putExtra("CORRECT_ANSWER", respuesta);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            preguntaActual++;
            cargarPregunta(preguntaActual, nombre);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // La pregunta se mantiene igual, el usuario volverá a intentarlo
        }
    }
}
