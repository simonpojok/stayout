package com.example.stayout.data.mapper

interface BaseDomainToDataMapper<in Domain, out Data> {
    fun map(model: Domain): Data
}
