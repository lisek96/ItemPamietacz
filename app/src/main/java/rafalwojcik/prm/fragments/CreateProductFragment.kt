package rafalwojcik.prm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var filePath : String

    fun setFilePath(value : String) : CreateProductFragment{
        filePath = value
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
            binding.logo.setImageBitmap(FileService.getBitmapFromFile(File(filePath)))
            binding.add.setOnClickListener {  addProduct() }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun addProduct(){
        val productDao = DatabaseGiver.getDb(parentActivity).productDao()
        thread {
            productDao.insert(
                Product(
                    filePath!!, binding.name.text.toString(), binding.address.text.toString()
                )
            )
        }
        thread{
            println(productDao.getAll())
        }
    }

}