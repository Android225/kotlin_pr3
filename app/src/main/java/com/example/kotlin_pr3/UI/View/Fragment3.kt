package com.example.kotlin_pr3.UI.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_pr3.UI.StateHolder.Adapters.PhotoListAdapter
import com.example.kotlin_pr3.databinding.Fragment3Binding
import java.io.File

class PhotosOverviewFragment : Fragment() {

    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding!!

    private val photoListManager = PhotoListManager()

    // Создание представления фрагмента
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment3Binding.inflate(inflater, container, false)
        initializeUI()
        return binding.root
    }

    // Инициализация пользовательского интерфейса
    private fun initializeUI() {
        setupRecyclerView()
        setupBackButton()
    }

    // Настройка RecyclerView и его адаптера
    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoListManager.createAdapter()
        }
    }

    // Настройка обработчика кнопки возврата
    private fun setupBackButton() {
        binding.buttonBackFragment3.setOnClickListener {
            navigateBack()
        }
    }

    // Логика перехода назад
    private fun navigateBack() {
        // Реализация навигации назад
    }

    // Загрузка и отображение списка фотографий
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoListManager.loadPhotos { dates ->
            updatePhotoList(dates)
        }
    }

    // Обновление списка фотографий в адаптере
    private fun updatePhotoList(dates: List<String>) {
        (binding.recyclerView.adapter as? PhotoListAdapter)?.submitList(dates)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Класс-помощник для управления списком фотографий
    private class PhotoListManager {
        private val photoDateFileName = "Date.txt"

        fun createAdapter(): PhotoListAdapter = PhotoListAdapter()

        fun loadPhotos(callback: (List<String>) -> Unit) {
            // Загрузка данных о фотографиях
        }
    }
}
