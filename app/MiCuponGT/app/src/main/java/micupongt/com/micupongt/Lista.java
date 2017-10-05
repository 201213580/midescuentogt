package micupongt.com.micupongt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.Uri;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static ListView listaelementos;
    static ArrayList<Contenedor> elementos=new ArrayList<Contenedor>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //noinspection SimplifiableIfStatement
        if (id== R.id.action_settings1) {
            Toast t = Toast.makeText(Lista.this,"Terminos y Condiciones",Toast.LENGTH_SHORT);
            t.show();
            return true;
        }else if(id== R.id.action_settings){
            Toast t = Toast.makeText(Lista.this,"Cerrando Sesion",Toast.LENGTH_SHORT);
            t.show();
            CerrarSesion();
            return true;
        }else{

        }

        return super.onOptionsItemSelected(item);
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


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }



        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_lista, container, false);
            listaelementos=(ListView)rootView.findViewById(R.id.section_lista);
            descargarInfo();
            listaelementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inici = new Intent(getContext(), Info.class);
                    startActivity(inici);
                }
            });
            return rootView;
        }
        private void descargarInfo(){
            elementos.clear();
            try{
                Cone conexion=new Cone();
                JSONObject datos =new JSONObject();
                datos.put("accion","cargar");
                String respuesta = conexion.execute(datos).get();
                String [] lista=respuesta.split("-");
                for(int i=1;i<lista.length;i++){
                    JSONObject object = new JSONObject(lista[i].toString());
                    Contenedor elemento1=new Contenedor(object.getString("fecha"),object.getString("titulo"),object.getString("contenido"),object.getString("ruta"),object.getString("imagen"));
                    elementos.add(elemento1);
                }
            }catch(Exception e){

            }
            listaelementos.setAdapter(new NoticiaAdapter(getContext()));
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Todos los Cupones";
                case 1:
                    return "Cupones del Dia";
                case 2:
                    return "Mis Cupones";
            }
            return null;
        }

    }
    private static class NoticiaAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView fecha_v,titulo_v,noticia_v,ruta_v;
        public NoticiaAdapter(Context applicationContext) {
            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return elementos.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.activity_main_item1,null);
            fecha_v=(TextView)viewGroup.findViewById(R.id.fech);
            titulo_v=(TextView)viewGroup.findViewById(R.id.tit);
            noticia_v=(TextView)viewGroup.findViewById(R.id.conten);
            ruta_v=(TextView)viewGroup.findViewById(R.id.link);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen1);
            fecha_v.setText(elementos.get(position).getFecha().toString());
            titulo_v.setText(elementos.get(position).getTitulo().toString());
            noticia_v.setText(elementos.get(position).getNoticia().toString());
            ruta_v.setText("Generar Cupon");
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            String url=elementos.get(position).getImagen().toString();
            if(position==0){
                smartImageView.setImageRawId(R.mipmap.lacasadel,rect);
            }else if(position==1){
                smartImageView.setImageRawId(R.mipmap.pizza,rect);
            }else if(position==2){
                smartImageView.setImageRawId(R.mipmap.mega,rect);
            }else if(position==3){
                smartImageView.setImageRawId(R.mipmap.pollo,rect);
            }else if(position==4){
                smartImageView.setImageRawId(R.mipmap.descarga,rect);
            }else if(position==5){
                smartImageView.setImageRawId(R.mipmap.cafe,rect);
            }
            return viewGroup;
        }
    }
}
