package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.danefinlay.opengltesting.MyGLSurfaceView

class OpenGlFragment : Fragment() {
    private var myGLView: MyGLSurfaceView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create a GLSurfaceView instance and
        myGLView = MyGLSurfaceView(context)

        // Set it as the ContentView for this Activity.
//        setContentView(myGLView)
        // Inflate the layout for this fragment
        return myGLView
    }

}