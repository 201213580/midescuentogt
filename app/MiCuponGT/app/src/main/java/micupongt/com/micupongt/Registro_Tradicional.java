package micupongt.com.micupongt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Registro_Tradicional extends AppCompatActivity {
    TextView terminos;
    ImageView registrar;
    EditText nombre,usuario,correo,contra;
    private Socket socket;
    {
        try{
            socket = IO.socket("http://192.168.0.13:9000");
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__tradicional);
        terminos=(TextView)findViewById(R.id.textView);
        registrar=(ImageView)findViewById(R.id.imageView12);
        nombre=(EditText)findViewById(R.id.editText6);
        usuario=(EditText)findViewById(R.id.editText7);
        correo=(EditText)findViewById(R.id.editText2);
        contra=(EditText)findViewById(R.id.editText9);
        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(Registro_Tradicional.this,"Terminos y Condiciones",Toast.LENGTH_SHORT);
                t.show();
                Intent inici = new Intent(getApplicationContext(), Terminos_Condiciones.class);
                startActivity(inici);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.connect();
                socket.on("registro",new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        Registro_Tradicional.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Boolean resultado = (Boolean) args[0];
                                if(resultado){
                                    Toast t = Toast.makeText(Registro_Tradicional.this,"Registro Completo",Toast.LENGTH_SHORT);
                                    t.show();
                                    startActivity(new Intent(getApplicationContext(), Lista.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();
                                    Toast t1 = Toast.makeText(Registro_Tradicional.this,"Bienvenido a MICUPONGT",Toast.LENGTH_SHORT);
                                    t1.show();
                                    socket.disconnect();
                                    Intent inici = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(inici);
                                    finish();
                                }else{
                                    Toast t = Toast.makeText(Registro_Tradicional.this,"No se Pudo Registrar Intente Nuevamente",Toast.LENGTH_SHORT);
                                    t.show();
                                    socket.disconnect();
                                }
                            }
                        });
                    }
                });
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nombre",nombre.getText().toString());
                    jsonObject.put("usuario",usuario.getText().toString());
                    jsonObject.put("correo",correo.getText().toString());
                    jsonObject.put("contra",contra.getText().toString());
                    socket.emit("registro",jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("JSONException",e.getMessage());
                }

                //conec.Conectar(usuario.getText().toString(),password.getText().toString());
            }
        });
    }
}
