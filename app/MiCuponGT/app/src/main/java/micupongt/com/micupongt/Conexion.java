package micupongt.com.micupongt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
public class Conexion extends AppCompatActivity {
    final String[] respu = new String[1];

    public Conexion(){
    }
    public String respuesta(){
        return respu[0];
    }
    public void Conectar(final String usuario, final String password){
        respu[0]="false";
        try{
            final Socket socket;
            try {
                socket = IO.socket("http://192.168.0.13:7000");
                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject contenido=new JSONObject();
                                    contenido.put("usuario",usuario.toString());
                                    contenido.put("password",password.toString());
                                    socket.emit("contenido", contenido);
                                    respu[0]="true";
                                    socket.disconnect();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).on("respuesta", new Emitter.Listener() {
                    //message is the keyword for communication exchanges
                    @Override
                    public void call(final Object... args) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String mensaje=args[0].toString();
                                respu[0]=mensaje;
                            }
                        });
                    }
                }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                    }
                });
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
        }
    }
}
