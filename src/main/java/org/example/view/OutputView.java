package org.example.view;

import org.example.domain.Student;

import java.util.List;

public class OutputView {

    public void printMenu() {
        System.out.println("----- 메뉴 -----");
        System.out.println("1. 학생 리스트 뷰");
        System.out.println("2. 학생 정보 입력");
        System.out.println("3. 학생 정보 검색");
        System.out.println("4. 학생 정보 수정");
        System.out.println("5. 학생 정보 삭제");
        System.out.println("---------------");
    }

    public void printStudents(List<Student> students) {
        System.out.println("----- 학생 -----");
        if (students.isEmpty()) {
            System.out.println("비어있음");
        }
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("---------------");
    }

    public void printInputForm() {
        System.out.println("입력(학번, 이름) :");
    }

    public void printAddResult(int count) {
        System.out.println("새로운 학생들이 성공적으로 입력되었습니다. (" + count + "건)");
    }

    public void printSearchForm() {
        System.out.println("입력(학번 or 학생 ) :");
    }

    public void printSearchResult(List<Student> result) {
        System.out.println("--- 검색결과 ---");
        for (Student s : result) {
            System.out.println(s);
        }
        System.out.println("--------------");
    }

    public void printUpdateForm() {
        System.out.println("입력(학번) :");
    }

    public void printUpdateResult(String message) {
        System.out.println(message);
    }

    public void printDeleteForm() {
        System.out.println("입력(학번) :");
    }

    public void printDeleteResult(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
