package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"name", "surname", "email", "matr"})
public class UserInformation {
    @NonNull
    public String name;
    @NonNull
    public String surname;
    @NonNull
    public String email;
    @NonNull
    public int matr;

    public UserInformation(String name, String surname, String email, int matr) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.matr = matr;
    }

    /* Getter */
    @NonNull
    public String getName() {
        return name;
    }
    @NonNull
    public String getSurname() {
        return surname;
    }
    @NonNull
    public String getEmail() {
        return email;
    }
    public int getMatr() {
        return matr;
    }

    /* Setter */
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }
    public void setEmail(@NonNull String email) {
        this.email = email;
    }
    public void setMatr(int matr) {
        this.matr = matr;
    }
}
