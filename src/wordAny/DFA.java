package wordAny;

import langAny.TokenToMyChar;

import java.util.*;

public class DFA {
    //以数字表示当前状态
    private int state;

    //上一个状态
    private int oldState;

    //开始状态
    private int startState;

    //结束状态集合
    private int[] endState;

    //转移规则类
    private DFARules dfaRules;

    //异常定位
    private int ErrorPeekStart;
    private int ErrorPeekEnd;

    //词法单元序列
    private List<Token> tokenList;
    //token首字符对应的字符数
    private List<Integer> tokenInt;

    /**
     * 构造方法初始化DFA
     */
    public DFA(){
        dfaRules = new Rule();
        startState = dfaRules.getStartState();
        endState = dfaRules.getEndStates();
        state = startState;
        oldState = startState;
    }

    /**
     * 重置DFA
     */
    private void reset(){
        state = startState;
        oldState = startState;
    }

    /**
     * DFA状态转移
     * @param ch
     */
    private void move(char ch){
        //状态转移之前暂存状态
        oldState = state;
        state = dfaRules.getNextState(state,ch);
        /*
        System.out.print(oldState);
        System.out.print("  ");
        System.out.print(ch);
        System.out.print("  ");
        System.out.println(state);
        */
    }

    /**
     * 一次分析过程
     * @param str
     * @return 分析成功返回true
     */
    public boolean run(String str){
        //初始化类局部变量
        ErrorPeekStart = 0;
        ErrorPeekEnd = 0;
        tokenList = new ArrayList<>();
        tokenInt = new ArrayList<>();
        //开始匹配
        int peek = 0;
        int oldpeek = 0;
        char [] chars = str.toCharArray();
        while(peek < chars.length){
            char ch = chars[peek];
            //不是空格
            if(ch!=' ') {
                //非空格
                this.move(ch);
                //异常状态
                if(state==-1){
                    break;
                }
                //若没有到结束态
                if (!this.isDone()) {
                    peek++;
                } else {
                    StringBuilder tokenStr = new StringBuilder();
                    //到达终止态
                    for (int i = oldpeek; i < peek; i++) {
                        tokenStr.append(chars[i]);
                    }
                    addToken(oldState,tokenStr.toString());
                    tokenInt.add(oldpeek);
                    //若未到达eof
                    if(ch!='#') {
                        //重置dfa
                        this.reset();
                        oldpeek = peek;
                    }else {
                        //到达eof则终止
                        peek++;
                        break;
                    }
                }
            }else{
                //是空格
                //保证空格只会出现在标识符之后
                //若输入#后不为终止态，抛出异常
                this.move('#');
                if(isDone()){
                    //若输入#后为终止态，输出,并重置dfa，从下一个字符开始重新匹配
                    StringBuilder tokenStr = new StringBuilder();
                    for (int i = oldpeek; i < peek; i++) {
                        tokenStr.append(chars[i]);
                    }
                    addToken(oldState,tokenStr.toString());
                    tokenInt.add(oldpeek);
                    reset();
                    peek++;
                    oldpeek = peek;
                }else{
                    break;
                }
            }
        }
        //词法分析使用
        TokenToMyChar.tokenInt = tokenInt;
        //异常处理
        if(peek<chars.length || !isDone()){
            //异常时错误发生于[oldpeek,peek]之间
            tokenList = new ArrayList<>();
            ErrorPeekStart = oldpeek;
            ErrorPeekEnd = peek;
            return false;
        }
        return true;
    }

