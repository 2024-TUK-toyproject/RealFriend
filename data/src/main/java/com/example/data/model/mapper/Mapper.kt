package com.example.data.model.mapper

import com.example.data.model.response.UserIdDTO
import com.example.data.model.response.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<Any>.toDomain(): Flow<T> = map { value ->
    val domain: Any = when (value) {
        is UserIdDTO -> value.asDomain()
//        isStatsDto -> value.asDomain()
        else -> throw NotImplementedError(
            "value ($value)) does not implement asDomain() function."
        )
    }
    return@map domain as T
}
