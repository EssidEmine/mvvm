package com.test.fdj.utils

inline fun <T> List<T>.filterByText(text: String, predicate: (T) -> String): List<T> {
    return this.filter { item ->
        predicate(item).contains(text, ignoreCase = true)
    }
}
