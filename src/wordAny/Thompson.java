package wordAny;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//RE->NFA
public class Thompson {
    /**
     * 根据仅含 * _ | 三种运算的后缀正规式生成NFA
     * @param re 后缀表达式
     * @return
     */
    public FAGraph createNFAByRE(String re){
        ArrayList<Edge> rules = new ArrayList<>();
        int start = -1;
        int end = -1;
        //已有的节点个数
        int conut = 0;
        //使用栈
        Stack<Start2End> stack = new Stack<>();
        int peek = 0;
        char[] chars = re.toCharArray();
        //开始匹配：
        while(peek<re.length()){
            char ch = chars[peek];
            //若是操作符，即 _ * |三种
            if(ch=='_'){
                //无需添加新节点
                //出栈两个操作数，作为b和a
                Start2End b = stack.pop();
                Start2End a = stack.pop();
                //a_b，ab为子图，需要添加1条规则，a.end --ϵ--> b.start
                Edge edge = new Edge();
                edge.setStart(a.getEnd());
                edge.setCh('ϵ');
                edge.setEnd(b.getStart());
                rules.add(edge);
                //构造子图 a.start -> b.end 入栈
                Start2End ij = new Start2End();
                ij.setStart(a.getStart());
                ij.setEnd(b.getEnd());
                //入栈
                stack.push(ij);
                //设置开始节点与终止节点
                start = a.getStart();
                end = b.getEnd();
            }else if(ch=='*'){
                //添加两个节点与四个规则
                //出栈一个操作数
                Start2End a = stack.pop();
                //添加新节点
                int i = conut;
                int j = conut+1;
                conut+=2;
                //i --ϵ--> a.start
                Edge edge1 = new Edge();
                edge1.setStart(i);
                edge1.setCh('ϵ');
                edge1.setEnd(a.getStart());
                rules.add(edge1);
                //a.end --ϵ--> j
                Edge edge2 = new Edge();
                edge2.setStart(a.getEnd());
                edge2.setCh('ϵ');
                edge2.setEnd(j);
                rules.add(edge2);
                //a.end --ϵ--> a.start
                Edge edge3 = new Edge();
                edge3.setStart(a.getEnd());
                edge3.setCh('ϵ');
                edge3.setEnd(a.getStart());
                rules.add(edge3);
                //i --ϵ--> j
                Edge edge4 = new Edge();
                edge4.setStart(i);
                edge4.setCh('ϵ');
                edge4.setEnd(j);
                rules.add(edge4);
                //新的图入栈
                Start2End ij = new Start2End();
                ij.setStart(i);
                ij.setEnd(j);
                //入栈
                stack.push(ij);
                //设置开始节点与终止节点
                start = i;
                end = j;
            }else if(ch=='|'){
                //添加两个节点与四个规则
                //出栈两个操作数
                Start2End b = stack.pop();
                Start2End a = stack.pop();
                //添加新节点
                int i = conut;
                int j = conut+1;
                conut+=2;
                //i --ϵ--> a.start
                Edge edge1 = new Edge();
                edge1.setStart(i);
                edge1.setCh('ϵ');
                edge1.setEnd(a.getStart());
                rules.add(edge1);
                //i --ϵ--> b.start
                Edge edge2 = new Edge();
                edge2.setStart(i);
                edge2.setCh('ϵ');
                edge2.setEnd(b.getStart());
                rules.add(edge2);
                //a.end --ϵ--> j
                Edge edge3 = new Edge();
                edge3.setStart(a.getEnd());
                edge3.setCh('ϵ');
                edge3.setEnd(j);
                rules.add(edge3);
                //b.end --ϵ--> j
                Edge edge4 = new Edge();
                edge4.setStart(b.getEnd());
                edge4.setCh('ϵ');
                edge4.setEnd(j);
                rules.add(edge4);
                //新的图入栈
                Start2End ij = new Start2End();
                ij.setStart(i);
                ij.setEnd(j);
                //入栈
                stack.push(ij);
                //设置开始节点与终止节点
                start = i;
                end = j;
            }else{
                //非运算符，构造单独的节点，然后入栈
                //单个节点，添加两个状态，
                int i = conut;
                int j = conut+1;
                conut+=2;
                Start2End ij = new Start2End();
                ij.setStart(i);
                ij.setEnd(j);
                //入栈
                stack.push(ij);
                //添加规则 i ch j
                Edge edge = new Edge();
                edge.setStart(i);
                edge.setCh(ch);
                edge.setEnd(j);
                rules.add(edge);
                //设置开始节点与终止节点
                start = i;
                end = j;
            }
            peek++;
        }
        FAGraph NFA = new FAGraph();
        NFA.setRules(rules);
        NFA.setStartState(start);
        NFA.setEndState(end);
        return NFA;
    }

