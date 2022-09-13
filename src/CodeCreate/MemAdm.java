package CodeCreate;


import java.util.HashMap;
import java.util.Map;

//内存管理，分配临时变量
public class MemAdm{
    //简化：每个变量均为32位，start为字节偏移
    static int start = -4;

    //存储变量内存地址
    public static Map<String,String> map = new HashMap<>();

    static int i = 0;

    /**
     * 分配临时变量
     * @return
     */

    public static String createVar(){
        i++;
        getMemAddr("variable"+String.valueOf(i));
        return "variable"+String.valueOf(i);
    }


    /**
     * 为变量分配空间
     * @param name
     * @return
     */
    public static String createVar(String name){
        i++;
        getMemAddr(name);
        return name;
    }

    /**
     * 为变量分配内存地址
     * @param vari
     * @return
     */
    public static String getMemAddr(String vari){
        start += 4;
        map.put(vari,String.valueOf(start));
        return String.valueOf(start);
    }

    public static void print(){
        System.out.println("变量内存空间分配表：");
        for (Map.Entry<String,String> m :map.entrySet()){
            System.out.print("变量 : ");
            System.out.print(m.getKey());
            System.out.print(" 内存空间偏移 : ");
            System.out.println(m.getValue());
        }
    }
}
