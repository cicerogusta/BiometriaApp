package com.example.biometriaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val verifyFingerprintButton = findViewById<Button>(R.id.verifyFingerprintButton)
        val textView = findViewById<TextView>(R.id.textView)

        // Solicitar permissão para usar a autenticação biométrica
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.USE_BIOMETRIC), 1)
        }

        val executor: Executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = androidx.biometric.BiometricPrompt(
            this,
            executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // A autenticação biométrica de impressão digital foi bem-sucedida
                    // Você pode permitir o acesso ao aplicativo ou realizar ação desejada aqui
                    textView.text = "BIOMETRIA ACIONADA"

                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Ocorreu um erro na autenticação biométrica de impressão digital
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // A autenticação biométrica de impressão digital falhou
                }
            })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação de Impressão Digital")
            .setSubtitle("Toque no sensor de impressão digital")
            .setNegativeButtonText("Cancelar")
            .build()

        verifyFingerprintButton.setOnClickListener {
            // Solicitar autenticação biométrica de impressão digital
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
