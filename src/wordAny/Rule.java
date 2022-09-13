package wordAny;

import java.util.HashMap;
import java.util.Map;

//定义转移规则
public class Rule implements DFARules{
    //字符表
    private static char[] charDict =
            ",#abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>+-*/%&!=_|\'\".()[]{};:".toCharArray();

    //第一个map的key值为当前状态state，value对应此state下的转移规则
    //第二个map的key值为当前输入，value值对应下一个状态
    private static Map<Integer, Map<Character,Integer>> rules;

    static {
        rules = new HashMap<>();
        //分别为每个状态添加转移规则
        /*
         * 根据状态机，有以下状态
         * state 输入char 转移到的state
         * 0 [1-9] 20
         * 0 0 17
         * 0 [+-] 16
         * 0 [* / % = ] 14
         * 0 [a-zA-Z_] 1
         * 0 < 2
         * 0 > 4
         * 0 & 6
         * 0 | 8
         * 0 [( ) { } [ ] ; : ,] 10
         * 0 ' 11
         * 0 " 13
         * 1 [a-zA-Z_0-9] 1
         * 1 [other] 26
         * 2 [<=] 3
         * 2 [other] 26
         * 3 [other] 26
         * 4 [>=] 5
         * 4 [other] 26
         * 5 [other] 26
         * 6 & 7
         * 6 [other] 26
         * 7 [other] 26
         * 8 | 9
         * 8 [other] 26
         * 9 [other] 26
         * 10 [other] 26
         * 11 ' 27
         * 11 [^'] 12
         * 12 ' 27
         * 13 " 28
         * 13 [^"] 13
         * 14 = 15
         * 14 [other] 26
         * 15 [other] 26
         * 16 0 17
         * 16 [1-9] 20
         * 16 = 15
         * 16 [other] 26
         * 17 [xX] 18
         * 17 . 21
         * 17 [0-9] 20
         * 17 [other] 26
         * 18 [0-9a-fA-F] 19
         * 19 [0-9a-fA-F] 19
         * 19 [other] 26
         * 20 [0-9] 20
         * 20 [other] 26
         * 20 . 21
         * 21 [0-9] 22
         * 22 [0-9] 22
         * 22 [eE] 23
         * 22 [other] 26
         * 23 [+-] 24
         * 23 [0-9] 25
         * 24 [0-9] 25
         * 25 [0-9] 25
         * 25 [other] 26
         * 27 [other] 26
         * 28 [other] 26
         * 0 ! 29
         * 29 = 30
         * 29 [other] 26
         * 30 [other] 26
         */

        //state==0
        Map<Character,Integer> map1 = new HashMap<>();
        for(char ch :charDict){
            if(isNum(ch)&&ch!='0'){
                //0 [1-9] 20
                map1.put(ch,20);
            }else if(ch=='0'){
                // 0 0 17
                map1.put(ch,17);
            }else if(ch=='+' || ch=='-'){
                // 0 [+-] 16
                map1.put(ch,16);
            } else if(isDis(ch)||ch=='_'){
                // 0 [a-zA-Z_] 1
                map1.put(ch,1);
            }else if(ch=='*' || ch=='/' || ch=='%' || ch=='='){
                // 0 [* / % = ] 14
                map1.put(ch,14);
            }else if(ch=='<'){
                // 0 < 2
                map1.put(ch,2);
            }else if(ch=='>'){
                // 0 > 4
                map1.put(ch,4);
            }else if(ch=='&'){
                //0 & 6
                map1.put(ch,6);
            }else if(ch=='|'){
                // 0 | 8
                map1.put(ch,8);
            }else if(ch=='(' || ch==')' || ch=='{' || ch=='}' || ch=='[' || ch==']' || ch==';' || ch==':'|| ch==','){
                // 0  [( ) { } [ ] ; : ,] 10
                map1.put(ch,10);
            }else if(ch=='\''){
                // 0 ' 11
                map1.put(ch,11);
            }else if(ch=='\"'){
                // 0 " 13
                map1.put(ch,13);
            }else if(ch=='!'){
                // 0 ! 29
                map1.put(ch,29);
            }else {
                //异常,转移到异常态
                map1.put(ch,-1);
            }
        }
        rules.put(0,map1);
        //state==1
        Map<Character, Integer> map2 = new HashMap<>();
        for(char ch :charDict) {
            if(isNum(ch)||isDis(ch)||ch=='_') {
                //1 [a-zA-Z_0-9] 1
                map2.put(ch, 1);
            }else {
                // 1 [other] 26
                map2.put(ch, 26);
            }
        }
        rules.put(1,map2);
        //state==2
        Map<Character, Integer> map3 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='<'||ch=='=') {
                //2 [<=] 3
                map3.put(ch, 3);
            }else {
                // 2 [other] 26
                map3.put(ch, 26);
            }
        }
        rules.put(2,map3);
        //state==3
        Map<Character, Integer> map4 = new HashMap<>();
        for(char ch :charDict) {
            // 3 [other] 26
            map4.put(ch, 26);
        }
        rules.put(3,map4);
        //state==4
        Map<Character, Integer> map5 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='>'||ch=='=') {
                //4 [>=] 5
                map5.put(ch, 5);
            }else {
                // 4 [other] 26
                map5.put(ch, 26);
            }
        }
        rules.put(4,map5);
        //state==5
        Map<Character, Integer> map6 = new HashMap<>();
        for(char ch :charDict) {
            // 5 [other] 26
            map6.put(ch, 26);
        }
        rules.put(5,map6);
        //state==6
        Map<Character, Integer> map7 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='&') {
                //6 & 7
                map7.put(ch, 7);
            }else {
                // 6 [other] 26
                map7.put(ch, 26);
            }
        }
        rules.put(6,map7);
        //state==7
        Map<Character, Integer> map8 = new HashMap<>();
        for(char ch :charDict) {
            // 7 [other] 26
            map8.put(ch, 26);
        }
        rules.put(7,map8);
        //state==8
        Map<Character, Integer> map9 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='|') {
                //8 | 9
                map9.put(ch, 9);
            }else {
                // 8 [other] 26
                map9.put(ch, 26);
            }
        }
        rules.put(8,map9);
        //state==9
        Map<Character, Integer> map10 = new HashMap<>();
        for(char ch :charDict) {
            // 9 [other] 26
            map10.put(ch, 26);
        }
        rules.put(9,map10);
        //state==10
        Map<Character, Integer> map11 = new HashMap<>();
        for(char ch :charDict) {
            // 10 [other] 26
            map11.put(ch, 26);
        }
        rules.put(10,map11);
        //state==11
        Map<Character, Integer> map12 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='\'') {
                // 11 ' 27
                map12.put(ch, 27);
            }else{
                // 11 [^'] 12
                map12.put(ch, 12);
            }
        }
        rules.put(11,map12);
        //state==12
        Map<Character, Integer> map13 = new HashMap<>();
        for(char ch :charDict) {
            // 12 ' 27
            if(ch=='\'') {
                map13.put(ch, 27);
            }else {
                //异常
                map13.put(ch, -1);
            }
        }
        rules.put(12,map13);
        //state==13
        Map<Character, Integer> map14 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='\"') {
                // 13 " 28
                map14.put(ch, 28);
            }else{
                // 13 [^"] 13
                map14.put(ch, 13);
            }
        }
        rules.put(13,map14);
        //state==14
        Map<Character, Integer> map15 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='=') {
                // 14 = 15
                map15.put(ch, 15);
            }else{
                // 14 [other] 26
                map15.put(ch, 26);
            }
        }
        rules.put(14,map15);
        //state==15
        Map<Character, Integer> map16 = new HashMap<>();
        for(char ch :charDict) {
            // 15 [other] 26
            map16.put(ch, 26);
        }
        rules.put(15,map16);
        //state==16
        Map<Character, Integer> map17 = new HashMap<>();
        for(char ch :charDict) {

            if(ch=='0') {
                // 16 0 17
                map17.put(ch, 17);
            }else if(isNum(ch)){
                // 16 [1-9] 20
                map17.put(ch, 20);
            }else if(ch=='='){
                // 16 = 15
                map17.put(ch , 15);
            }else {
                // 16 [other] 26
                map17.put(ch , 26);
            }

        }
        rules.put(16,map17);
        //state==17
        Map<Character, Integer> map18 = new HashMap<>();
        for(char ch :charDict) {

            if(ch=='x'||ch=='X') {
                //  17 [xX] 18
                map18.put(ch, 18);
            }else if(ch=='.'){
                // 17 . 21
                map18.put(ch, 21);
            }else if(isNum(ch)){
                // 17 [0-9] 20
                map18.put(ch , 20);
            }else {
                // 17 [other] 26
                map18.put(ch , 26);
            }

        }
        rules.put(17,map18);
        //state==18
        Map<Character, Integer> map19 = new HashMap<>();
        for(char ch :charDict) {
            if(isNum(ch)||ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f'
                    ||ch=='A'||ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F') {
                // 18 [0-9a-fA-F] 19
                map19.put(ch, 19);
            }else{
                // 异常
                map19.put(ch, -1);
            }
            rules.put(18,map19);
        }
        //state==19
        Map<Character, Integer> map20 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)||ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f'
                    ||ch=='A'||ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F') {
                // 19 [0-9a-fA-F] 19
                map20.put(ch, 19);
            }else{
                //19 [other] 26
                map20.put(ch, 26);
            }

        }
        rules.put(19,map20);
        //state==20
        Map<Character, Integer> map21 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)) {
                // 20 [0-9] 20
                map21.put(ch, 20);
            }else if(ch=='.'){
                // 20 . 21
                map21.put(ch, 21);
            }else {
                // 20 [other] 26
                map21.put(ch, 26);
            }
            rules.put(20,map21);
        }
        //state==21
        Map<Character, Integer> map22 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)) {
                //  21 [0-9] 22
                map22.put(ch, 22);
            }else{
                //error
                map22.put(ch, -1);
            }

        }
        rules.put(21,map22);
        //state==22
        Map<Character, Integer> map23 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)) {
                // 22 [0-9] 22
                map23.put(ch, 22);
            }else if(ch=='e'||ch=='E'){
                //22 [eE] 23
                map23.put(ch, 23);
            }else {
                // 22 [other] 26
                map23.put(ch, 26);
            }

        }
        rules.put(22,map23);
        //state==23
        Map<Character, Integer> map24 = new HashMap<>();
        for(char ch :charDict) {

            if(ch=='+'||ch=='-') {
                //23 [+-] 24
                map24.put(ch, 24);
            }else if(isNum(ch)){
                //23 [0-9] 25
                map24.put(ch, 25);
            }else {
                // error
                map24.put(ch, -1);
            }

        }
        rules.put(23,map24);
        //state==24
        Map<Character, Integer> map25 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)) {
                //  24 [0-9] 25
                map25.put(ch, 25);
            }else{
                //error
                map25.put(ch, -1);
            }

        }
        rules.put(24,map25);
        //state==25
        Map<Character, Integer> map26 = new HashMap<>();
        for(char ch :charDict) {

            if(isNum(ch)) {
                //  24 [0-9] 25
                map26.put(ch, 25);
            }else{
                //25 [other] 26
                map26.put(ch, 26);
            }

        }
        rules.put(25,map26);
        //state==27
        Map<Character, Integer> map27 = new HashMap<>();
        for(char ch :charDict) {
            // 27 [other] 26
            map27.put(ch, 26);
        }
        rules.put(27,map27);
        //state==28
        Map<Character, Integer> map28 = new HashMap<>();
        for(char ch :charDict) {
            // 28 [other] 26
            map28.put(ch, 26);
        }
        rules.put(28,map28);
        //state==29
        Map<Character, Integer> map29 = new HashMap<>();
        for(char ch :charDict) {
            if(ch=='=') {
                // 29 = 30
                map29.put(ch, 30);
            }else{
                //29 [other] 26
                map29.put(ch, 26);
            }
        }
        rules.put(29,map29);
        //state==30
        Map<Character, Integer> map30 = new HashMap<>();
        for(char ch :charDict) {
            // 30 [other] 26
            map30.put(ch, 26);
        }
        rules.put(30,map30);
    }

    /**
     * 判断是否为数字
     * @param ch
     * @return
     */
    private static boolean isNum(char ch){
        return ch=='0'|| ch=='1'|| ch=='2'|| ch=='3'|| ch=='4'||
                ch=='5'|| ch=='6'|| ch=='7'|| ch=='8'|| ch=='9';
    }

    /**
     * 判断是否为字母
     * @param ch
     * @return
     */
    private static boolean isDis(char ch){
        return ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f'||ch=='g'||ch=='h'||ch=='i'||
                ch=='j'||ch=='k'||ch=='l'||ch=='m'||ch=='n'||ch=='o'||ch=='p'||ch=='q'||ch=='r'||
                ch=='s'||ch=='t'||ch=='u'||ch=='v'||ch=='w'||ch=='x'||ch=='y'||ch=='z'||ch=='A'||
                ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F'||ch=='G'||ch=='H'||ch=='I'||ch=='J'||
                ch=='K'||ch=='L'||ch=='M'||ch=='N'||ch=='O'||ch=='P'||ch=='Q'||ch=='R'||ch=='S'||
                ch=='T'||ch=='U'||ch=='V'||ch=='W'||ch=='X'||ch=='Y'||ch=='Z';
    }

    /**
     * 判断是否在字母表中
     * @param ch
     * @return
     */
    private static boolean inDict(char ch){
        for(char inch:charDict){
            if(ch==inch){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据当前状态和下一个输入获取下一个状态
     * @param state
     * @param ch
     * @return
     */
    public int getNextState(int state,char ch){
        //不在字母表中
        if(!inDict(ch)){
            return -1;
        }
        Map<Character,Integer> rulesThisState = rules.get(state);
        //此状态下无规则
        if (rulesThisState==null){
            return -1;
        }
        //返回下一状态
        return rulesThisState.get(ch);
    }

    /**
     * 输出全部规则
     */
    public static void printRules(){
        for(Map.Entry<Integer,Map<Character,Integer>> map1:rules.entrySet()){
            int state = map1.getKey();
            for(Map.Entry<Character,Integer> map2:map1.getValue().entrySet()){
                char ch = map2.getKey();
                int nextState = map2.getValue();
                System.out.print("含有规则：state = ");
                System.out.print(state);
                System.out.print(" input = ");
                System.out.print(ch);
                System.out.print(" next state = ");
                System.out.println(nextState);
            }
        }
    }

    /**
     * 获取开始状态
     * @return
     */
    public int getStartState(){
        return 0;
    }

    /**
     * 获取终止状态集合
     * @return
     */
    public int[] getEndStates(){
        return new int[]{26};
    }
}