package langAny;

//产生式字符
public class MyChar {
    //产生式符号
    private String c;
    //是否为终结符
    private boolean isEndChar;



    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setEndChar(boolean endChar) {
        isEndChar = endChar;
    }

    public boolean isEndChar() {
        return isEndChar;
    }

    /**
     * 是否为ε
     * @return
     */
    public boolean isE(){
        return c.equals("ε");
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;
        if(obj instanceof MyChar){
            MyChar c = (MyChar)obj;
            flag = this.getC().equals(c.getC());
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return c.hashCode();
    }
}
