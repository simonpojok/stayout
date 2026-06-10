package com.example.stayout.data.mapper

interface BaseDataToDomainMapper<in Data, out Domain> {
    fun map(model: Data): Domain
}
