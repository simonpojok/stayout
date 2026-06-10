package com.example.stayout.data.remote.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ImageGalleryDataModelTest {
    @Test
    fun `toUrl concatenates https prefix and suffix`() {
        val model =
            ImageGalleryDataModel(
                prefix = "res.cloudinary.com/hostelworld/image/upload/",
                suffix = "v1/propertyimages/1/100/36.jpg",
            )
        assertEquals(
            "https://res.cloudinary.com/hostelworld/image/upload/v1/propertyimages/1/100/36.jpg",
            model.toUrl(),
        )
    }

    @Test
    fun `toUrl with empty suffix returns prefix only`() {
        val model = ImageGalleryDataModel(prefix = "example.com/img/", suffix = "")
        assertEquals("https://example.com/img/", model.toUrl())
    }

    @Test
    fun `toUrl with empty prefix returns suffix only`() {
        val model = ImageGalleryDataModel(prefix = "", suffix = "path/to/image.jpg")
        assertEquals("https://path/to/image.jpg", model.toUrl())
    }
}
