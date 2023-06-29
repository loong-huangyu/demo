package com.example.demo.cti.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.assertj.core.util.Lists;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rain
 * @description:
 * @date 2022/10/12 9:48 上午
 */
@Data
@NoArgsConstructor
public class User implements Serializable {

    private Name name;

    private String age;

    private List<AbstractEvent> event = Lists.newArrayList();

    public User(String first, String second, String age) {
        this.name = new Name(first, second);
        this.age = age;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public void clearData() {
        this.event.add(new UserEvent(this.clone()));
        this.name = null;
    }

    @Override
    protected User clone() {
        try {
            // 将对象本身序列化到字节流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            // 再将字节流通过反序列化方式得到对象副本
            ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            return (User) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}
