package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey
    int id;
    @NonNull
    String name;
    @NonNull
    String academicYear;
    @NonNull
    String professor;
    String description;

    public Course(int id, String name, String academicYear, String professor, String description) {
        this.id = id;
        this.name = name;
        this.academicYear = academicYear;
        this.professor = professor;
        this.description = description;
    }

    /*      Getter      */
    public int getId() {
        return id;
    }
    public String getName(){ return name; }
    public String getAcademicYear() {
        return academicYear;
    }
    public String getProfessor() {
        return professor;
    }
    public String getDescription() {
        return description;
    }

    /*      Setter      */
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name){ this.name = name; }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    public void setProfessor(String professor) {
        this.professor = professor;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /*      toString        */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", professor='" + professor + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}