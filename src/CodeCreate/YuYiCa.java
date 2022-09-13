package CodeCreate;

import langAny.CA;
import langAny.MyChar;
import CodeCreate.CharList;

import java.util.ArrayList;
import java.util.List;

//语义扩展
public class YuYiCa {

    public static boolean ErrorFlag;

    public static boolean TypeErrorFlag;

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
        return x;
    }

    /**
     * 创建规则
     * @param startChar
     * @param endChar
     * @return
     */
    public static CA createCA(MyChar startChar, List<MyChar> endChar) {
        CA ca = new CA();
        ca.setStart(startChar);
        ca.setEnd(endChar);
        return ca;
    }

    /**
     * 语义处理
     * @param father 当前语法树节点的的父节点
     * @param node 当前语法树节点
     * @param ca 当前生成语法树使用的产生式
     * @param n 产生式右边的第n个字符的下标，
     *          即当前是产生式右边第几个字符，即需要判断第n个字符前是否有语义动作，
     *          n=-1时为执行产生式末尾的语义动作。
     */
    public static void yuyiAny(CodeCreate.LTreeNode father,CodeCreate.LTreeNode node, CA ca, int n){

        ErrorFlag = false;
        TypeErrorFlag = false;
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
        MyChar bianLiangLieBiaoPie = createMyChar("变量列表'", false);
        MyChar yunSuanYuJvPie = createMyChar("运算语句'", false);
        MyChar yunSuanYuJv1Pie = createMyChar("运算语句1'", false);
        MyChar yunSuanYuJv2Pie = createMyChar("运算语句2'", false);
        MyChar yunSuanYuJv3Pie = createMyChar("运算语句3'", false);
        MyChar luoJiYuJvPie = createMyChar("逻辑语句'", false);
        MyChar luoJiYuJv1Pie = createMyChar("逻辑语句1'", false);
        MyChar guanXiYuJvPie = createMyChar("关系语句'", false);
        MyChar guanXiYuJv1Pie = createMyChar("关系语句1'", false);
        MyChar guanXiYuJv2Pie = createMyChar("关系语句2'", false);
        MyChar guanXiYuJv3Pie = createMyChar("关系语句3'", false);
        MyChar guanXiYuJv4Pie = createMyChar("关系语句4'", false);
        MyChar guanXiYuJv5Pie = createMyChar("关系语句5'", false);
        //赋值语句 -> 左值 {赋值语句扩展.type = 左值.type} 赋值语句扩展
        if(ca.equals(createCA(fuZhiYuJv,new ArrayList<MyChar>(){{
            add(zuoZhi);
            add(fuZhiYuJvKuoZhan);
        }}))){
            if(n==1){
                node.addAtt("value",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr",father.getChilds().get(0).getAtt("addr"));
                node.addAtt("type",father.getChilds().get(0).getAtt("type"));
            }
        }
        //赋值语句扩展 -> =右值 {类型检查（赋值语句扩展.type，右值.type）}
        if(ca.equals(createCA(fuZhiYuJvKuoZhan,new ArrayList<MyChar>(){{
            add(fuZhi);
            add(youZhi);
        }}))){
            if(n==-1){
                typeCheck(father.getAtt("type"),father.getChilds().get(1).getAtt("type"));
                //gen(赋值语句扩展.addr=右值.addr);
                CreateCode.createCode("=",father.getChilds().get(1).getAtt("addr"),"",father.getAtt("addr"));
            }
        }
        //左值 -> 声明语句 {左值.type = 声明语句.type}
        if(ca.equals(createCA(zuoZhi,new ArrayList<MyChar>(){{
            add(shengMingYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //声明语句 -> int{变量列表.type=int}变量列表 {声明语句.type=int }
        if(ca.equals(createCA(shengMingYuJv,new ArrayList<MyChar>(){{
            add(anInt);
            add(bianLiangLieBiao);
        }}))){
            if(n==1){
                node.addAtt("type","int");
                father.addAtt("value", "0");
            }
            if(n==-1){
                father.addAtt("value", "0");
                father.addAtt("type", "int");
                father.addAtt("addr", CharList.getLastChar());//获取此时符号表的最后一个变量的地址(名称)
            }
        }
        //声明语句 -> bool{变量列表.type=bool}变量列表  {声明语句.type=bool }
        if(ca.equals(createCA(shengMingYuJv,new ArrayList<MyChar>(){{
            add(bool);
            add(bianLiangLieBiao);
        }}))){
            if(n==1){
                node.addAtt("type","bool");
                father.addAtt("value", "0");
            }
            if(n==-1){
                father.addAtt("value", "0");
                father.addAtt("type", "bool");
                father.addAtt("addr", CharList.getLastChar());//获取此时符号表的最后一个变量的地址
            }
        }
        //声明语句 -> 调用语句 {声明语句.type=调用语句.type}
        if(ca.equals(createCA(shengMingYuJv,new ArrayList<MyChar>(){{
            add(chengXvDiaoYongYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //变量列表 ->{变量列表1.type = 变量列表.type} 变量列表1{变量列表'.type = 变量列表.type}变量列表'
        if(ca.equals(createCA(bianLiangLieBiao,new ArrayList<MyChar>(){{
            add(bianLiangLieBiao1);
            add(bianLiangLieBiaoPie);
        }}))){
            if(n==0){
                node.addAtt("type",father.getAtt("type"));
                node.addAtt("value",father.getAtt("value"));
            }
            if(n==1){
                node.addAtt("type",father.getAtt("type"));
                node.addAtt("value",father.getAtt("value"));
            }
        }
        //变量列表' -> ,{变量列表1.type = 变量列表'（父）.type}变量列表1{变量列表'（子）.type = 变量列表'（父）.type}变量列表'
        if(ca.equals(createCA(bianLiangLieBiaoPie,new ArrayList<MyChar>(){{
            add(douHao);
            add(bianLiangLieBiao1);
            add(bianLiangLieBiaoPie);
        }}))){
            if(n==1){
                node.addAtt("type",father.getAtt("type"));
                node.addAtt("value",father.getAtt("value"));
            }
            if(n==2){
                node.addAtt("type",father.getAtt("type"));
                node.addAtt("value",father.getAtt("value"));
            }
        }
        //变量列表1 -> {标识符.type = 变量列表1.type }标识符
        if(ca.equals(createCA(bianLiangLieBiao1,new ArrayList<MyChar>(){{
            add(biaoShiFu);
        }}))){
            if(n==0){
                node.addAtt("type",father.getAtt("type"));
                node.addAtt("value",father.getAtt("value"));
                //加入符号表
                CharList.add(node.getToken().getItem(),father.getAtt("type"),father.getAtt("value"));
            }
        }
        //右值 -> 运算语句 {右值.type=运算语句.type}
        if(ca.equals(createCA(youZhi,new ArrayList<MyChar>(){{
            add(yunSuanYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //运算语句' -> ε {运算语句' .type(s)=运算语句' .type(i)}
        if(ca.equals(createCA(yunSuanYuJvPie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //运算语句' -> +运算语句1{ 类型检查（运算语句1.type，运算语句'（父）.type(i)）   ，运算语句'（子）.type(i) = 运算语句1.type ，运算语句'（子）.val(i)=运算语句'（父）.val(i)+运算语句1.val } 运算语句' {运算语句'（父）.type(s) = 运算语句'（子）.type(s)}
        if(ca.equals(createCA(yunSuanYuJvPie,new ArrayList<MyChar>(){{
            add(jia);
            add(yunSuanYuJv1);
            add(yunSuanYuJvPie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",String.valueOf(Integer.parseInt(father.getAtt("value(i)")) + Integer.parseInt(father.getChilds().get(1).getAtt("value"))));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（运算语句'（子）.addr(i) = 运算语句'（父）.addr(i) +运算语句1.addr ）
                CreateCode.createCode("+",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //运算语句 -> 运算语句1{运算语句'.type(i) = 运算语句1.type}运算语句' {运算语句.type = 运算语句'.type(s)}
        if(ca.equals(createCA(yunSuanYuJv,new ArrayList<MyChar>(){{
            add(yunSuanYuJv1);
            add(yunSuanYuJvPie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr",father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //运算语句1' -> ε {运算语句1' .type(s)=运算语句1' .type(i)}
        if(ca.equals(createCA(yunSuanYuJv1Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //运算语句1' -> -运算语句2{ 类型检查（运算语句2.type，运算语句1'（父）.type(i)）   ，运算语句1'（子）.type(i) = 运算语句2.type ，运算语句1'（子）.val(i)=运算语句1'（父）.val(i)-运算语句2.val }运算语句1' {运算语句1'（父）.type(s) = 运算语句1'（子）.type(s)}
        if(ca.equals(createCA(yunSuanYuJv1Pie,new ArrayList<MyChar>(){{
            add(jian);
            add(yunSuanYuJv2);
            add(yunSuanYuJv1Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",String.valueOf(Integer.parseInt(father.getAtt("value(i)")) - Integer.parseInt(father.getChilds().get(1).getAtt("value"))));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（运算语句1'（子）.addr(i) = 运算语句1'（父）.addr(i) - 运算语句2.addr ）
                CreateCode.createCode("-",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //运算语句1 -> 运算语句2{运算语句1'.type(i) = 运算语句2.type}运算语句1' {运算语句1.type = 运算语句1'.type(s)}
        if(ca.equals(createCA(yunSuanYuJv1,new ArrayList<MyChar>(){{
            add(yunSuanYuJv2);
            add(yunSuanYuJv1Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //运算语句2' -> ε {运算语句2' .type(s)=运算语句2' .type(i)}
        if(ca.equals(createCA(yunSuanYuJv2Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //运算语句2' -> &运算语句3{ 类型检查（运算语句3.type，运算语句2'（父）.type(i)）   ，运算语句2'（子）.type(i) = 运算语句3.type }运算语句2' {运算语句2'（父）.type(s) = 运算语句2'（子）.type(s)}
        if(ca.equals(createCA(yunSuanYuJv2Pie,new ArrayList<MyChar>(){{
            add(weiYu);
            add(yunSuanYuJv3);
            add(yunSuanYuJv2Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（运算语句2'（子）.addr(i) = 运算语句2'（父）.addr(i) & 运算语句3.addr ）
                CreateCode.createCode("&",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //运算语句2 -> 运算语句3{运算语句2'.type(i) = 运算语句3.type}运算语句2' {运算语句2.type = 运算语句2'.type(s)}
        if(ca.equals(createCA(yunSuanYuJv2,new ArrayList<MyChar>(){{
            add(yunSuanYuJv3);
            add(yunSuanYuJv2Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //运算语句3' -> ε {运算语句3' .type(s)=运算语句3' .type(i)}
        if(ca.equals(createCA(yunSuanYuJv3Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //运算语句3' -> |运算语句4{ 类型检查（运算语句4.type，运算语句3'（父）.type(i)）   ，运算语句3'（子）.type(i) = 运算语句4.type }运算语句3' {运算语句3'（父）.type(s) = 运算语句3'（子）.type(s)}
        if(ca.equals(createCA(yunSuanYuJv3Pie,new ArrayList<MyChar>(){{
            add(weiHuo);
            add(yunSuanYuJv4);
            add(yunSuanYuJv3Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（运算语句3'（子）.addr(i) = 运算语句3'（父）.addr(i) +运算语句4.addr ）
                CreateCode.createCode("|",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //运算语句3 -> 运算语句4{运算语句3'.type(i) = 运算语句4.type}运算语句3' {运算语句3.type = 运算语句3'.type(s)}
        if(ca.equals(createCA(yunSuanYuJv3,new ArrayList<MyChar>(){{
            add(yunSuanYuJv4);
            add(yunSuanYuJv3Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //运算语句4 -> 逻辑语句 {运算语句4.type = 逻辑语句.type}
        if(ca.equals(createCA(yunSuanYuJv4,new ArrayList<MyChar>(){{
            add(luoJiYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //逻辑语句' -> ε {逻辑语句' .type(s)=逻辑语句' .type(i)}
        if(ca.equals(createCA(luoJiYuJvPie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //逻辑语句' -> ||逻辑语句{ 类型检查（逻辑语句1.type，逻辑语句'（父）.type(i)）   ，逻辑语句'（子）.type(i) = 逻辑语句1.type }1逻辑语句' {逻辑语句'（父）.type(s) = 逻辑语句'（子）.type(s)}
        if(ca.equals(createCA(luoJiYuJvPie,new ArrayList<MyChar>(){{
            add(huo);
            add(luoJiYuJv);
            add(luoJiYuJvPie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（逻辑语句'（子）.addr(i) = 逻辑语句'（父）.addr(i) || 逻辑语句.addr ）
                CreateCode.createCode("||",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //逻辑语句 -> 逻辑语句1{逻辑语句'.type(i) = 逻辑语句1.type}逻辑语句' {逻辑语句.type = 逻辑语句'.type(s)}
        if(ca.equals(createCA(luoJiYuJv,new ArrayList<MyChar>(){{
            add(luoJiYuJv1);
            add(luoJiYuJvPie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //逻辑语句1' -> ε {逻辑语句1' .type(s)=逻辑语句1' .type(i)}
        if(ca.equals(createCA(luoJiYuJv1Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //逻辑语句1' -> &&逻辑语句2{ 类型检查（逻辑语句2.type，逻辑语句1'（父）.type(i)）   ，逻辑语句1'（子）.type(i) = 逻辑语句2.type }逻辑语句1' {逻辑语句1'（父）.type(s) = 逻辑语句1'（子）.type(s)}
        if(ca.equals(createCA(luoJiYuJv1Pie,new ArrayList<MyChar>(){{
            add(yu);
            add(luoJiYuJv2);
            add(luoJiYuJv1Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));

                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（逻辑语句1'（子）.addr(i) = 逻辑语句2'（父）.addr(i) && 逻辑语句1.addr ）
                CreateCode.createCode("&&",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //逻辑语句1 -> 逻辑语句2{逻辑语句1'.type(i) = 逻辑语句2.type}逻辑语句1' {逻辑语句1.type = 逻辑语句1'.type(s)}
        if(ca.equals(createCA(luoJiYuJv1,new ArrayList<MyChar>(){{
            add(luoJiYuJv2);
            add(luoJiYuJv1Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //逻辑语句2 -> !逻辑语句2  {逻辑语句2(父).type = 逻辑语句2(子).type 类型检查(逻辑语句2(子).type)}
        if(ca.equals(createCA(luoJiYuJv2,new ArrayList<MyChar>(){{
            add(fei);
            add(luoJiYuJv2);
        }}))){
            if(n==-1) {
                typeCheck(father.getChilds().get(1).getAtt("value"),"bool");
                father.addAtt("value", father.getChilds().get(1).getAtt("value"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type"));
                father.addAtt("addr",MemAdm.createVar());//创建新的临时变量
                //gen（逻辑语句2(父).addr = ！逻辑语句2(子).addr ）
                CreateCode.createCode("!",
                        father.getChilds().get(1).getAtt("addr"),
                        "",
                        father.getAtt("addr"));
            }
        }
        //逻辑语句2 -> 逻辑语句3  {逻辑语句2.type = 逻辑语句3.type}
        if(ca.equals(createCA(luoJiYuJv2,new ArrayList<MyChar>(){{
            add(luoJiYuJv3);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //逻辑语句3 -> 关系语句 {逻辑语句3.type = 关系语句.type}
        if(ca.equals(createCA(luoJiYuJv3,new ArrayList<MyChar>(){{
            add(guanXiYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //关系语句' -> ε {关系语句' .type(s)=关系语句' .type(i)}
        if(ca.equals(createCA(guanXiYuJvPie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句' -> >关系语句1{ 类型检查（关系语句1.type，关系语句'（父）.type(i)）   ，关系语句'（子）.type(i) = 关系语句1.type }关系语句' {关系语句'（父）.type(s) = 关系语句'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJvPie,new ArrayList<MyChar>(){{
            add(daYu);
            add(guanXiYuJv1);
            add(guanXiYuJvPie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                // new(关系语句'（子）.addr(i))
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句'（子）.addr(i) = 关系语句'（父）.addr(i) > 关系语句1.addr ）
                CreateCode.createCode(">",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //关系语句 -> 关系语句1{关系语句'.type(i) = 关系语句1.type}关系语句' {关系语句.type = 关系语句'.type(s)}
        if(ca.equals(createCA(guanXiYuJv,new ArrayList<MyChar>(){{
            add(guanXiYuJv1);
            add(guanXiYuJvPie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句1' -> ε {关系语句1' .type(s)=关系语句1' .type(i)}
        if(ca.equals(createCA(guanXiYuJv1Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句1' -> <关系语句2{ 类型检查（关系语句2.type，关系语句1'（父）.type(i)）   ，关系语句1'（子）.type(i) = 关系语句2.type }关系语句1' {关系语句1'（父）.type(s) = 关系语句1'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJv1Pie,new ArrayList<MyChar>(){{
            add(xiaoYu);
            add(guanXiYuJv2);
            add(guanXiYuJv1Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句1'（子）.addr(i) = 关系语句1'（父）.addr(i) < 关系语句2.addr ）
                CreateCode.createCode("<",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //关系语句1 -> 关系语句2{关系语句1'.type(i) = 关系语句2.type}关系语句1' {关系语句1.type = 关系语句1'.type(s)}
        if(ca.equals(createCA(guanXiYuJv1,new ArrayList<MyChar>(){{
            add(guanXiYuJv2);
            add(guanXiYuJv1Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句2' -> ε {关系语句2' .type(s)=关系语句2' .type(i)}
        if(ca.equals(createCA(guanXiYuJv2Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句2' -> >=关系语句3{ 类型检查（关系语句3.type，关系语句2'（父）.type(i)）   ，关系语句2'（子）.type(i) = 关系语句3.type }关系语句2' {关系语句2'（父）.type(s) = 关系语句2'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJv2Pie,new ArrayList<MyChar>(){{
            add(daYuDengYu);
            add(guanXiYuJv3);
            add(guanXiYuJv2Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句2'（子）.addr(i) = 关系语句2'（父）.addr(i) >= 关系语句3.addr ）
                CreateCode.createCode(">=",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //关系语句2 -> 关系语句3{关系语句2'.type(i) = 关系语句3.type}关系语句2' {关系语句2.type = 关系语句2'.type(s)}
        if(ca.equals(createCA(guanXiYuJv2,new ArrayList<MyChar>(){{
            add(guanXiYuJv3);
            add(guanXiYuJv2Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句3' -> ε {关系语句3' .type(s)=关系语句3' .type(i)}
        if(ca.equals(createCA(guanXiYuJv3Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句3' -> <=关系语句4{ 类型检查（关系语句4.type，关系语句3'（父）.type(i)）   ，关系语句3'（子）.type(i) = 关系语句4.type } 关系语句3' {关系语句3'（父）.type(s) = 关系语句3'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJv3Pie,new ArrayList<MyChar>(){{
            add(xiaoYuDengYu);
            add(guanXiYuJv4);
            add(guanXiYuJv3Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句3'（子）.addr(i) = 关系语句3'（父）.addr(i) <= 关系语句4.addr ）
                CreateCode.createCode("<=",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //关系语句3 -> 关系语句4{关系语句3'.type(i) = 关系语句4.type}关系语句3' {关系语句3.type = 关系语句3'.type(s)}
        if(ca.equals(createCA(guanXiYuJv3,new ArrayList<MyChar>(){{
            add(guanXiYuJv4);
            add(guanXiYuJv3Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句4' -> ε {关系语句4' .type(s)=关系语句4' .type(i)}
        if(ca.equals(createCA(guanXiYuJv4Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句4' -> ==关系语句5{ 类型检查（关系语句5.type，关系语句4'（父）.type(i)）   ，关系语句4'（子）.type(i) = 关系语句5.type } 关系语句4' {关系语句4'（父）.type(s) = 关系语句4'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJv4Pie,new ArrayList<MyChar>(){{
            add(dengYu);
            add(guanXiYuJv5);
            add(guanXiYuJv4Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句4'（子）.addr(i) = 关系语句4'（父）.addr(i) == 关系语句5.addr ）
                CreateCode.createCode("==",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }

        //关系语句4 -> 关系语句5{关系语句4'.type(i) = 关系语句5.type} 关系语句4' {关系语句4.type = 关系语句4'.type(s)}
        if(ca.equals(createCA(guanXiYuJv4,new ArrayList<MyChar>(){{
            add(guanXiYuJv5);
            add(guanXiYuJv4Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句5' -> ε {关系语句5' .type(s)=关系语句5' .type(i)}
        if(ca.equals(createCA(guanXiYuJv5Pie,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1) {
                father.addAtt("value(s)", father.getAtt("value(i)"));
                father.addAtt("type(s)", father.getAtt("type(i)"));
                father.addAtt("addr(s)", father.getAtt("addr(i)"));
            }
        }
        //关系语句5' -> <>关系语句6 { 类型检查（关系语句6.type，关系语句5'（父）.type(i)）   ，关系语句5'（子）.type(i) = 关系语句6.type } 关系语句5' {关系语句5'（父）.type(s) = 关系语句5'（子）.type(s)}
        if(ca.equals(createCA(guanXiYuJv5Pie,new ArrayList<MyChar>(){{
            add(buDengYu);
            add(guanXiYuJv6);
            add(guanXiYuJv5Pie);
        }}))){
            if(n==2){
                typeCheck(father.getChilds().get(1).getAtt("type"),father.getAtt("type(i)"));
                node.addAtt("type(i)",father.getChilds().get(1).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(1).getAtt("value"));
                String addr = MemAdm.createVar();
                node.addAtt("addr(i)",addr);//创建新的临时变量
                //gen（关系语句5'（子）.addr(i) = 关系语句5'（父）.addr(i) <> 关系语句6.addr ）
                CreateCode.createCode("!=",
                        father.getAtt("addr(i)"),
                        father.getChilds().get(1).getAtt("addr"),
                        addr);
            }
            if(n==-1){
                father.addAtt("type(s)",father.getChilds().get(2).getAtt("type(s)"));
                father.addAtt("value(s)",father.getChilds().get(2).getAtt("value(s)"));
                father.addAtt("addr(s)",father.getChilds().get(2).getAtt("addr(s)"));
            }
        }
        //关系语句5 -> 关系语句6 {关系语句5'.type(i) = 关系语句6.type} 关系语句5' {关系语句5.type = 关系语句5'.type(s)}
        if(ca.equals(createCA(guanXiYuJv5,new ArrayList<MyChar>(){{
            add(guanXiYuJv6);
            add(guanXiYuJv5Pie);
        }}))){
            if(n==1){
                node.addAtt("type(i)",father.getChilds().get(0).getAtt("type"));
                node.addAtt("value(i)",father.getChilds().get(0).getAtt("value"));
                node.addAtt("addr(i)",father.getChilds().get(0).getAtt("addr"));
            }
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value(s)"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type(s)"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr(s)"));
            }
        }
        //关系语句6 -> (运算语句) {关系语句6.type = 运算语句.type}
        if(ca.equals(createCA(guanXiYuJv6,new ArrayList<MyChar>(){{
            add(zuoXiaoKuoHao);
            add(yunSuanYuJv);
            add(youXiaoKuoHao);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(1).getAtt("value"));
                father.addAtt("type", father.getChilds().get(1).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(1).getAtt("addr"));
            }

        }
        //关系语句6 -> 关系语句7 {关系语句6.type=关系语句7.type}
        if(ca.equals(createCA(guanXiYuJv6,new ArrayList<MyChar>(){{
            add(guanXiYuJv7);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //关系语句7 -> 调用语句  {关系语句7.type=调用语句.type}
        if(ca.equals(createCA(guanXiYuJv7,new ArrayList<MyChar>(){{
            add(chengXvDiaoYongYuJv);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getAtt("value"));
                father.addAtt("type", father.getChilds().get(0).getAtt("type"));
                father.addAtt("addr", father.getChilds().get(0).getAtt("addr"));
            }
        }
        //调用语句 -> 标识符 参数说明 {调用语句.type=标识符.type}
        if(ca.equals(createCA(chengXvDiaoYongYuJv,new ArrayList<MyChar>(){{
            add(biaoShiFu);
            add(canShuShuoMing);
        }}))){
            if(n==0){
                if(node.getToken().getId()!=28){
                    ErrorFlag = true;
                }
            }
            if(n==-1) {
                if(CharList.get(father.getChilds().get(0).getToken().getItem())!=null) {
                    father.addAtt("value",CharList.get(father.getChilds().get(0).getToken().getItem()).get("value"));
                    father.addAtt("type", CharList.get(father.getChilds().get(0).getToken().getItem()).get("type"));
                    //变量addr为变量名，在中间代码to汇编时才被分配为地址
                    father.addAtt("addr", father.getChilds().get(0).getToken().getItem());
                }else{
                    father.addAtt("value","0");
                    father.addAtt("type", "int");
                    father.addAtt("addr", "");
                    if(!father.getChilds().get(0).getToken().getItem().equals("put")
                            && !father.getChilds().get(0).getToken().getItem().equals("get") ) {
                        System.out.print("变量未定义 :");
                        System.out.println(father.getChilds().get(0).getToken().getItem());
                        throw new RuntimeException();
                    }
                }
            }
        }
        //调用语句 -> false {调用语句.type=bool}
        if(ca.equals(createCA(chengXvDiaoYongYuJv,new ArrayList<MyChar>(){{
            add(aFalse);
        }}))){
            if(n==-1) {
                father.addAtt("value", "false");
                father.addAtt("type", "bool");
                //常量addr为常量值
                father.addAtt("addr", "false");
            }
        }
        //调用语句 -> true  {调用语句.type=bool}
        if(ca.equals(createCA(chengXvDiaoYongYuJv,new ArrayList<MyChar>(){{
            add(aTrue);
        }}))){
            if(n==-1) {
                father.addAtt("value", "true");
                father.addAtt("type", "bool");
                //常量addr为常量值
                father.addAtt("addr", "true");
            }
        }
        //调用语句 -> 常量  {调用语句.type=int}
        if(ca.equals(createCA(chengXvDiaoYongYuJv,new ArrayList<MyChar>(){{
            add(changLiang);
        }}))){
            if(n==-1) {
                father.addAtt("value", father.getChilds().get(0).getToken().getItem());
                father.addAtt("type", "int");
                //常量addr为常量值
                father.addAtt("addr", father.getChilds().get(0).getToken().getItem());
            }
        }

        //循环语句 -> while {记录此时的行数为L’，即stack.push（L’）} (关系语句 { gen（j=，关系语句.addr，false，L ），list.push(L) } ) {语句表  {  gen（goto L‘，即L‘=stack.pop（））}  }  {回填L，L为此时的代码行数 ，即L=list.pop，然后对L赋值为此时的代码行数}
        if(ca.equals(createCA(xunHuanYuJv,new ArrayList<MyChar>(){{
            add(aWhile);
            add(zuoXiaoKuoHao);
            add(guanXiYuJv);
            add(youXiaoKuoHao);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
        }}))){
            if(n==1){
                //{记录此时的行数为L’，即stack.push（L’）}
                CreateCode.stack.push(CreateCode.getCodeLine());
            }
            if(n==3){
                //{ gen（j=，关系语句.addr，false，L ），list.push(L) }
                MidCode midCode = CreateCode.createCode("j=",
                        father.getChilds().get(2).getAtt("addr"),
                        "false",
                        "null");
                CreateCode.list.push(midCode);
            }
            if(n==6){
                //{  gen（goto L‘，即L‘=stack.pop（））}
                CreateCode.createCode("j",
                        "",
                        "",
                        String.valueOf(CreateCode.stack.pop()));
            }
            if(n==-1){
                //回填L，L为此时的代码行数 ，即L=list.pop，然后对L赋值为此时的代码行数
                MidCode midCode = CreateCode.list.pop();
                midCode.setD(String.valueOf(CreateCode.getCodeLine()));
            }
        }
        //分支语句 -> if(关系语句   { gen（j=，关系语句.addr，false，L ），list.push(L) }  ){ 语句表 } 分支语句1
        if(ca.equals(createCA(fenZhiYuJv,new ArrayList<MyChar>(){{
            add(anIf);
            add(zuoXiaoKuoHao);
            add(guanXiYuJv);
            add(youXiaoKuoHao);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
            add(fenZhiYuJv1);
        }}))){
            if(n==3){
                //{ gen（j=，关系语句.addr，false，L ），list.push(L) }
                //{ gen（j=，关系语句.addr，false，L ），list.push(L) }
                MidCode midCode = CreateCode.createCode("j=",
                        father.getChilds().get(2).getAtt("addr"),
                        "false",
                        "null");
                CreateCode.list.push(midCode);
            }
        }
        //分支语句1 -> else{语句表} { 回填L，L为此时的代码行数 ，即L=list.pop()，然后对L赋值为此时的代码行数 }
        if(ca.equals(createCA(fenZhiYuJv,new ArrayList<MyChar>(){{
            add(anElse);
            add(zuoDaKuoHao);
            add(yuJvBiao);
            add(youDaKuoHao);
        }}))){
            if(n==-1){
                //{ 回填L，L为此时的代码行数 ，即L=list.pop()，然后对L赋值为此时的代码行数 }
                MidCode midCode = CreateCode.list.pop();
                midCode.setD(String.valueOf(CreateCode.getCodeLine()));
            }
        }
        //分支语句1 -> ε  { 回填L，L为此时的代码行数 ，即L=list.pop()，然后对L赋值为此时的代码行数 }
        if(ca.equals(createCA(fenZhiYuJv1,new ArrayList<MyChar>(){{
            add(kong);
        }}))){
            if(n==-1){
                //{ 回填L，L为此时的代码行数 ，即L=list.pop()，然后对L赋值为此时的代码行数 }
                MidCode midCode = CreateCode.list.pop();
                midCode.setD(String.valueOf(CreateCode.getCodeLine()));
            }
        }


    }

    /**
     * 结尾语义处理
     * @param node
     * @param ca
     */
    public static void yuyiAny(CodeCreate.LTreeNode node, CA ca){
        yuyiAny(node,node,ca,-1);
    }

    /**
     * 类型检查
     * @param a
     * @param b
     * @return
     */
    public static boolean typeCheck(String a,String b){
        if(a.equals(b)){
            return true;
        }else{
            TypeErrorFlag = true;
            ErrorFlag = true;
            return false;
        }
    }
}


