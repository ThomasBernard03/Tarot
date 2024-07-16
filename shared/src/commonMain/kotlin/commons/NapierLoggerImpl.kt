package commons

import io.github.aakira.napier.Napier

class NapierLoggerImpl : Logger {
    override fun v(tag: String?, message: String) {
        Napier.v(tag = tag) { message }
    }

    override fun d(tag: String?, message: String) {
        Napier.d(tag = tag) { message }
    }

    override fun i(tag: String?, message: String) {
        Napier.i(tag = tag) { message }
    }

    override fun w(tag: String?, message: String) {
        Napier.w(tag = tag) { message }
    }

    override fun e(tag: String?, message: String) {
        Napier.e(tag = tag) { message }
    }

    override fun e(tag: String?, message: String, throwable: Throwable) {
        Napier.e(tag = tag, throwable = throwable) { message }
    }

    override fun e(tag: String?, throwable: Throwable) {
        Napier.e(tag = tag, throwable = throwable) { throwable.message ?: "No message" }
    }

    override fun e(throwable: Throwable) {
        Napier.e(throwable = throwable) { throwable.message ?: "No message" }
    }
}