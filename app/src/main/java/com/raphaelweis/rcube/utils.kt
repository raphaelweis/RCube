package com.raphaelweis.rcube

fun formatSolveTime(timeMs: Long): String {
    val seconds = timeMs / 1000
    val milliseconds = timeMs % 1000
    return if (timeMs == 0L) {
        "0.00"
    } else {
        "${seconds}.${(milliseconds / 10).toString().padStart(2, '0')}"
    }
}

