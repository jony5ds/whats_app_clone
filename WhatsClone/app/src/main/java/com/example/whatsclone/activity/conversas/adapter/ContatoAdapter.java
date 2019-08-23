package com.example.whatsclone.activity.conversas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.whatsclone.R;
import com.example.whatsclone.databinding.ListaContatoBinding;
import com.example.whatsclone.obj.ContatoObj;

import java.util.ArrayList;

public class ContatoAdapter extends ArrayAdapter<ContatoObj> {

    private ArrayList<ContatoObj> mContatos;
    private Context mContext;



    public ContatoAdapter( Context context,  ArrayList<ContatoObj> objects) {
        super(context,0, objects);
        this.mContatos = objects;
        this.mContext = context;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = null;
        //Verifica se a lista está vazia
        if (mContatos != null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

            //monta a view apartir do xml
            view = inflater.inflate(R.layout.lista_contato,parent,false);

            //pegando Id
          TextView nomeContato = view.findViewById(R.id.nome);
          TextView emailContato = view.findViewById(R.id.email);

          ContatoObj contatoObj = mContatos.get(position);
          nomeContato.setText(contatoObj.getNome());
          emailContato.setText(contatoObj.getEmail());


        }
        return view;
    }
}
