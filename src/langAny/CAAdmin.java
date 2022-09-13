package langAny;

import java.util.*;

//产生式管理
public class CAAdmin {
    //上下文无关文法，采用Map形式存储，Map<langAny.MyChar, List<langAny.MyChar>>表示一条产生式，c->list
    private static List<CA> cas = new ArrayList<>();
    //起始符
    private static MyChar startChar;
    //终结符
    private static Set<MyChar> endChars = new HashSet<>();
    //非终结符
    private static Set<MyChar> unEndChars = new HashSet<>();

    public static Set<MyChar> getEndChars() {
        return endChars;
    }

    public static Set<MyChar> getUnEndChars() {
        return unEndChars;
    }

    //初始化
    static {
        //创建全部文法符号
        MyChar yuJvBiao = createMyChar("语句表", false);
        MyChar yuJv = createMyChar("语句", false);
        MyChar shengMingYuJv = createMyChar("声明语句", false);
        MyChar yunSuanYuJv = createMyChar("运算语句", false);
        MyChar luoJiYuJv = createMyChar("逻辑语句", false);
        MyChar fuZhiYuJv = createMyChar("赋值语句", false);
        MyChar fenZhiYuJv = createMyChar("分支语句", false);
        MyChar xunHuanYuJv = createMyChar("循环语句", false);
        MyChar guanXiYuJv = createMyChar("关系语句", false);
        MyChar chengXvDiaoYongYuJv = createMyChar("调用语句", false);
        MyChar bianLiangLieBiao = createMyChar("变量列表", false);
        MyChar bianLiangLieBiao1 = createMyChar("变量列表1", false);
        MyChar biaoShiFu = createMyChar("标识符", true);
        MyChar zuoDaKuoHao = createMyChar("{", true);
        MyChar youDaKuoHao = createMyChar("}", true);
        MyChar douHao = createMyChar(",", true);
        MyChar fenHao = createMyChar(";", true);
        MyChar anInt = createMyChar("int", true);
        MyChar bool = createMyChar("bool", true);
        MyChar yunSuanYuJv1 = createMyChar("运算语句1", false);
        MyChar yunSuanYuJv2 = createMyChar("运算语句2", false);
        MyChar yunSuanYuJv3 = createMyChar("运算语句3", false);
        MyChar yunSuanYuJv4 = createMyChar("运算语句4", false);
        MyChar yunSuanYuJv5 = createMyChar("运算语句5", false);

        MyChar jia = createMyChar("+", true);
        MyChar jian = createMyChar("-", true);
        MyChar zuoXiaoKuoHao = createMyChar("(", true);
        MyChar youXiaoKuoHao = createMyChar(")", true);
        MyChar luoJiYuJv1 = createMyChar("逻辑语句1", false);
        MyChar luoJiYuJv2 = createMyChar("逻辑语句2", false);
        MyChar luoJiYuJv3 = createMyChar("逻辑语句3", false);
        MyChar luoJiYuJv4 = createMyChar("逻辑语句4", false);
        MyChar changLiang = createMyChar("常量", true);
        MyChar huo = createMyChar("||", true);
        MyChar yu = createMyChar("&&", true);
        MyChar fei = createMyChar("!", true);
        MyChar fuZhi = createMyChar("=", true);
        MyChar zuoZhi = createMyChar("左值", false);
        MyChar youZhi = createMyChar("右值", false);
        MyChar anIf = createMyChar("if", true);
        MyChar anElse = createMyChar("else", true);
        MyChar fenZhiYuJv1 = createMyChar("分支语句1", false);
        MyChar kong = createMyChar("ε", true);
        MyChar aWhile = createMyChar("while", true);
        MyChar guanXiYuJv1 = createMyChar("关系语句1", false);
        MyChar guanXiYuJv2 = createMyChar("关系语句2", false);
        MyChar guanXiYuJv3 = createMyChar("关系语句3", false);
        MyChar guanXiYuJv4 = createMyChar("关系语句4", false);
        MyChar guanXiYuJv5 = createMyChar("关系语句5", false);
        MyChar guanXiYuJv6 = createMyChar("关系语句6", false);
        MyChar guanXiYuJv7 = createMyChar("关系语句7", false);
        MyChar daYu = createMyChar(">", true);
        MyChar xiaoYu = createMyChar("<", true);
        MyChar daYuDengYu = createMyChar(">=", true);
        MyChar xiaoYuDengYu = createMyChar("<=", true);
        MyChar dengYu = createMyChar("==", true);
        MyChar buDengYu = createMyChar("<>", true);
        MyChar aTrue = createMyChar("true", true);
        MyChar aFalse = createMyChar("false", true);
        MyChar canShuLieBiao = createMyChar("参数列表", false);
        MyChar canShuLieBiao1 = createMyChar("参数列表1", false);
        MyChar sentenceEnd = createMyChar("$", true);
        MyChar weiYu = createMyChar("&", true);
        MyChar weiHuo = createMyChar("|", true);
        MyChar canShuShuoMing = createMyChar("参数说明", false);
        MyChar fuZhiYuJvKuoZhan = createMyChar("赋值语句扩展", false);
        startChar = yuJvBiao;
        //创建产生式

        createCA(yuJvBiao, new ArrayList<>() {{
            add(yuJv);
        }});
        createCA(yuJvBiao, new ArrayList<>() {{
            add(yuJvBiao);
            add(yuJv);
        }});

        createCA(yuJv, new ArrayList<>() {{
            add(fenZhiYuJv);
            add(fenHao);
        }});
        createCA(yuJv, new ArrayList<>() {{
            add(xunHuanYuJv);
            add(fenHao);
        }});
        createCA(yuJv, new ArrayList<>() {{
            add(fuZhiYuJv);
            add(fenHao);
        }});

        createCA(fuZhiYuJv, new ArrayList<>() {{
            add(zuoZhi);
            add(fuZhiYuJvKuoZhan);
        }});
        createCA(fuZhiYuJvKuoZhan, new ArrayList<>() {{
            add(fuZhi);
            add(youZhi);
        }});
        createCA(fuZhiYuJvKuoZhan, new ArrayList<>() {{
            add(kong);
        }});


        createCA(zuoZhi, new ArrayList<>() {{
            add(shengMingYuJv);
        }});
        createCA(youZhi, new ArrayList<>() {{
            add(yunSuanYuJv);
        }});

        createCA(shengMingYuJv, new ArrayList<>() {{
            add(anInt);
            add(bianLiangLieBiao);
        }});
        createCA(shengMingYuJv, new ArrayList<>() {{
            add(bool);
            add(bianLiangLieBiao);
        }});
        createCA(shengMingYuJv, new ArrayList<>() {{
            add(chengXvDiaoYongYuJv);
        }});
        createCA(bianLiangLieBiao, new ArrayList<>() {{
            add(bianLiangLieBiao);
            add(douHao);
            add(bianLiangLieBiao1);
        }});
        createCA(bianLiangLieBiao, new ArrayList<>() {{
            add(bianLiangLieBiao1);
        }});
        createCA(bianLiangLieBiao1, new ArrayList<>() {{
            add(biaoShiFu);
        }});

        createCA(yunSuanYuJv, new ArrayList<>() {{
            add(yunSuanYuJv);
            add(jia);
            add(yunSuanYuJv1);
        }});
        createCA(yunSuanYuJv, new ArrayList<>() {{
            add(yunSuanYuJv1);
        }});
        createCA(yunSuanYuJv1, new ArrayList<>() {{
            add(yunSuanYuJv1);
            add(jian);
            add(yunSuanYuJv2);
        }});
        createCA(yunSuanYuJv1, new ArrayList<>() {{
            add(yunSuanYuJv2);
        }});
        createCA(yunSuanYuJv2, new ArrayList<>() {{
            add(yunSuanYuJv2);
            add(weiYu);
            add(yunSuanYuJv3);
        }});
        createCA(yunSuanYuJv2, new ArrayList<>() {{
            add(yunSuanYuJv3);
        }});
        createCA(yunSuanYuJv3, new ArrayList<>() {{
            add(yunSuanYuJv3);
            add(weiHuo);
            add(yunSuanYuJv4);
        }});
        createCA(yunSuanYuJv3, new ArrayList<>() {{
            add(yunSuanYuJv4);
        }});
        createCA(yunSuanYuJv4, new ArrayList<>() {{
            add(luoJiYuJv);
        }});

        createCA(luoJiYuJv, new ArrayList<>() {{
            add(luoJiYuJv);
            add(huo);
            add(luoJiYuJv1);
        }});
        createCA(luoJiYuJv, new ArrayList<>() {{
            add(luoJiYuJv1);
        }});
        createCA(luoJiYuJv1, new ArrayList<>() {{
            add(luoJiYuJv1);
            add(yu);
            add(luoJiYuJv2);
        }});
        createCA(luoJiYuJv1, new ArrayList<>() {{
            add(luoJiYuJv2);
        }});
        createCA(luoJiYuJv2, new ArrayList<>() {{
            add(fei);
            add(luoJiYuJv2);
        }});
        createCA(luoJiYuJv2, new ArrayList<>() {{
            add(luoJiYuJv3);
        }});
        createCA(luoJiYuJv3, new ArrayList<>() {{
            add(guanXiYuJv);
        }});

        createCA(guanXiYuJv, new ArrayList<>() {{
            add(guanXiYuJv);
            add(daYu);
            add(guanXiYuJv1);
        }});
        createCA(guanXiYuJv, new ArrayList<>() {{
            add(guanXiYuJv1);
        }});
        createCA(guanXiYuJv1, new ArrayList<>() {{
            add(guanXiYuJv1);
            add(xiaoYu);
            add(guanXiYuJv2);
        }});
        createCA(guanXiYuJv1, new ArrayList<>() {{
            add(guanXiYuJv2);
        }});
        createCA(guanXiYuJv2, new ArrayList<>() {{
            add(guanXiYuJv2);
            add(daYuDengYu);
            add(guanXiYuJv3);
        }});
        createCA(guanXiYuJv2, new ArrayList<>() {{
            add(guanXiYuJv3);
        }});
        createCA(guanXiYuJv3, new ArrayList<>() {{
            add(guanXiYuJv3);
            add(xiaoYuDengYu);
            add(guanXiYuJv4);
        }});
        createCA(guanXiYuJv3, new ArrayList<>() {{
            add(guanXiYuJv4);
        }});
        createCA(guanXiYuJv4, new ArrayList<>() {{
            add(guanXiYuJv4);
            add(dengYu);
            add(guanXiYuJv5);
        }});
        createCA(guanXiYuJv4, new ArrayList<>() {{
            add(guanXiYuJv5);
        }});
        createCA(guanXiYuJv5, new ArrayList<>() {{
            add(guanXiYuJv5);
            add(buDengYu);
            add(guanXiYuJv6);
        }});
        createCA(guanXiYuJv5, new ArrayList<>() {{
            add(guanXiYuJv6);
        }});
        createCA(guanXiYuJv6, new ArrayList<>() {{
            add(zuoXiaoKuoHao);
            add(yunSuanYuJv);
            add(youXiaoKuoHao);
        }});
        createCA(guanXiYuJv6, new ArrayList<>() {{
            add(guanXiYuJv7);
        }});
        createCA(guanXiYuJv7, new ArrayList<>() {{
            add(chengXvDiaoYongYuJv);
        }});

        createCA(chengXvDiaoYongYuJv, new ArrayList<>() {{
            add(biaoShiFu);
            add(canShuShuoMing);
        }});
        createCA(chengXvDiaoYongYuJv, new ArrayList<>() {{
            add(aFalse);
        }});
        createCA(chengXvDiaoYongYuJv, new ArrayList<>() {{
            add(aTrue);
        }});
        createCA(chengXvDiaoYongYuJv, new ArrayList<>() {{
            add(changLiang);
        }});
        createCA(canShuShuoMing, new ArrayList<>() {{
            add(zuoXiaoKuoHao);
            add(canShuLieBiao);
            add(youXiaoKuoHao);
        }});
        createCA(canShuShuoMing, new ArrayList<>() {{
            add(kong);
        }});
        createCA(canShuLieBiao, new ArrayList<>() {{
            add(canShuLieBiao);
            add(douHao);
            add(canShuLieBiao1);
        }});
        createCA(canShuLieBiao, new ArrayList<>() {{
            add(canShuLieBiao1);
        }});
        createCA(canShuLieBiao1, new ArrayList<>() {{
            add(biaoShiFu);
        }});
        createCA(canShuLieBiao1, new ArrayList<>() {{
            add(changLiang);
        }});

        createCA(fenZhiYuJv, new ArrayList<>() {{
            add(anIf);
            add(zuoXiaoKuoHao);
            add(guanXiYuJv);
            add(youXiaoKuoHao);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
            add(fenZhiYuJv1);
        }});
        createCA(fenZhiYuJv1, new ArrayList<>() {{
            add(anElse);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
        }});
        createCA(fenZhiYuJv1, new ArrayList<>() {{
            add(kong);
        }});

        createCA(xunHuanYuJv, new ArrayList<>() {{
            add(aWhile);
            add(zuoXiaoKuoHao);
            add(guanXiYuJv);
            add(youXiaoKuoHao);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
        }});

        deleteLeft();
    }

