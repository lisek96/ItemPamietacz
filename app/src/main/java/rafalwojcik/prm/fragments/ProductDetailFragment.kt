package rafalwojcik.prm.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.database.DatabaseGiver
import rafalwojcik.prm.databinding.CreateProductBinding
import rafalwojcik.prm.databinding.GalleryBinding
import rafalwojcik.prm.databinding.ProductDetailBinding
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.FileService
import java.io.File
import kotlin.concurrent.thread
class ProductDetailFragment : Fragment(){

    private lateinit var binding : ProductDetailBinding
    private lateinit var parentActivity : MainActivity
    private lateinit var product: Product

    fun setProduct(product: Product) : ProductDetailFragment{
        this.product = product
        return this
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentActivity = activity as MainActivity
        return ProductDetailBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
            binding.detailAddress.text = product.productAddress
            binding.detailCreateDate.text = product.createdDate
            binding.detailName.text = Editable.Factory.getInstance().newEditable(product.productName)
            binding.logo.setImageBitmap(FileService.getBitmapFromFile(File(product.filePath)))
            binding.buttonUpdate.setOnClickListener {
                updateProduct()
                parentActivity.goMainFragment()
                Toast.makeText(context, "Product updated!",
                    Toast.LENGTH_SHORT).show();
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailChooseAddress.setOnClickListener {
            parentActivity.goMapFragment(product, PickPlaceFragment.Mode.EDIT)
        }
    }

    private fun updateProduct(){
        product.productAddress = binding.detailAddress.text.toString()
        product.productName = binding.detailName.text.toString()
        parentActivity.updateProduct(product)
    }
}