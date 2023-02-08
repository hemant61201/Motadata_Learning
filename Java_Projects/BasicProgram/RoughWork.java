// Java program to demonstrate that
// The static method does not have
// access to the instance variable

import java.io.*;

public class RoughWork {
    // static variable
    public static double[] calculateGPA(int[] studentIdList, char[][] studentsGrades) {
        // your code
        double[] result = new double[studentIdList.length];
        int index = 0;
        for (char[] studentGrade : studentsGrades){
            double sum = 0.0;
            for(char j : studentGrade){
                if(j == 'A'){
                    sum += 4;
                } else if (j == 'B') {
                    sum += 3;
                } else if (j == 'C') {
                    sum += 2;
                }
            }
            double avg = sum / studentsGrades.length;
            result[index++] = avg;
        }
        return result;
    }

    public static int[] getStudentsByGPA(double lower, double higher, int[] studentIdList, char[][] studentsGrades) {
       if(lower < 0 && higher < 0 && lower > higher){
           return null;
       }
       int count = 0;
       double[] gpalist = calculateGPA(studentIdList, studentsGrades);

       for (double gpa : gpalist){
           if(gpa >= lower && gpa <= higher){
               count++;
           }
       }
       int[] result = new int[count];
       int index = 0;
       for(int i = 0; i < gpalist.length; i++){
           if(gpalist[i] >= lower && gpalist[i] <= higher){
               result[index++] = studentIdList[i];
           }
       }
       return result;
    }

    public static void main(String[] args) {
        int id = 1000;
        RoughWork obj = new RoughWork();
        int[] std = {1,2};
        char[][] grd = {{'A','A','B'},{'A','B'}};
        double[] result = calculateGPA(std, grd);
        System.out.println(result[0]);


    }
    // main method

}
