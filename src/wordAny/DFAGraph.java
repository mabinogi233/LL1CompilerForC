package wordAny;

import java.util.List;
import java.util.Map;

public class DFAGraph extends Rule{

    private static char[] charDict =
            "#abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>+-*/%&!=_|\'\".()[]{};:".toCharArray();

    //第一个map的key值为当前状态state，value对应此state下的转移规则
    //第二个map的key值为当前输入，value值对应下一个状态
    private Map<Integer, Map<Character,Integer>> rules;

    private int startState;

    private List<Integer> endState;

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public void setEndState(List<Integer> endState) {
        this.endState = endState;
    }

    public void setRules(Map<Integer, Map<Character, Integer>> rules) {
        this.rules = rules;
    }

    @Override
    public int getStartState() {
        return startState;
    }

    @Override
    public int[] getEndStates() {
        int[] ints = new int[endState.size()];
        for(int i=0;i<endState.size();i++){
            ints[i]=endState.get(i);
        }
        return ints;
    }

    @Override
    public int getNextState(int state, char ch) {
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

    public void print(){
        System.out.println("起始状态： "+String.valueOf(startState));
        System.out.print("终止状态集合：");
        System.out.println(endState.toString());
        System.out.println("状态转移表：");
        System.out.println("state input nextState");
        for(Map.Entry<Integer,Map<Character,Integer>> map:rules.entrySet()){
            for(Map.Entry<Character,Integer> map1:map.getValue().entrySet()){
                System.out.print(map.getKey());
                System.out.print("  ");
                System.out.print(map1.getKey());
                System.out.print("  ");
                System.out.println(map1.getValue());
            }
        }
    }

}
