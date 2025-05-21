package util

import java.io.File

fun writeToFile(text: String) {
    try {
        // Create a new File object
        val file = File("Disk/txt/1.txt")

        // Open the file for writing with auto-close
        file.bufferedWriter().use { bufferedWriter ->
            // Write the text to the file
            bufferedWriter.write(text)
        }

    } catch (e: Exception) {
        println("Error writing to file: ${e.message}")
        e.printStackTrace()
    }
}