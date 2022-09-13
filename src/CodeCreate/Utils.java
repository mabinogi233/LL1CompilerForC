package CodeCreate;

import langAny.CA;
import langAny.MyChar;
import langAny.TokenToMyChar;
import wordAny.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//翻译语法树工具类
public class Utils{
    //规则指示器
    private static int i = 0;

    //token指示器
    private static int token_i = 0;

    //error
    public static boolean errorFlag = false;
    public static boolean typeErrorFlag = false;
    public static boolean variErrorflag = false;

    //异常发生位置
    public static int ErrorTokenId;

    /**
     * 根据最左推导生成语法树
     * @param leftT
     * @return
     */
    public static LTreeNode createTreeByLeftT(List<CA> leftT, List<Token> tokens){
        try {
            errorFlag = false;
            typeErrorFlag = false;
            variErrorflag = false;
            //构建语法树
            CA fa = leftT.get(0);
            LTreeNode root = new LTreeNode();
            root.setItem(fa.getStart());
            i = 0;
            token_i = 0;
            createTree(root, leftT, tokens);
            return root;
        }catch (RuntimeException e){
            System.out.println("变量未定义");
            variErrorflag = true;
            return null;
        }catch (Exception e){
            errorFlag = true;
            return null;
        }
    }

    /**
     * 输出异常信息
     * @param tokens
     */
    public static void printError(List<Token> tokens){
        System.out.println("类型检查失败");
        if(typeErrorFlag || YuYiCa.TypeErrorFlag){
            //类型异常
            System.out.print("首次出错行数：");
            System.out.println(TokenToMyChar.getErrorLineNumber(token_i-1, tokens));
            System.out.println("类型不匹配");
        } else if(errorFlag){

            //特殊类型异常
            try {
                if (tokens.get(token_i - 1).getId() == 0 || tokens.get(token_i - 1).getId() == 1) {
                    System.out.println("缺失操作数");
                }else {
                    boolean zuoKuoHao = false;
                    for(int i=0;i<token_i;i++){
                        if(tokens.get(i).getId()==20){
                            zuoKuoHao = true;
                        }
                        if(tokens.get(i).getId()==21){
                            zuoKuoHao = false;
                        }
                    }
                    if(zuoKuoHao){
                        System.out.println("缺失右小括号");
                    }else if(token_i!=tokens.size()){
                        System.out.println("缺失运算符");
                    }
                }
            }catch (Exception e){
                System.out.println("发生未知异常");
            }
        }
    }

    /**
     * 根据第i条规则添加node的子节点
     * @param node
     * @param leftT
     * @param tokens 词法单元序列
     */
    private static void createTree(CodeCreate.LTreeNode node, List<CA> leftT, List<Token> tokens){
        //使用第i条生成式构建树
        CA ca = leftT.get(i);

        List<MyChar> chars = ca.getEnd();

        //第j个子节点
        int j = 0;
        for(MyChar c:chars){

            LTreeNode node1 = new LTreeNode();
            //设置属性
            node1.setItem(c);
            //终结符,非空,设置token
            if(!c.isE()&&c.isEndChar()){
                node1.setToken(tokens.get(token_i));
                token_i++;
            }
            //判断此结点之前有无语义规则
            //若有则设置

            YuYiCa.yuyiAny(node,node1,ca,j);
            if(YuYiCa.ErrorFlag){
                //error，类型检验报错
                errorFlag = true;
                if(YuYiCa.TypeErrorFlag) {
                    typeErrorFlag = true;
                }
            }
            //设置子节点
            if(!c.isEndChar()){
                //非终结符,使用下一条规则生成子树
                i++;
                createTree(node1,leftT,tokens);
            }
            //加入node的子节点
            node.addChild(node1);
            //处理下一个子节点
            j++;
        }
        //结尾判断是否执行语义
        YuYiCa.yuyiAny(node,ca);
        if(YuYiCa.ErrorFlag){
            //error，类型检验报错
            errorFlag = true;
            if(YuYiCa.TypeErrorFlag) {
                typeErrorFlag = true;
            }
        }
    }

