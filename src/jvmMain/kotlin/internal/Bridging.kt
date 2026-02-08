package org.ntqqrev.acidify.codec.internal

import com.sun.jna.Memory
import kotlinx.io.Buffer
import kotlinx.io.readByteArray

internal fun processAudio(input: ByteArray, func: AudioCodecFunction): ByteArray {
    val inputMem = Memory(input.size.toLong())
    inputMem.write(0, input, 0, input.size)
    val outputBuffer = Buffer()
    val callback = AudioCodecCallback { _, p, len ->
        val byteArray = p!!.getByteArray(0, len)
        outputBuffer.write(byteArray)
    }
    val result = func(inputMem, input.size, callback, null)
    require(result == 0) { "audio processing failed with code $result" }
    inputMem.clear()
    return outputBuffer.readByteArray()
}