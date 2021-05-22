package ServerDB;

//import localDatabase.QuestionDao;
import java.sql.*;
import javax.xml.transform.Result;

public interface QuestionManager {  //implements Runnable{

    //leggere domande con query
    ResultSet ReadQuestion();//decidere quali argomenti passare, qid? argomento? più versioni per questo metodo?

    //modificare la domanda
    //testo
    //risposte
    //difficoltà/argomento
    void ModifyQuestion(int qid, String newtext, int option);//id domanda, nuovo testo, e opzione: testo doamnda, riposte o altro

    //cancellare la domanda
    void DeleteQuestion(int quid);

    //nuova domanda
    void NewQuestion();//il metodo assegna qid in automatico (incrementale?),
    // chiede il testo (chiama una gui apposita per con text view per inserire nuova domanda?),
    //e definizione dell'argomento


    //è qui che si farà uso concorrente del db e della sua gestione tramite thread?

}


//Matteo