
# Face-Recognition-App

Developed an Android App implementing Face Recognition to find lost people with the help of ML kit and custom tensorFlow Lite Model



## Tools and Frameworks Used
* Android Studio(Kotlin)
* ML Kit
* TensorFlow Lite

## Model
Used a custom tflite model to predict the faces of missing person .The custom model was prepared by training the tflite architecture on 4 different classes: Harshit,Kapil,Prashant,Khushal. The data for these 4 classes was obtained from a dummy database that is used for demonstration purpose only.In real world a missing person database containing name and image of missing person will be maintained and the no. of missing person will constitute the no. of classes that will be used to perform the face classification task using tflite architecture.
After then we implement the ML Kit's Image Classification and Face Detection to the tflite model

The custom tflite model with was created using the website: https://teachablemachine.withgoogle.com/
The dummy dataset used for training my custom tflite model is:  
https://drive.google.com/drive/folders/1vPpNVHzzvt7awoW-hwc6px7L9FyaxG0-?usp=sharing

## Key Features 
* It can take input from both camera and storage
* Fully Responsive and Accurate
* Simple and Clean UI
* Light Energy Usage
* Low CPU Utilization
* Low Memory Usage
## Installation

Use Import from Version Control in Android Studio or Clone repo and open the project in Android Studio.

```bash
  git clone https://github.com/HT13-coder/Face-Recognition-App.git
```
Application APK File :      [Face_Recognition.apk](http://www.google.fr/ "Named link title")
## UI


[App Screenshot #1]![Screenshot_2022-05-29-18-34-35-11_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170890274-277fc4d0-bd28-49a3-9e69-2c69478e5e8a.png) 
[App Screenshot #2]![Screenshot_2022-05-29-18-34-37-93_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170890391-a5b5a82a-9f60-46db-aa54-c3b09c1603b0.png)   
[App Screenshot #3]![Screenshot_2022-05-29-18-34-41-71_1c38212aaa1de0de66e78bc3853a497e](https://user-images.githubusercontent.com/76600106/170890419-3bb7986d-b81a-4549-8de5-1ed8ef3b2e19.png)


## Running Tests


Pick image From Storage 

![Pick image from storage](https://user-images.githubusercontent.com/76600106/170874844-5d07064b-e5a4-4b45-897f-ccea05dce586.gif) 


Pick Image from Camera

![Pick from Camera ](https://user-images.githubusercontent.com/76600106/170875331-2d7e0a50-f206-4362-97bc-9a8c942f0f32.gif)


Find Lost person 

![lost person](https://user-images.githubusercontent.com/76600106/170874693-b617e446-4a25-4b11-a27c-02b153838250.gif)



Detect Faces 


![Lost Person Resize](https://user-images.githubusercontent.com/76600106/170874767-b95558aa-4330-4712-9f10-818e9cfe250f.gif)


## CPU Utilization
<img width="1440" alt="Screenshot 2022-05-30 at 3 15 42 AM" src="https://user-images.githubusercontent.com/76600106/170893286-68de7595-573b-47a2-b8ed-c9e3b22dc463.png">


## Memory Usage
<img width="1440" alt="Screenshot 2022-05-30 at 3 16 17 AM" src="https://user-images.githubusercontent.com/76600106/170893294-6170f34f-f715-41fe-accf-eb35be591426.png">



## Improvements

* This app can be further improved by implementing it in realtime i.e by collecting realtime data as input and applying face recognition algorithm on each frame or in some intervals of frame. This application can have a promising scope in a real world scenario where such technique can be employed at the cameras situated in public spaces for finding missing person.
* Though this app shows pretty accurate results but there is still a scope of employing a better face recognition mode to further improve the performance.
* While making the project I also thought of an another approach which involves implementing the FaceNet model which can also be used.

## Presentation
* [Link to Presentation](https://youtu.be/hLoOXhkBt2g)
* [Link to PPT](https://drive.google.com/file/d/1GA8RDi1gzG_jV-HSycYgNJH5c4FZ8jtK/view?usp=sharing)
## Authors

- [Harshit Taneja](https://github.com/HT13-coder)


## 🔗 Links
[![Email](https://img.shields.io/badge/Email-000?style=for-the-badge&logo=ko-fi&logoColor=white)](harshittaneja.ht4@gmail)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/harshit-taneja-bb5706171)


