package wordAny;

import java.util.*;

//NFA同样为输入DFARules接口对象
public class NFA2DFA {
    //字符表
    private static char[] charDict =
            "ab".toCharArray();
    /**
     * 子集构造算法
     * @param nfa
     * @return
     */
    public static DFAGraph nfa2dfa(FAGraph nfa){
        List<Integer> node = new ArrayList<>();
        Map<Integer, Map<Character,Integer>> rules = new HashMap<>();
        StatePools pools = new StatePools();
        //获取初始状态
        int s0 = nfa.getStartState();
        //求ϵ-closure(s0)
        Set<Integer> e0 = nfa.getEClosure(s0);

        //e0作为一个状态加入DFA
        pools.createState(e0);
        //有没有标记的节点时
        while(pools.hasNoFlag()){
            //获取一个没有标记的节点，然后标记此节点
            State D1 = pools.getNoFlagAndFlag();
            Set<Integer> T = D1.getNFAStates();
            //处理每个输入字符
            for(char a:charDict){
                Set<Integer> U = nfa.getEClosure(nfa.getMoveTa(T,a));
                //U不在状态集合内
                if(pools.isUninque(U)){
                    //将U加入集合
                    pools.createState(U);
                }
                //move(T, a) = U为一条状态转移规则
                //即DFA规则 f(d1,ch) -> d2
                int d1 = D1.getDFAState();
                int d2 = pools.getStateByNFAState(U).getDFAState();
                //将规则加入rules
                rules.computeIfAbsent(d1, k -> new HashMap<Character, Integer>());
                rules.get(d1).put(a,d2);
            }
        }
        DFAGraph dfaGraph = new DFAGraph();
        //添加规则
        dfaGraph.setRules(rules);
        //DFA初始状态
        dfaGraph.setStartState(0);
        //找到DFA的终止状态集合
        dfaGraph.setEndState(pools.getEndStates(nfa.getEndState()));
        return dfaGraph;
    }

    /**
     * 单元测试函数
     */
    public static void test(){
        FAGraph nfa = new FAGraph();
        ArrayList<Edge> rules = new ArrayList<>();
        Edge edge1 = new Edge();
        edge1.setStart(0);
        edge1.setCh('ϵ');
        edge1.setEnd(1);
        rules.add(edge1);

        Edge edge2 = new Edge();
        edge2.setStart(0);
        edge2.setCh('ϵ');
        edge2.setEnd(7);
        rules.add(edge2);

        Edge edge3 = new Edge();
        edge3.setStart(1);
        edge3.setCh('ϵ');
        edge3.setEnd(2);
        rules.add(edge3);

        Edge edge4 = new Edge();
        edge4.setStart(1);
        edge4.setCh('ϵ');
        edge4.setEnd(4);
        rules.add(edge4);

        Edge edge5 = new Edge();
        edge5.setStart(2);
        edge5.setCh('a');
        edge5.setEnd(3);
        rules.add(edge5);

        Edge edge6 = new Edge();
        edge6.setStart(3);
        edge6.setCh('ϵ');
        edge6.setEnd(6);
        rules.add(edge6);

        Edge edge7 = new Edge();
        edge7.setStart(4);
        edge7.setCh('b');
        edge7.setEnd(5);
        rules.add(edge7);

        Edge edge8 = new Edge();
        edge8.setStart(5);
        edge8.setCh('ϵ');
        edge8.setEnd(6);
        rules.add(edge8);

        Edge edge9 = new Edge();
        edge9.setStart(6);
        edge9.setCh('ϵ');
        edge9.setEnd(7);
        rules.add(edge9);

        Edge edge10 = new Edge();
        edge10.setStart(6);
        edge10.setCh('ϵ');
        edge10.setEnd(1);
        rules.add(edge10);

        Edge edge11 = new Edge();
        edge11.setStart(7);
        edge11.setCh('a');
        edge11.setEnd(8);
        rules.add(edge11);

        Edge edge12 = new Edge();
        edge12.setStart(8);
        edge12.setCh('b');
        edge12.setEnd(9);
        rules.add(edge12);

        Edge edge13 = new Edge();
        edge13.setStart(9);
        edge13.setCh('b');
        edge13.setEnd(10);
        rules.add(edge13);

        nfa.setRules(rules);
        nfa.setStartState(0);
        nfa.setEndState(10);
        //nfa.print();
        System.out.println("**************************");
        NFA2DFA.nfa2dfa(nfa).print();
    }

}
//State工具类，提供相关操作
class StatePools{
    //存储当前存在的、不同的states
    List<State> states = new ArrayList<>();

    /**
     * 创建State,并加入states
     * @param nfaState
     * @return
     */
    public State createState(Set<Integer> nfaState){
        State state0 = new State();
        state0.setDFAState(states.size());
        state0.setNFAStates(nfaState);
        state0.setFlag(false);
        //是不同的状态，则加入
        if(isUninque(state0)){
            states.add(state0);
        }
        return state0;
    }

    /**
     * 返回是否有没被标记的状态，若有没被标记的，返回true
     * @return
     */
    public boolean hasNoFlag(){
        for(State state1:states){
            //有没被标记的
            if(!state1.getFlag()){
                return true;
            }
        }
        return false;
    }

    /**
     * 标记并返回一个没有标记的state
     * @return
     */
    public State getNoFlagAndFlag(){
        for(int i=0;i<states.size();i++){
            //有没被标记的
            if(!states.get(i).getFlag()){
                states.get(i).setFlag(true);
                return states.get(i);
            }
        }
        return null;
    }

    /**
     * 判断state是否唯一
     * @param state
     * @return
     */
    public boolean isUninque(State state){
        for(State state1:states){
            if(state.equals(state1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断state是否唯一
     * @param NFAstate
     * @return
     */
    public boolean isUninque(Set<Integer> NFAstate){
        State state = getStateByNFAState(NFAstate);
        if(state==null){
            return true;
        }
        for(State state1:states){
            if(state.equals(state1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据nfaState找到State
     * @param nfaState
     * @return
     */
    public State getStateByNFAState(Set<Integer> nfaState){
        for(State state:states){
            if(State.equals(state.getNFAStates(),nfaState)){
                return state;
            }
        }
        return null;
    }

    /**
     * 获取DFA的终止状态
     * @param NFAEnd
     * @return
     */
    public List<Integer> getEndStates(int NFAEnd){
        List<Integer> ends = new ArrayList<>();
        for(State state:states){
            if(state.getNFAStates().contains(NFAEnd)){
                ends.add(state.getDFAState());
            }
        }
        return ends;
    }
}


class State{
    //DFA状态
    private int DFAState;
    //对应NFA的集合
    private Set<Integer> NFAStates;
    //是否被标记
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public int getDFAState() {
        return DFAState;
    }

    public Set<Integer> getNFAStates() {
        return NFAStates;
    }

    public void setDFAState(int DFAState) {
        this.DFAState = DFAState;
    }

    public void setNFAStates(Set<Integer> NFAStates) {
        this.NFAStates = NFAStates;
    }

    /**
     * 比较两个状态是否相同
     * @param state
     * @return
     */
    public boolean equals(State state){
        return State.equals(state.getNFAStates(),this.getNFAStates());
    }

    /**
     * 集合比较
     * @param set1
     * @param set2
     * @return
     */
    public static boolean equals(Set<Integer> set1, Set<Integer> set2){
        if(set1 == null || set2 ==null){
            return false;
        }
        if(set1.size()!=set2.size()){
            return false;
        }
        return set1.containsAll(set2);
    }


}





