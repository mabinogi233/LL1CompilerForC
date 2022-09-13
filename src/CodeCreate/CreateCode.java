package CodeCreate;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CreateCode {

    private static List<MidCode> codeList;

    //回填栈
    public static Stack<Integer> stack;

    public static Stack<MidCode> list;

    static{
        codeList = new ArrayList<>();
        stack = new Stack<>();
        list = new Stack<>();
    }

    /**
     * 初始化
     */
    public static void init(){
        codeList = new ArrayList<>();
        stack = new Stack<>();
        list = new Stack<>();
    }

    /**
     * 获取中间代码
     * @return
     */
    public static List<MidCode> getCodeList(){
        return codeList;
    }

    /**
     * 输出中间代码
     */
    public static void printMidCode(){
        int i = 0;
        for(MidCode midCode:codeList){
            System.out.print(i);
            System.out.print(" : ");
            midCode.print();
            i++;
        }
        System.out.print(i);
        System.out.print(" : ");
        System.out.println("code finish");
    }

    /**
     * 输出一行三地址代码
     * @param op
     * @param x
     * @param y
     * @param z
     */
    public static MidCode createCode(String op,String x,String y,String z){
        MidCode midCode = new MidCode(op,x,y,z);
        codeList.add(midCode);
        return midCode;
    }

    /**
     * 获取当前代码的行数
     * @return
     */
    public static int getCodeLine(){
        return codeList.size();
    }
}


//三地址代码的四元组式
class MidCode{
    private String op;

    private String b;

    private String c;

    private String d;

    public MidCode(String op,String b,String c,String d){
        this.op = op;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * 输出中间代码
     */
    public void print(){
        System.out.print("<");
        System.out.print(op);
        System.out.print(",");
        System.out.print(b);
        System.out.print(",");
        System.out.print(c);
        System.out.print(",");
        System.out.print(d);
        System.out.println(">");
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getC() {
        return c;
    }

    public String getB() {
        return b;
    }

    public String getD() {
        return d;
    }

    public String getOp() {
        return op;
    }
}
