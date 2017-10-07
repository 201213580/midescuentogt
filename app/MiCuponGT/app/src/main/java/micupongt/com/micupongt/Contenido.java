package micupongt.com.micupongt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Contenido extends AppCompatActivity {
    TextView fecha_t,titulo_t,contenido_t,imagen_t,empresa_t,direccion_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);
        String fecha = getIntent().getStringExtra("fecha");
        String titulo = getIntent().getStringExtra("titulo");
        String contenido = getIntent().getStringExtra("contenido");
        String imagen = getIntent().getStringExtra("imagen");
        String empresa = getIntent().getStringExtra("empresa");
        String direccion = getIntent().getStringExtra("direccion");
        fecha_t=(TextView)findViewById(R.id.textView12);
        titulo_t=(TextView)findViewById(R.id.textView10);
        contenido_t=(TextView)findViewById(R.id.textView14);
        empresa_t=(TextView)findViewById(R.id.textView13);
        direccion_t=(TextView)findViewById(R.id.textView15);
        fecha_t.setText(fecha);
        titulo_t.setText(titulo);
        contenido_t.setText(contenido);
        empresa_t.setText(empresa);
        direccion_t.setText(direccion);
    }
}
