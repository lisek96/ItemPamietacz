package rafalwojcik.prm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafalwojcik.prm.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    fun actOnOptionSelected(item : MenuItem) : Boolean{
        when(item.itemId){
            R.id.load_photo -> print("xx")
            R.id.take_photo -> openPhotoCaptureFragment()
        }
        return true
    }

    fun openPhotoCaptureFragment(){
        (activity as MainActivity).goTakePhoto()
    }
}