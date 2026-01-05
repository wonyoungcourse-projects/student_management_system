package org.example.domain;

import java.util.*;

public class Students {

    private final Map<String, org.example.domain.Student> students = new HashMap<>();

    public int add(String input) {
        String[] tokens = input.split(",");
        int count = 0;

        for (String t : tokens) {
            String[] pair = t.trim().split(" ");
            if (pair.length != 2) {
                throw new IllegalArgumentException("[ERROR] 입력 형식 오류");
            }
            if (!students.containsKey(pair[0])) {
                students.put(pair[0], new org.example.domain.Student(pair[0], pair[1]));
                count++;
            }
        }
        return count;
    }
//리스트 사용은 생각을 못했음. Map으로 하다가 일부 학생 출력에서 뇌정지오고 gpt 쓰긴 했음..
    public List<org.example.domain.Student> findAll() {
        List<org.example.domain.Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparing(org.example.domain.Student::getId));
        return list;
    }

    public List<org.example.domain.Student> search(String keyword) {
        List<org.example.domain.Student> result = new ArrayList<>();
        for (org.example.domain.Student s : students.values()) {
            if (s.match(keyword)) {
                result.add(s);
            }
        }
        result.sort(Comparator.comparing(org.example.domain.Student::getId));
        return result;
    }

    public String update(String input) {
        String[] pair = input.split(",");
        if (pair.length != 2) {
            throw new IllegalArgumentException("[ERROR] 입력 형식 오류");
        }
        org.example.domain.Student s = students.get(pair[0].trim());
        if (s == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 학번");
        }
        return s.update(pair[1].trim());
    }

    public String delete(String input) {
        String[] ids = input.split(",");
        int count = 0;
        for (String id : ids) {
            if (students.remove(id.trim()) != null) {
                count++;
            }
        }
        if (count == 0) {
            throw new IllegalStateException("[ERROR] 삭제할 학생 없음");
        }
        return "삭제 성공! (" + count + "건)";
    }
}
