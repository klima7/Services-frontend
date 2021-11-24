package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.firebase.FirebaseSource
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo
import com.klima7.services.common.models.Failure

class ClientsRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun createClientAccount(): Outcome<Failure, None> {
        return firebase.clientsDao.createClientAccount()
    }

    suspend fun getClient(uid: String): Outcome<Failure, Client> {
        return firebase.clientsDao.getClient(uid)
    }

    suspend fun setClientInfo(info: ClientInfo): Outcome<Failure, None> {
        return firebase.clientsDao.setClientInfo(info)
    }

    suspend fun deleteAccount(): Outcome<Failure, None> {
        return firebase.clientsDao.deleteAccount()
    }

}