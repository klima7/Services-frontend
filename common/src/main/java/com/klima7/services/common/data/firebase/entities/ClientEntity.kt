package com.klima7.services.common.data.firebase.entities

data class ClientEntity(
    var info: InfoEntity = InfoEntity(),
) {

    data class InfoEntity(
        val name: String? = null,
        val phone: String? = null,
    )

}
