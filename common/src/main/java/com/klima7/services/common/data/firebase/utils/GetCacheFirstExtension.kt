package com.klima7.services.common.data.firebase.utils

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source

fun DocumentReference.getCacheFirst(): Task<DocumentSnapshot> {
    return get(Source.CACHE).continueWithTask { task ->
        var result: DocumentSnapshot? = null
        try {
            result = task.result
        } catch(e: Exception) {}
        if (task.isSuccessful && result != null) {
            val t = TaskCompletionSource<DocumentSnapshot>()
            t.setResult(result)
            t.task
        } else
            get(Source.SERVER)
    }
}
