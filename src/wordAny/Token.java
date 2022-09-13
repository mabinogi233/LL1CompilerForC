package wordAny;

//对应一个词法单元
public class Token {
    //词法单元编号
    private int id;
    //属性
    private String item;
    //具体文字描述
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }
}
