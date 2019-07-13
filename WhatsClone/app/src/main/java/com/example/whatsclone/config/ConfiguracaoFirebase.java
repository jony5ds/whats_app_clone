package com.example.whatsclone.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebase() {

        if(referenciaFireBase == null)
         referenciaFireBase  = FirebaseDatabase.getInstance().getReference();

        return referenciaFireBase;
    }

    public static FirebaseAuth getAutenticacao()
    {
        if(auth == null)
            auth = FirebaseAuth.getInstance();
        return auth;
    }
}
