package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danefinlay.opengltesting.MyGLSurfaceView

class AboutMeActivity : AppCompatActivity() {
    /*
    8) Menampilkan animasi dengan OpenGL
     */
    private var myGLView: MyGLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myGLView = MyGLSurfaceView(this)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "About Kevin"
        setContentView(R.layout.activity_about_me)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}