    /**
     * 根据转移到done之前的状态state判断tokenStr
     * @param state
     * @param tokenStr
     */
    private void addToken(int state,String tokenStr){
        /* 转移到end态的前一个状态可以确定词法单元的类型
         * 以下为对照表：
         * state 含义
         * 1 标识符或保留字
         * 2 <
         * 3 <<
         * 4 >
         * 5 >>
         * 6 &
         * 7 &&
         * 8 |
         * 9 ||
         * 10 分界符
         * 27 字符常量
         * 28 字符串常量
         * 14 * / % =
         * 15 += -= *= /= %= ==
         * 16 +-
         * 17 整数0
         * 19 十六进制整数
         * 20 整数
         * 22 含.的浮点数
         * 25 含指数的浮点数
         */
        /* 词表如下所示
         * id  属性
         * 0  +
         * 1  -
         * 2  *
         * 3  /
         * 4  %
         * 5  =
         * 6  <
         * 7  >
         * 8  &
         * 9  |
         * 10 +=
         * 11 -=
         * 12 *=
         * 13 /=
         * 14 %=
         * 15 ==
         * 16 <<
         * 17 >>
         * 18 &&
         * 19 ||
         * 20 (
         * 21 )
         * 22 {
         * 23 }
         * 24 [
         * 25 ]
         * 26 ;
         * 27 :
         * 28 标识符
         * 29 整数
         * 30 浮点数
         * 31 16进制整数
         * 32 字符常量
         * 33 字符串常量
         * 34 char
         * 35 double
         * 36 float
         * 37 enum
         * 38 int
         * 39 long
         * 40 short
         * 41 signed
         * 42 struct
         * 43 union
         * 44 unsigned
         * 45 void
         * 46 for
         * 47 do
         * 48 while
         * 49 break
         * 50 continue
         * 51 if
         * 52 else
         * 53 goto
         * 54 switch
         * 55 case
         * 56 default
         * 57 return
         * 58 auto
         * 59 extern
         * 60 register
         * 61 static
         * 62 const
         * 63 sizeof
         * 64 typedef
         * 65 volatile
         * 66 ,
         */
        if(state==1){
            //标识符或保留字
            if (KeyWord.isKeyWord(tokenStr)!=-1) {
                //关键字
                Token token = new Token();
                token.setId(KeyWord.isKeyWord(tokenStr));
                token.setItem(tokenStr);
                token.setText("关键字");
                tokenList.add(token);
            }else {
                //标识符
                Token token = new Token();
                token.setId(28);
                token.setItem(tokenStr);
                token.setText("标识符");
                tokenList.add(token);
            }
        }else if(state==2){
            //<
            Token token = new Token();
            token.setId(6);
            token.setItem("无");
            token.setText("<");
            tokenList.add(token);
        }else if(state==3){
            //<<
            if(tokenStr.equals("<<")) {
                Token token = new Token();
                token.setId(16);
                token.setItem("无");
                token.setText("<<");
                tokenList.add(token);
            }else{
                //<=
                Token token = new Token();
                token.setId(67);
                token.setItem("无");
                token.setText("<=");
                tokenList.add(token);
            }
        }else if(state==4){
            //>
            Token token = new Token();
            token.setId(7);
            token.setItem("无");
            token.setText(">");
            tokenList.add(token);
        }else if(state==5){
            //>>
            if(tokenStr.equals(">>")) {
                Token token = new Token();
                token.setId(17);
                token.setItem("无");
                token.setText(">>");
                tokenList.add(token);
            }else{
                //>=
                Token token = new Token();
                token.setId(66);
                token.setItem("无");
                token.setText(">=");
                tokenList.add(token);
            }
        }else if(state==6){
            //&
            Token token = new Token();
            token.setId(8);
            token.setItem("无");
            token.setText("&");
            tokenList.add(token);
        }else if(state==7){
            //&&
            Token token = new Token();
            token.setId(18);
            token.setItem("无");
            token.setText("&&");
            tokenList.add(token);
        }else if(state==8){
            //|
            Token token = new Token();
            token.setId(9);
            token.setItem("无");
            token.setText("|");
            tokenList.add(token);
        }else if(state==9){
            //||
            Token token = new Token();
            token.setId(19);
            token.setItem("无");
            token.setText("||");
            tokenList.add(token);
        }else if(state==10){
            //分界符
            if(tokenStr.equals("(")){
                Token token = new Token();
                token.setId(20);
                token.setItem("无");
                token.setText("(");
                tokenList.add(token);
            }else if(tokenStr.equals(")")){
                Token token = new Token();
                token.setId(21);
                token.setItem("无");
                token.setText(")");
                tokenList.add(token);
            }else if(tokenStr.equals("{")){
                Token token = new Token();
                token.setId(22);
                token.setItem("无");
                token.setText("{");
                tokenList.add(token);
            }else if(tokenStr.equals("}")){
                Token token = new Token();
                token.setId(23);
                token.setItem("无");
                token.setText("}");
                tokenList.add(token);
            }else if(tokenStr.equals("[")){
                Token token = new Token();
                token.setId(24);
                token.setItem("无");
                token.setText("[");
                tokenList.add(token);
            }else if(tokenStr.equals("]")){
                Token token = new Token();
                token.setId(25);
                token.setItem("无");
                token.setText("]");
                tokenList.add(token);
            }else if(tokenStr.equals(";")){
                Token token = new Token();
                token.setId(26);
                token.setItem("无");
                token.setText(";");
                tokenList.add(token);
            }else if(tokenStr.equals(",")){
                Token token = new Token();
                token.setId(72);
                token.setItem("无");
                token.setText(",");
                tokenList.add(token);
            }else{
                Token token = new Token();
                token.setId(27);
                token.setItem("无");
                token.setText(":");
                tokenList.add(token);
            }
        }else if(state==14){
            // * / % =
            if(tokenStr.equals("*")){
                Token token = new Token();
                token.setId(2);
                token.setItem("无");
                token.setText("*");
                tokenList.add(token);
            }else if(tokenStr.equals("/")){
                Token token = new Token();
                token.setId(3);
                token.setItem("无");
                token.setText("/");
                tokenList.add(token);
            }else if(tokenStr.equals("%")){
                Token token = new Token();
                token.setId(4);
                token.setItem("无");
                token.setText("%");
                tokenList.add(token);
            }else if(tokenStr.equals("=")){
                Token token = new Token();
                token.setId(5);
                token.setItem("无");
                token.setText("=");
                tokenList.add(token);
            }
        }else if(state==15){
            //+= -= *= /= %= ==
            if(tokenStr.equals("+=")){
                Token token = new Token();
                token.setId(10);
                token.setItem("无");
                token.setText("+=");
                tokenList.add(token);
            }else if(tokenStr.equals("-=")){
                Token token = new Token();
                token.setId(11);
                token.setItem("无");
                token.setText("-=");
                tokenList.add(token);
            }else if(tokenStr.equals("*=")){
                Token token = new Token();
                token.setId(12);
                token.setItem("无");
                token.setText("*=");
                tokenList.add(token);
            }else if(tokenStr.equals("/=")){
                Token token = new Token();
                token.setId(13);
                token.setItem("无");
                token.setText("/=");
                tokenList.add(token);
            }else if(tokenStr.equals("%=")){
                Token token = new Token();
                token.setId(14);
                token.setItem("无");
                token.setText("%=");
                tokenList.add(token);
            }else if(tokenStr.equals("==")){
                Token token = new Token();
                token.setId(15);
                token.setItem("无");
                token.setText("==");
                tokenList.add(token);
            }
        }else if(state==16){
            //+-
            if(tokenStr.equals("+")){
                Token token = new Token();
                token.setId(0);
                token.setItem("无");
                token.setText("+");
                tokenList.add(token);
            }else if(tokenStr.equals("-")){
                Token token = new Token();
                token.setId(1);
                token.setItem("无");
                token.setText("-");
                tokenList.add(token);
            }
        }else if(state==17){
            //整数
            Token token = new Token();
            token.setId(29);
            token.setItem(tokenStr);
            token.setText("整数");
            tokenList.add(token);
        }else if(state==19){
            //16进制整数
            Token token = new Token();
            token.setId(31);
            token.setItem(tokenStr);
            token.setText("16进制整数");
            tokenList.add(token);
        }else if(state==20){
            //整数
            Token token = new Token();
            token.setId(29);
            token.setItem(tokenStr);
            token.setText("整数");
            tokenList.add(token);
        }else if(state==22){
            //浮点数
            Token token = new Token();
            token.setId(30);
            token.setItem(tokenStr);
            token.setText("浮点数");
            tokenList.add(token);
        }else if(state==25){
            //浮点数
            Token token = new Token();
            token.setId(30);
            token.setItem(tokenStr);
            token.setText("浮点数");
            tokenList.add(token);
        }else if(state==27){
            //字符常量
            Token token = new Token();
            token.setId(32);
            token.setItem(tokenStr);
            token.setText("字符常量");
            tokenList.add(token);
        }else if(state==28){
            //字符串常量
            Token token = new Token();
            token.setId(33);
            token.setItem(tokenStr);
            token.setText("字符串常量");
            tokenList.add(token);
        }else if(state==29){
            //!
            Token token = new Token();
            token.setId(68);
            token.setItem(tokenStr);
            token.setText("!");
            tokenList.add(token);
        }else if(state==30){
            //!=
            Token token = new Token();
            token.setId(69);
            token.setItem(tokenStr);
            token.setText("!=");
            tokenList.add(token);
        }
    }

    /**
     * 判断当前是否为末态
     * @return
     */
    private boolean isDone(){
        for(int eState:endState){
            if(eState==state){
                return true;
            }
        }
        return false;
    }


    //获取词法单元序列
    public List<Token> getTokenList(){
        return tokenList;
    }

    //输出展示词法单元序列
    public void printTokenList(){
        List<Token> tokens = getTokenList();
        //输出词法单元序列
        for (Token token:tokens){
            System.out.print("< ");
            System.out.print(token.getId());
            System.out.print(" , ");
            System.out.print(token.getText());
            System.out.print(" , ");
            System.out.print(token.getItem());
            System.out.println(" >");
        }
    }

    public int getErrorPeekEnd() {
        return ErrorPeekEnd;
    }

    public int getErrorPeekStart() {
        return ErrorPeekStart;
    }

}



