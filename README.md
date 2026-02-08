# acidify-codec

[LagrangeCodec](https://github.com/LagrangeDev/LagrangeCodec) 的 Kotlin 绑定，支持 JVM 和 Native (Windows / macOS / Linux) 平台。

在 JVM 平台，该模块使用 JNA 调用 LagrangeCodec 的动态链接库；在 Native 平台，该模块通过 Kotlin C Interop 静态链接 LagrangeCodec 的静态库。JVM 平台的 jar 文件已经将动态库文件包含在内。

项目的 `src/jvmMain/resources` 目录下包含了各个平台的动态库文件；`src/nativeInterop/lib` 目录下包含了各个平台的静态库文件。静态库文件编译自 [Wesley-Young/LagrangeCodec](https://github.com/Wesley-Young/LagrangeCodec) 仓库，对原仓库的构建逻辑进行了一些调整以适应 Kotlin/Native 链接静态库的需求。

## 已知问题

在链接 `mingwX64` 目标的应用程序时，**必须**指定如下的 `linkerOpts`：

```
-Wl,-Bstatic -lstdc++ -lgcc -Wl,-Bdynamic
```

否则程序启动时会报错，提示缺失 `libstdc++-6.dll`。
