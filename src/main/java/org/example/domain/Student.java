package org.example.domain;

public class Student {
    private final String id;
    private String name;

    public Student(String id, String name) {
        if (id.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 학번 또는 이름이 비어있음");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean match(String keyword) {
        return id.contains(keyword) || name.contains(keyword);
    }

    public String update(String newName) {
        String before = toString();
        this.name = newName;
        return "변경 성공! [" + before.replace(" ", ", ") +
                "] -> [" + id + ", " + name + "]";
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
