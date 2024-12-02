import okio.FileSystem
import okio.Path
import okio.buffer

inline fun FileSystem.readByUtf8Line(path: Path, block: (String) -> Unit) {
    source(path).use { source ->
        source.buffer().use {
            while (true) {
                block(it.readUtf8Line() ?: break)
            }
        }
    }
}
