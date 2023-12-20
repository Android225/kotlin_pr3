package com.example.kotlin_pr3.UI.View

import android.Manifest
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin_pr3.R
import com.example.kotlin_pr3.databinding.Fragment2Binding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding
    private var imageCapture: ImageCapture? = null

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH:mm:ss"
        private const val FILENAME = "Date.txt"
        val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = Fragment2Binding.inflate(inflater, container, false).apply {
            // Назначение обработчиков событий для кнопок
            btnCapture.setOnClickListener { takePhoto() }
            buttonBackFragment2.setOnClickListener { findNavController().navigate(R.id.action_fragment2_to_fragment1) }
        }

        requestPermissions()
        return binding.root
    }

    private fun requestPermissions() {
        // Запуск процесса запроса необходимых разрешений
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        // Проверка всех полученных разрешений
        if (permissions.values.all { it }) {
            startCamera()
        } else {
            Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        // Получение Future для ProcessCameraProvider
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            try {
                // Получение CameraProvider и связывание его с жизненным циклом приложения
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                bindCameraLifecycle(cameraProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Camera start failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindCameraLifecycle(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build().also {
            // Настройка источника для предпросмотра камеры
            it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder().build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // Привязка используемых компонентов камеры к жизненному циклу приложения
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        // Создание уникального имени файла и настройка метаданных для сохраняемого изображения
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }

        // Проверка и создание директории для сохранения фотографий
        val picturesDir = File(requireContext().getExternalFilesDir(null), "Pictures/CameraX-Image")
        if (!picturesDir.exists()) picturesDir.mkdirs()

        // Запись временной метки фотографии в файл для отслеживания
        val file = File(picturesDir, FILENAME)
        file.appendText("$name\n")

        // Настройка опций для сохранения изображения
        val outputOptions = ImageCapture.OutputFileOptions.Builder(requireContext().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build()

        // Захват изображения и обработка результата
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                Toast.makeText(requireContext(), "Photo capture succeeded", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
