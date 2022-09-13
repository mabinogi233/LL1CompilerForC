package langAny;

import wordAny.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//语法树工具类
public class LTree{
    //规则指示器
    private static int i = 0;

    //token指示器
    private static int token_i = 0;

    /**
     * 根据最左推导生成语法树
     * @param leftT
     * @return
     */
    public static LTreeNode createTreeByLeftT(List<CA> leftT,List<Token> tokens){
        //构建语法树
        CA fa = leftT.get(0);
        LTreeNode root = new LTreeNode();
        root.setItem(fa.getStart());
        i = 0;
        token_i = 0;
        createTree(root,leftT,tokens);
        return root;
    }

    /**
     * 根据第i条规则添加node的子节点
     * @param node
     * @param leftT
     * @param tokens 词法单元序列
     */
    private static void createTree(LTreeNode node,List<CA> leftT,List<Token> tokens){
        //使用第i条生成式构建树
        CA ca = leftT.get(i);
        List<MyChar> chars = ca.getEnd();
        for(MyChar c:chars){
            LTreeNode node1 = new LTreeNode();
            //设置属性
            node1.setItem(c);
            //设置子节点
            if(!c.isEndChar()){
                //非终结符,使用下一条规则生成子树
                i++;
                createTree(node1,leftT,tokens);
            }else{
                //终结符,非空
                if(!c.isE()){
                    node1.setToken(tokens.get(token_i));
                    token_i++;
                }
            }
            //加入node的子节点
            node.addChild(node1);
        }
    }

    /**
     * 输出语法树
     * @param root
     */
    public static void printTree(LTreeNode root){
        printTreePath path = new printTreePath();
        path.setPath(new ArrayList<>());
        printTree(root,0,path);
    }


    private static void printTree(LTreeNode root,int length,printTreePath path){
        if(root.getChilds()==null || root.getChilds().size()==0){
            //终结符
            //输出path
            for(int i =0;i<path.getStartNodeLength();i++){
                //8个空格,格式对齐
                System.out.print("        ");
                System.out.print("----");

            }
            for(int i = 0;i < path.getPath().size();i++){
                String outLine = path.getPath().get(i).getItem().getC();
                System.out.print(outLine);
                for(int j=0;j<6 - outLine.length();j++) {
                    System.out.print(" ");
                }
                if(i!=path.getPath().size()-1) {
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

    LTreeNode(){
        attribute.put("type","");
        attribute.put("value","");
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
