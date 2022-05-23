package rafalwojcik.prm.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.adapter.GalleryAdapter
import rafalwojcik.prm.databinding.GalleryBinding
import rafalwojcik.prm.databinding.MainFragmentBinding
import java.io.File

class GalleryFragment : Fragment() {
    private lateinit var binding: GalleryBinding
    private lateinit var parentActivity : MainActivity
    private val galleryAdapter by lazy {
        GalleryAdapter(this::goTakeNotes, parentActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentActivity = activity as MainActivity
        return GalleryBinding.inflate(
            inflater, container, false
        ).apply {
           binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = galleryAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this.context, 2)
        }
    }

    private fun goTakeNotes(file: File){
        parentActivity.goTakeNoteOnPhoto(file)
    }
}