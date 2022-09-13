package langAny;

import java.util.*;

/**
 * LL(1)预测分析器
 */
public class LL1 {

    //预测分析表
    private Map<MyChar,Map<MyChar,CA>> M = new HashMap<>();

    //异常信息异步记录
    private List<Integer> errorIndex = new ArrayList<>();

    //异步记录最左推导
    private List<CA> leftT = new ArrayList<>();

    //是否开启恐慌模式
    public static boolean isKHmoShi = true;

    /**
     * 构造函数，生成M[A,a]预测分析表
     */
    public LL1(){
        //初始化M
        initM();
        //遍历每条产生式
        for(CA ca:CAAdmin.getAllCa()){
            //A->a
            MyChar A = ca.getStart();
            List<MyChar> a = ca.getEnd();
            //求first(a)
            Set<MyChar> firstA = Utils.getFirst(a);
            //找出first(a)中的终结符
            for(MyChar b:firstA){
                if(b.isEndChar()&&!b.isE()){
                    //非ε终结符
                    //将产生式ca加入M[A,b]
                    if(M.get(A).get(b).getStart()!=null||M.get(A).get(b).getEnd()!=null){
                        System.out.println("非LL(1)文法");
                        M.get(A).get(b).print();
                        System.out.println("输入："+b.getC());
                        ca.print();
                    }
                    M.get(A).replace(b,ca);
                }else if(b.isE()){
                    //ε属于first(a)
                    //求Follow(A)
                    Set<MyChar> followA = Utils.getFollow(A);
                    //对每个followA中的终结符
                    for(MyChar bb:followA){
                        if(bb.isEndChar()&&!bb.isE()){
                            if(M.get(A).get(bb).getStart()!=null||M.get(A).get(bb).getEnd()!=null){
                                System.out.println("非LL(1)文法");
                                M.get(A).get(bb).print();
                                System.out.println("输入："+bb.getC());
                                ca.print();
                            }
                            //将ca加入M[A,bb]
                            M.get(A).replace(bb,ca);
                        }
                    }
                }
            }

        }
    }

    /**
     * 获取分析结果
     * @return
     */
    public List<CA> getResult() {
        return leftT;
    }

    /**
     * 获取异常信息
     * @return
     */
    public List<Integer> getErrorIndex() {
        return errorIndex;
    }

    /**
     * 初始化表M[A,a]
     * @return
     */
    private void initM(){

        //非终结符
        Set<MyChar> UnEndChars = CAAdmin.getUnEndChars();
        //终结符
        Set<MyChar> EndChars = CAAdmin.getEndChars();
        //初始化
        for(MyChar unEndChar:UnEndChars){
            M.computeIfAbsent(unEndChar,k -> new HashMap<>());
            for(MyChar endChar:EndChars){
                M.get(unEndChar).computeIfAbsent(endChar,k->new CA());
            }
        }
    }

    /**
     * LL1分析
     * @param input 终结符组成的串
     * @return 是否分析成功，最左推导过程和异常信息通过get方法异步获得
     * 异常处理采用直接弹出方式，即栈顶的终结符不能匹配时，弹出该终结符，也就是把所有符号作为同步符号
     */
    public boolean runLL1(List<MyChar> input){
        //保存最左推导的产生式
        leftT.clear();
        //error发生的位置
        errorIndex.clear();
        //栈
        Stack<MyChar> stack = new Stack<>();
        //初始化
        MyChar start = CAAdmin.getStartChar();
        MyChar endC = new MyChar();
        endC.setC("$");
        endC.setEndChar(true);
        stack.push(endC);
        stack.push(start);
        //完成标志
        boolean success = false;
        //输入字符
        int i = 0;
        while(i<input.size()){
            MyChar a = input.get(i);
            //获取栈顶元素
            MyChar X = stack.peek();

            if(X.isEndChar() && !X.getC().equals("$")){

                //X为终结符且不为$
                if(X.equals(a)){
                    stack.pop();
                    //置下一个字符
                    i+=1;
                    continue;
                }else{
                    //error
                    //跳过此字符
                    errorIndex.add(i);
                    i+=1;
                    continue;
                }
            }
            if(!X.isEndChar()){
                //X为非终结符
                //获取产生式M[A,a]
                CA ca = M.get(X).get(a);
                if(ca==null || ca.getEnd()==null || ca.getStart()==null){
                    //跳过此字符
                    errorIndex.add(i);
                    i+=1;
                    continue;
                }else{
                    stack.pop();
                    //产生式ca：X->UVM 按M,V,U依次入栈
                    for(int j = ca.getEnd().size()-1;j>=0;j--){
                        //ε不需要进栈
                        if(!ca.getEnd().get(j).isE()) {
                            stack.push(ca.getEnd().get(j));
                        }
                    }
                    //输出ca
                    leftT.add(ca);
                    //ca.print();
                    continue;
                }
            }
            if(X.getC().equals("$") && X.equals(a)){
                //X==a==$
                success = true;
                break;
            }
        }
        if(!isKHmoShi) {
            if (success) {
                //恐慌式修改后正确
                if (errorIndex.size() > 0) {
                    return false;
                }
                //分析成功
                System.out.println("success");
            } else {
                //失败
                System.out.println("error");
            }
            return success;
        }else{
            //恐慌模式
            return true;
        }
    }

    /**
     * 测试用例
     */
    public static void test(){
        LL1 l = new LL1();

        List<MyChar> input = new ArrayList<>();

        MyChar id = new MyChar();
        id.setEndChar(true);
        id.setC("id");

        MyChar plus = new MyChar();
        plus.setEndChar(true);
        plus.setC("+");

        MyChar ma = new MyChar();
        ma.setEndChar(true);
        ma.setC("*");

        MyChar endd = new MyChar();
        endd.setEndChar(true);
        endd.setC("$");

        input.add(id);
        input.add(plus);
        input.add(id);
        input.add(ma);
        input.add(id);
        input.add(endd);

        l.runLL1(input);
    }

    /**
     * 输出M
     */
    public void print(){
        //非终结符
        Set<MyChar> UnEndChars = CAAdmin.getUnEndChars();
        //终结符
        Set<MyChar> EndChars = CAAdmin.getEndChars();
        //初始化
        for(MyChar unEndChar:UnEndChars){
            for(MyChar endChar:EndChars){

                if(M.get(unEndChar).get(endChar).getStart()!=null) {
                    System.out.print("非终结符：");
                    System.out.print(unEndChar.getC());
                    System.out.print(" 终结符：");
                    System.out.print(endChar.getC());
                    System.out.print(" 产生式：");
                    M.get(unEndChar).get(endChar).print();
                }else{
                    //System.out.println(" error");
                }
            }
        }
    }
}
