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
import android.support.annotation.NonNull;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private ImageView terminos;
    public static final int SIGN_IN_CODE=777;
    String mandar_usuario;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton=(SignInButton)findViewById(R.id.botongmail);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        terminos=(ImageView)findViewById(R.id.imageView2);
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
        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.setTitulo("Terminos y Condiciones de Servicio");
                dialogo.setMensaje("En nuestra aplicación móvil ofrecemos Cupones, Ofertas y Promociones, que pueden ser canjeados en los comercios inscritos. Los siguientes términos y condiciones de servicio aplican a todos los usuarios que estén registrados en MiCuponGT. \n" +
                        "\n" +
                        "1. Contacto MiCuponGT: Nos puede contactar por medio de nuestra página de contacto www.micupongt.com/contacto o por teléfono al +(502)5979-1821.\n" +
                        "\n" +
                        "2. Funcionamiento aplicación móvil: MiCuponGT cuenta con una aplicación móvil en la cual se publican los cupones, ofertar o promociones de los comercios inscritos al sistema, generando un código único el cual se canjea con los comercios inscritos, los términos de servicio para canjear el cupón, oferta o promoción son descritos y determinados por parte del comercio, indicándolo en la sección de información en la aplicación móvil.\n" +
                        "\n" +
                        "3. Relación entre MiCuponGT, el comercio inscrito y el usuario:\n" +
                        "El comercio inscrito es el único responsable de cumplir con los servicios descritos en el cupón, oferta o promoción y deberá de velar por la garantía y entrega de los productos que se ofrezcan, desligando de cualquier responsabilidad a MiCuponGT.\n" +
                        "\n" +
                        "4. Publicidad de Cupones, Ofertas y Promociones: La publicidad de los cupones, ofertas y promociones en nuestro sitio web, aplicaciones móviles, redes sociales, correo electrónico es de nuestros comercios inscritos por lo que solamente el comercio inscrito es responsable de la certeza de la información, sin embargo, MiCuponGT realiza un análisis de la publicidad para verificar su veracidad.\n" +
                        "\n" +
                        "5. No Cumplimiento de servicios descritos en Cupones, Ofertas y Promociones por parte del comercio inscrito: Si el comercio inscrito no cumple con los servicios descritos o existen anomalías en el cumplimiento se deberá de enviar un correo electrónico a adminmicupongt@gmail.com para poder realizar el seguimiento adecuado se solicita que enviar una captura de pantalla del teléfono, donde se pueda visualizar el cupón, oferta o promoción, indicando fecha, nombre y número de teléfono para poder contactar con el usuario de la manera más pronta o comunicarse al teléfono +(502)5979-1821 para darle seguimiento a la anomalía.\n" +
                        "\n" +
                        "6. Responsabilidad de daños: MiCuponGT no se hace responsable por cualquier daño causado por el canje del cupón, oferta o promoción. Solamente el comercio inscrito se responsabiliza.\n" +
                        "\n" +
                        "7. Facturación: El comercio inscrito es el encargado de emitir la factura correspondiente por los servicios del cupón, oferta o promoción.\n" +
                        " \n" +
                        "8. Canje de cupones, ofertas o promociones:  Nuestros Cupones, ofertas o promociones tienen un código único, el comercio inscrito ingresa el código en una página de nuestro sistema para verificar la validez del mismo, si es correcto se procede al canje por parte del usuario. MiCuponGT toma el código como canjeado al momento que lo registra el sistema.\n" +
                        "\n" +
                        "9. Tiempo de expiración: El código único que tiene cada cupón, oferta o promoción no tiene expiración, sin embargo, puede tener un límite de canje o límite de existencias descrito por el comercio afiliado en la descripción de la publicación en la aplicación móvil o página web.\n" +
                        "\n" +
                        "10. Privacidad: MiCuponGT no compartirá la información de tu cuenta con terceros, sin embargo, MiCuponGT podrá analizar la información que se genere por medio de la aplicación móvil para retroalimentación de las cupones, ofertas o promociones y brindar un mejor servicio a los usuarios. MiCuponGT tomara todas las medidas para resguardar la información, sin embargo, no nos hacemos responsables en caso del robo de la información de nuestros servidores.\n" +
                        " \n" +
                        "11. Propiedad Intelectual: Los contenidos de la aplicación móvil, pagina web, así como los programas, diseño de base de datos, infraestructura, archivos e imágenes que permiten el funcionamiento del sistema son propiedad de MiCuponGT por lo que están resguardadas por la ley de derechos de autor. La reproducción total o parcial de los contenidos quedan totalmente prohibidos.\n" +
                        "\n" +
                        "12. Cambios Términos y Condiciones: MiCuponGT puede actualizar los términos y condiciones informándole de los cambios a todos los usuarios que estén registrados, por medio de un correo electrónico.\n" +
                        "\n" +
                        "13. Servicios Aplicación Móvil:\n" +
                        "- MiCuponGT puede interrumpir el funcionamiento de la aplicación móvil para realizar mantenimiento, preventivo o correctivo. \n" +
                        "- El usuario acepta que MiCuponGT pueda dar de baja su perfil por un mal uso del mismo.\n" +
                        "- El usuario está de acuerdo en que la información generada por MiCuponGT y el registro de los usuarios está alojado en un proveedor de servicios por lo que se podrán realizar migraciones y análisis de la información para prestar un mejor servicio.\n" +
                        "\n" +
                        "14. Registro: Al registrarse en la aplicación MiCuponGT se asume que está de acuerdo con los términos y condiciones anteriormente descritos.\n");
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });
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
            Toast t = Toast.makeText(MainActivity.this,"Cuenta registrada con exito, Presione el boton de iniciar sesion.",Toast.LENGTH_SHORT);
            t.show();

        }
    }
    private void goMailScreen() {
        Intent i = new Intent(this, Lista.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast t = Toast.makeText(MainActivity.this,"Ocurrio un error de conexion",Toast.LENGTH_SHORT);
        t.show();
    }
}