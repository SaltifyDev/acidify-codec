package org.ntqqrev.acidify.codec

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageCodecTest {
    @Test
    fun imageDecodeTest() {
        listOf(
            "png",
            "jpg",
            "gif",
            "webp",
            "bmp",
            "tiff"
        ).forEach { ext ->
            val file =
                SystemFileSystem.source(Path(testResourcesPath, "image", "test.$ext"))
                    .buffered()
                    .readByteArray()
            val info = getImageInfo(file)
            if (info.format == ImageFormat.JPEG) {
                assertEquals("jpg", ext)
            } else {
                assertEquals(info.format.toString().lowercase(), ext)
            }
            assertEquals(info.width, 96)
            assertEquals(info.height, 96)
        }
    }
}