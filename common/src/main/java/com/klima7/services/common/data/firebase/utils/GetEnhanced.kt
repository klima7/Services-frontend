package com.klima7.services.common.data.firebase.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.klima7.services.common.platform.BaseApp

fun DocumentReference.getEnhanced(): Task<DocumentSnapshot> {
    val internetAvailable = BaseApp.getInstance().isInternetAvailable()

    return if(internetAvailable) {
        get(Source.DEFAULT)
    }
    else {
        get(Source.CACHE)
    }
}

fun Query.getEnhanced(): Task<QuerySnapshot> {
    val internetAvailable = BaseApp.getInstance().isInternetAvailable()

    return if(internetAvailable) {
        get(Source.DEFAULT)
    }
    else {
        get(Source.CACHE)
    }
}
