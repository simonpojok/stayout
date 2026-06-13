package com.example.stayout.data.local.converter

import com.example.stayout.domain.model.FacilityCategoryDomainModel
import com.example.stayout.domain.model.FacilityDomainModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

@Serializable(with = StoredFacilitySerializer::class)
private data class StoredFacility(
    val id: String = "",
    val name: String,
)

private object StoredFacilitySerializer : KSerializer<StoredFacility> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("StoredFacility") {
            element<String>("id")
            element<String>("name")
        }

    override fun serialize(
        encoder: Encoder,
        value: StoredFacility,
    ) {
        (encoder as JsonEncoder).encodeJsonElement(
            buildJsonObject {
                put("id", value.id)
                put("name", value.name)
            },
        )
    }

    override fun deserialize(decoder: Decoder): StoredFacility =
        when (val element = (decoder as JsonDecoder).decodeJsonElement()) {
            // Old format stored each facility as a plain string
            is JsonPrimitive -> StoredFacility(id = "", name = element.content)
            // New format stores each facility as {"id":"...","name":"..."}
            is JsonObject ->
                StoredFacility(
                    id = element["id"]?.jsonPrimitive?.content ?: "",
                    name = element["name"]?.jsonPrimitive?.content ?: "",
                )
            else -> StoredFacility(id = "", name = "")
        }
}

@Serializable
private data class StoredFacilityCategory(
    val id: String = "",
    val name: String,
    val items: List<StoredFacility>,
)

private val converterJson = Json { ignoreUnknownKeys = true }
private val categoryListSerializer = ListSerializer(StoredFacilityCategory.serializer())

fun encodeFacilities(facilities: List<FacilityCategoryDomainModel>): String =
    converterJson.encodeToString(
        categoryListSerializer,
        facilities.map { category ->
            StoredFacilityCategory(
                id = category.id,
                name = category.name,
                items = category.facilities.map { StoredFacility(id = it.id, name = it.name) },
            )
        },
    )

fun decodeFacilities(encoded: String): List<FacilityCategoryDomainModel> =
    converterJson.decodeFromString(categoryListSerializer, encoded).map { category ->
        FacilityCategoryDomainModel(
            id = category.id,
            name = category.name,
            facilities = category.items.map { FacilityDomainModel(id = it.id, name = it.name) },
        )
    }
