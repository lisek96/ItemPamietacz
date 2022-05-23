package rafalwojcik.prm.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.database.DatabaseGiver
import rafalwojcik.prm.databinding.ProductItemBinding
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.FileService
import java.io.File
import kotlin.concurrent.thread

class ProductAdapter(
    private var onProductClick: (Product)->Unit,
    private var context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    private var products : MutableList<Product> = mutableListOf()

    init {
        products = DatabaseGiver.getDb(context).productDao().getAll().toMutableList()
    }

    class ProductViewHolder(
        private val binding : ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productAddress.text = product.productAddress
            binding.productCreatedDate.text = product.createdDate
            binding.productName.text = product.productName
            binding.productLogo.setImageBitmap(FileService.getBitmapFromFile(File(product.filePath)))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productItemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(productItemBinding).apply {
            productItemBinding.root.setOnClickListener {
                var product = products[this.adapterPosition]
                onProductClick(product)
            }
            productItemBinding.root.setOnLongClickListener {
                var product = products[this.adapterPosition]
                products.remove(product)
                notifyItemRemoved(this.adapterPosition)
                Toast.makeText(context, "Product deleted!",
                    Toast.LENGTH_SHORT).show();
                thread{
                    DatabaseGiver.getDb(context).productDao().delete(product)
                }
                true
            }
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =  holder.bind(products[position])

    override fun getItemCount(): Int = products.size

    fun add(product: Product){
        products.add(product)
        this.notifyItemInserted(products.indexOf(product))
        thread {
            val productDao = DatabaseGiver.getDb(context).productDao()
            productDao.insert(product)
        }
    }

}