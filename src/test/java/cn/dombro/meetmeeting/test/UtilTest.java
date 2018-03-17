package cn.dombro.meetmeeting.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTest {

    @Test
    public void labelTest(){
        String[] labels = {"会议","同学会","酒会"};
        String label = "";
        for(int i = 0;i < labels.length;i++){
            if ( i == labels.length-1){
                label += labels[i];
            }else {
                label += labels[i] + ",";
            }
        }
        System.out.println(label);
    }

    @Test
    public void regexTest(){
        //单词边界来匹配
        Pattern pattern = Pattern.compile("\\b111\\b\\s");
        Matcher matcher = pattern.matcher("21 2142 111 ");
        while (matcher.find()){
            System.out.println(matcher.group());
        }

        String a = "21 2142 111 ";
        a = a.replaceAll("\\b111\\b\\s","");
        System.out.println(a);
    }

    @Test
    public void testInteger(){
//        Integer aa = 59;
//        int bb = 59;
//        Integer cc = Integer.valueOf(59);
//        Integer dd = new Integer(59);
//        System.out.println(aa == bb);
//        System.out.println(aa == cc);
//        System.out.println(cc == dd);
//        System.out.println(bb == dd);

        String[] item = {"你好","我好","大家好"};
        String items = "";
        for (String it:item){
            items += it +" ";
        }
        System.out.println(items);
        String[] a =  items.split("\\s");
        System.out.println(a.length);
        for (String aa:a){
            System.out.println(aa+" : "+aa.length());
        }


        String s = null;
        s = "xixxixi";
        System.out.println(s);

    }
}
