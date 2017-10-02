package micupongt.com.micupongt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class recuperar extends AppCompatActivity {
    ImageView enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        enviar=(ImageView)findViewById(R.id.imageView6);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(recuperar.this,"Se a enviado un correo para reestablecer la contrase;a",Toast.LENGTH_SHORT);
                t.show();
                Intent inici = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inici);
                finish();
            }
        });
    }
}
