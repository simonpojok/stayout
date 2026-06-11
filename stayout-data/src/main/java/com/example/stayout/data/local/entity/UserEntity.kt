package com.example.stayout.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    @Embedded(prefix = "address_") val address: AddressEntity,
    @Embedded(prefix = "company_") val company: CompanyEntity,
)
