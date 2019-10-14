package com.example.whatsclone.activity.conversas.telaConversas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.whatsclone.R;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.MensagemObj;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MensagemAdapter extends ArrayAdapter<MensagemObj> {

    private Context mContext;
    private ArrayList<MensagemObj> mensagens;

    public MensagemAdapter(Context context, ArrayList<MensagemObj> objects) {
        super(context, 0 , objects);
        this.mContext = context;
        this.mensagens = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(mensagens != null)
        {
            //recuperar os dados do usuario remetente
            Preferencias preferencias = new Preferencias(mContext);
            String usuarioQueEnvia = preferencias.getIdentificador();
            //recuperar a mensagem
            MensagemObj mensagemObj = mensagens.get(position);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

            if(usuarioQueEnvia.equals(mensagemObj.getIdUsuario()))
                view = inflater.inflate(R.layout.item_mensagem_direita,parent,false);
            else
                view = inflater.inflate(R.layout.item_mensagem_esquerdo,parent,false);

            TextView textoMensagem = view.findViewById(R.id.mensagem_esquerdo);

            textoMensagem.setText(mensagemObj.getMensagem());
        }


        return view;

    }
}
