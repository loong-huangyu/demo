package com.example.demo.cti.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import com.example.demo.cti.demo.domain.model.Device;
import com.example.demo.cti.demo.domain.model.StudentInfo;

class DemoApplicationTests {

    // 从十六进制字符串到字节数组转换
    public static byte[] HexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    @Test
    void contextLoads() throws UnsupportedEncodingException {
        LocalTime time2 = LocalTime.parse("09:30:00");
        System.out.println(time2);
        /**
         * 微信返回16进制数据解码
         */
        String hexStr = "e8aea2e58d95e98791e9a29de68896e98080e6acbee98791e9a29de4b88ee4b98be5898de8afb7e6b182e4b88de4b880e887b4efbc8ce8afb7e6a0b8e5ae9ee5908ee5868de8af95";
        byte[] by = HexStringToBytes(hexStr);
        String store_name = new String(by, "utf-8");
        System.out.println(store_name);

    }

    @Test
    void steamMap() {
        Map<String, List<Device>> itemOrderMap = new HashMap<>();
        List<Device> deviceList1 = new ArrayList<>();
        deviceList1.add(new Device("1", "huang"));
        deviceList1.add(new Device("2", "yang"));
        deviceList1.add(new Device("3", "zhou"));
        itemOrderMap.put("key1", deviceList1);
        List<Device> deviceList2 = new ArrayList<>();
        deviceList2.add(new Device("4", "huang"));
        deviceList2.add(new Device("5", "yang"));
        deviceList2.add(new Device("6", "zhou"));
        itemOrderMap.put("key2", deviceList2);
        List<Device> deviceList3 = new ArrayList<>();
        deviceList3.add(new Device("7", "huang"));
        deviceList3.add(new Device("8", "yang"));
        deviceList3.add(new Device("9", "zhou"));
        deviceList3.add(new Device("10", "jiang"));
        deviceList3.add(new Device("11", "zhou"));
        itemOrderMap.put("key3", deviceList3);

        List<String> aaa = deviceList3.stream().map(Device::getId).collect(Collectors.toList());
        System.out.println("aaa = " + aaa.toString());
        int a = deviceList3.stream().filter(device -> device.getName().equals("zhou")).collect(Collectors.toList())
            .size();
        System.out.println("a = " + a);
    }

    @Test
    void stringToTimestamp() throws ParseException {
        String str = "2018-08-16 20:07:56";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(str);
        System.out.println(date.getTime());

        Long ts = Timestamp.valueOf(str).getTime(); //2018-08-16 20:07:56.0
        System.out.println(ts);

        String dateFormat = sdf.format(new Date());
        System.out.println(dateFormat);
        String dateFormat2 = (new Date()).toString();
        System.out.println(dateFormat2);

    }

    @Test
    void testSort() {
        //测试数据，请不要纠结数据的严谨性
        List<StudentInfo> studentList = new ArrayList<>();
        studentList.add(new StudentInfo("李小明", true, 18, 1.76, LocalDate.of(2001, 3, 23)));
        studentList.add(new StudentInfo("张小丽", false, 18, 1.61, LocalDate.of(2001, 6, 3)));
        studentList.add(new StudentInfo("王大朋", true, 19, 1.82, LocalDate.of(2000, 3, 11)));
        studentList.add(new StudentInfo("陈小跑", false, 17, 1.67, LocalDate.of(2002, 10, 18)));

        StudentInfo.printStudents(studentList);
        //按年龄排序(Integer类型)
        //List<StudentInfo> studentsSortName =
        studentList.stream().sorted(Comparator.comparing(StudentInfo::getGender).reversed())
            .collect(Collectors.toList());
        //排序后输出
        StudentInfo.printStudents(studentList);
    }

    @Test
    void testRead() {
        List<String> resultList = new ArrayList<>();
        File file = new File("/Users/cti/test/data_20210904_050219.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                if (StringUtils.isNotBlank(s)) {
                    int startChar = s.indexOf("对象") + 4;
                    int endChar = s.indexOf("__topic__") - 4;
                    System.out.println(s.substring(startChar, endChar));
                    resultList.add(s.substring(startChar, endChar));
                } else {
                    resultList.add("");
                }
            }
            br.close();
            System.out.println();
            Collections.reverse(resultList);
            resultList.stream().forEach(s1 -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(s1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("123", "123");
        map.put("3423", "3423");
        map.put("1423", "1423");
        String[] value = map
            .values()
            .stream()
            .map(Object::toString)
            .collect(Collectors.toList())
            .toArray(new String[map.size()]);
        for (String ss : value) {
            System.out.println(ss);
        }
    }



}
