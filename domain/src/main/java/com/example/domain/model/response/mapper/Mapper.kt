package com.example.domain.model.response.mapper

import com.example.domain.model.response.UserIdResponse
import com.example.domain.model.response.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<Any>.toDomain(): Flow<T> = map { value ->
    val domain: Any = when (value) {
        is UserIdResponse -> value.asDomain()
//        isStatsDto -> value.asDomain()
        else -> throw NotImplementedError(
            "value ($value)) does not implement asDomain() function."
        )
    }
    return@map domain as T
}
