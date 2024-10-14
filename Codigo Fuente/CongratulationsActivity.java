package com.example.semestral;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CongratulationsActivity extends AppCompatActivity {
    private TextView mensaje;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        mensaje = findViewById(R.id.mensaje);

        Button finishButton = findViewById(R.id.finishButton);
        String nombre_de_usuario = getIntent().getStringExtra("USER_NAME");
        mensaje.setText("Congratulations, " + nombre_de_usuario + " for completing the game!" );

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a la pantalla principal o cerrar la aplicación
                Intent intent = new Intent(CongratulationsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
