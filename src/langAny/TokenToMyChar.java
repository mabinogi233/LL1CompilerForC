package langAny;

import wordAny.PreProgramm;
import wordAny.Token;

import java.util.ArrayList;
import java.util.List;

//将词法单元序列封装为终结符
public class TokenToMyChar {

    //换行符的peek位置
    public static List<Integer> enterLines = new ArrayList<>();
    //token首字符对应的字符数
    public static List<Integer> tokenInt = new ArrayList<>();

    /**
     * 将词法单元序列封装为终结符串
     * @param tokens
     * @return
     */
    public static List<MyChar> Token2MyChar(List<Token> tokens){
        List<MyChar> chars = new ArrayList<>();
        for(Token token:tokens){
            MyChar charx = createChar(token);
            if(charx!=null){
                chars.add(charx);
            }else{
                System.out.println("error");
            }
        }
        return chars;
    }

    private static MyChar createChar(Token token){
        if(token.getId()==0){
            // +
            return createMyChar("+");
        }else if(token.getId()==1){
            // -
            return createMyChar("-");
        } else if(token.getId()==5){
            // =
            return createMyChar("=");
        }else if(token.getId()==28){
            // 标识符
            return createMyChar("标识符");
        }else if(token.getId()==8){
            // &
            return createMyChar("&");
        }else if(token.getId()==9){
            // |
            return createMyChar("|");
        } else if(token.getId()==68){
            // !
            return createMyChar("!");
        }else if(token.getId()==19){
            // ||
            return createMyChar("||");
        }else if(token.getId()==18){
            // &&
            return createMyChar("&&");
        }else if(token.getId()==7){
            // >
            return createMyChar(">");
        }else if(token.getId()==6){
            // <
            return createMyChar("<");
        }else if(token.getId()==66){
            // >=
            return createMyChar(">=");
        }else if(token.getId()==67){
            // <=
            return createMyChar("<=");
        }else if(token.getId()==15){
            // ==
            return createMyChar("==");
        }else if(token.getId()==69){
            // !=
            return createMyChar("<>");
        }else if(token.getId()==20){
            // (
            return createMyChar("(");
        }else if(token.getId()==21){
            // )
            return createMyChar(")");
        }else if(token.getId()==22){
            // {
            return createMyChar("{");
        }else if(token.getId()==23){
            // }
            return createMyChar("}");
        }else if(token.getId()==70){
            // true
            return createMyChar("true");
        }else if(token.getId()==71){
            // false
            return createMyChar("false");
        }else if(token.getId()==29||token.getId()==30||token.getId()==31||token.getId()==32||token.getId()==33){
            // 常量
            return createMyChar("常量");
        } else if(token.getId()==51){
            // if
            return createMyChar("if");
        }else if(token.getId()==52){
            // else
            return createMyChar("else");
        }else if(token.getId()==48){
            // while
            return createMyChar("while");
        }else if(token.getId()==72){
            // ,
            return createMyChar(",");
        }else if(token.getId()==26){
            // ;
            return createMyChar(";");
        }else if(token.getId()==38){
            // int
            return createMyChar("int");
        }else if(token.getId()==73){
            // bool
            return createMyChar("bool");
        }else{
            return null;
        }
    }

    /**
     * 创建文法符号
     * @param C
     * @return
     */
    public static MyChar createMyChar(String C) {
        MyChar x = new MyChar();
        x.setC(C);
        x.setEndChar(true);
        return x;
    }

    /**
     * 返回第i个词法单元的行数
     * @param index
     * @return
     */
    public static int getErrorLineNumber(int index,List<Token> tokens){
        int peek = tokenInt.get(index);
        int lineNum = 1;
        for(int i:enterLines){
            if(i<=peek){
                lineNum++;
            }else{
                break;
            }
        }
        return lineNum;
    }
}
