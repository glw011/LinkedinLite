package util

import java.io.File

fun readLinesFromFile(): List<String> {
    return File("Disk/txt/1.txt").readLines() // Replace with your file path
}