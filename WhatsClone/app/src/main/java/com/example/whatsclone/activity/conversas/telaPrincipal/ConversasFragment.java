package com.example.whatsclone.activity.conversas.telaPrincipal;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.whatsclone.R;
import com.example.whatsclone.activity.conversas.adapter.ConversasAdaper;
import com.example.whatsclone.activity.conversas.telaConversas.ConversaActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.FragmentConversasBinding;
import com.example.whatsclone.helper.Base64Custom;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.ConversaObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Criado por Jônatas Barbosa
 * em 02/08/2019
 */
public class ConversasFragment extends Fragment {

    private FragmentConversasBinding mBinding;
    private ArrayAdapter<ConversaObj> mAdapterConversas;
    private ArrayList<ConversaObj> mConversas;
    private DatabaseReference mFirebase;
    private ValueEventListener mValueEventListenerConversas;


    public ConversasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversas, container, false);
        mConversas = new ArrayList<>();
        mAdapterConversas = new ConversasAdaper(getActivity(),mConversas);
        mBinding.listaConversas.setAdapter(mAdapterConversas);

        //Recuperar dados do usuario
        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();
        //Recuperar conversas do Firebase
        mFirebase = ConfiguracaoFirebase.getFirebase().child("conversas")
        .child(idUsuarioLogado);

        mValueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mConversas.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    ConversaObj conversa = dados.getValue(ConversaObj.class);
                    mConversas.add(conversa);
                }

                mAdapterConversas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mBinding.listaConversas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                irParaConversa(position);

            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebase.addListenerForSingleValueEvent(mValueEventListenerConversas);

    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebase.removeEventListener(mValueEventListenerConversas);
    }

    private void irParaConversa(int position)
    {
        Intent intent = new Intent(getActivity(), ConversaActivity.class);
        ConversaObj conversa = mConversas.get(position);
        intent.putExtra("nome",conversa.getNome() );
        String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
        intent.putExtra("email", email );
        startActivity(intent);
    }


}