    /**
     * 删除左递归
     */
    private static void deleteLeft(){
        List<CA> new_cas = new ArrayList<>();
        List<CA> new_new_cas = new ArrayList<>();
        ArrayList<MyChar> unEndCharList = new ArrayList<MyChar>(unEndChars);
        //遍历非终结符
        for(int i = 0;i<unEndCharList.size();i++){
            MyChar Ai = unEndCharList.get(i);
            //找出Ai的全部产生式
            List<CA> cais = getCa(Ai);
            for(CA cai:cais){
                //产生式cai是否被修改
                boolean isUpdate = false;
                //遍历Aj
                for(int j = 0 ;j<i;j++) {
                    MyChar Aj = unEndCharList.get(j);
                    //存在规则Ai -> Aj y
                    if (cai.getEnd().get(0).equals(Aj)) {
                        isUpdate = true;
                        //修改Ai -> Aj y
                        //找到Aj->全部产生式
                        List<CA> cajs = getCa(Aj);
                        for (CA caj : cajs) {
                            CA new_ca = new CA();
                            new_ca.setStart(Ai);
                            new_ca.setEnd(new ArrayList<>(){{
                                addAll(caj.getEnd());
                                addAll(cai.getEnd().subList(1,cai.getEnd().size()));
                            }});
                            new_cas.add(new_ca);
                        }
                    }
                }
                if(!isUpdate){
                    new_cas.add(cai);
                }
            }
        }
        //消除直接左递归
        for(int i = 0;i<unEndCharList.size();i++){
            MyChar Ai = unEndCharList.get(i);
            //Ai的全部规则
            List<CA> cais = getCa(Ai);
            boolean hasLeft = false;
            for(CA cai:cais){
                //存在直接左递归
                if(cai.getStart().equals(cai.getEnd().get(0))){
                    hasLeft = true;
                    break;
                }
            }
            if(hasLeft){
                //创建非终结符A’
                MyChar A_ = createMyChar(Ai.getC()+"'", false);
                //加入规则A'->空
                MyChar kong = new MyChar();
                kong.setC("ε");
                kong.setEndChar(true);
                CA cakong = new CA();
                cakong.setStart(A_);
                cakong.setEnd(new ArrayList<>(){{
                    add(kong);
                }});
                new_new_cas.add(cakong);
                //加入修改后的规则
                for(CA cai:cais){
                    //是左递归产生式
                    if(cai.getStart().equals(cai.getEnd().get(0))){
                        // A' -> aA'  (原A -> Aa)
                        CA ca = new CA();
                        ca.setStart(A_);
                        ca.setEnd(new ArrayList<>(){{
                            addAll(cai.getEnd().subList(1,cai.getEnd().size()));
                            add(A_);
                        }});
                        new_new_cas.add(ca);
                    }else{
                        //不是左递归
                        // A -> bA' (原A->b)
                        CA ca = new CA();
                        ca.setStart(cai.getStart());
                        ca.setEnd(new ArrayList<>(){{
                            addAll(cai.getEnd());
                            add(A_);
                        }});
                        new_new_cas.add(ca);
                    }
                }
            }else{
                //不存在直接左递归
                new_new_cas.addAll(cais);
            }
        }
        cas = new_new_cas;
    }


