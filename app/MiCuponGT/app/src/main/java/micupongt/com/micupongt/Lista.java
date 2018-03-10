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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
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
    public static GoogleSignInAccount account;
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
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            account=result.getSignInAccount();
            try {
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
                Toast t = Toast.makeText(Lista.this,"Bienvenido "+account.getDisplayName(),Toast.LENGTH_SHORT);
                t.show();
                mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                setupViewPager(mViewPager);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
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
            dialogo.setMensaje(terminos);
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
