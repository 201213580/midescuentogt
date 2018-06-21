package micupongt.com.micupongt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by anton on 14/12/2017.
 */

public class DialogoAlerta extends DialogFragment {
    String Mensaje;
    String Titulo;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(Mensaje)
                .setTitle(Titulo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
