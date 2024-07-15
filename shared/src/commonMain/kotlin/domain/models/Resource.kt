package domain.models

sealed class Resource<T, E> {

    fun getOrNull() : T? {
        return when(this) {
            is Success -> data
            else -> null
        }
    }

    fun errorOrNull() : E? {
        return when(this) {
            is Error -> data
            else -> null
        }
    }

    data class Success<T, E>(val data: T) : Resource<T, E>()
    class Error<T, E>(val data : E) : Resource<T, E>()
}