package com.bortxapps.respository

import android.util.Log
import com.bortxapps.data.TransactionPoko
import com.bortxapps.infrastructure.datasource.TransactionsLocalDataSource
import com.bortxapps.infrastructure.datasource.TransactionsRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//TODO we can generalize this with an interface
class TransactionRepository @Inject constructor(
        private val dataBase: TransactionsLocalDataSource,
        private val remoteService: TransactionsRemoteService,
) {

    /**
     * Get the information of a remote service. If the service is not available
     * we use a local storage copy
     *
     * Remarks: Because this is the repository (the realm of the data) we don't apply any business
     * logic here
     */
    suspend fun getAll(): Flow<List<TransactionPoko>> {
        val remoteData = remoteService.getAll()

        if (remoteData.any()) {
            Log.i("Transaction test", "Saving data in local storage")
            dataBase.addBatch(remoteData)
        }

        //TODO this is a very simple implementation, in a real world implementation we can user the
        //room as a data base to show old transactions while the system is loading the new ones
        return flow {
            emit(remoteData.ifEmpty {
                Log.i("Transaction test", "Retrieving data from local storage")
                dataBase.getAll()
            })
        }
    }
}