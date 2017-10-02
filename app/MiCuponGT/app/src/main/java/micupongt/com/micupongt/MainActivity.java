package micupongt.com.micupongt;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    ImageView inicio,olvido,crear;
    EditText usuario;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicio=(ImageView)findViewById(R.id.imageView2);
        olvido=(ImageView)findViewById(R.id.imageView3);
        crear=(ImageView)findViewById(R.id.imageView4);
        usuario=(EditText) findViewById(R.id.editText3);
        password=(EditText)findViewById(R.id.editText4);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(usuario.getText().toString().equals("")||password.getText().toString().equals("")){
                Toast t = Toast.makeText(MainActivity.this,"Uno de los campos esta vacio",Toast.LENGTH_SHORT);
                t.show();
            }else{
                String respuesta="";
                try {
                    Cone cone=new Cone();
                    JSONObject datos =new JSONObject();
                    datos.put("accion","login");
                    datos.put("usuario",usuario.getText().toString());
                    datos.put("password",password.getText().toString());
                    respuesta = cone.execute(datos).get();
                    if(respuesta.equals("True")){
                        Toast t = Toast.makeText(MainActivity.this,"Bienvenido "+usuario.getText().toString(),Toast.LENGTH_SHORT);
                        t.show();
                        Intent i = new Intent(MainActivity.this, Lista.class);
                        startActivity(i);
                    }else{
                        Toast t = Toast.makeText(MainActivity.this,"Usuario/Password incorrectos. Intenta de Nuevo",Toast.LENGTH_SHORT);
                        t.show();
                        usuario.setText("");
                        password.setText("");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            }
        });
        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inici = new Intent(getApplicationContext(), recuperar.class);
                startActivity(inici);
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(MainActivity.this,"Crear Cuenta",Toast.LENGTH_SHORT);
                t.show();
                Intent inici = new Intent(getApplicationContext(), Registro_Redes.class);
                startActivity(inici);
            }
        });
    }
    //private Emitter.Listener onAuthenticate =
}
