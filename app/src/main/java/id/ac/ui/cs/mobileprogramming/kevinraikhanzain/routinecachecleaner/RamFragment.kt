package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import kotlinx.android.synthetic.main.fragment_ram.*


class RamFragment : Fragment() {
    private lateinit var viewModel: RamFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ram, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                activity!!.application
            )
        ).get(RamFragmentViewModel::class.java)



        viewModel.getAllStorage()?.observe(this, Observer {
            it.forEach {
                textViewRamFree.text = "Free : " + StringUtils.bytesToHuman(it.free ?: 0)
                textViewRamUsed.text = "Used : " + StringUtils.bytesToHuman(it.used ?: 0)
                textViewRamTotal.text = "Total : " + StringUtils.bytesToHuman(it.total ?: 0)
                determinateBarRam.progress = ((it.used!!.toDouble() / it.total!!) * 100).toInt()
            }
        })
        viewModel.calculateInternalStorage()
    }
}