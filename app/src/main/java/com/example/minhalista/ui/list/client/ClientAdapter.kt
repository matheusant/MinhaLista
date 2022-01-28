package com.example.minhalista.ui.list.client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minhalista.R
import com.example.minhalista.data.db.entity.ClientEntity

class ClientAdapter(
    private val client: List<ClientEntity>
) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_list_holder, parent, false)

        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bindHolder(client[position])
    }

    override fun getItemCount() = client.size

    inner class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCliName: TextView = itemView.findViewById(R.id.tv_cli_name)
        val tvCliDate: TextView = itemView.findViewById(R.id.tv_cli_date)
        val tvCliTotal: TextView = itemView.findViewById(R.id.tv_cli_total)

        fun bindHolder(client: ClientEntity) {
            tvCliName.text = client.name
            tvCliDate.text = client.date
            tvCliTotal.text = client.total.toString()
        }
    }
}