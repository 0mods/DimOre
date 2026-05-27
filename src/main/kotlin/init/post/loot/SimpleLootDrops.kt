package com.algorithmlx.dimore.init.post.loot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("self")
@Serializable
data class SelfDrop(
    override val functions: List<SimpleFunction> = emptyList(),
    override val requires: List<SimpleRequire> = emptyList()
): SimpleDrop()

@SerialName("item")
@Serializable
data class ItemDrop(
    val id: String,
    override val functions: List<SimpleFunction> = emptyList(),
    override val requires: List<SimpleRequire> = emptyList()
): SimpleDrop()
