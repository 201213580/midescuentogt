package micupongt.com.micupongt;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Registro_Redes extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE=777;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__redes);

        signInButton=(SignInButton)findViewById(R.id.botongmail);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast t = Toast.makeText(Registro_Redes.this,"Ocurrio un error",Toast.LENGTH_SHORT);
        t.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_CODE){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMailScreen();
        }else{
            Toast t = Toast.makeText(Registro_Redes.this,"No pudo iniciar sesion",Toast.LENGTH_SHORT);
            t.show();
        }
    }
    private void goMailScreen() {
        Toast t = Toast.makeText(Registro_Redes.this,"Inicio Sesion",Toast.LENGTH_SHORT);
        t.show();
        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult2(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult2(googleSignInResult);
                }
            });
        }
        //Intent i = new Intent(this, Lista.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(i);
    }
    private void handleSignInResult2(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            try {
                String respuesta="";
                Cone cone=new Cone();
                JSONObject datos =new JSONObject();
                datos.put("accion","registro");
                datos.put("nombre",account.getDisplayName().toString());
                datos.put("usuario",account.getEmail());
                datos.put("correo",account.getEmail());
                datos.put("contra",account.getId());
                datos.put("tipo","gmail");
                respuesta = cone.execute(datos).get();
                //CrearSesion(account.getEmail().toString(),account.getId().toString());
                Toast t = Toast.makeText(Registro_Redes.this,"Bienvenido",Toast.LENGTH_SHORT);
                t.show();
                Intent i = new Intent(Registro_Redes.this, Lista.class);
                startActivity(i);
                finish();
            } catch (InterruptedException e) {
                Toast t = Toast.makeText(Registro_Redes.this,"Exception 1",Toast.LENGTH_SHORT);
                t.show();
                e.printStackTrace();
            } catch (ExecutionException e) {
                Toast t = Toast.makeText(Registro_Redes.this,"Exception 1",Toast.LENGTH_SHORT);
                t.show();
                e.printStackTrace();
            } catch (JSONException e) {
                Toast t = Toast.makeText(Registro_Redes.this,"Exception 1",Toast.LENGTH_SHORT);
                t.show();
                e.printStackTrace();
            }
        }else{
            IrRegistro();
        }
    }
    public void CrearSesion(String usuario,String password){
        ConexionLocal usdbh =
                new ConexionLocal(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null) {
            db.execSQL("INSERT INTO Usuario (usuario, password)VALUES ('" +usuario+ "', '" +password+"')");
            db.close();
            Toast t = Toast.makeText(Registro_Redes.this,"Se agregaron los datos",Toast.LENGTH_SHORT);
            t.show();
        }else{
            Toast t = Toast.makeText(Registro_Redes.this,"No se logro abrir la base de datos local",Toast.LENGTH_SHORT);
            t.show();
        }

    }
    private void IrRegistro() {
        Intent i = new Intent(this, Registro_Redes.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
