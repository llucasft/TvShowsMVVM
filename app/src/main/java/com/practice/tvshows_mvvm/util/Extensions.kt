package com.practice.tvshows_mvvm.util

fun String.removeHtmlTags(): String {
    return this.replace("<[^>]*>".toRegex(), "")
}