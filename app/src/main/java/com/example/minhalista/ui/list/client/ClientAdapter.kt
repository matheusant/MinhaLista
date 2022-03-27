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

    var onItemClick: ((entity: ClientEntity) -> Unit)? = null
    var onItemLongClick: ((entity: ClientEntity) -> Unit)? = null

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
            val total = String.format("%.2f", client.total).replace(".", ",")
            tvCliName.text = client.name
            tvCliDate.text = client.date
            tvCliTotal.text = "R$ $total"

            itemView.setOnClickListener {
                onItemClick?.invoke(client)
            }

            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(client)
                true
            }
        }
    }
}