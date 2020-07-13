package com.printer;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Times {
    private static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SSS");

    public Times() {
    }

    public static void test(String title, Task task) {
        if (task != null) {
            title = title == null ? "" : "【" + title + "】";
            System.out.println(title);
            System.out.println("开始：" + fmt.format(new Date()));
            long begin = System.currentTimeMillis();
            task.execute();
            long end = System.currentTimeMillis();
            System.out.println("结束：" + fmt.format(new Date()));
            double delta = (double)(end - begin) / 1000.0D;
            System.out.println("【" + title + "】"+"耗时：" + delta + "秒");
            System.out.println("-------------------------------------");
        }
    }
}
