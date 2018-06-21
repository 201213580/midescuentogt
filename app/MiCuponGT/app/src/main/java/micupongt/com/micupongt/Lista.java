package micupongt.com.micupongt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Lista extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public String usuario_sesion;
    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private GoogleApiClient googleApiClient;
    GoogleSignInAccount account;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trimCache(getApplicationContext());
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Verificar_Red()){
            setContentView(R.layout.activity_lista);
            try{
                GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                googleApiClient=new GoogleApiClient.Builder(this)
                        .enableAutoManage(this,this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                        .build();
            }catch(Exception e){
                Toast t = Toast.makeText(Lista.this,"El servicio no esta disponible temporalmente",Toast.LENGTH_SHORT);
                t.show();
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }else{
            Toast t = Toast.makeText(Lista.this,"Por favor verifique su conexion a internet",Toast.LENGTH_LONG);
            t.show();
            finish();
        }
    }
    public boolean Verificar_Red(){
        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean respuesta = false;
        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mWifi.isConnected()) {
                respuesta = true;
            }
            if (mMobile.isConnected()) {
                respuesta = true;
            }
        }
        return respuesta;
    }
    @Override
    protected void onStart() {
        super.onStart();
        try{
            OptionalPendingResult<GoogleSignInResult>opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if(opr.isDone()){
                GoogleSignInResult result=opr.get();
                handleSignInResult(result);
            }else{
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }catch(Exception e){

        }

    }
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            try {
                Thread.sleep(4000);
                account=result.getSignInAccount();
                String respuesta="";
                Cone cone=new Cone();
                JSONObject datos =new JSONObject();
                datos.put("accion","registro");
                datos.put("nombre",account.getDisplayName().toString());
                datos.put("usuario",account.getEmail().toString());
                datos.put("correo",account.getEmail().toString());
                datos.put("contra",account.getId().toString());
                datos.put("tipo","gmail");
                respuesta = cone.execute(datos).get();
                Toast t2 = Toast.makeText(Lista.this,"Bienvenido "+account.getDisplayName().toString(),Toast.LENGTH_SHORT);
                t2.show();
                mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                setupViewPager(mViewPager);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast t2 = Toast.makeText(Lista.this,"Ocurrio un error",Toast.LENGTH_LONG);
                t2.show();
                goLogInScreen();
            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast t2 = Toast.makeText(Lista.this,"Ocurrio un error",Toast.LENGTH_LONG);
                t2.show();
                goLogInScreen();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast t2 = Toast.makeText(Lista.this,"Ocurrio un error",Toast.LENGTH_LONG);
                t2.show();
                goLogInScreen();
            }catch(Exception e){
                e.printStackTrace();
                Toast t2 = Toast.makeText(Lista.this,"Error al iniciar sesion, intente nuevamente",Toast.LENGTH_LONG);
                t2.show();
                goLogInScreen();
            }
        }else{
            goLogInScreen();
        }
    }
    private void goLogInScreen() {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    public void logOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreen();
                }else{
                    //Toast.makeText(getApplicationContext(),"No se pudo cerrar sesion logout",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void revoke(){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreen();
                }else{
                    //Toast.makeText(getApplicationContext(),"No se pudo cerrar sesion revoke",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String terminos="Terminos y Condiciones";
        //noinspection SimplifiableIfStatement
        if (id== R.id.action_settings1) {
            Toast t = Toast.makeText(Lista.this,"Terminos y Condiciones",Toast.LENGTH_SHORT);
            t.show();
            FragmentManager fragmentManager = getSupportFragmentManager();
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
            return true;
        }else if(id== R.id.action_settings){
            Toast t = Toast.makeText(Lista.this,"Cerrando Sesion",Toast.LENGTH_SHORT);
            t.show();
            revoke();
            logOut();
            return true;
        }else{
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Fragment1 fragment1=new Fragment1(account.getEmail().toString());
        Fragment2 fragment2=new Fragment2(account.getEmail().toString());
        Fragment3 fragment3=new Fragment3(account.getEmail().toString());
        adapter.addFragment(fragment1, "General");
        adapter.addFragment(fragment2, "Ofertas");
        adapter.addFragment(fragment3, "Descuentos");
        viewPager.setAdapter(adapter);
    }
    public void CerrarSesion(){
        ConexionLocal usdbh =new ConexionLocal(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null) {
            db.execSQL("DELETE FROM Usuario where 1=1");
            db.close();
            Intent inici = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(inici);
            finish();
        }else{
            Toast t = Toast.makeText(Lista.this,"No se logro abrir la base de datos local",Toast.LENGTH_SHORT);
            t.show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast t = Toast.makeText(Lista.this,"No se logro abrir la base de datos local",Toast.LENGTH_SHORT);
        t.show();
    }
}
