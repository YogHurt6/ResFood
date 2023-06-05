package com.yc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Test1 {
    class A implements Predicate<String> {
        @Override
        public boolean test(String s) {
            if (s.length() > 3) {
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Predicate p;
        BiPredicate bp;

        //使用Predicate过滤集合
        List<String> ns = Arrays.asList("Alice", "Bob", "Charlie", "David");//创建不可变集合
        List<String> names = new ArrayList<>(ns);//转为可变集合,方便后面使用removeIf删除元素

        //定义一个Predicate,判断字符串长度是否大于3
        Predicate<String> lengthPredicate = s -> s.length() > 3;//lamda写法 函数式编程

        //使用removeIf方法移除不满足Predicate的元素
        names.removeIf(lengthPredicate);
        System.out.println(names);//[Bob]

        //TODO:Predicate<T> and方法使用
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> predicate1 = x -> x > 3;
        Predicate<Integer> predicate2 = x -> x < 9;
        List<Integer> collect = list.stream().filter(predicate1.and(predicate2)).collect(Collectors.toList());
        System.out.println(collect);

        //TODO:Predicate<T> or方法使用
        collect = list.stream().filter(predicate1.or(predicate2)).collect(Collectors.toList());
        System.out.println(collect);

        //TODO:Predicate<T> negate方法使用  否定
        List<String> list3 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate3 = x -> x.endsWith("+");
        List<String> collect3 = list3.stream().filter(predicate3.negate()).collect(Collectors.toList());
        System.out.println(collect3);

        //TODO:Predicate<T> test方法使用
        List<String> list4 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate4 = x -> x.endsWith("+");
        List<String> collect4 = list3.stream().filter(predicate4::test).collect(Collectors.toList());//::调用测试方法
        System.out.println(collect4);

        //TODO:Predicate<T> 链式调用
        List<String> list5 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate5 = x -> x.startsWith("c");
        //以c开头或者以t结尾
        boolean ret = predicate5.or(x -> x.endsWith("t")).test("javascript");
        System.out.println(ret);
        //!(以c开头且长度等于4)
        boolean ret2 = predicate5.and(x -> x.length() == 4).negate().test("java");
        System.out.println(ret2);

        //TODO:使用BiPredicate判断两个字符串是否相等
        BiPredicate<String, String> equalPredicate = (s1, s2) -> s1.equals(s2);
        System.out.println(equalPredicate.test("Hello", "Hello"));//true
        System.out.println(equalPredicate.test("Hello", "World"));//false
    }
}
