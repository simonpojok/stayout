package com.example.stayout.presentation.mapper

interface BasePresentationToDomainMapper<in Presentation, out Domain> {
    fun map(model: Presentation): Domain
}
