package micupongt.com.micupongt;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by anton on 28/09/2017.
 */

public class Cone extends AsyncTask<JSONObject,Void,String> {
    @Override
    protected String doInBackground(JSONObject... params) {
        String respuesta="nada";
        try{
            try{
                Socket socket=new Socket("192.168.1.37",7000);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
                // Send first message - Message is being correctly received
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw.write(params[0]+"\n");
                pw.flush();
                respuesta=in.readLine(); //here you process you line result
                in.close();
                pw.close();
                socket.close();
            }catch(IOException e){

            }

        }catch(Exception e){
            return respuesta;
        }
        return respuesta;
    }
}
