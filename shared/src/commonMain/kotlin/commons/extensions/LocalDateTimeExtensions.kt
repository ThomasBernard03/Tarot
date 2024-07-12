package commons.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTimeNow() : LocalDateTime {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return now
}