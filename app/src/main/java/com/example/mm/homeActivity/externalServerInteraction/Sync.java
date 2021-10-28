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
    private final String SERVER_IP = "192.168.1.67";
    private final int SERVER_PORT = 3000;
    private final int TIME_TO_STAY = 5;

    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    Context context;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    StringBuilder communicationText;

    PopupWindow popupWindow;
    TextView popupWindowsText;
    ProgressBar popupWindowsProgressBar;

    List<Course> courseList;
    List<Question> questionList;

    public Sync(Context context, Context contextDatabase, PopupWindow popupWindow, TextView popupWindowsText, ProgressBar popupWindowsProgressBar) {
        this.context = context;
        this.popupWindow = popupWindow;
        this.popupWindowsText = popupWindowsText;
        this.popupWindowsProgressBar = popupWindowsProgressBar;

        localDatabase = Room.databaseBuilder(contextDatabase, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()  /* Is needed to overwrite the old scheme of the
         *  local database, it will ERASE all the current
         *  data.
         * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();

        communicationText = new StringBuilder();
        communicationText.append("Connection to server...");
        updateText(communicationText.toString());

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
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch (UnknownHostException e) {
                communicationText.append("\t\t[failed]\nError server connection,\n operation aborted.\n");
                setProgressBarPosition(0);
                updateText(communicationText.toString());
                try {
                    TimeUnit.SECONDS.sleep(TIME_TO_STAY);
                } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
                terminatePopup();
                return;
            } catch  (IOException e) {
                communicationText.append("\t\t[failed]\nError I/O,\n operation aborted.\n");
                setProgressBarPosition(0);
                updateText(communicationText.toString());
                try {
                    TimeUnit.SECONDS.sleep(TIME_TO_STAY);
                } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
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
                } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
                terminatePopup();

                /* Close server connection. */
                try {
                    if(out != null){ out.close(); }
                    if(in != null){ in.close(); }
                    if(socket != null){ socket.close(); }
                } catch (IOException e) {
                    /* Do nothing. */
                }

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
            int numberOfCourses;
            int numberOfQuestion;
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

            /* Sending ACK. */
            out.println("ACK");
            out.flush();

            /* Receiving courses. */
            for(int i = 0; i < numberOfCourses; i++){
                int id;
                String name;
                String year;
                String professor;
                String description;

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
            }

            /* Receiving questions. */
            for(int i = 0; i < numberOfQuestion; i++){
                int id;
                int idCourse;
                String lastChange;
                String questionText;
                String answer1;
                String answer2;
                String answer3;
                String answer4;

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
            }

            communicationText.append("\t\t[ok]\nSaving question and courses...");
            updateText(communicationText.toString());
            setProgressBarPosition(75);

            /* Saving courses. */
            for(Course c: courseList){
                if(localDatabaseDao.getCourseById(c.getId()).isEmpty()){
                    localDatabaseDao.insertCourses(c);
                }
            }

            /* Saving courses. */
            for(Question q: questionList){
                if(localDatabaseDao.getAllQuestionByIds(q.getQid()).isEmpty()){
                    localDatabaseDao.insertAllQuestion(q);
                }
            }

            /* Sending ACK. */
            out.println("ACK");
            out.flush();

            communicationText.append("\t\t[ok]\nOperation succeeded.");
            updateText(communicationText.toString());
            setProgressBarPosition(100);
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
            terminatePopup();
        }
        catch (IOException e) {
            communicationText.append("\t\t[failed]\nError external server,\n operation aborted.\n");
            updateText(communicationText.toString());
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
            terminatePopup();
        }
        catch (SQLiteException e) {
            communicationText.append("\t\t[failed]\nError local server,\n operation aborted.\n");
            updateText(communicationText.toString());
            try {
                TimeUnit.SECONDS.sleep(TIME_TO_STAY);
            } catch (InterruptedException interruptedException) { /* Just terminate the popup and return. */ }
            terminatePopup();
        }
        finally {
            try {
                if(out != null){ out.close(); }
                if(in != null){ in.close(); }
                if(socket != null){ socket.close(); }
            } catch (IOException e) {
                /* Do nothing. */
            }

        }
    }

    public void updateText(String newText){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    popupWindowsText.setText(newText);
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
                    popupWindowsProgressBar.setProgress(position, true);
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
