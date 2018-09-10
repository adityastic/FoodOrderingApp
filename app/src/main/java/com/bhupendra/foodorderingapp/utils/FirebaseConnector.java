package com.bhupendra.foodorderingapp.utils;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConnector {
    private static FirebaseFirestore db;
    private static StorageReference mStorageRef;

    public static FirebaseFirestore getFirebaseFirestore()
    {
        if(db == null)
            db = FirebaseFirestore.getInstance();
        return db;
    }

    public static StorageReference getStorageReference()
    {
        if(mStorageRef == null)
            mStorageRef = FirebaseStorage.getInstance().getReference();
        return mStorageRef;
    }
}
