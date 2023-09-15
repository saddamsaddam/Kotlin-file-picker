package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.MimeMessage

class UserEmailSending: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SendEmailActivity()
                }
            }
        }
    }
}

@Composable
fun SendEmailActivity() {
    val context = LocalContext.current

    var from by remember { mutableStateOf(TextFieldValue("sohojitofficial@gmail.com")) }
    var to by remember { mutableStateOf(TextFieldValue("ce18046@mbstu.ac.bd")) }
    var subject by remember { mutableStateOf(TextFieldValue("sre")) }
    var body by remember { mutableStateOf(TextFieldValue("hhul")) }
    var isLoading by remember { mutableStateOf(false) }
    //sendEmailWithAttachment("sohojitofficial@gmail.com","ce18046@mbstu.ac.bd","ixqveomlojfjyivh","saddamnvn","$uri");

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("From:")
        BasicTextField(
            value = from,
            onValueChange = {
                from = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text("To:")
        BasicTextField(
            value = to,
            onValueChange = {
                to = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Subject:")
        BasicTextField(
            value = subject,
            onValueChange = {
                subject = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Body:")
        BasicTextField(
            value = body,
            onValueChange = {
                body = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                isLoading = true
                sendEmail(context, from.text, to.text, subject.text, body.text)
                isLoading = false
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Text("Send")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

fun sendEmail(context: Context, from: String, to: String, subject: String, body: String) {
    GlobalScope.launch(Dispatchers.IO) {
        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.port"] = "587"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(from, "ixqveomlojfjyivh")
            }
        })

        try {
            val message = MimeMessage(session)
            message.setFrom(from)
            message.setRecipients(Message.RecipientType.TO, to)
            message.setSubject(subject)
            message.setText(body)

            val transport = session.getTransport("smtp")
            transport.connect()
            transport.sendMessage(message, message.getAllRecipients())
            transport.close()
            Log.d("Email", "Email sent successfully")
        } catch (e: MessagingException) {
            Log.e("Email", "Failed to send email", e)
        }
    }
}
