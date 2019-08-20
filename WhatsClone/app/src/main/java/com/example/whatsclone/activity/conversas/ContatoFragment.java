package com.example.whatsclone.activity.conversas;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.whatsclone.R;
import com.example.whatsclone.databinding.FragmentContatoBinding;

import java.util.ArrayList;


public class ContatoFragment extends Fragment {

    private FragmentContatoBinding mBinding;
    private ArrayAdapter mAdapter;
    private ArrayList<String> mContatos;

    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contato, container, false);

        //Intanciar objetos
        mContatos = new ArrayList<>();

        //Teste
        mContatos.add("Maria Julia");
        mContatos.add("Ana Clara");
        mContatos.add("Jeferson Silva");
        mContatos.add("Rodolpho Oliveira");
        mContatos.add("Ewerton Ribeiro");

        //Monta listView e adapter
        mAdapter = new ArrayAdapter(getActivity(),R.layout.lista_contato,mContatos);

        mBinding.listaContatos.setAdapter(mAdapter);

        return mBinding.getRoot();
    }


}
