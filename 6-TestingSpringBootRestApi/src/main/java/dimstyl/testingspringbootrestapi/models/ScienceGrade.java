package dimstyl.testingspringbootrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "science_grade")
public class ScienceGrade implements Grade {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int studentId;

    @Column
    private double grade;

    public ScienceGrade(int studentId, double grade) {
        this.studentId = studentId;
        this.grade = grade;
    }
}