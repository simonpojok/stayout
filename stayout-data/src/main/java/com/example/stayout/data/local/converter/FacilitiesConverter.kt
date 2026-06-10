package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

fun encodeFacilities(facilities: List<FacilityCategoryDomainModel>): String =
    buildJsonArray {
        facilities.forEach { category ->
            add(
                buildJsonObject {
                    put("name", category.name)
                    put("items", buildJsonArray { category.facilities.forEach { add(it) } })
                },
            )
        }
    }.toString()

fun decodeFacilities(json: String): List<FacilityCategoryDomainModel> =
    Json.parseToJsonElement(json).jsonArray.map { element ->
        val obj = element.jsonObject
        FacilityCategoryDomainModel(
            name = obj.getValue("name").jsonPrimitive.content,
            facilities = obj.getValue("items").jsonArray.map { it.jsonPrimitive.content },
        )
    }
