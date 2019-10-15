package com.example.whatsclone.activity.conversas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.whatsclone.R;
import com.example.whatsclone.obj.ContatoObj;
import com.example.whatsclone.obj.ConversaObj;

import java.util.ArrayList;

public class ConversasAdaper extends ArrayAdapter<ConversaObj> {

    private ArrayList<ConversaObj> mConversas;
    private Context mContext;


    public ConversasAdaper(Context context, ArrayList<ConversaObj> objects) {

        super(context, 0, objects);
        this.mConversas = objects;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (mConversas != null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_conversas,parent,false);

            TextView titulo = (TextView) view.findViewById(R.id.titulo);
            TextView ultimaMensagem = (TextView) view.findViewById(R.id.subtitulo);

            ConversaObj conversa = mConversas.get(position);
            titulo.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());

        }
        return view;
    }
}
