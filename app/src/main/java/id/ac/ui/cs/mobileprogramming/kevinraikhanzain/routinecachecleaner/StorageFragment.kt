package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_storage.*

class StorageFragment : Fragment() {
    private lateinit var viewModel: StorageFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                activity!!.application
            )
        ).get(StorageFragmentViewModel::class.java)



        viewModel.getAllStorage()?.observe(this, Observer {
            it.forEach {
                textViewInternalStorageFree.text = "Free : " + StringUtils.bytesToHuman(it.internalFree ?: 0)
                textViewInternalStorageUsed.text = "Used : " + StringUtils.bytesToHuman(it.internalUsed ?: 0)
                textViewInternalStorageTotal.text = "Total : " + StringUtils.bytesToHuman(it.internalTotal ?: 0)
                determinateBarInternalStorage.progress = ((it.internalFree!!.toDouble() / it.internalTotal!!) * 100).toInt()
            }
        })
        viewModel.calculateInternalStorage()

    }
}