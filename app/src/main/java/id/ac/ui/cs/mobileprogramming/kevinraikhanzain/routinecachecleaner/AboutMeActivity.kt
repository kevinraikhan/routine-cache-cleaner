package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danefinlay.opengltesting.MyGLSurfaceView

class AboutMeActivity : AppCompatActivity() {

    private var myGLView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create a GLSurfaceView instance and
        myGLView = MyGLSurfaceView(this)

        // Set it as the ContentView for this Activity.
//        setContentView(myGLView)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "About Kevin"
         setContentView(R.layout.activity_about_me)

        // DON'T restore any state information here. Do so in onRestoreInstanceState
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



}
