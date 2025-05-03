package util

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Opens a file manager dialog on the user system to choose an image file.
 * Accepts jpg, png, and gif files.
 *
 * @return The absolute path of the selected image file as a String. If no file is selected,
 * returns an empty string.
 */
fun openFileChooser(): String {
    val fileChooser = JFileChooser()
    fileChooser.fileFilter =
        FileNameExtensionFilter("Image Files", "jpg", "png", "gif")
    val returnValue = fileChooser.showOpenDialog(null)

    val imagePath: String

    // Check if a file was selected
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.selectedFile
        imagePath = selectedFile.absolutePath
    } else {
        imagePath = ""
    }

    return imagePath
}