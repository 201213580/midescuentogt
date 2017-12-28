package micupongt.com.micupongt;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
    String mandar_usuario;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicio=(ImageView)findViewById(R.id.imageView2);
        olvido=(ImageView)findViewById(R.id.imageView3);
        crear=(ImageView)findViewById(R.id.imageView4);
        usuario=(EditText) findViewById(R.id.editText3);
        password=(EditText)findViewById(R.id.editText4);
        if(VerificarSesion()){
            Intent i = new Intent(MainActivity.this, Lista.class);
            startActivity(i);
            finish();
        }
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
                            CrearSesion();
                            Toast t = Toast.makeText(MainActivity.this,"Bienvenid@ "+usuario.getText().toString(),Toast.LENGTH_SHORT);
                            t.show();
                            Intent i = new Intent(MainActivity.this, Lista.class);
                            startActivity(i);
                            finish();
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
                Dialogo_Recuperar recuperar=new Dialogo_Recuperar();
                recuperar.show(getFragmentManager(),"TAg");

            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(MainActivity.this,"Crear Cuenta",Toast.LENGTH_SHORT);
                t.show();
                Intent inici = new Intent(getApplicationContext(), Registro_Redes.class);
                startActivity(inici);
                finish();
            }
        });
    }
    public void CrearSesion(){
        ConexionLocal usdbh =
                new ConexionLocal(this, "DBUsuarios", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null) {
            db.execSQL("INSERT INTO Usuario (usuario, password)VALUES ('" +usuario.getText().toString()+ "', '" +password.getText().toString() +"')");
            db.close();
        }else{
            Toast t = Toast.makeText(MainActivity.this,"No se logro abrir la base de datos local",Toast.LENGTH_SHORT);
            t.show();
        }

    }
    public boolean VerificarSesion(){
        boolean respuesta=false;
        ConexionLocal usdbh =new ConexionLocal(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null) {
            Cursor c =db.rawQuery("SELECT * FROM Usuario",null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    mandar_usuario= c.getString(0);
                    respuesta=true;
                } while(c.moveToNext());
            }
            db.close();
        }else{
            Toast t = Toast.makeText(MainActivity.this,"No se logro abrir la base de datos local",Toast.LENGTH_SHORT);
            t.show();
        }
        return respuesta;
    }


}

