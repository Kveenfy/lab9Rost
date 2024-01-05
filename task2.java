package lab9;

import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class task2 {
    public static void main(String[] args) throws IOException, NegativeValue {
        BufferedReader input = new BufferedReader(new FileReader("lab9/task2.txt"));
        String s = "", t = "";
        int countOfMarks = Integer.parseInt(input.readLine()),
            minAverageMark = Integer.parseInt(input.readLine());
        Abiturient array[] = new Abiturient[Integer.parseInt(input.readLine())];
        Teacher teacher = new Teacher();
        while ((t = input.readLine()) != null) s += t + "\n";
        input.close();

        try {
            if (countOfMarks <= 0 || minAverageMark <= 0)
                throw new NegativeValue();
        } catch (NegativeValue e) {
            System.out.println("Отрицательные значения недопустимы!");
            return;
        }

        for (int i = 0, j = 0; i < s.split("\n").length; i+=2, j++)
            array[j] = new Abiturient(s.split("\n")[i], s.split("\n")[i + 1]);

        for (int i = 0; i < array.length; i++) {
            array[i].setExamMarks(teacher.rate(countOfMarks, ThreadLocalRandom.current().nextInt(1, 6)));
            if (array[i].getAverageMark() >= minAverageMark)
                array[i].setWillStudy(true);
        }

        for (int i = 0; i < array.length; i++)
            array[i].showInfo();
    }
}

class Teacher {

    String rate(int count, int v) {
        String r = "";
        for (int i = 0; i < count; i++)
            if (v >= 3)
                r += ThreadLocalRandom.current().nextInt(7, 11) + " ";
            else 
                r += ThreadLocalRandom.current().nextInt(1, 11) + " ";
        return r;
    }
    
}

class Abiturient {

    private String FIO;
    private String faculty;
    private String examMarks = "";
    private boolean willStudy = false;
    
    Abiturient(String FIO, String faculty) {
        this.FIO = FIO;
        this.faculty = faculty;
    }

    boolean getWillStudy() {
        return willStudy;
    }

    void setWillStudy(boolean w) {
        willStudy = w;
    }

    String getFIO() {
        return FIO;
    }

    String getFaculty() {
        return faculty;
    }

    void setExamMarks(String e) {
        examMarks = e;
    }

    double getAverageMark() {
        double s = 0;
        for (int i = 0; i < examMarks.split(" ").length; i++)
            s += Double.parseDouble(examMarks.split(" ")[i]);
        return s / examMarks.split(" ").length;
    }

    void showInfo() {
        System.out.println("ФИО: " + FIO);
        System.out.println("Факультет: " + faculty);
        System.out.println("Экзаменационные оценки: " + examMarks);
        System.out.println("Средний балл: " + getAverageMark());
        System.out.println(willStudy ? "Зачислен\n" : "Не зачислен\n");
    }
}