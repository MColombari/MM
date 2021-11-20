# MM
## Project for OOP class
This project was made for the final exam of OOP class, and it was made in collaboration with [Matteo Alboni](https://github.com/AlboniMatteo).
This project is a simple concept to exercise with course questions and show the respective result.
It is not finished and it probably never will, but it could be, for someone, a good starting point for their own project.
The app in home supports gestures (swipe right/left/top/bottom) and all the main activity is customized.
All the local database interactions are managed with [Room](https://developer.android.com/training/data-storage/room).
The application can communicate with and external server to download all the recent courses and respective question, the implementation is quite simple and was used only to show the potentiality of this app, it use a client-server communication, the server side could be found [here](https://github.com/MattiaColombari/MMServer).
To make the communication with the server work properly you need to modify the variable “SERVER_IP” in “java>com.example.mm>homeActivity>externalServerInteraction>Sync.java” with the IP of your server, and also you need check if the port “3000” is open and in case change it in the app and in the server.
