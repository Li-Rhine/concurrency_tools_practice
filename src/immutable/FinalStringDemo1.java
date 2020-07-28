package immutable;

public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        String d = "wukong";
        String c = b + 2; // b是final的，b直接看成wukong,指向常量池里的地址，等于wukong2
        String e = d + 2; //运行时确定的结果，会在堆中建立空间
        System.out.println((a == c));
        System.out.println((a == e));
    }
}
