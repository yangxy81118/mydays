package me.yxy.mydays

import java.util.regex.Pattern

fun test(){
    val pattern = "[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]"
    val isMatch = Pattern.matches(pattern,"ab🐰nih你好")
    println(isMatch)
}

