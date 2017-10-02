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
import java.util.concurrent.ExecutionException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Registro_Tradicional extends AppCompatActivity {
    TextView terminos;
    ImageView registrar;
    EditText nombre,usuario,correo,contra,contra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__tradicional);
        terminos=(TextView)findViewById(R.id.textView);
        registrar=(ImageView)findViewById(R.id.imageView14);
        nombre=(EditText)findViewById(R.id.editText6);
        usuario=(EditText)findViewById(R.id.editText7);
        correo=(EditText)findViewById(R.id.editText2);
        contra=(EditText)findViewById(R.id.editText9);
        contra2=(EditText)findViewById(R.id.editText8);
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
                if(nombre.getText().toString().equals("")||usuario.getText().toString().equals("")||correo.getText().toString().equals("")||contra.getText().toString().equals("")||contra2.getText().toString().equals("")){
                    Toast t = Toast.makeText(Registro_Tradicional.this,"Un Campo esta vacio",Toast.LENGTH_SHORT);
                    t.show();
                }else {
                    if(contra.getText().toString().equals(contra2.getText().toString())){
                        String respuesta="";
                        try {
                            Cone cone=new Cone();
                            JSONObject datos =new JSONObject();
                            datos.put("accion","registro");
                            datos.put("nombre",nombre.getText().toString());
                            datos.put("usuario",usuario.getText().toString());
                            datos.put("correo",correo.getText().toString());
                            datos.put("contra",contra.getText().toString());
                            respuesta = cone.execute(datos).get();
                            if(respuesta.equals("True")){
                                Toast t = Toast.makeText(Registro_Tradicional.this,"Registro Exitoso",Toast.LENGTH_SHORT);
                                t.show();
                                finish();
                                Intent i = new Intent(Registro_Tradicional.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast t = Toast.makeText(Registro_Tradicional.this,respuesta,Toast.LENGTH_SHORT);
                                t.show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast t = Toast.makeText(Registro_Tradicional.this,"La Contraseña no es igual",Toast.LENGTH_SHORT);
                        t.show();
                        contra.setText("");
                        contra2.setText("");
                    }
                }
            }
        });
    }
}
