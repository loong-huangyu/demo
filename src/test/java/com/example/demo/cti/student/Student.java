package com.example.demo.cti.student;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rain
 * @description:
 * @date 2023/5/10 2:26 下午
 */

@Data
@AllArgsConstructor
public class Student {

    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private Integer classNumber;

}
