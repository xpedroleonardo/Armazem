package com.example.armazem

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

class SeparationScreenActivity : AppCompatActivity() {
  private val requestCodeCameraPermission = 1001
  private lateinit var cameraSource: CameraSource
  private lateinit var barcodeDetector: BarcodeDetector

  private var contexto: AppCompatActivity = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_separation_screen)

    println("#### INÍCIO SEPARATION ####")

    val paramsHome = intent.extras

    if (ContextCompat.checkSelfPermission(
        contexto, android.Manifest.permission.CAMERA
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      askForCameraPermission()
    } else {
      setupControls()
    }

    val botao: Button = findViewById(R.id.buscar_produtos)

    botao.setOnClickListener {
      val params = Bundle()
      params.putAll(paramsHome)

      val proximaTela = Intent(this, SearchScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }
  }

  private fun setupControls() {
    barcodeDetector =
      BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

    cameraSource = CameraSource.Builder(this, barcodeDetector)
      .setRequestedPreviewSize(1080, 1920)
      .setAutoFocusEnabled(true) //you should add this feature
      .build()

    val cameraView: SurfaceView = findViewById(R.id.scanner)

    cameraView.getHolder().addCallback(object : SurfaceHolder.Callback {
      @SuppressLint("MissingPermission")
      override fun surfaceCreated(holder: SurfaceHolder) {
        try {
          //Start preview after 1s delay
          cameraSource.start(holder)
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }

      @SuppressLint("MissingPermission")
      override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
      ) {
        try {
          cameraSource.start(holder)
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }

      override fun surfaceDestroyed(holder: SurfaceHolder) {
        cameraSource.stop()
      }
    })


    barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
      override fun release() {
        Toast.makeText(contexto, "Scanner has been closed", Toast.LENGTH_SHORT)
          .show()
      }

      override fun receiveDetections(detections: Detector.Detections<Barcode>) {
        val barcodes = detections.detectedItems
        if (barcodes.size() == 1) {
          val scannedValue = barcodes.valueAt(0).rawValue

          //Don't forget to add this line printing value or finishing activity must run on main thread
          runOnUiThread {
//        cameraSource.stop()

            if (scannedValue == "7896319420546") {
              Toast.makeText(contexto, "RUA ENCONTRADA", Toast.LENGTH_SHORT)
                .show()
              cameraSource.stop()
            }

            Toast.makeText(
              contexto,
              "value- $scannedValue",
              Toast.LENGTH_SHORT
            ).show()
//            finish()
          }
        } else {
          Toast.makeText(
            contexto,
            "Qrcode não identificado",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    })
  }

  private fun askForCameraPermission() {
    ActivityCompat.requestPermissions(
      contexto,
      arrayOf(android.Manifest.permission.CAMERA),
      requestCodeCameraPermission
    )
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        setupControls()
      } else {
        Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    cameraSource.stop()
  }
}