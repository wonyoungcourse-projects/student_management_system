package org.example.controller;

import org.example.domain.Students;
import org.example.view.InputView;
import org.example.view.OutputView;

public class StudentController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Students students = new Students();

    public StudentController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }
//depth를 줄이는 방법으로 일단 메서드 분리를 했음
    public void run() {
        while (true) {
            outputView.printMenu();
            String command = inputView.read();

            if (command.equals("exit")) {
                return;
            }
            process(command);
        }
    }

    private void process(String command) {
        try {
            if (command.equals("1")) viewStudents();
            else if (command.equals("2")) addStudents();
            else if (command.equals("3")) searchStudents();
            else if (command.equals("4")) updateStudent();
            else if (command.equals("5")) deleteStudents();
            else throw new IllegalArgumentException("[ERROR] 잘못된 메뉴 입력");
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void viewStudents() {
        outputView.printStudents(students.findAll());
        inputView.readExit();
    }

    private void addStudents() {
        while (true) {
            try {
                outputView.printInputForm();
                String input = inputView.read();
                if (input.equals("exit")) return;
                int count = students.add(input);
                outputView.printAddResult(count);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage()); //에러 메시지만 출력하고 메뉴다시
            }
        }
    }

    private void searchStudents() {
        while (true) {
            outputView.printSearchForm();
            String keyword = inputView.read();
            if (keyword.equals("exit")) return;
            outputView.printSearchResult(students.search(keyword));
        }
    }

    private void updateStudent() {
        while (true) {
            outputView.printUpdateForm();
            String input = inputView.read();
            if (input.equals("exit")) return;
            outputView.printUpdateResult(students.update(input));
        }
    }

    private void deleteStudents() {
        while (true) { //while 1개 try 1개 if 1개
            try {
                outputView.printDeleteForm();
                String input = inputView.read();
                if (input.equals("exit")) {
                    return;
                }
                String result = students.delete(input);
                outputView.printDeleteResult(result);
            } catch (IllegalArgumentException | IllegalStateException e) { //이건 try depth에 포함되는가? 포함되니까 테스트 성공했겠죠?
                outputView.printError(e.getMessage());
            }
        }
    }
}/*
private void deleteStudents() {
    while (true) {
        outputView.printDeleteForm();
        String input = inputView.read();
        if (input.equals("exit")) return;
        outputView.printDeleteResult(students.delete(input));
    }
}
처음에 이렇게 짰는데 다시 입력받기가 안돼서 예외처리 추가했음
*/
