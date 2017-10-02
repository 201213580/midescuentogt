package micupongt.com.micupongt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Registro_Redes extends AppCompatActivity {
    ImageView facebook, gmail, tradicional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__redes);
        facebook=(ImageView)findViewById(R.id.imageView9);
        gmail=(ImageView)findViewById(R.id.imageView10);
        tradicional=(ImageView)findViewById(R.id.imageView11);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(Registro_Redes.this,"Registro con facebook",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(Registro_Redes.this,"Registro con gmail",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        tradicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(Registro_Redes.this,"Registro Tradicional",Toast.LENGTH_SHORT);
                t.show();
                Intent inici = new Intent(getApplicationContext(), Registro_Tradicional.class);
                startActivity(inici);
                finish();
            }
        });
    }
}
