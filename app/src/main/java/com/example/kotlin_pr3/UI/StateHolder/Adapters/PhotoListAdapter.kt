package com.example.kotlin_pr3.UI.StateHolder.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_pr3.R

class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    // Список строк, каждая из которых представляет дату фотографии
    private var data: List<String> = emptyList()

    // Класс ViewHolder, используемый для отображения каждого элемента списка
    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TextView для отображения даты фотографии
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    // Создание нового экземпляра ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        // Инфлейтинг (создание) View из XML-макета для каждого элемента списка
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        // Установка текста в TextView, соответствующего дате фотографии
        holder.dateTextView.text = data[position]
    }

    // Возвращает общее количество элементов в списке
    override fun getItemCount(): Int = data.size

    // Обновление списка данных для отображения в RecyclerView
    fun submitList(list: List<String>) {
        // Обновление внутреннего списка данных
        data = list
        // Уведомление адаптера о том, что данные изменились
        notifyDataSetChanged()
    }

    // Дополнительные методы могут быть добавлены здесь
}
