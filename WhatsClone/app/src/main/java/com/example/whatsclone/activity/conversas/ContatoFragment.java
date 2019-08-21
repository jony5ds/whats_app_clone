package com.example.whatsclone.activity.conversas;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.whatsclone.R;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.FragmentContatoBinding;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.ContatoObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ContatoFragment extends Fragment {

    private FragmentContatoBinding mBinding;
    private ArrayAdapter mAdapter;
    private ArrayList<ContatoObj> mContatos;
    private DatabaseReference mFirebase;
    private ValueEventListener mValueEventListenerContatos;

    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebase.removeEventListener(mValueEventListenerContatos);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contato, container, false);

        //Intanciar objetos
        mContatos = new ArrayList<>();

        //Monta listView e adapter
        Preferencias preferencias = new Preferencias(getActivity());
        String usuario = preferencias.getIdentificador();
        mAdapter = new ArrayAdapter(getActivity(),R.layout.lista_contato,mContatos);

        mBinding.listaContatos.setAdapter(mAdapter);

        //recuperar dados do firebase
        mFirebase = ConfiguracaoFirebase.getFirebase()
                    .child("contatos")
                    .child(usuario);
        mValueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // limpar contatos
                mContatos.clear();
                //listar contatos
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    ContatoObj contato = data.getValue(ContatoObj.class);
                    mContatos.add(contato);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFirebase.addValueEventListener(mValueEventListenerContatos);

        return mBinding.getRoot();
    }


}
