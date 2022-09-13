package langAny;

import java.util.List;

//一条产生式 start->end
public class CA{
    private MyChar start;
    private List<MyChar> end;

    public List<MyChar> getEnd() {
        return end;
    }

    public MyChar getStart() {
        return start;
    }

    public void setEnd(List<MyChar> end) {
        this.end = end;
    }

    public void setStart(MyChar start) {
        this.start = start;
    }

    public void print(){
        System.out.print(getStart().getC());
        System.out.print(" -> ");
        for(MyChar c:getEnd()){
            System.out.print(c.getC());
        }
        System.out.println(" ");
    }
    public void printT(){
        System.out.print(getStart().getC());
        System.out.print(" -> ");
        for(MyChar c:getEnd()){
            System.out.print(c.getC());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CA){
            CA objj = (CA) obj;
            if(objj.getStart().equals(this.getStart())){
                if(this.getEnd().size()!=objj.getEnd().size()){
                    return false;
                }else{
                    for(int i=0;i<objj.getEnd().size();i++){
                        if(!objj.getEnd().get(i).equals(this.getEnd().get(i))){
                            return false;
                        }
                    }
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
