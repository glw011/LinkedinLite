package util

fun openFileChooser(): String {
    val os = System.getProperty("os.name").lowercase()

    return try {
        when {
            os.contains("linux") -> {
                when {
                    isCommandAvailable("kdialog") -> {
                        ProcessBuilder("kdialog", "--getopenfilename")
                            .start().inputStream.bufferedReader().readLine().orEmpty()
                    }
                    isCommandAvailable("zenity") -> {
                        ProcessBuilder("zenity", "--file-selection")
                            .start().inputStream.bufferedReader().readLine().orEmpty()
                    }
                    else -> {
                        println("No file picker available: please install 'kdialog' or 'zenity'")
                        ""
                    }
                }
            }

            os.contains("mac") -> {
                ProcessBuilder("osascript", "-e", "POSIX path of (choose file)")
                    .start().inputStream.bufferedReader().readLine().orEmpty()
            }

            os.contains("windows") -> {
                val command = listOf(
                    "powershell", "-Command",
                    "Add-Type -AssemblyName System.Windows.Forms; " +
                            "\$dialog = New-Object System.Windows.Forms.OpenFileDialog; " +
                            "\$dialog.Filter = '...'; " +
                            "if (\$dialog.ShowDialog() -eq 'OK') { Write-Output \$dialog.FileName }"
                )

                ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start().inputStream.bufferedReader().readLine().orEmpty()
            }

            else -> ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

private fun isCommandAvailable(command: String): Boolean {
    return try {
        ProcessBuilder("which", command)
            .start()
            .waitFor() == 0
    } catch (e: Exception) {
        false
    }
}
