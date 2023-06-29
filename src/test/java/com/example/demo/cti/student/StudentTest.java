package com.example.demo.cti.student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * @author rain
 * @description:
 * @date 2023/5/10 4:04 下午
 */
public class StudentTest {

    @Test
    public void test() {
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(new Student(1, "张三", "男", 20, 2));
        studentList.add(new Student(2, "李四", "男", 21, 1));
        studentList.add(new Student(3, "王五", "女", 19, 1));
        studentList.add(new Student(4, "赵六", "男", 19, 3));
        studentList.add(new Student(5, "王大锤", "男", 20, 2));

        int count = studentList.stream().collect(Collectors.groupingBy(Student::getClassNumber)).size();
        System.out.println("count : " + count);
    }

}
