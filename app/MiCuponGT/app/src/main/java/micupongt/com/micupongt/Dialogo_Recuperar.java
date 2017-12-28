package micupongt.com.micupongt;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.R.attr.password;

/**
 * Created by anton on 20/12/2017.
 */

public class Dialogo_Recuperar extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_recuperar, null))
                // Add action buttons
                .setPositiveButton(R.string.recuperar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String respuesta="";
                        final AlertDialog alertDialog = (AlertDialog) dialog;

                        EditText correo = (EditText) alertDialog
                                .findViewById(R.id.username);
                        try {
                            Cone cone=new Cone();
                            JSONObject datos =new JSONObject();
                            datos.put("accion","recuperar");
                            datos.put("correo",correo.getText().toString());
                            respuesta = cone.execute(datos).get();
                            if(respuesta.equals("True")){
                                Toast t = Toast.makeText(getActivity(),"Informacion de Usuario Enviada",Toast.LENGTH_SHORT);
                                t.show();
                            }else{
                                Toast t = Toast.makeText(getActivity(),"Entro al else",Toast.LENGTH_SHORT);
                                t.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();
                    }
                });
        return builder.create();
    }

}
