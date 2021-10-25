package com.example.mm.homeActivity.externalServerInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.Question;
import localDatabase.Tables.UserInformation;

public class Sync implements Runnable{
    private final String SERVER_IP = "192.168.1.66";
    private final int SERVER_PORT = 3000;
    private final int TIME_TO_STAY = 5;

    private final String message = "Hello wor- ... server?!?!";
    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    StringBuilder communicationText;

    PopupWindow popupWindow;
    TextView text;
    ProgressBar progressBar;
    Context context;

    List<Course> courseList;
    List<Question> questionList;

    public Sync(Context context, Context contextDatabase, PopupWindow popupWindow, TextView text, ProgressBar progressBar) {
        this.popupWindow = popupWindow;
        this.text = text;
        this.progressBar = progressBar;
        this.context = context;

        localDatabase = Room.databaseBuilder(contextDatabase, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()  /* Is needed to overwrite the old scheme of the
         *  local database, it will ERASE all the current
         *  data.
         * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();

        communicationText = new StringBuilder();
        communicationText.append("Connection to server...");

        courseList = new ArrayList<>();
        questionList = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            /* Server connection inspired by the following source:
            *   https://www.oracle.com/java/technologies/jpl2-socket-communication.html. */
            try{
                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(),
                        true);
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
            } catch (UnknownHostException e) {
                communicationText.append("\t\t[failed]\nError server connection,\n operation aborted.\n");
                setProgressBarPosition(0);
                updateText(communicationText.toString());
                try {
                    TimeUnit.SECONDS.sleep(TIME_TO_STAY);
                } catch (InterruptedException interruptedException) { terminatePopup(); return;}
                terminatePopup();
                return;
            } catch  (IOException e) {
                communicationText.append("\t\t[failed]\nError I/O,\n operation aborted.\n");
                setProgressBarPosition(0);
                updateText(communicationText.toString());
                try {
                    TimeUnit.SECONDS.sleep(TIME_TO_STAY);
                } catch (InterruptedException interruptedException) { terminatePopup(); return;}
                terminatePopup();
                return;
            }

            communicationText.append("\t\t[ok]\nSending user information...");
            updateText(communicationText.toString());
            setProgressBarPosition(25);

            /* Getting user information. */
            List<UserInformation> userInformationList = localDatabaseDao.getAllUserInformation();
            if(userInformationList.isEmpty()){
                communicationText.append("\t\t[failed]\nError no user information found,\n operation aborted.\n");
                setProgressBarPosition(0);
                updateText(communicationText.toString());
                try {
                    TimeUnit.SECONDS.sleep(TIME_TO_STAY);
                } catch (InterruptedException interruptedException) { terminatePopup(); return;}
                terminatePopup();
                return;
            }

            /* Send user information. */
            out.println(userInformationList.get(0).getName());
            out.println(userInformationList.get(0).getSurname());
            out.println(userInformationList.get(0).getEmail());
            out.println(Integer.toString(userInformationList.get(0).getMatr()));
            out.flush();

            /* Waiting for ACK. */
            String messageIn;
            if(((messageIn = in.readLine()) != null))
            {
                if(!messageIn.equals("ACK")){
                    throw new IOException();
                }
            }
            else{
                throw new IOException();
            }

            communicationText.append("[ok]\nReceiving courses and questions...");
            updateText(communicationText.toString());
            setProgressBarPosition(50);

            /* Receiving number of courses and questions. */
            int numberOfCourses = 0;
            int numberOfQuestion = 0;
            if((messageIn = in.readLine()) != null){
                numberOfCourses = Integer.parseInt(messageIn);
            }
            else{
                throw new IOException();
            }
            if((messageIn = in.readLine()) != null){
                numberOfQuestion = Integer.parseInt(messageIn);
            }
            else{
                throw new IOException();
            }

            out.println("ACK");
            out.flush();

            System.out.println(numberOfCourses);
            System.out.println(numberOfQuestion);

            for(int i = 0; i < numberOfCourses; i++){
                int id = 0;
                String name = null;
                String year = null;
                String professor = null;
                String description = null;

                if((messageIn = in.readLine()) != null){
                    id = Integer.parseInt(messageIn);
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    name = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    year = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    professor = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    description = messageIn;
                }
                else{
                    throw new IOException();
                }

                courseList.add(new Course(id, name, year, professor, description));
                System.out.println(courseList.get(courseList.size() - 1).toString());
            }
            for(int i = 0; i < numberOfQuestion; i++){
                int id = 0;
                int idCourse = 0;
                String lastChange = null;
                String questionText = null;
                String answer1 = null;
                String answer2 = null;
                String answer3 = null;
                String answer4 = null;

                if((messageIn = in.readLine()) != null){
                    id = Integer.parseInt(messageIn);
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    idCourse = Integer.parseInt(messageIn);
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    lastChange = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    questionText = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    answer1 = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    answer2 = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    answer3 = messageIn;
                }
                else{
                    throw new IOException();
                }
                if((messageIn = in.readLine()) != null){
                    answer4 = messageIn;
                }
                else{
                    throw new IOException();
                }

                questionList.add(new Question(id, idCourse, lastChange, questionText, answer1, answer2, answer3, answer4));
                System.out.println(questionList.get(questionList.size() - 1).toString());
            }

            communicationText.append("\t\t[ok]\nSaving question and courses...");
            updateText(communicationText.toString());
            setProgressBarPosition(75);


            for(Course c: courseList){
                if(localDatabaseDao.getCourseById(c.getId()).isEmpty()){
                    localDatabaseDao.insertCourses(c);
                }
            }

            for(Question q: questionList){
                if(localDatabaseDao.getAllQuestionByIds(q.getQid()).isEmpty()){
                    localDatabaseDao.insertAllQuestion(q);
                }
            }

            out.println("ACK");
            out.flush();

            communicationText.append("\t\t[ok]\nOperation succeeded.");
            updateText(communicationText.toString());
            setProgressBarPosition(100);
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { terminatePopup(); return;}
            terminatePopup();

        } catch (IOException e) {
            communicationText.append("\t\t[failed]\nError external server,\n operation aborted.\n");
            updateText(communicationText.toString());
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { terminatePopup(); return;}
            terminatePopup();
        } catch (SQLiteException e){
            communicationText.append("\t\t[failed]\nError local server,\n operation aborted.\n");
            updateText(communicationText.toString());
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { terminatePopup(); return;}
            terminatePopup();
        } finally {
            try {
                if(out != null){ out.close(); }
                if(in != null){ in.close(); }
                if(socket != null){ socket.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void updateText(String newText){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(newText);
                }
            });
        }
    }
    public void setProgressBarPosition(int position){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    progressBar.setProgress(position, true);
                }
            });
        }
    }
    public void terminatePopup(){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    popupWindow.dismiss();
                }
            });
        }
    }
}