    /**
     * 测试用例
     */
    public static void text(){
        Thompson t = new Thompson();
        System.out.println(ShuntingYardAlgorithm.shuntingYardAlgorithm("(a_b)*_a"));
        FAGraph nfa = t.createNFAByRE("ab_*a_");
        nfa.print();
    }

}


//此类记录栈中的一个子图的初始节点和终止节点
class Start2End{
    //子图初始节点
    private int start;
    //子图终止节点
    private int end;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}


class ShuntingYardAlgorithm{
    /**
     * 调度场算法，中缀转后缀
     * @param re
     * @return
     */
    public static String shuntingYardAlgorithm(String re){
        /**
         * 1. 依次按顺序读入：
         * 	1.1. 读到字符：直接输出；
         * 	1.2. 读到运算符o：如果栈顶的运算符p优先级不低于o (注，左括号优先级最低)，则将p从栈里弹出并输出，直到栈空或不满足上述条件为止；然后将o入栈；
         * 	1.3. 读到左括号：直接入栈；
         * 	1.4. 读到右括号：将栈顶运算符弹出并输出，直到栈顶为左括号为止；令左括号出栈。
         * 2. 当读入完毕时，依次输出并弹出栈顶运算符，直到栈被清空。
         */
        StringBuilder newRe = new StringBuilder();
        char[] chars = re.toCharArray();
        Stack<Character> stack = new Stack<>();
        for(char ch:chars){
            if(isChar(ch)){
                //字符
                newRe.append(ch);
            }else if(isOperator(ch)) {
                //操作符
                while (!stack.empty() && getPriority(stack.peek()) >= getPriority(ch)) {
                    newRe.append(stack.pop());
                }
                stack.push(ch);
            }else if(ch=='('){
                stack.push(ch);
            }else if(ch==')'){
                while(stack.peek()!='('){
                    newRe.append(stack.pop());
                }
                stack.pop();
            }
        }
        while(!stack.empty()){
            newRe.append(stack.pop());
        }
        return newRe.toString();
    }

    /**
     * 判断是不是字符
     * @param ch
     * @return
     */
    private static boolean isChar(char ch){
        return ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f'||ch=='g'||ch=='h'||ch=='i'||
                ch=='j'||ch=='k'||ch=='l'||ch=='m'||ch=='n'||ch=='o'||ch=='p'||ch=='q'||ch=='r'||
                ch=='s'||ch=='t'||ch=='u'||ch=='v'||ch=='w'||ch=='x'||ch=='y'||ch=='z'||ch=='A'||
                ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F'||ch=='G'||ch=='H'||ch=='I'||ch=='J'||
                ch=='K'||ch=='L'||ch=='M'||ch=='N'||ch=='O'||ch=='P'||ch=='Q'||ch=='R'||ch=='S'||
                ch=='T'||ch=='U'||ch=='V'||ch=='W'||ch=='X'||ch=='Y'||ch=='Z';
    }

    /**
     * 是否为操作符
     * @param ch
     * @return
     */
    private static boolean isOperator(char ch){
        return ch=='|'||ch=='_'||ch=='*';
    }

    /**
     * 获取操作符的优先级
     * @param operator
     * @return
     */
    private static int getPriority(char operator){
        if(operator=='_'){
            return 4;
        }else if(operator=='*'){
            return 3;
        }else if(operator=='|'){
            return 2;
        }else if(operator=='('){
            return 1;
        }else{
            return 0;
        }
    }
}