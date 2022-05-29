# Face-Recognition-App
Developed an Android App implementing Face Recognition to find lost people with the help of ML kit and custom tensorFlow Lite Model 

# Key Features 
*Fast and Accurate 
*Simple and Clean UI
*Light Energy Usage
*Low CPU Utilization
*Low Memory Usage

# Tools and Frameworks Used
*Android Studio(Kotlin)
*ML Kit
*TensorFlow Lite
![image](https://user-images.githubusercontent.com/76600106/170875724-71b89a98-e552-4b44-b42b-ee1792651c0a.jpeg)![image](https://user-images.githubusercontent.com/76600106/170875741-5a25c6a1-d929-40dd-9010-12f9aff73a15.gif)![image](https://user-images.githubusercontent.com/76600106/170875800-eb08e479-95b0-411f-ba3f-8eaaa9c7b599.jpeg)



# Model
Used a custom tflite model to predict the faces of missing person .The custom model was prepared by training the tflite architecture on 4 different classes: Harshit,Kapil,Prashant,Khushal. The data for these 4 classes was obtained from a dummy database that is used for demonstration purpose only.In real world a missing person database containing name and image of missing person will be maintained and the no. of missing person will constitute the no. of classes that will be used to perform the face classification task using tflite architecture

Created the custom tflite model with the help of 


# Installation![image](https://user-images.githubusercontent.com/76600106/170875745-ba1b592a-c26d-408c-af5d-44aaf09b2dea.gif)



# UI
![Screenshot_2022-05-29-18-34-35-11_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170872733-3b73de1d-8315-4b01-892b-d0cd437c51c6.jpeg)


![Screenshot_2022-05-29-18-34-37-93_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170872742-2b75b4e4-698b-4f35-a1f0-621b9ddea5d7.jpeg)


![Screenshot_2022-05-29-18-34-41-71_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170872752-cf2ff99d-4b44-4843-9cf9-8cc136018c21.jpeg)



# Usage
Pick image From Storage

![Pick image from storage](https://user-images.githubusercontent.com/76600106/170874844-5d07064b-e5a4-4b45-897f-ccea05dce586.gif)



Pick Image from Camera

![Pick from Camera ](https://user-images.githubusercontent.com/76600106/170875331-2d7e0a50-f206-4362-97bc-9a8c942f0f32.gif)



Find Lost person

![lost person](https://user-images.githubusercontent.com/76600106/170874693-b617e446-4a25-4b11-a27c-02b153838250.gif)



Detect Faces

![Lost Person Resize](https://user-images.githubusercontent.com/76600106/170874767-b95558aa-4330-4712-9f10-818e9cfe250f.gif)







# Presentation


# Improvements
*This app can be further improved by implementing it in realtime i.e by collecting realtime data as input and applying face recognition algorithm on each frame or in some intervals of frame. This application can have a promising scope in a real world scenario where such technique can be employed at the cameras situated in public spaces for finding missing person.
*Though this app shows pretty accurate results but there is still a scope of employing a better face recognition mode to further improve the performance.

# Author
Harshit Taneja
Email : harshittaneja.ht4@gmail
Linkedin : https://www.linkedin.com/in/harshit-taneja-bb5706171




