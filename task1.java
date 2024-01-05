package lab9;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class task1 {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws SizeMismatch {
        System.out.print("Введите размер массива объектов: ");
        int n = Integer.parseInt(in.nextLine());
        Matrix a[] = new Matrix[n];
        for (int i = 0; i < a.length; i++) {
            System.out.print("Введите размер матрицы: ");
            a[i] = new Matrix(Integer.parseInt(in.nextLine()));
        }

        int norm1 = a[0].countFNorm();
        double norm2 = a[0].countSNorm();
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            a[i].showMatrix();
            System.out.println("Первая норма: " + a[i].countFNorm() + ", вторая норма: " + a[i].countSNorm());
            if (a[i].countFNorm() < norm1 && a[i].countSNorm() < norm2) {
                norm1 = a[i].countFNorm();
                norm2 = a[i].countFNorm();
                index = i;
            }
        }

        System.out.println("\nМатрица с минимальными нормами:");
        a[index].showMatrix();
        System.out.println("Первая норма: " + a[index].countFNorm() + ", вторая норма: " + a[index].countSNorm());

        try {
            a[0].add(a[1]);
        } catch (SizeMismatch e) {
            System.out.println("\nОперация невозможна из-за несоответствия размеров матриц!");
        }
    }
}

class Matrix {
    private int[][] matrix;
    private int size;
    
    Matrix(int n[][]) {
        matrix = n;
        size = matrix.length;
    }

    Matrix(int n) {
        size = n;
        matrix = new int[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(-50, 51);
        }
    }

    Matrix(int n, boolean zeros) {
        size = n;
        matrix = new int[size][];
        for (int i = 0; i < size; i++)
            matrix[i] = new int[size];
    }

    Matrix(int n, int mingen, int maxgen) {
        size = n;
        matrix = new int[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(mingen, maxgen + 1);
        }
    }

    int getSize() {
        return size;
    }

    void setSize(int n) {
        size = n;
        matrix = new int[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(-50, 51);
        }
    }

    void setSize(int n, int mingen, int maxgen) {
        size = n;
        matrix = new int[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new int[size];
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(mingen, maxgen + 1);
        }
    }

    void showMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j] + "\t");
            System.out.println();
        }
    }

    void add(Matrix b) throws SizeMismatch {
        if (size != b.size)
            throw new SizeMismatch();
            
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] += b.matrix[i][j];
    }

    void sub(Matrix b) throws SizeMismatch {
        if (size != b.size)
            throw new SizeMismatch();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] -= b.matrix[i][j];
    }

    void mul(Matrix b) throws SizeMismatch {
        if (size != b.size)
            throw new SizeMismatch();
        Matrix c = new Matrix(size, true);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                for (int k = 0; k < matrix[i].length; k++)
                    c.matrix[i][j] += matrix[i][k] * b.matrix[k][j];
            }
        }
        matrix = c.matrix;
    }

    int countFNorm() {
        int[] t = new int[size];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                t[i] += Math.abs(matrix[j][i]);

        int max = t[0];
        for (int i = 0; i < t.length; i++)
            if (t[i] > max) max = t[i];
        
        return max;
    }

    double countSNorm() {
        int t = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                t += Math.pow(matrix[i][j], 2);

        return Math.sqrt(t);
    }
}