package com.danefinlay.opengltesting

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import com.danefinlay.util.OpenGLShape
import com.danefinlay.util.createGLESProgram
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Example OpenGL surface view implementation
 * Created by dane on 4/11/16.
 */
class MyGLSurfaceView : GLSurfaceView {
    val myRenderer: MyGLRendererExample

    // Members used for pinch zooming in and out

    constructor(context: Context?): super(context) {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        myRenderer = MyGLRendererExample()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(myRenderer)

        // Render the view only when there is a change in the drawing data
        // To allow the triangle to rotate automatically, this line is commented out:
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        // Set up gesture detectors
    }



    inner class MyGLRendererExample : GLSurfaceView.Renderer {
        private var triangle1: OpenGLShape? = null
        private var triangle2: OpenGLShape? = null

        // Changes to this variable need to be atomic because it runs on a different thread
        // from the main user interface. So it should be either:
        // 1) volatile because renderer code runs, or
        // 2) an AtomicFloat (which does not exist...)
        @Volatile var angle: Float = 0.0f
            get() = field
            set(value) { field = value }

        // Use this for changing the camera position when scaling (pinching in or out)
        @Volatile var cameraEyeZ: Float = 3f
            get() = field
            set(value) { field = value }

        // Matrices
        // These are here because the matrices are not really attributes of each object in OpenGL
        // they are operated on and then objects are drawn with respect to them
        private val rotationMatrix = FloatArray(16)
        private val vPMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16)
        private val projectionMatrix = FloatArray(16)

        /** Equivalent to glutInit - called once to set up the view's OpenGL ES environment. */
        override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(255F, 255F, 255F, 1F)
            triangle1 = Triangle(createGLESProgram())
            triangle2 = Triangle(createGLESProgram())

        }

        /** Same as glut's displayCallback - called for each redraw of the View obj */
        override fun onDrawFrame(unused: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

            // Set the camera position (View matrix)
            Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 20.0f)

            // Calculate the projection and view transformation
            Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

            val scratch = FloatArray(16)

            // Create a rotation transformation for the triangle
            val time = SystemClock.uptimeMillis() % 4000L
            val angle = 0.090f * time.toInt()
            Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)

            // Combine the rotation matrix with the projection and camera view
            // Note that the vPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

            // Draw triangle
            triangle1?.draw(scratch)


//            triangle1?.draw(mMVPMatrix)
        }

        /** Same as glut's reshape callback function for when the window geometry changes
         * called when the screen is rotated, for example
         */
        override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
            val ratio: Float = width.toFloat() / height.toFloat()

            // this projection matrix is applied to object coordinates
            // in the onDrawFrame() methodJNIMilisToDate
            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }
    }

    companion object {
        // Values used for OpenGL projection and touch scaling gestures (pinch in-out)
        private val PORTRAIT_NEAR_LIMIT = 1.5F
        private val PORTRAIT_FAR_LIMIT = 12F
    }
}
