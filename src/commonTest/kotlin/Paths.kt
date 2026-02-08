package org.ntqqrev.acidify.codec

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

val testResourcesPath = Path("src/commonTest/resources")

val testOutputPath = Path("build/testOutput").also {
    if (!SystemFileSystem.exists(it)) {
        SystemFileSystem.createDirectories(it)
    }
}