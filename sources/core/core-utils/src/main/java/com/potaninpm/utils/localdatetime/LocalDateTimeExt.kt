package com.potaninpm.utils.localdatetime

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/** Converts [LocalDateTime] to epoch seconds using the system default time zone. */
fun LocalDateTime.localDateTimeToEpochSeconds(): Long =
    this.atZone(ZoneId.systemDefault()).toEpochSecond()

/** Converts epoch seconds to [LocalDateTime] using the system default time zone. */
fun Long.epochSecondsToLocalDateTime(): LocalDateTime =
    Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