    /**
     * 输出语法树
     * @param root
     */
    public static void printTree(CodeCreate.LTreeNode root){
        printTreePath path = new printTreePath();
        path.setPath(new ArrayList<>());
        printTree(root,0,path);
    }



    private static void printTree(CodeCreate.LTreeNode root, int length, printTreePath path){
        if(root.getChilds()==null || root.getChilds().size()==0){
            //终结符
            //输出path
            for(int i =0;i<path.getStartNodeLength();i++){
                //8个空格,格式对齐
                System.out.print("                                                                                  ");
                System.out.print("----");

            }
            for(int i = 0;i < path.getPath().size();i++) {
                String outLine = path.getPath().get(i).getItem().getC();
                String type = path.getPath().get(i).getAtt("type");
                String value = path.getPath().get(i).getAtt("value");
                String valueI = path.getPath().get(i).getAtt("value(i)");
                String typeI = path.getPath().get(i).getAtt("type(i)");
                String valueS = path.getPath().get(i).getAtt("value(s)");
                String typeS = path.getPath().get(i).getAtt("type(s)");
                System.out.print(outLine);
                System.out.print("type:");
                System.out.print(type);
                System.out.print(" type(i):");
                System.out.print(typeI);
                System.out.print(" type(s):");
                System.out.print(typeS);
                System.out.print(" value:");
                System.out.print(value);
                System.out.print(" value(i):");
                System.out.print(valueI);
                System.out.print(" value(s):");
                System.out.print(valueS);
                for (int j = 0; j < 30 - outLine.length() - type.length() - value.length()
                        - typeS.length() - valueS.length()- typeI.length() - valueI.length(); j++) {
                    System.out.print(" ");
                }


                if (i != path.getPath().size() - 1) {
                    System.out.print("----");
                }
            }
            if(!root.getItem().isE()){
                System.out.print("( token = < ");
                System.out.print(root.getToken().getId());
                System.out.print(" , ");
                System.out.print(root.getToken().getItem());
                System.out.print(" , ");
                System.out.print(root.getToken().getText());
                System.out.print("> )");
            }
            System.out.println("");
            //清空路径
            path.getPath().clear();
        }else{
            if(length==0){
                //根节点
                path.getPath().add(root);
                path.setStartNodeLength(length);
            }
            for(LTreeNode child:root.getChilds()){
                if(path.getPath().size()==0){
                    path.setStartNodeLength(length+1);
                }
                path.getPath().add(child);
                printTree(child,length+1,path);
            }
        }
    }
}

//语法树节点
class LTreeNode {
    //信息
    private MyChar item;

    //属性
    private Map<String,String> attribute = new HashMap<>();

    public LTreeNode(){
        addAtt("type","");
        addAtt("type(s)","");
        addAtt("type(i)","");
        addAtt("value","0");
        addAtt("value(s)","0");
        addAtt("value(i)","0");
        addAtt("addr","");
        addAtt("addr(s)","");
        addAtt("addr(i)","");
    }

    /**
     * 添加属性
     * @param key
     * @param value
     */
    public void addAtt(String key,String value){
        if(attribute.get(key)==null) {
            attribute.put(key, value);
        }else{
            attribute.replace(key, value);
        }
    }

    /**
     * 获取属性
     * @param key
     * @return
     */
    public String getAtt(String key){
        return attribute.get(key);
    }

    //非空字符叶子节点存入token
    private Token token;

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    //子节点
    private List<LTreeNode> childs = new ArrayList<>();

    public void setItem(MyChar item) {
        this.item = item;
    }

    public MyChar getItem() {
        return item;
    }

    /**
     * 加入子节点
     * @param node
     */
    public void addChild(LTreeNode node){
        childs.add(node);
    }

    /**
     * 获取全部子节点
     * @return
     */
    public List<LTreeNode> getChilds() {
        return childs;
    }
}


//用于输出语法树
class printTreePath{
    private List<LTreeNode> path;
    //首节点深度
    private int startNodeLength;


    public int getStartNodeLength() {
        return startNodeLength;
    }

    public List<LTreeNode> getPath() {
        return path;
    }

    public void setPath(List<LTreeNode> path) {
        this.path = path;
    }

    public void setStartNodeLength(int startNodeLength) {
        this.startNodeLength = startNodeLength;
    }
}
