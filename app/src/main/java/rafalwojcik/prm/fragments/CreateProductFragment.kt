package rafalwojcik.prm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.database.DatabaseGiver
import rafalwojcik.prm.databinding.CreateProductBinding
import rafalwojcik.prm.databinding.GalleryBinding
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.FileService
import java.io.File
import kotlin.concurrent.thread

class CreateProductFragment : Fragment() {

    private lateinit var binding : CreateProductBinding
    private lateinit var parentActivity : MainActivity
    private lateinit var product: Product

    fun setProduct(product: Product) : CreateProductFragment{
        this.product = product
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentActivity = activity as MainActivity
        return CreateProductBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logo.setImageBitmap(FileService.getBitmapFromFile(File(product.filePath)))
        binding.add.setOnClickListener{
            addProduct()
            parentActivity.goMainFragment()
            Toast.makeText(context, "Product added!",
                Toast.LENGTH_SHORT).show();
        }
        binding.createPickFromMapButton.setOnClickListener {
            parentActivity.goMapFragment(product, PickPlaceFragment.Mode.NEW)
        }
        binding.address.text = product.productAddress
    }

    fun addProduct(){
        var product = Product(product.filePath, binding.name.text.toString(), binding.address.text.toString())
        parentActivity.addProduct(product)
    }

}