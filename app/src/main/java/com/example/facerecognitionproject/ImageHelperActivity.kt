package com.example.facerecognitionproject

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.example.facerecognitionproject.helpers.BoxWithLabel
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


open class ImageHelperActivity : AppCompatActivity() {

    companion object{
         const val REQUEST_PICK_IMAGE=1000
         const val REQUEST_CAPTURE_IMAGE=1001
    }

    private lateinit var outputTextView :TextView
    private lateinit var inputImageView :ImageView
    private lateinit var inputImage: InputImage
    private lateinit var photoFile: File


    private lateinit var imageLabeler: ImageLabeler
    private lateinit var faceDetector: FaceDetector

    private lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_helper)

        progressBar=findViewById(R.id.progressbar)


            outputTextView=findViewById(R.id.textViewOutput)
            inputImageView=findViewById(R.id.imageViewInput)

        val localModel: LocalModel = LocalModel.Builder().setAssetFilePath("model.tflite").build()
        val options = CustomImageLabelerOptions.Builder(localModel).
        setMaxResultCount(4).
        build()

        imageLabeler=ImageLabeling.getClient(options)

        val options2= FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .enableTracking()
            .build()

        faceDetector = FaceDetection.getClient(options2)
    }

        //When camera button is clicked
     fun onStartCamera(view: View) {

        checkPermission(Manifest.permission.CAMERA, REQUEST_CAPTURE_IMAGE)
            // create a file to share with camera
        progressBar.visibility=View.VISIBLE
        photoFile=createphotofile()
        val fileuri :Uri= FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile)

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
    //Function to provide run time permission of camera and storage
    private fun checkPermission(permission:String,requestCode: Int){
        if(ContextCompat.checkSelfPermission(this,permission)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
        }else{
            Toast.makeText(this,"Permission granted already",Toast.LENGTH_LONG).show()
        }
    }
    // Creates file to share with camera
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
    //When pick image button is clicked
     fun onPickImage(view: View) {
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, FaceDetectionActivity.REQUEST_PICK_IMAGE)
        progressBar.visibility=View.VISIBLE
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE ) {
                inputImageView.setImageURI(data?.data)
                val stream = data?.data?.let {
                    contentResolver.openInputStream(it)
                }
                val bitmap = BitmapFactory.decodeStream(stream)
                // view.setImageBitmap(bitmap)
                // val bitmap=data?.extras?.get("data") as Bitmap
                // inputImage=InputImage.fromBitmap(bitmap,0)
                //   inputImageView.setImageBitmap(bitmap)
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
    //Fuction for image Processing and face detection
     fun runclassification(bitmap: Bitmap){


        inputImage=InputImage.fromBitmap(bitmap,0)
        imageLabeler.process(inputImage).addOnSuccessListener {
            var result=""
            var name:String
            var max_confidence:Float = 0F

            var idx:Int = -1
            for (label in it){
                if(max_confidence<label.confidence){
                    max_confidence  = label.confidence
                    idx = label.index
                }
            }
            if (max_confidence>0.9f){
                if(idx==0){
                    name="Harshit"
                }else if(idx==1){
                    name="Kapil"
                }else if(idx==2){
                    name="Prashant"
                }else {
                    name="Khushal"
                }
                result = "Found  " + name + "  :  " + max_confidence
            }
            else{
                result="Missing Person not Found"
            }
            outputTextView.text=result
        }.addOnFailureListener {
            Log.d(ContentValues.TAG,"process image:$(it.message)")
            outputTextView.text="Missing Person not Found"
        }

         val outputBitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true)
         val inputImage2=InputImage.fromBitmap(outputBitmap,0)


         faceDetector.process(inputImage2)
             .addOnSuccessListener { faces->
                 if(faces.isEmpty()){
                     val text="No faces Detected"
                     outputTextView.text= text
                 }
                 else{
                     val boxes= ArrayList<BoxWithLabel>()
                     for (face in faces){
                         val boxWithLabel= BoxWithLabel(face.boundingBox,
                             "" + face.trackingId)
                         boxes.add(boxWithLabel)

                     }
                     drawDetectionResult(boxes, bitmap)
                 }
             }.addOnFailureListener{
                 Log.d(ContentValues.TAG,"process image:$(it.message)")
             }
     }

    //Function to create Bounding Box around the Detected Face
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


        for (boxWithLabel: BoxWithLabel in boxes) {
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


