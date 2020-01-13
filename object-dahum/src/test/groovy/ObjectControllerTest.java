import java.util.List;
import java.util.Map;
import org.junit.Test;

class ObjectControllerTest extends ObjectController{

    ObjectController oc = new ObjectController();
    TestClass tc = new TestClass();


    @Test
    void objToMap() {
        Map<String,Object> ret = ObjectController.objToMap(new TestClass("aaa",10,"BBB",4.3));

        System.out.println(ret);
    }

    @Test
    void getFieldsList() {
        List<String> list = ObjectController.getFieldNameList(tc);
        System.out.println(list);
    }

    @Test
    void getFieldsListWithAllSuperClasses() {
        List<String> list = ObjectController.getFieldNameListWithAllSuperClasses(tc);
        System.out.println(list);
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