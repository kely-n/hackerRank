package day11_2D_Arrays;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Stream;

public class day11 {
    private static int[][] getMatrix() {
        int[][] matrix = new int[6][];
        //get input from file
        URL path = day11.class.getResource("file.matrix") ;
        try {
            File file = new File(path.getFile());
            Scanner sc = new Scanner(file);
            int col = 0;
            while (sc.hasNextLine()){
                int []row = Stream.of(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                matrix[col] = row;
                col++;
            }
        }catch (Exception e){
            System.out.println("File not found");
        }
        return matrix;
    }
    private static int getSumOf(int [][]hourglass){
        int sum = 0;
        for(int i=0; i<hourglass.length; i++){
            for(int j=0; j<hourglass[i].length; j++){
                sum+=hourglass[i][j];
            }
        }
        return sum;
    }
    public static void main (String ... args){
        //initialize 2d array
        int[][] matrix = getMatrix();
        //initialize max hourglass to the minimum value possible
        int[][] maxHourglass = {
                {-9 , -9, -9},
                {-9},
                {-9 , -9, -9},
            };
        //iterate loop to get max hourglass;
        for(int i=0; i<matrix.length-2;i++){
            for(int j=0; j<matrix.length-2;j++){
                int[][] hourglass = {
                        {matrix[i][j], matrix[i][j+1], matrix[i][j+2]},
                        {matrix[i+1][j+1]},
                        {matrix[i+2][j] , matrix[i+2][j+1], matrix[i+2][j+2]},
                };
                if(getSumOf(hourglass)>getSumOf(maxHourglass)){
                    maxHourglass = hourglass;
                }
            }
        }
        System.out.println(+getSumOf(maxHourglass));

//        System.out.println("the hourglass with the largest sum ("+getSumOf(maxHourglass)+") is ");
//        for(int i=0; i<maxHourglass.length; i++){
//            for(int j=0; j<maxHourglass[i].length; j++){
//                System.out.print(maxHourglass[i][j] +" ");
//            }
//            System.out.println();
//        }
    }
}
