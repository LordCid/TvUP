package com.example.omapp.common

interface Mapper<out T, in R> {
    fun map(input: R): T
}