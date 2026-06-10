package com.example.stayout.presentation.mapper

interface BaseDomainToPresentationMapper<in Domain, out Presentation> {
    fun map(model: Domain): Presentation
}
