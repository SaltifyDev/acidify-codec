package org.ntqqrev.acidify.codec

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.test.Test

class AudioCodecTest {
    @Test
    fun audioToPcmTest() {
        val file = SystemFileSystem.source(Path(testResourcesPath,  "audio", "test.mp3"))
            .buffered()
            .readByteArray()
        val pcm = audioToPcm(file)
        println("PCM size: ${pcm.size}")
        println("duration: ${calculatePcmDuration(pcm)}")
        SystemFileSystem.sink(Path(testOutputPath, "test-pcm-24000.pcm"))
            .buffered()
            .use { it.write(pcm) }
    }
}