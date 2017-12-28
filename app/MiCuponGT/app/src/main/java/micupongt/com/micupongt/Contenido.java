package micupongt.com.micupongt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

public class Contenido extends AppCompatActivity {
    TextView fecha_t,titulo_t,contenido_t,imagen_t,empresa_t,direccion_t,codigo_t;
    SmartImageView smartImageView;
    TextView Terminos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido);
        String codigo=getIntent().getStringExtra("codigo");
        String imagen2 = getIntent().getStringExtra("imagen2");
        final String Contenido = getIntent().getStringExtra("contenido");
        Terminos=(TextView)findViewById(R.id.textView12);
        smartImageView=null;
        smartImageView=(SmartImageView)findViewById(R.id.imagen2);
        Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
        smartImageView.setImageUrl(imagen2,rect);
        codigo_t=(TextView)findViewById(R.id.textView10);
        codigo_t.setText(codigo);
        Terminos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.setMensaje("Mensaje de la alerta");
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });
        smartImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoInfo dialogo = new DialogoInfo();
                dialogo.setMensaje(Contenido);
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });

    }
}
