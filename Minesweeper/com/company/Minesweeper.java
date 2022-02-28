package com.company;
import java.util.Scanner;
import java.lang.Math;

public class Minesweeper {

    public static void main(String[] args) {

        boolean cycle = true;
        boolean cycle_cc = true;
        int int_chance;
        float chance = 0.2f;
        String enter_size;
        int size = 0;
        Scanner enter_size_str = new Scanner(System.in);
        while (cycle) {
            System.out.println("Enter field size");
            enter_size = enter_size_str.nextLine();
            if (enter_size.equals("change_chance")) {
                while (cycle_cc) {
                    enter_size = enter_size_str.nextLine();
                    if (isNumber(enter_size)) {
                        int_chance = Integer.parseInt(enter_size);
                        if (int_chance > -1 && int_chance < 101) {
                            cycle_cc = false;
                            chance = Integer.parseInt(enter_size);
                            chance = chance / 100;
                            System.out.println("Enter field size");
                            enter_size = enter_size_str.nextLine();
                        } else {
                            System.out.println("Invalid value");
                        }
                    } else if (!isNumber(enter_size)) {
                        System.out.println("Invalid value");
                    }
                }
            }
            if (isNumber(enter_size)) {
                size = Integer.parseInt(enter_size);
                if (size > 1 && size < 65) {
                    cycle = false;
                } else {
                    System.out.println("Invalid value. Maximum size is 64. Minimum size is 2.");
                }
            } else if (!isNumber(enter_size)) {
                System.out.println("Invalid value");
            }
        }

        String[][] showfield = new String[size][size];
        int fill_y = 0, fill_x = 0;
        boolean fill_done = false;
        while (!fill_done) {
            showfield[fill_y][fill_x] = "#";
            fill_x++;
            if (fill_x == size) {
                fill_x = 0;
                fill_y++;
            }
            if (fill_y == size) {
                fill_done = true;
            }
        }

        String[][] bombfield = new String[size][size];
        int generate_y = 0, generate_x = 0;
        double random_generate;
        boolean generate_done = false;
        while (!generate_done) {
            random_generate = Math.random();
            if (random_generate >= 0 && random_generate < chance) {
                bombfield[generate_y][generate_x] = "X";
            } else {
                bombfield[generate_y][generate_x] = "0";
            }
            generate_x++;
            if (generate_x == size) {
                generate_x = 0;
                generate_y++;
            }
            if (generate_y == size) {
                generate_done = true;
            }
        }

        int bombcheck = 0;

        Scanner enter = new Scanner(System.in);
        String enter_rightcheck;
        int int_enterx = 0;
        int int_entery = 0;
        Scanner enter_action = new Scanner(System.in);
        String action;
        boolean wincheck = false;
        int wincheck1 = 0, wincheck2 = 0;
        boolean win = false;

        while (true) {
            cycle = true;
            while (cycle) {
                System.out.println("Enter Y");
                enter_rightcheck = enter.nextLine();
                if (isNumber(enter_rightcheck)) {
                    int int_enter_rightcheck = Integer.parseInt(enter_rightcheck);
                    if (int_enter_rightcheck > 0 && int_enter_rightcheck < size+1) {
                        int_entery = int_enter_rightcheck - 1;
                        cycle = false;
                    } else {
                        System.out.println("Invalid value");
                    }
                } else if (!isNumber(enter_rightcheck)) {
                    System.out.println("Invalid value");
                }
            }
            cycle = true;
            while (cycle) {
                System.out.println("Enter X");
                enter_rightcheck = enter.nextLine();
                if (isNumber(enter_rightcheck)) {
                    int int_enter_rightcheck = Integer.parseInt(enter_rightcheck);
                    if (int_enter_rightcheck > 0 && int_enter_rightcheck < size+1) {
                        int_enterx = int_enter_rightcheck - 1;
                        cycle = false;
                    } else {
                        System.out.println("Invalid value");
                    }
                } else if (!isNumber(enter_rightcheck)) {
                    System.out.println("Invalid value");
                }
            }
            cycle = true;
            while (cycle) {
                System.out.println("Choice action (flag[P] / check[X])");
                action = enter_action.nextLine();
                if (action.equals("P")) {
                    if (showfield[int_entery][int_enterx] == "#") {
                        showfield[int_entery][int_enterx] = "P";
                        cycle = false;
                        show(showfield,size);
                    } else if (showfield[int_entery][int_enterx] == "P") {
                        showfield[int_entery][int_enterx] = "#";
                        cycle = false;
                        show(showfield,size);
                    }
                    if (showfield[int_entery][int_enterx] != "#" && showfield[int_entery][int_enterx] != "P") {
                        System.out.println("Invalid action");
                    }
                } else if (action.equals("X")) {
                    cycle = false;
                    bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                    show(showfield,size);
                    wincheck(wincheck, win, wincheck1, wincheck2, bombfield, showfield, size);
                }
                if (!action.equals("P") && !action.equals("X")) {
                    System.out.println("Invalid action");
                }
            }
        }
    }
    public static boolean isNumber(String enter_rightcheck) {
        for (char c: enter_rightcheck.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    public static String[][] bombcount(String[][] showfield, int bombcheck, String[][] bombfield, int int_enterx, int int_entery, int size) {
        if (bombfield[int_entery][int_enterx] == "0") {
            bombcheck = 0;
            if (int_entery-1 >= 0 && int_enterx-1 >= 0) {
                if (bombfield[int_entery-1][int_enterx-1] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_entery-1 >= 0) {
                if (bombfield[int_entery-1][int_enterx] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_entery-1 >= 0 && int_enterx+1 <= size-1) {
                if (bombfield[int_entery-1][int_enterx+1] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_enterx-1 >= 0) {
                if (bombfield[int_entery][int_enterx-1] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_enterx+1 <= size-1) {
                if (bombfield[int_entery][int_enterx+1] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_entery+1 <= size-1 && int_enterx-1 >= 0) {
                if (bombfield[int_entery+1][int_enterx-1] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_entery+1 <= size-1) {
                if (bombfield[int_entery+1][int_enterx] == "X") {
                    bombcheck += 1;
                }
            }
            if (int_entery+1 <= size-1 && int_enterx+1 <= size-1) {
                if (bombfield[int_entery+1][int_enterx+1] == "X") {
                    bombcheck += 1;
                }
            }
            String str_bombcheck = String.valueOf(bombcheck);
            showfield[int_entery][int_enterx] = str_bombcheck;
            if (bombcheck == 0) {
                bombcount_cont(showfield,bombcheck,bombfield,int_enterx,int_entery,size);
            }
        } else if (bombfield[int_entery][int_enterx] == "X"){
            showfield[int_entery][int_enterx] = "X";
            show(showfield,size);
            lose();
        }
        return showfield;
    }
    public static String[][] bombcount_cont(String[][] showfield, int bombcheck, String[][] bombfield, int int_enterx, int int_entery, int size) {
        if (int_entery-1 >= 0 && int_enterx-1 >= 0) {
            if (showfield[int_entery-1][int_enterx-1] == "#") {
                int_enterx--;
                int_entery--;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx++;
                int_entery++;
            }
        }
        if (int_entery-1 >= 0) {
            if (showfield[int_entery - 1][int_enterx] == "#") {
                int_entery--;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_entery++;
            }
        }
        if (int_entery-1 >= 0 && int_enterx+1 <= size-1) {
            if (showfield[int_entery - 1][int_enterx + 1] == "#") {
                int_enterx++;
                int_entery--;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx--;
                int_entery++;
            }
        }
        if (int_enterx-1 >= 0) {
            if (showfield[int_entery][int_enterx - 1] == "#") {
                int_enterx--;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx++;
            }
        }
        if (int_enterx+1 <= size-1) {
            if (showfield[int_entery][int_enterx + 1] == "#") {
                int_enterx++;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx--;
            }
        }
        if (int_entery+1 <= size-1 && int_enterx-1 >= 0) {
            if (showfield[int_entery + 1][int_enterx - 1] == "#") {
                int_enterx--;
                int_entery++;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx++;
                int_entery--;
            }
        }
        if (int_entery+1 <= size-1) {
            if (showfield[int_entery + 1][int_enterx] == "#") {
                int_entery++;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_entery--;
            }
        }
        if (int_entery+1 <= size-1 && int_enterx+1 <= size-1) {
            if (showfield[int_entery + 1][int_enterx + 1] == "#") {
                int_enterx++;
                int_entery++;
                bombcount(showfield, bombcheck, bombfield, int_enterx, int_entery, size);
                int_enterx--;
                int_entery--;
            }
        }
        return showfield;
    }
    public static int show(String showfield[][],int size) {
        System.out.print("   ");
        for (int show = 0; show < size; show++) {
            if (show < 9) {
                System.out.print(show+1 + "  ");
            } else {
                System.out.print(show+1 + " ");
            }
        }
        System.out.println("");
        for (int show = 0; show < size; show++) {
            if (show < 9) {
                System.out.print(show+1 + "  ");
            } else {
                System.out.print(show+1 + " ");
            }
            for (int show2 = 0; show2 < size; show2++) {
                System.out.print(showfield[show][show2] + "  ");
            }
            System.out.print(show+1);
            System.out.print("  ");
            System.out.println("");
        }
        System.out.print("   ");
        for (int show = 0; show < size; show++) {
            if (show < 9) {
                System.out.print(show+1 + "  ");
            } else {
                System.out.print(show+1 + " ");
            }
        }
        System.out.println("");
        return 0;
    }
    public static int lose() {
        System.out.println("You hit the bomb! Lose!");
        Scanner exit = new Scanner(System.in);
        exit.nextLine();
        System.exit(0);
        return 0;
    }
    public static int wincheck(boolean wincheck,boolean win,int wincheck1, int wincheck2, String[][] bombfield, String[][] showfield, int size){
        while (wincheck != true) {
            if (bombfield[wincheck1][wincheck2] == "0") {
                if (showfield[wincheck1][wincheck2] != "#") {
                    wincheck = false;
                    wincheck1++;
                    if (wincheck1 == size-1) {
                        wincheck1 = 0;
                        wincheck2++;
                    }
                } else {
                    wincheck = true;
                }
            } else {
                if (wincheck1 < size-1) {
                    wincheck1++;
                } else if (wincheck1 == size-1) {
                    wincheck1 = 0;
                    wincheck2++;
                }
            }
            if (wincheck2 == size) {
                wincheck = true;
                win = true;
            }
        }
        if (win == true) {
            System.out.println("You have found all the bombs! You have won!");
            Scanner exit = new Scanner(System.in);
            exit.nextLine();
            System.exit(0);
        }
        return 0;
    }
}