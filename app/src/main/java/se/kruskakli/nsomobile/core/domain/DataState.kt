package se.kruskakli.nsomobile.core.domain

import androidx.compose.runtime.Composable

//
// See: https://youtu.be/LM6B03sCFxY?si=JNc3mKPUe3sF4orz
// "Useful Wrapper class That I use on a Daily Basis! - RequestState()"
//

sealed class DataState<out T> {
    object Idle : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Failure(val exception: Throwable) : DataState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure

    fun getSuccesData() = (this as Success).data
    fun getSuccessDataOrNull() : T? {
        return try {
            getSuccesData()
        } catch (e: Exception) {
            null
        }
    }

    fun getFailureMessage() = (this as Failure).exception.message
    fun getFailureMessageOrNull() : String? {
        return try {
            getFailureMessage()
        } catch (e: Exception) {
            null
        }
    }

    @Composable
    fun DisplayResult(
        onIdle: (@Composable () -> Unit)? = null,
        onSuccess: @Composable (T) -> Unit,
        onFailure: @Composable (String) -> Unit,
        onLoading: @Composable () -> Unit
    ) {
        when (this) {
            is Idle -> onIdle?.invoke()
            is Loading -> onLoading()
            is Success -> onSuccess(data)
            is Failure -> onFailure(exception.message ?: "Unknown error")
        }
    }
}
