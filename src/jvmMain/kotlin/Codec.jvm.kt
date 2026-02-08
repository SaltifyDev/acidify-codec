package org.ntqqrev.acidify.codec

import com.sun.jna.Memory
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference
import org.ntqqrev.acidify.codec.internal.VideoInfoStruct
import org.ntqqrev.acidify.codec.internal.codecLibrary
import org.ntqqrev.acidify.codec.internal.processAudio
import kotlin.time.Duration.Companion.seconds

actual fun audioToPcm(input: ByteArray) =
    processAudio(input, codecLibrary::audio_to_pcm)

actual fun silkDecode(input: ByteArray) =
    processAudio(input, codecLibrary::silk_decode)

actual fun silkEncode(input: ByteArray) =
    processAudio(input, codecLibrary::silk_encode)

actual fun getVideoInfo(videoData: ByteArray): VideoInfo {
    val lib = codecLibrary
    val inputMem = Memory(videoData.size.toLong())
    inputMem.write(0, videoData, 0, videoData.size)
    val infoStruct = VideoInfoStruct()
    val result = lib.video_get_size(inputMem, videoData.size, infoStruct)
    require(result == 0) { "videoGetSize failed with code $result" }
    inputMem.clear()
    return VideoInfo(
        width = infoStruct.width,
        height = infoStruct.height,
        duration = infoStruct.duration.seconds
    )
}

actual fun getVideoFirstFrameJpg(videoData: ByteArray): ByteArray {
    val lib = codecLibrary
    val inputMem = Memory(videoData.size.toLong())
    inputMem.write(0, videoData, 0, videoData.size)
    val outputPtr = PointerByReference()
    val outputLenPtr = IntByReference()
    val result = lib.video_first_frame(inputMem, videoData.size, outputPtr, outputLenPtr)
    require(result == 0) { "videoFirstFrame failed with code $result" }
    val outputLen = outputLenPtr.value
    val outputMem = outputPtr.value
    val byteArray = ByteArray(outputLen)
    outputMem.read(0, byteArray, 0, outputLen)
    inputMem.clear()
    return byteArray
}