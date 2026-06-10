package com.example.stayout.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseNoParamUseCase<out Result>(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseUseCase<Unit, Result>(dispatcher) {
    suspend operator fun invoke(): Result = invoke(Unit)

    final override suspend fun execute(params: Unit): Result = execute()

    protected abstract suspend fun execute(): Result
}
