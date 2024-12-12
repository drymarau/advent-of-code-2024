import okio.FileSystem
import okio.Path
import okio.buffer

fun FileSystem.readByUtf8LineToSequence(path: Path): Sequence<String> = sequence {
    source(path).use { source ->
        source.buffer().use {
            while (true) yield(it.readUtf8Line() ?: break)
        }
    }
}
