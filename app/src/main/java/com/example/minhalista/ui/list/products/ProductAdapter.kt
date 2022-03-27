package com.example.minhalista.ui.list.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minhalista.R
import com.example.minhalista.data.db.entity.ProductsEntity

class ProductAdapter(
    private val products: List<ProductsEntity>
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClick: ((entity: ProductsEntity) -> Unit)? = null
    var onItemLongClick: ((entity: ProductsEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.products_list_holder, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindHolder(products[position])
    }

    override fun getItemCount() = products.size

    fun grandTotal() : Double {
        var totalPrice = 0.0
        for (i in products.indices) {
            totalPrice += products[i].price
        }
        return totalPrice
    }

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvProdName: TextView = itemView.findViewById(R.id.tv_prod_name)
        private val tvProdPrice: TextView = itemView.findViewById(R.id.tv_prod_price)

        fun bindHolder(prods: ProductsEntity) {
            val price = String.format("%.2f", prods.price).replace(".", ",")
            tvProdName.text = prods.name
            tvProdPrice.text = "R$ $price"

            itemView.setOnClickListener {
                onItemClick?.invoke(prods)
            }

            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(prods)
                true
            }
        }
    }
}