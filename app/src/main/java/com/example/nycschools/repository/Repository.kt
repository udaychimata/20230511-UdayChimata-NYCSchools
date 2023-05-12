package com.example.nycschools.repository

import com.example.nycschools.response.NYCSchoolItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.nycschools.api.Service

class Repository @Inject constructor(
    private val service: Service,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getNYSSchoolsList(): List<NYCSchoolItem> {
        return withContext(ioDispatcher) {
            service.getNYSSchoolsList()
        }
    }
}