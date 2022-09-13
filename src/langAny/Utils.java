package langAny;

import java.util.*;


/**
 * 工具类，用于计算First集和Follow集
 */
public class Utils {

    //first集集合
    private static Map<MyChar,Set<MyChar>> firsts = new HashMap<>();
    //follow集集合
    private static Map<MyChar,Set<MyChar>> follows = new HashMap<>();

    //初始化时生成全部非终结符的Follow集合
    static {
        addToFollow();
    }

    /**
     * 计算字符c的First集合，非左递归文法不会出现死循环
     * @param c
     * @return
     */
    public static Set<MyChar> getFirst(MyChar c){
        //暂存
        if(firsts.get(c)!=null){
            return firsts.get(c);
        }
        Set<MyChar> finalFirst = new HashSet<>();
        if(c.isEndChar()){
            //终结符
            finalFirst.add(c);
        }else{
            //非终结符
            //获取产生式c->xxxxxx
            for(CA ca:CAAdmin.getCa(c)){
                //加入First(xxxxxx)
                finalFirst.addAll(getFirst(ca.getEnd()));
            }
        }
        firsts.putIfAbsent(c,finalFirst);
        return finalFirst;
    }

    /**
     * 获取串chars的First集合
     * @param chars
     * @return
     */
    public static Set<MyChar> getFirst(List<MyChar> chars){
        boolean isFinish = true;
        Set<MyChar> finalFirst = new HashSet<>();
        for(MyChar c:chars){
            isFinish = true;
            Set<MyChar> first = getFirst(c);
            //若ε在first(c)里，则加入first(c+1)，即继续循环
            for(MyChar cc:first){
                if(cc.isE()){
                    isFinish = false;
                }else{
                    //加入first(c)中不为ε的字符
                    finalFirst.add(cc);
                }
            }
            if(isFinish){
                break;
            }
        }
        if(isFinish) {
            return finalFirst;
        }else{
            //加入ε
            MyChar e = new MyChar();
            e.setC("ε");
            e.setEndChar(true);
            finalFirst.add(e);
            return finalFirst;
        }
    }


    /**
     * 多次迭代生成每个非终结符的FOLLOW集合
     */
    private static void addToFollow(){
        //加入结尾符$
        MyChar endC = new MyChar();
        endC.setC("$");
        endC.setEndChar(true);
        follows.clear();
        MyChar start = CAAdmin.getStartChar();
        follows.put(start,new HashSet<>());
        follows.get(start).add(endC);
        //循环至每个FOLLOW集都不再扩大为止
        boolean fl = true;
        while(fl) {
            fl = false;
            //循环每条产生式
            for (CA ca : CAAdmin.getAllCa()) {
                //循环产生式右侧的每个字符
                for (int i = 0; i < ca.getEnd().size(); i++) {
                    MyChar B = ca.getEnd().get(i);
                    if (!B.isEndChar()) {
                        //非终结符B
                        if (i == ca.getEnd().size() - 1) {
                            // A-> aB
                            MyChar A = ca.getStart();
                            //将此时的Follow(A)加入Follow(B)
                            if(follows.get(A)==null || follows.get(B)==null){
                                fl = true;
                            }
                            //初始化
                            follows.computeIfAbsent(A, k -> new HashSet<>());
                            follows.computeIfAbsent(B, k -> new HashSet<>());
                            //将此时的Follow(A)加入Follow(B)
                            Set<MyChar> FollowA = follows.get(A);
                            int b1 = follows.get(B).size();
                            Set<MyChar> FollowB = follows.get(B);
                            FollowB.addAll(FollowA);
                            follows.replace(B,FollowB);
                            //判断是否更新过
                            int b2 = follows.get(B).size();
                            if(b2!=b1){
                                //已修改
                                fl = true;
                            }
                        } else {
                            // A-> aBb
                            List<MyChar> b = ca.getEnd().subList(i + 1, ca.getEnd().size());
                            //计算first（b）
                            Set<MyChar> first = getFirst(b);
                            //初始化
                            if(follows.get(B)==null){
                                fl = true;
                            }
                            follows.computeIfAbsent(B, k -> new HashSet<>());
                            Set<MyChar> FollowB = follows.get(B);
                            //判断ε是否∈first（b）,并加入除ε以外的全部字符
                            int b1 = follows.get(B).size();
                            boolean hasE = false;
                            for (MyChar cc : first) {
                                if (cc.isE()) {
                                    hasE = true;
                                } else {
                                    FollowB.add(cc);
                                }
                            }
                            follows.replace(B,FollowB);
                            int b2 = follows.get(B).size();
                            if(b1!=b2){
                                //已修改
                                fl = true;
                            }
                            if (hasE) {
                                //ε∈first（b）
                                MyChar A = ca.getStart();
                                //将此时的Follow(A)加入Follow(B)
                                if(follows.get(A)==null){
                                    fl = true;
                                }
                                follows.computeIfAbsent(A, k -> new HashSet<>());
                                //将此时的Follow(A)加入Follow(B)
                                Set<MyChar> FollowA = follows.get(A);
                                int b3 = follows.get(B).size();
                                FollowB = follows.get(B);
                                FollowB.addAll(FollowA);
                                follows.replace(B,FollowB);
                                //判断是否更新过
                                int b4 = follows.get(B).size();
                                if(b3!=b4){
                                    //已修改
                                    fl = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取非终结符的follow集合
     * @param unEndChar
     * @return
     */
    public static Set<MyChar> getFollow(MyChar unEndChar){
        return follows.get(unEndChar);
    }

    /**
     * 输出follow集
     */
    public static void printFollow(){
        for(Map.Entry<MyChar,Set<MyChar>> set:follows.entrySet()){
            System.out.print(set.getKey().getC());
            System.out.print(" : ");
            for(MyChar c:set.getValue()){
                System.out.print(c.getC());
                System.out.print(" ");
            }
            System.out.println(" ");
        }
    }

    /**
     * 测试用例
     */
    public static void test(){
        CAAdmin.print();
        Utils.printFollow();
        MyChar test = new MyChar();
        test.setC("E");
        test.setEndChar(false);
        /*
        Set<langAny.MyChar> first = utils.getFirst(test);
        for(langAny.MyChar c:first){
            System.out.print(c.getC());
            System.out.print("  ");
        }
        */
        Set<MyChar> follow= Utils.getFollow(test);
        for(MyChar c:follow){
            System.out.print(c.getC());
            System.out.print("  ");
        }
    }

}








