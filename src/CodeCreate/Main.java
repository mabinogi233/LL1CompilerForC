package CodeCreate;


import langAny.CA;
import langAny.LL1;
import langAny.MyChar;
import langAny.TokenToMyChar;
import wordAny.Token;
import wordAny.wordAnalyse;
import CodeCreate.Utils;

import java.util.List;

public class Main {
    /**
     * 输入词法单元序列，输出语法树
     * @param token 词法单元序列
     * @return 语法树
     */
    public CodeCreate.LTreeNode Analysis(List<Token> token){
        //将词法单元序列映射为终结符串
        List<MyChar> input = TokenToMyChar.Token2MyChar(token);
        //加入$
        MyChar enC = new MyChar();
        enC.setC("$");
        enC.setEndChar(true);
        input.add(enC);
        //构造LL1文法
        LL1 l = new LL1();
        //LL(1)分析
        boolean isSuccess = l.runLL1(input);
        if(isSuccess){
            //成功
            //获取最左推导的产生式序列
            List<CA> cas = l.getResult();
            //构造语法树
            CodeCreate.LTreeNode root = Utils.createTreeByLeftT(cas,token);
            //输出语法树
            Utils.printTree(root);
            return root;
        }else {
            //失败
            System.out.println("失败");
            List<Integer> errorIndex = l.getErrorIndex();
            System.out.print("首次出错行数：");
            System.out.print(TokenToMyChar.getErrorLineNumber(errorIndex.get(0), token));
            System.out.println(" ");
            return null;
        }
    }

    /**
     * 测试用例
     */
    public static void main(String[] args){

        //词法分析
        List<Token> tokens = wordAnalyse.runWordAnalyse("/Users/lwz/Documents/documents/文件/编译原理/20184341-刘文泽/实验3/text.c");
        System.out.println("词法分析成功");
        //语法分析
        if(tokens!=null) {
            LL1 l = new LL1();
            List<MyChar> input = TokenToMyChar.Token2MyChar(tokens);
            //加入$
            MyChar enC = new MyChar();
            enC.setC("$");
            enC.setEndChar(true);
            input.add(enC);
            if(l.runLL1(input)){
                //成功
                System.out.println("语法分析成功");
                //语义分析及生成语法树
                CodeCreate.LTreeNode root = Utils.createTreeByLeftT(l.getResult(),tokens);
                //语义分析异常标志
                if(Utils.errorFlag){
                    //类型不匹配
                    System.out.println("语义分析失败");
                    //输出特定错误信息
                    Utils.printError(tokens);
                }else if(Utils.variErrorflag){
                    System.out.println("语义分析失败");
                }else  {
                    System.out.println("语义分析成功");
                    //语义分析成功则输出翻译语法树，即带属性的语法树
                    Utils.printTree(root);
                    //中间代码生成
                    try {
                        System.out.println("以四元式的形式输出三地址码格式的中间代码:");
                        List<MidCode> codes = CreateCode.getCodeList();
                        CreateCode.printMidCode();
                        String ass = MidCode2Ass.Code2Ass(codes);
                        System.out.println("输出Mips汇编代码:");
                        printMips(ass);
                        //输出变量内存空间
                        MemAdm.print();
                    }catch (Exception e){
                        System.out.println("Error！中间代码生成或汇编代码生成出错");
                    }
                }
            }else {
                System.out.println("语法分析失败");
                List<Integer> errorIndex = l.getErrorIndex();
                System.out.print("首次出错行数：");
                System.out.print(TokenToMyChar.getErrorLineNumber(errorIndex.get(0), tokens));
                System.out.println(" ");
            }
        }

    }

    /**
     * 输出便于验证的Mips
     * @param code
     */
    public static void printMips(String code){
        String[] ss = code.split("\n");
        int i = 0;
        for(String s:ss){
            System.out.print(i);
            System.out.print(": ");
            System.out.println(s);
            i++;
        }
        System.out.println("finish code");
    }

}

