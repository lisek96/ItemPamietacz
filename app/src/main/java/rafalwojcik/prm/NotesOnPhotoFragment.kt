package rafalwojcik.prm

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding

class NotesOnPhotoFragment(var photoBitMap : Bitmap) : Fragment() {
    private lateinit var binding: NotesOnPhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NotesOnPhotoFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
            binding.paintView.photo = photoBitMap
        }.root
    }
}