import java.util.Arrays;
import java.util.Scanner;
/*
*This problem was asked by Atlassian
*
* MegaCorp want to give bonuses to its employees based on how many lines
* of code they have written. They would like to give the smallest positive
* amount to each worker consistent with the constraint that if a developer
* written more lines of code than their neighbour, they should receive more
* money.
*
* Given an array representing a line of seats of employees at Megacorp,
* determine how much each one should get paid.
*
* for example [10,40,200,1000,60,30], you should return
* [1,2,3,4,2,1]
 */
class Person{
    public Person(int linesOfCode) {
        this.linesOfCode = linesOfCode;
        //the least amount of award by default is 1. none should get 0
        this.award = 1;
    }

    private int linesOfCode;
    private int award;

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public void setLinesOfCode(int linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    public int getAward() {
        return award;
    }

    public void addAward(int award) {
        this.award += award;
    }
}

public class Atlassian {
    //lines of code collected according to sitting position
    static Person[] person;
    static int[] list;
    //check if has a more lines than previous neighbour
    public static boolean wroteMoreLinesThanPreviousNeighbour(Person currentPerson, Person previousPerson) {
        return currentPerson.getLinesOfCode() > previousPerson.getLinesOfCode();
    }

    //main algorithm to award persons that participated
    public static void  awardPersonNo(int i){
        int j = 1;
        Person personBeingAwarded = person[i];
        Person previousPersonAwarded = person[i-j];
        //if person wrote more codes than previous person, award points of previous persons
        //else punish, don't award.
        if(wroteMoreLinesThanPreviousNeighbour(personBeingAwarded,previousPersonAwarded)) {
            personBeingAwarded.addAward(previousPersonAwarded.getAward());
        }
        //whenever ties in points with the previous person
        //always award previous person one point
        while(previousPersonAwarded.getAward()==personBeingAwarded.getAward() && i - j >= 0){
            //reinit previous person and current person, then award
            personBeingAwarded = previousPersonAwarded;
            personBeingAwarded.addAward(1);
            //increment the value of j, then get another previous person
            j++;
            try {
                previousPersonAwarded = person[i - j];
            }catch(Exception ArrayIndexOutOfBoundsException){
                return;
            }
        }
    }


    public static void main(String...args){
        //get input
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter list in the form [1,2,5,3,4] : ");

        //read user input into an array of integers
        list = Arrays.stream(scanner.nextLine()
                                .replace("[","")
                                .replace("]","")
                                .split(","))
                                .mapToInt(Integer::parseInt).toArray();

        //initialize the total number of person array that participated
        person = new Person[list.length];

        //iterate the array of lines of code creating a person for each and
        //assigning them the lines of code.

        for (int i = 0; i < list.length; i++ ) {
            person[i] = new Person(list[i]);
            //the first person is awarded 1 automatically on initialisation,
            //no need to award first person
            if(i>0) {
                awardPersonNo(i);
            }

        }
        //printing the output to console
        System.out.print("[");
        for (int i = 0; i < person.length; i++){

            System.out.print(person[i].getAward());
            //position the comas in place
            if (i<person.length-1){
                System.out.print(",");
            }
        }
        System.out.print("]");

    }
}
