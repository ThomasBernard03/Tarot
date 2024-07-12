package domain.models

sealed class Resource<T, E> {
    data class Success<T, E>(val data: T) : Resource<T, E>()
    class Error<T, E>(val data : E) : Resource<T, E>()
}