    /**
     * 创建文法符号
     * @param C
     * @param isEndChar
     * @return
     */
    public static MyChar createMyChar(String C,boolean isEndChar) {
        MyChar x = new MyChar();
        x.setC(C);
        x.setEndChar(isEndChar);
        if(isEndChar){
            endChars.add(x);
        }else {
            unEndChars.add(x);
        }
        return x;
    }

    /**
     * 创建规则
     * @param startChar
     * @param endChar
     * @return
     */
    public static CA createCA(MyChar startChar,List<MyChar> endChar) {
        CA ca = new CA();
        ca.setStart(startChar);
        ca.setEnd(endChar);
        cas.add(ca);
        return ca;
    }

    /**
     * 获取文法起始符
     * @return
     */
    public static MyChar getStartChar() {
        return startChar;
    }

    //获取产生式c->xxxxx
    public static List<CA> getCa(MyChar c){
        List<CA> c_cas = new ArrayList<>();
        for(CA ca:cas) {
            //比较产生式左边字符
            if (ca.getStart().getC().equals(c.getC())) {
                c_cas.add(ca);
            }
        }
        return c_cas;
    }

    /**
     * 获取全部产生式规则
     * @return
     */
    public static List<CA> getAllCa(){
        return cas;
    }

    /**
     * 输出产生式规则
     */
    public static void print(){
        for(CA ca:cas){
            System.out.print(ca.getStart().getC());
            System.out.print(" -> ");
            for(MyChar c:ca.getEnd()){
                System.out.print(c.getC());
            }
            System.out.println(" ");
        }
    }
}


