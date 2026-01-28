package com.algorithmlx.dimore.init.config

import kotlinx.serialization.Polymorphic

@Polymorphic
@Target(AnnotationTarget.PROPERTY)
annotation class JsonComment(val comments: Array<String>, val multiline: Boolean = false)
