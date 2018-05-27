package micupongt.com.micupongt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by anton on 21/01/2018.
 */
@SuppressLint("ValidFragment")
public class Fragment1 extends Fragment {
    static ListView listaelementos;
    static ArrayList<Contenedor> elementos=new ArrayList<Contenedor>();
    public static String usuario;
    public Fragment1(String usuario){
        this.usuario=usuario;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista, container, false);
        listaelementos=(ListView)rootView.findViewById(R.id.section_lista1);
        descargarInfo();
        listaelementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                try{
                    Intent i = new Intent(getContext(), Contenido.class);
                    i.putExtra("fecha",elementos.get(position).getFecha().toString());
                    i.putExtra("titulo",elementos.get(position).getTitulo().toString());
                    i.putExtra("contenido",elementos.get(position).getNoticia().toString());
                    i.putExtra("imagen",elementos.get(position).getRuta().toString()+elementos.get(position).getImagen().toString());
                    i.putExtra("empresa",elementos.get(position).getEmpresa().toString());
                    i.putExtra("direccion",elementos.get(position).getDireccion().toString());
                    i.putExtra("codigo",elementos.get(position).getCodigo().toString());
                    i.putExtra("imagen2",elementos.get(position).getRuta().toString()+elementos.get(position).getImagen2().toString());
                    startActivity(i);
                }catch(Exception e){
                    Toast t4 = Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT);
                    t4.show();
                }
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
            datos.put("usu_sesion",usuario);
            String respuesta = conexion.execute(datos).get();
            String [] lista=respuesta.split("~");
            for(int i=1;i<lista.length;i++){
                JSONObject object = new JSONObject(lista[i].toString());
                Contenedor elemento1=new Contenedor(object.getString("fecha"),object.getString("titulo"),object.getString("contenido"),object.getString("ruta"),object.getString("imagen"),object.getString("empresa"),object.getString("direccion"),object.getString("codigo"),object.getString("imagen2"));
                elementos.add(elemento1);
            }
        }catch(Exception e){
        }
        listaelementos.setAdapter(new NoticiaAdapter(getContext()));
    }
    private static class NoticiaAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        //TextView fecha_v,titulo_v,empresa_v,ruta_v;
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
            smartImageView=null;
            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.activity_main_item1,null);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen1);
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            String url=elementos.get(position).getRuta().toString()+elementos.get(position).getImagen().toString();
            smartImageView.setImageUrl(url,rect);
            return viewGroup;
        }
    }
}
