package com.michalek;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int width, height;

        width = scanner.nextInt();
        height = scanner.nextInt();

        boolean[][] map = new boolean[width][height];

        for(int i = height - 1; i >= 0; i--) {
            for(int j = 0; j < width; j++) {
                if(scanner.nextInt() == 1) {
                    map[j][i] = true;
                } else {
                    map[j][i] = false;
                }
            }
        }

        int numOfQueries = scanner.nextInt();
        scanner.nextLine();
        String input;

        for(int i = 0; i < numOfQueries; i++) {
            input = scanner.next();
            int startX = scanner.nextInt();
            int startY = scanner.nextInt();
            int endX = scanner.nextInt();
            int endY = scanner.nextInt();


            boolean[][] newMap = new boolean[width][height];

            for(int z = 0; z < height; z++) {
                for(int k = 0; k < width; k++) {
                    newMap[k][z] = map[k][z];
                }
            }

            if(input.equals("r")) {
                RecursivePathfinder recursivePathfinder = new RecursivePathfinder();
                LinkedList<Character> outputList = recursivePathfinder.findPath(newMap, startX, startY, endX, endY);

                String output = getOutputString(outputList, "r");

                System.out.println(output);
            } else if(input.equals("i")) {
                IterativePathfinder iterativePathfinder = new IterativePathfinder();
                LinkedList<Character> outputList = iterativePathfinder.findPath(newMap, startX, startY, endX, endY);

                String output = getOutputString(outputList, "i");

                System.out.println(output);
            }
        }
    }

    public static String getOutputString(LinkedList<Character> outputList, String mode) {
        if(outputList.isEmpty()) {
            return mode + " X";
        } else {

            String outputString = mode + " ";

            for(int i = 0; !outputList.isEmpty(); i++) {
                outputString += outputList.pop() + " ";
            }

            return outputString;
        }
    }
}

class RecursivePathfinder {

    private int endX, endY;
    static int currentX, currentY;
    static LinkedList<Character> stack;
    static boolean[][] map;
    static boolean[][] beenThere;


    public LinkedList<Character> findPath(boolean[][] inputMap, int startX, int startY, int endX, int endY) {

        currentX = startX;
        currentY = startY;
        this.endX = endX;
        this.endY = endY;

        stack = new LinkedList<Character>();
        beenThere = new boolean[inputMap.length][inputMap[0].length];
        map = inputMap;

        if(currentX == endX && currentY == endY) {
            stack.add(' ');
            return stack;
        }

        goNext();

        return stack;
    }

    public void goNext() {

        if(currentX == endX && currentY == endY) {
            return;
        }

        if(currentX > 0) {

            if(!map[currentX - 1][currentY] && !beenThere[currentX - 1][currentY]) {
                beenThere[currentX][currentY] = true;
                stack.add('W');
                currentX--;
                goNext();
                return;
            }

        }

        if(currentX < map.length - 1) {

            if(!map[currentX + 1][currentY] && !beenThere[currentX + 1][currentY]) {
                beenThere[currentX][currentY] = true;
                stack.add('E');
                currentX++;
                goNext();
                return;
            }

        }

        if(currentY > 0) {

            if(!map[currentX][currentY - 1] && !beenThere[currentX][currentY - 1]) {
                beenThere[currentX][currentY] = true;
                stack.add('S');
                currentY--;
                goNext();
                return;
            }

        }

        if(currentY < map[0].length - 1) {

            if(!map[currentX][currentY + 1] && !beenThere[currentX][currentY + 1]) {
                beenThere[currentX][currentY] = true;
                stack.add('N');
                currentY++;
                goNext();
                return;
            }

        }
        
        beenThere[currentX][currentY] = true;

        if(stack.isEmpty()) {
            return;
        }


        if(stack.peekLast().equals('S')) {
            currentY++;
        } else if(stack.peekLast().equals('N')) {
            currentY--;
        } else if(stack.peekLast().equals('W')) {
            currentX++;
        } else if(stack.peekLast().equals('E')) {
            currentX--;
        }

        stack.removeLast();

        goNext();
    }
}

class IterativePathfinder {

    int endX, endY;
    int currentX, currentY;
    LinkedList<Character> stack;
    boolean[][] map;
    boolean[][] beenThere;


    public LinkedList<Character> findPath(boolean[][] inputMap, int startX, int startY, int endX, int endY) {

        currentX = startX;
        currentY = startY;
        this.endX = endX;
        this.endY = endY;

        stack = new LinkedList<Character>();
        beenThere = new boolean[inputMap.length][inputMap[0].length];
        map = inputMap;

        if(currentX == endX && currentY == endY) {
            stack.add(' ');
            return stack;
        }

        goNext();

        return stack;
    }

    public void goNext() {

        while(true) {
            if(currentX == endX && currentY == endY) {
                return;
            }

            int lastX = currentX;
            int lastY = currentY;

                if(currentX > 0 && !map[currentX - 1][currentY] && !beenThere[currentX - 1][currentY]) {
                    stack.add('W');
                    currentX--;
                } else if(currentX < map.length - 1 && !map[currentX + 1][currentY] && !beenThere[currentX + 1][currentY]) {
                     stack.add('E');
                     currentX++;
                } else if(currentY > 0 && !map[currentX][currentY - 1] && !beenThere[currentX][currentY - 1]) {
                     stack.add('S');
                     currentY--;
                } else if(currentY < map[0].length - 1 && !map[currentX][currentY + 1] && !beenThere[currentX][currentY + 1]) {
                    stack.add('N');
                    currentY++;
                } else {

                    if(stack.isEmpty()) {
                        return;
                    }
                    map[currentX][currentY] = true;

                    if(stack.peekLast().equals('S')) {
                        currentY++;
                    } else if(stack.peekLast().equals('N')) {
                        currentY--;
                    } else if(stack.peekLast().equals('W')) {
                        currentX++;
                    } else if(stack.peekLast().equals('E')) {
                        currentX--;
                    }

                    stack.removeLast();
                }


            beenThere[lastX][lastY] = true;
        }
    }
}
