package com.example.stayout.domain.model

data class UserDomainModel(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressDomainModel,
    val phone: String,
    val website: String,
    val company: CompanyDomainModel,
)
