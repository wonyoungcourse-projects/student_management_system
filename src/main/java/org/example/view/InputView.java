package org.example.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.nextLine().trim();
    }

    public void readExit() {
        read();
    }
}
