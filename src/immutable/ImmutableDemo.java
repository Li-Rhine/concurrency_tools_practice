package immutable;

import java.util.HashSet;
import java.util.Set;

/**
 *  一个属性是对象，但是整体不可变，其他类无法修改Set里面的数据
 */
public class ImmutableDemo {
    private final Set<String> students = new HashSet<>();

    public ImmutableDemo() {
        students.add("李小美");
        students.add("王状");
        students.add("徐福记");
    }

    public boolean isStudents(String name) {
        return students.contains(name);
    }
}
