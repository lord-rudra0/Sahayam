package com.rudra.sahayam.util

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observe(): Flow<Boolean>
}
