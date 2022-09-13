package CodeCreate;

import java.util.HashMap;
import java.util.Map;

//符号表
public class CharList {

    //最后加入的符号的主键
    private static String lastChar;

    // 标识符（主键） - （返回）类型 - 值
    private static Map<String,Map<String,String>> map = new HashMap<>();

    public static String getLastChar() {
        return lastChar;
    }

    /**
     * 添加新的符号
     * @param biaoShiFu
     * @param type
     * @param value
     */
    public static void add(String biaoShiFu,String type,String value){
        map.putIfAbsent(biaoShiFu,new HashMap<>());
        map.replace(biaoShiFu,new HashMap<>());
        map.get(biaoShiFu).put("type",type);
        map.get(biaoShiFu).put("value",value);
        lastChar = biaoShiFu;
    }

    /**
     * 更新符号表的值
     * @param biaoShiFu
     * @param item
     * @param name 当name==type时更新type，当name==value时更新value
     */
    public static void update(String biaoShiFu,String item,String name){
        if(map.get(biaoShiFu)!=null) {
            if (name.equals("type")) {
                map.get(biaoShiFu).putIfAbsent("type",item);
                map.get(biaoShiFu).replace("type",item);
            } else if (name.equals("value")) {
                map.get(biaoShiFu).putIfAbsent("value",item);
                map.get(biaoShiFu).replace("value",item);
            }
        }
    }

    /**
     * 查找符号对应的属性
     * @param biaoShiFu
     * @return
     */
    public static Map<String,String> get(String biaoShiFu){
        return map.get(biaoShiFu);
    }
}
