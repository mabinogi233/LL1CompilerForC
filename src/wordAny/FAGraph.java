package wordAny;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//存储状态机的图形式，仅存储边的集合
public class FAGraph {
    //转移规则
    private ArrayList<Edge> rules = new ArrayList<>();
    //起始节点
    private int startState;
    //终止节点
    private int endState;
    //节点
    private Set<Integer> nodes;

    public void setRules(ArrayList<Edge> rules) {
        this.rules = rules;
    }

    public void setEndState(int endState) {
        this.endState = endState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public void setNodes(Set<Integer> nodes) {
        this.nodes = nodes;
    }


    public int getEndState() {
        return endState;
    }

    public int getStartState() {
        return startState;
    }

    public Set<Integer> getNodes() {
        return nodes;
    }

    /**
     * 获取ϵ-closure(state)
     * @param state
     * @return
     */
    public Set<Integer> getEClosure(int state){
        Set<Integer> S = new HashSet<>();
        S.add(state);
        for(Edge edge:getStateAll(state)){
            //遍历此节点的全部转移规则
            if(edge.getCh()=='ϵ'){
                //找到一条空转移，加入edge.end，并递归查找
                S.add(edge.getEnd());
                //递归
                S.addAll(getEClosure(edge.getEnd()));
            }
        }
        return S;
    }

    /**
     * 获取ϵ-closure(states)
     * @param states
     * @return
     */
    public Set<Integer> getEClosure(Set<Integer> states){
        Set<Integer> S = new HashSet<>();
        for(Integer state:states){
            S.addAll(getEClosure(state));
        }
        return S;
    }


    /**
     * 获取move(state,a)
     * @param state
     * @param a
     * @return
     */
    public Set<Integer> getMoveTa(int state,char a){
        Set<Integer> S = new HashSet<>();
        for(Edge edge:getStateAll(state)){
            //遍历此节点的全部转移规则
            if(edge.getCh()=='ϵ'){
                //找到一条空转移,递归查找
                S.addAll(getMoveTa(edge.getEnd(),a));
            }else if(edge.getCh()==a){
                //加入edge.end状态
                S.add(edge.getEnd());
            }
        }
        return S;
    }

    /**
     * 获取move(T,a)
     * @param states
     * @param a
     * @return
     */
    public Set<Integer> getMoveTa(Set<Integer> states,char a){
        Set<Integer> S = new HashSet<>();
        for(Integer state:states){
            S.addAll(getMoveTa(state,a));
        }
        return S;
    }

    /**
     * 获取state状态的全部转移规则
     * @param state
     * @return
     */
    private Set<Edge> getStateAll(int state){
        Set<Edge> edges = new HashSet<>();
        for(Edge edge:rules){
            if(edge.getStart()==state){
                edges.add(edge);
            }
        }
        return edges;
    }

    public void print(){
        System.out.print("start: ");
        System.out.println(startState);
        System.out.print("end: ");
        System.out.println(endState);
        for(Edge edge:rules){
            System.out.print(edge.getStart());
            System.out.print("  ");
            System.out.print(edge.getCh());
            System.out.print("  ");
            System.out.println(edge.getEnd());
        }
    }

}

//一条NFA的边
class Edge{
    //起点
    private int start;
    //终点
    private int end;
    //输入字符
    private char ch;

    public char getCh() {
        return ch;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setStart(int start) {
        this.start = start;
    }
}