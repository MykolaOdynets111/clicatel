package util;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

public class DataProvider {
    private static final AtomicInteger numeric = new AtomicInteger(15);
    private static final AtomicInteger ukPhoneNumeric = new AtomicInteger(10);
    private static final AtomicInteger saPhoneNumeric = new AtomicInteger(9);
    private static final AtomicReference<Faker> faker = new AtomicReference<>(new Faker(Locale.ENGLISH));

//    private DataProvider() {​​
//    }​​
//
//    public static List<String> getStringsList(final int count) {​​
//        return faker.get().lorem().words(count);
//    }​​
//    public static String getRandomSingleWord() {​​
//        return faker.get().lorem().word();
//    }​​
//    public static String getRandomFirstName() {​​
//        return faker.get().name().firstName();
//    }​​
//    public static String getRandomLastName() {​​
//        return faker.get().name().lastName();
//    }​​
//    public static String getRandomFullName() {​​
//        return faker.get().name().fullName();
//    }​​
//    public static String getRandomCompanyName() {​​
//        return faker.get().company().name();
//    }​​
//    public static String getRandomUrl() {​​
//        return format("https://www.%s.com", faker.get().lorem().word());
//    }​​
}
