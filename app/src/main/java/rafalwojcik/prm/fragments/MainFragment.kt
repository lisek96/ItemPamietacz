package rafalwojcik.prm.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import rafalwojcik.prm.R
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var parentActivity : MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = activity as MainActivity
        return MainFragmentBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
            binding.bottomNavigationView.setOnItemSelectedListener { actOnOptionSelected(it) }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun actOnOptionSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.load_photo -> {
                askForExternalStoragePermission()
                parentActivity.goOpenGallery() }
            R.id.take_photo -> {
                askForCameraPermission()
                parentActivity.goTakePhoto()
            }
        }
        return true
    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(parentActivity,
            arrayOf(Manifest.permission.CAMERA),
            200
        );
    }

    private fun askForExternalStoragePermission(){
        ActivityCompat.requestPermissions(parentActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            200
        );
    }
}