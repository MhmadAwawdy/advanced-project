package com.example.libraryfinalproject.DB;
import com.example.libraryfinalproject.DB.*;
import com.example.libraryfinalproject.Models.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // إعداد الاتصال باستخدام Hibernate
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml") // التأكد من أن المسار للملف صحيح
                    .addAnnotatedClass(Book.class) // إضافة الـEntity المعنية (مثل Book) لتأكُّد من التطابق
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
        }
    }

    // الحصول على SessionFactory لاستخدامها في إنشاء الجلسات (sessions)
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // إضافة هذه الطريقة التي تتيح غلق SessionFactory بشكل صحيح عند نهاية الدورة البرمجية
    public static void shutdown() {
        getSessionFactory().close();  // غلق الاتصال بـ Hibernate
    }
}
