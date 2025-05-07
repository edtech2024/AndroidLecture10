package com.example.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemUid(@SerialName("uid")val uid: String)
