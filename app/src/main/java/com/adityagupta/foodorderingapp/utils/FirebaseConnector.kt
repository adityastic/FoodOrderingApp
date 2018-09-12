package com.adityagupta.foodorderingapp.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


object FirebaseConnector {

    private var db: FirebaseFirestore? = null
    private var mStorageRef: StorageReference? = null

    val firebaseFirestore: FirebaseFirestore
        get() {
            var data = db
            if (data == null)
                data = FirebaseFirestore.getInstance()
            db = data
            return data
        }

    val storageReference: StorageReference
        get() {
            var data = mStorageRef
            if (data == null)
                data = FirebaseStorage.getInstance().reference
            mStorageRef = data
            return data
        }
}
