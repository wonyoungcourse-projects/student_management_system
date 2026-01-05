package org.example;

import org.example.controller.StudentController;
import org.example.view.InputView;
import org.example.view.OutputView;

public class Application {
    public static void main(String[] args) {
        StudentController controller =
                new StudentController(new InputView(), new OutputView());
        controller.run();
    }
}
