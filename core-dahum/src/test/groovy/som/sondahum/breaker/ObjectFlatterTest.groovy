package som.sondahum.breaker

import com.sondahum.breaker.ObjectFlatter
import org.junit.Before;
import org.junit.Test;

class ObjectFlatterTest {

    ObjectFlatter objectFlatter
    TestClass testClass

    @Before
    void init() {
        objectFlatter = new ObjectFlatter()
        testClass = new TestClass()
    }


    @Test
    void objToMap() {
        Map<String,Object> ret = objectFlatter.objToMap(testClass)

        println ret
    }

    @Test
    void objToMApWithAllSuperClasses() {
        Map<String, Object> ret =
                objectFlatter.objToMapAllSuperClasses(testClass)

        println ret
    }

    @Test
    void getFieldsList() {
        List<String> list = objectFlatter.getFieldsList(testClass)

        println list
    }

    @Test
    void getFieldsListWithAllSuperClasses() {
        List<String> list = objectFlatter.getFieldsListWithAllSuperClasses(testClass)

        println list
    }



    class SuperTestClass {
        String gender = "male";
        Boolean married = false;
    }

    class TestClass extends SuperTestClass{
        String name = "aaa";
        Integer age = 10;
        String address = "BBB";
        Double gpa = 4.3;

        public TestClass() {}

        public TestClass(String name, Integer age, String address, Double gpa) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.gpa = gpa;
        }
    }
}