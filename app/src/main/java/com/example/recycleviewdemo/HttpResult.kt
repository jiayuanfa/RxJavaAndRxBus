package com.example.recycleviewdemo

/**
 * 使用泛型进行返回
 */
data class HttpResult<T> (
    val code: Int,
    val message: String,
    val data: T
)