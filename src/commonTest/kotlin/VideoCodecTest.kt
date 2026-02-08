package org.ntqqrev.acidify.codec

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.test.Test

class VideoCodecTest {
    @Test
    fun videoGetInfoTest() {
        val file = SystemFileSystem.source(Path(testResourcesPath, "video", "test.mp4"))
            .buffered()
            .readByteArray()
        val info = getVideoInfo(file)
        println("Video info: $info")
    }

    @Test
    fun videoGetFirstFrameTest() {
        val file = SystemFileSystem.source(Path(testResourcesPath, "video", "test.mp4"))
            .buffered()
            .readByteArray()
        val jpg = getVideoFirstFrameJpg(file)
        SystemFileSystem.sink(Path(testOutputPath, "test-video-first-frame.jpg"))
            .buffered()
            .use { it.write(jpg) }
    }
}