package com.example.armazem

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

class SearchScreenActivity : AppCompatActivity() {
  private val requestCodeCameraPermission = 1001
  private lateinit var cameraSource: CameraSource
  private lateinit var barcodeDetector: BarcodeDetector
  private var contexto: AppCompatActivity = this
  private lateinit var leitorQrcode: SurfaceHolder

  private lateinit var EAN: String
  private lateinit var RUA: String
  private lateinit var NUMERO: String
  private lateinit var ANDAR: String
  private var QUANTIDADE: Int = 0
  private lateinit var PRODUTO: String
  private var QUANTIDADEPRODUTOS: Int = 0
  private var ULTIMO: Boolean = false

  private lateinit var statusProcura: TextView
  private lateinit var botao: Button
  private lateinit var produtoProcura: TextView
  private lateinit var nomeProduto: TextView
  private lateinit var proximaLocalizacao: String

  private lateinit
  var separacaoAtual: HashMap<String, HashMap<String, String>>
  private var flagQrcode: Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_screen)

    println("#### INÍCIO SEARCH ####")

    val paramsSeparation = intent.extras
    separacaoAtual =
      paramsSeparation!!.getSerializable("Separation") as HashMap<String, HashMap<String, String>>

    botao = findViewById(R.id.continuar)
    produtoProcura = findViewById(R.id.produto_procura)
    nomeProduto = findViewById(R.id.nome_produto)
    statusProcura = findViewById(R.id.status_procura)

    val separacao = HashMap<String, String>()

    if (ContextCompat.checkSelfPermission(
        contexto, android.Manifest.permission.CAMERA
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      askForCameraPermission()
    } else {
      setupControls()
    }
    botao.visibility = Button.INVISIBLE

    proximaLocalizacao = "BUSCARUA"
    for ((index, x) in separacaoAtual.keys.withIndex()) {
      QUANTIDADEPRODUTOS++
      if (index == 0) {
        val detalhes = separacaoAtual[x] as HashMap<String, String>

        EAN = x
        RUA = detalhes["RUA"].toString()
        NUMERO = detalhes["NUMERO"].toString()
        ANDAR = detalhes["ANDAR"].toString()
        QUANTIDADE = detalhes["QUANTIDADE"]?.toInt() ?: 0
        PRODUTO = detalhes["PRODUTO"].toString()

        produtoProcura.text = "Vá até a rua $RUA\nPara chegar até o produto:\n"
        nomeProduto.text = PRODUTO

        statusProcura.text = "Escaneie o Qrcode"
        statusProcura.setTextColor(Color.parseColor("#000000"))
      }

      if (separacaoAtual.keys.size == 1) {
        ULTIMO = true
      }
    }

    for (x in separacaoAtual.keys) {
      val values = separacaoAtual[x] as HashMap<String, String>
      val junta =
        "${values["RUA"].toString()}:${values["NUMERO"].toString()}:${values["ANDAR"].toString()}:${values["PRODUTO"].toString()}:${values["QUANTIDADE"].toString()}"

      separacao[x] = junta
    }

    Toast.makeText(this, "Quantidade de Produtos: $QUANTIDADEPRODUTOS", Toast.LENGTH_LONG).show()

    botao.setOnClickListener {
      var flag = 1
      flagQrcode = true

      if (proximaLocalizacao == "BUSCARUA" && flag == 1) {

        for ((index, x) in separacaoAtual.keys.withIndex()) {
          if (index == 0) {
            val detalhes = separacaoAtual[x] as HashMap<String, String>

            EAN = x
            RUA = detalhes["RUA"].toString()
            NUMERO = detalhes["NUMERO"].toString()
            ANDAR = detalhes["ANDAR"].toString()
            QUANTIDADE = detalhes["QUANTIDADE"]?.toInt() ?: 0
            PRODUTO = detalhes["PRODUTO"].toString()

            produtoProcura.text = "Vá até a rua $RUA\nPara chegar até o produto:\n"
            nomeProduto.text = PRODUTO
            statusProcura.text = "Escaneie o Qrcode"
            statusProcura.setTextColor(Color.parseColor("#000000"))
          }
        }

        if (separacaoAtual.keys.size == 1) {
          ULTIMO = true
        }

        botao.visibility = Button.INVISIBLE
        cameraSource.start(leitorQrcode)
      } else if (proximaLocalizacao == "NUMERO" && flag == 1) {
        produtoProcura.text =
          "Vá até a coluna número $NUMERO\nPara chegar até o produto:\n"
        nomeProduto.text = PRODUTO

        statusProcura.text = "Escaneie o Qrcode"
        statusProcura.setTextColor(Color.parseColor("#000000"))
        botao.visibility = Button.INVISIBLE

        flag++
        cameraSource.start(leitorQrcode)
      } else if (proximaLocalizacao == "ANDAR" && flag == 1) {
        produtoProcura.text = "Vá até o $ANDAR° andar\nPara chegar até o produto:\n"
        nomeProduto.text = PRODUTO

        statusProcura.text = "Escaneie o Qrcode"
        statusProcura.setTextColor(Color.parseColor("#000000"))
        botao.visibility = Button.INVISIBLE

        flag++
        cameraSource.start(leitorQrcode)
      } else if (proximaLocalizacao == "ESCANEAR" && flag == 1) {
        produtoProcura.text =
          "Você está em frente do produto\n Escaneie $QUANTIDADE vezes"
        nomeProduto.text = PRODUTO

        statusProcura.text = "Escaneie o Qrcode"
        statusProcura.setTextColor(Color.parseColor("#000000"))
        botao.visibility = Button.INVISIBLE

        flag++
        cameraSource.start(leitorQrcode)
      } else if (proximaLocalizacao == "SEPARADOS" && flag == 1) {
        val params = Bundle()

        params.putAll(paramsSeparation)
        params.putSerializable("Remove", separacao)

        val proximaTela = Intent(this, InventoryScreenActivity::class.java)
        proximaTela.putExtras(params)
        startActivity(proximaTela)

        finish()
      }
    }
  }

  private fun setupControls() {
    barcodeDetector =
      BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
    cameraSource = CameraSource.Builder(this, barcodeDetector)
      .setRequestedPreviewSize(1080, 1920)
      .setAutoFocusEnabled(true)
      .build()

    val cameraView: SurfaceView = findViewById(R.id.scanner)

    cameraView.getHolder().addCallback(object : SurfaceHolder.Callback {
      @SuppressLint("MissingPermission")
      override fun surfaceCreated(holder: SurfaceHolder) {
        try {
          leitorQrcode = holder
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

          runOnUiThread {

            if (scannedValue == "R${RUA}" && proximaLocalizacao == "BUSCARUA" && flagQrcode) {
              flagQrcode = false

              proximaLocalizacao = "NUMERO"
              statusProcura.text = "Você está na rua certa!\nVerifique o próximo passo"
              statusProcura.setTextColor(Color.parseColor("#35BD20"))

              botao.visibility = Button.VISIBLE

              cameraSource.stop()

            } else if (scannedValue == "R${RUA}-N${NUMERO}" && proximaLocalizacao == "NUMERO" && flagQrcode) {
              flagQrcode = false

              proximaLocalizacao = "ANDAR"
              statusProcura.text = "Você está na coluna correta!\nVerifique o próximo passo"
              statusProcura.setTextColor(Color.parseColor("#35BD20"))
              botao.visibility = Button.VISIBLE

              cameraSource.stop()

            } else if (scannedValue == "R${RUA}-N${NUMERO}-${ANDAR}A" && proximaLocalizacao == "ANDAR" && flagQrcode) {
              flagQrcode = false

              proximaLocalizacao = "ESCANEAR"
              statusProcura.text = "Você está no andar correto!\nVerifique o próximo passo"
              statusProcura.setTextColor(Color.parseColor("#35BD20"))

              botao.visibility = Button.VISIBLE

              cameraSource.stop()

            } else if (scannedValue == EAN && proximaLocalizacao == "ESCANEAR" && flagQrcode) {
              flagQrcode = false
              QUANTIDADE--

              if (QUANTIDADE == 0 && ULTIMO) {
                statusProcura.text = "Separação Finalizada!\nIndo para o relatório da separação"
                statusProcura.setTextColor(Color.parseColor("#35BD20"))
                botao.text = "Ver relatório"

                proximaLocalizacao = "SEPARADOS"

              } else if (QUANTIDADE == 0 && QUANTIDADEPRODUTOS > 1) {
                statusProcura.text = "Produto Separado!\nIndo para o próximo produto"
                statusProcura.setTextColor(Color.parseColor("#35BD20"))
                separacaoAtual.remove(EAN)

                proximaLocalizacao = "BUSCARUA"

              } else {
                statusProcura.text = "Produto escaneado!\nVerifique o próximo passo"
                statusProcura.setTextColor(Color.parseColor("#35BD20"))
              }

              botao.visibility = Button.VISIBLE
              cameraSource.stop()

            } else if (flagQrcode) {
              statusProcura.text = "Você está no lugar Errado!!!\nEscaneie novamente..."
              statusProcura.setTextColor(Color.parseColor("#C72020"))
            }
          }
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