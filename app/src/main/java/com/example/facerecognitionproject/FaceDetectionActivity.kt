package com.example.facerecognitionproject

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.facerecognitionproject.helpers.BoxWithLabel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FaceDetectionActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_PICK_IMAGE=1000
        const val REQUEST_CAPTURE_IMAGE=1001
    }

    private lateinit var faceDetector: FaceDetector
    private lateinit var outputTextView : TextView
    private lateinit var inputImageView : ImageView
    private lateinit var inputImage: InputImage
    private lateinit var photoFile: File

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_helper)

        progressBar=findViewById(R.id.progressbar)

        outputTextView=findViewById(R.id.textViewOutput)
        inputImageView=findViewById(R.id.imageViewInput)

        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .enableTracking()
            .build()

        faceDetector = FaceDetection.getClient(options)
    }

     fun onPickImage(view: View) {
         progressBar.visibility=View.VISIBLE
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PICK_IMAGE)
         val intent= Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    fun onStartCamera(view: View) {
        checkPermission(Manifest.permission.CAMERA, REQUEST_CAPTURE_IMAGE)
        // create a file to share with camera
        progressBar.visibility=View.VISIBLE
        photoFile=createphotofile()

        val fileuri :Uri= FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile)
        // create an intent
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileuri)
        //startactivityforresult
            if (intent.resolveActivity(this.packageManager)!=null) {
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
            }else{
                Toast.makeText(this,"unable to open camera",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkPermission(permission:String,requestCode: Int){
        if(ContextCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
        }else{
            Toast.makeText(this,"Permission granted already",Toast.LENGTH_LONG).show()
        }
    }

    fun createphotofile(): File {
        val photoFileDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ML_HELPER_IMAGE")
        if (!photoFileDir.exists()) {
            photoFileDir.mkdirs()
        }
        val name: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()).toString()
        val file=File(photoFileDir.path + File.separator + name)
        return file
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
                inputImageView.setImageURI(data?.data)
                val stream = data?.data?.let {
                    contentResolver.openInputStream(it)
                }
                val bitmap = BitmapFactory.decodeStream(stream)
                inputImage = InputImage.fromFilePath(this, data?.data!!)
                inputImageView.setImageBitmap(bitmap)
                runclassification(bitmap)
                progressBar.visibility=View.GONE
            } else if(requestCode == REQUEST_CAPTURE_IMAGE){
                val bitmap =BitmapFactory.decodeFile(photoFile.absolutePath)
                inputImageView.setImageBitmap(bitmap)
                runclassification(bitmap)
                progressBar.visibility=View.GONE
            }
        }
    }



     private fun runclassification(bitmap:Bitmap){
        val outputBitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true)
        val inputImage=InputImage.fromBitmap(outputBitmap,0)


        faceDetector.process(inputImage)
            .addOnSuccessListener { faces->
                if(faces.isEmpty()){
                    val text="No faces Detected"
                    outputTextView.text=text
                }
                else{
                    val boxes= ArrayList<BoxWithLabel>()
                    for (face in faces){

                        val boxWithLabel= BoxWithLabel(face.boundingBox,
                            "" + face.trackingId)
                        boxes.add(boxWithLabel)
                        outputTextView.text="Face Detected"

                    }
                    drawDetectionResult(boxes, bitmap)
                }
            }.addOnFailureListener{
                Log.d(ContentValues.TAG,"process image:$(it.message)")
            }
    }

    private fun drawDetectionResult(boxes: List<BoxWithLabel>, bitmap: Bitmap){
        val outputBitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true)
        val canvas= Canvas(outputBitmap)

        val penrect= Paint()
        penrect.color = Color.RED
        penrect.style = Paint.Style.STROKE
        penrect.textSize = 96f
        penrect.strokeWidth=8.0f

        val penLabel= Paint()
        penLabel.color = Color.YELLOW
        penLabel.style = Paint.Style.FILL_AND_STROKE
        penLabel.textSize = 96f


        for (boxWithLabel:BoxWithLabel in boxes) {
            canvas.drawRect(boxWithLabel.rect,penrect)
            val labelSize= Rect(0,0,0,0)
            penLabel.getTextBounds(boxWithLabel.label,0,boxWithLabel.label.length,labelSize)
            val fontSize=penLabel.textSize *boxWithLabel.rect.width()/labelSize.width()

                if (fontSize<penLabel.textSize){
                    penLabel.textSize = fontSize
                }
                canvas.drawText(boxWithLabel.label,
                    boxWithLabel.rect.left.toFloat(),boxWithLabel.rect.top + labelSize.height().toFloat(),penLabel)
        }
        inputImageView.setImageBitmap(outputBitmap)
        inputImage=InputImage.fromBitmap(outputBitmap,0)
    }
}






