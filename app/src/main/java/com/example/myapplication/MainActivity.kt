package com.example.myapplication
import DatabaseHelper
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import isTableExists
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    var link=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpForm()
                    //ImagePickerDemo() // Add the image picker here
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) } // Variable to store the selected file URI
    var fileByteArray by remember { mutableStateOf<ByteArray?>(null) } // Variable to store the file content as ByteArray
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            // Do something with the file UR
            selectedFileUri = uri
            showToast(context, selectedFileUri.toString())
            // Convert the selected file to ByteArray
            // Convert the selected file to ByteArray and store it in fileByteArray
            fileByteArray = uri?.let { context.contentResolver.openInputStream(it)?.readBytes() }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {

                filePickerLauncher.launch(arrayOf("image/*"))

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Select File", color = Color.White)
        }
        Button(
            onClick = {
                val dbHelper = DatabaseHelper(context)
                if (isTableExists(context, "filetable")) {
                    if (selectedFileUri != null && fileByteArray != null) {
                        // Now you can use the fileByteArray as needed
                        val name = email
                        val byteArray = fileByteArray!! // Non-null assertion (you should handle nullability appropriately)
                        // Use name and byteArray for further processing or storage
                        dbHelper.insertUser(name,byteArray)

                        showToast(context, "Selected File: $selectedFileUri, ByteArray Size: ${byteArray.size}")
                    } else {
                        showToast(context, "No file selected")
                    }
                } else {
                    showToast(context,"Table not exist")
                }




            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Sign Up", color = Color.White)
        }
        Button(
            onClick = {
                val dbHelper = DatabaseHelper(context)
                if (isTableExists(context, "filetable")) {

                    val dataList = dbHelper.getAllDataFromFileTable()

                    for ((name, fileData) in dataList) {
                        // Process the name and fileData as needed
                        val uri = saveFileDataAsImage(context, name + ".jpg", fileData)

                        if (uri != null) {
                            showToast(context, "Image saved to: $uri")
                            println("Image saved to: $uri")
                        } else {
                            showToast(context, "Failed to save image")
                        }
                        //showToast(context, "Name: $name, File Data Size: ${fileData.size}")
                    }
                    // showToast(context,userNames.toString())
                } else {
                    showToast(context,"Table not exist")
                }



            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Show File", color = Color.White)
        }
    }

}

fun saveFileDataAsImage(context: Context, fileName: String, fileData: ByteArray): Uri? {
    val imagesDirectory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "YourDirectoryName")

    if (!imagesDirectory.exists()) {
        imagesDirectory.mkdirs()
    }

    val imageFile = File(imagesDirectory, fileName)

    return try {
        val fos = FileOutputStream(imageFile)
        fos.write(fileData)
        fos.close()
        Uri.fromFile(imageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Preview
@Composable
fun PreviewSignUpForm() {
    MyApplicationTheme {
        SignUpForm()
    }
}
