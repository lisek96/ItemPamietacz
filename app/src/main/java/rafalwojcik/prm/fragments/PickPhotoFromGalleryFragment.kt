package rafalwojcik.prm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding
import rafalwojcik.prm.databinding.PickPhotoFromGalleryFragmentBinding

class PickPhotoFromGalleryFragment : Fragment()
{

    private lateinit var parentActivity : MainActivity
    private lateinit var binding : PickPhotoFromGalleryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = requireActivity() as MainActivity
        return PickPhotoFromGalleryFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
        }.root
    }
}