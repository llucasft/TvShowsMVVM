package com.practice.tvshows_mvvm.util

fun String.removeHtmlTags(): String {
    return this.replace("<[^>]*>".toRegex(), "")
}

fun String.limitSummary(characters: Int): String {
    if (this.length > characters) {
        val firstCharacter = 0
        return "${this.substring(firstCharacter, characters)}..."
    }
    return this
}