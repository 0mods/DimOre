package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("self")
@Serializable
data class SelfEntry(
    override val functions: List<SimpleFunction> = emptyList(),
    override val requires: List<SimpleRequire> = emptyList()
): SimpleEntry()

@SerialName("item")
@Serializable
data class ItemEntry(
    val id: String,
    override val functions: List<SimpleFunction> = emptyList(),
    override val requires: List<SimpleRequire> = emptyList()
): SimpleEntry()
