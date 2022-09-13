package wordAny;

//判断关键字
public class KeyWord {
    /**
     * 判断是否为关键字，是则返回关键字id，不是则返回-1
     * @param token
     * @return
     */
    public static int isKeyWord(String token){
       /*
        * 34 char
        * 35 double
        * 36 float
        * 37 enum
        * 38 int
        * 39 long
        * 40 short
        * 41 signed
        * 42 struct
        * 43 union
        * 44 unsigned
        * 45 void
        * 46 for
        * 47 do
        * 48 while
        * 49 break
        * 50 continue
        * 51 if
        * 52 else
        * 53 goto
        * 54 switch
        * 55 case
        * 56 default
        * 57 return
        * 58 auto
        * 59 extern
        * 60 register
        * 61 static
        * 62 const
        * 63 sizeof
        * 64 typedef
        * 65 volatile
        */
        if(token.equals("char")){
            return 34;
        }else if(token.equals("double")){
            return 35;
        } else if(token.equals("float")){
            return 36;
        }else if(token.equals("enum")){
            return 37;
        }else if(token.equals("int")){
            return 38;
        }else if(token.equals("long")){
            return 39;
        }else if(token.equals("short")){
            return 40;
        }else if(token.equals("signed")){
            return 41;
        }else if(token.equals("struct")){
            return 42;
        }else if(token.equals("union")){
            return 43;
        }else if(token.equals("unsigned")){
            return 44;
        }else if(token.equals("void")){
            return 45;
        }else if(token.equals("for")){
            return 46;
        }else if(token.equals("do")){
            return 47;
        }else if(token.equals("while")){
            return 48;
        }else if(token.equals("break")){
            return 49;
        }else if(token.equals("continue")){
            return 50;
        }else if(token.equals("if")){
            return 51;
        }else if(token.equals("else")){
            return 52;
        }else if(token.equals("goto")){
            return 53;
        }else if(token.equals("switch")){
            return 54;
        }else if(token.equals("case")){
            return 55;
        }else if(token.equals("default")){
            return 56;
        }else if(token.equals("return")){
            return 57;
        }else if(token.equals("auto")){
            return 58;
        }else if(token.equals("extern")){
            return 59;
        }else if(token.equals("register")){
            return 60;
        }else if(token.equals("static")){
            return 61;
        }else if(token.equals("const")){
            return 62;
        }else if(token.equals("sizeof")){
            return 63;
        }else if(token.equals("typedef")){
            return 64;
        }else if(token.equals("volatile")){
            return 65;
        }else if(token.equals("true")){
            return 70;
        }else if(token.equals("false")){
            return 71;
        }else if(token.equals("bool")){
            return 73;
        }else{
            return -1;
        }
    }


}
