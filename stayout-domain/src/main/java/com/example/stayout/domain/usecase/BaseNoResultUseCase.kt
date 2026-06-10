package com.example.stayout.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseNoResultUseCase<in Params>(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseUseCase<Params, Unit>(dispatcher)
