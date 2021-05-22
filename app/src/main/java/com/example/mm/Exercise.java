package com.example.mm;

//import ServerDB.QuestionManager;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import java.sql.*;

//classe per gestire l'interfaccia degli esercizi, assegnare testi e risposte
public class Exercise{
    ResultSet rs;
    //variabile per il tempo, va qui?

    //Costruttore
    public Exercise(ResultSet rs)
    {
        this.rs = rs;//corretto?
    }

    //imposta testo domanda
    public void setQText(TextView tv) throws SQLException
    {
        tv.setText(rs.getString("text"));//decidere in seguito il nome effettivo delle colonna

    }

    //imposta testo rispote, passare anche il suo numero
    public void setAText(RadioButton rb, int num) throws SQLException
    {
        rb.setText(rs.getString("answer " + Integer.toString(num)));
    }

    public void Previous()
    {
        //se premi tasto indietro, riporta e ricarica la domanda precedente
    }


    public void Next()
    {
        //se premi tasto avanti, passa alla domanda successiva ( decidere se usare un solo metodo che scorre avanti indietro la lista)
    }

    public void Leave()
    {
        //abbandona esercitazione
    }

    public void SetTimer(double time)
    {
        //settare il timer, decidere in quale formato si passa il tempo, in caso di tempo infinito usare un metodo differente
    }

    //altri metodi, più avanti

}


//Matteo
