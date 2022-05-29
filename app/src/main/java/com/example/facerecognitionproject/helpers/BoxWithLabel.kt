package com.example.facerecognitionproject.helpers

import android.graphics.Rect
import com.example.facerecognitionproject.ImageHelperActivity

data class BoxWithLabel(val rect: Rect,val label:String): ImageHelperActivity() {}