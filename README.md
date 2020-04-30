# Android Pokedex
This is my first android app that I had made through visual studio code. It is a pokedex; it displays information on pokemon, such as their names, types, moves, and abilities. Through the project, I have learned many things about android app development

## Demo of the App

<img src="demo_files/app_demo.gif" height="800px">

## Build/Run the App
1. Clone the repository
2. Open the project with Android Studio
3. Run on a Android Virtual Device

## Things Learned Through this Project
### 1) Developing UIs Through Constraints and Other Layout Tools.
The main concepts in developing the UI were not dissimilar to those used in developing iOS apps, and the use of xml was also not a new concept. Getting accustomed to the UI design portion of Android was not too difficult as it was not too foreign

### 2) Using RecyclerViews to Efficiently Manage Scrolling
The RecyclerView was surprisingly easy to implement compared to iOS's finnicky counterpart. Furthermore, the RecyclerView handles resources more efficiently compared to Android's ScollView by reusing the same frames, but rendered with different content as you scroll.

### 3) Changing Activities Using Intents
Changing the activities between the list of pokemon to the detailed view of a selected pokemon was simple once I had learned about Intents as well as the Parcelable interface that allows me to move data between two activities.


### 4) Creating a Parallax Effect on Scrolling
This was something that I had never consciously noticed in applications that I use, nevermind implemented before. What is seemingly complex behavior is simply abstracted by the tools that anroid libraries supply, making it relatively painless to implement.

### 5) Handling Loading of Data Asynchronously
One part that turned out more difficult than I had originally thought was the integration of the pokedex app with the Pokemon API. As the application relies entirely on the Pokemon API for data, I had to consider how to handle the UI while the data was still loading in. I had to come up with a way that allowed the model to update the view asynchronously, which is why I had made listener interfaces for the different data types that I was loading in.
