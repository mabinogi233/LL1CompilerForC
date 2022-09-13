package wordAny;

import langAny.TokenToMyChar;

import java.util.ArrayList;
import java.util.List;

//预处理，清除多余的空格，两个串之间最多含有一个空格
//还需要记录每个换行符的位置，并剔除换行符
public class PreProgramm {
    //换行符的peek位置
    public List<Integer> enterLines = new ArrayList<>();

    /**
     * 编译预处理第一步，清除多余的空格，两个串之间最多含有一个空格，并保证\n后无空格
     * @param text
     * @return
     */
    private String passKongGe(String text){
        StringBuilder s = new StringBuilder();
        char[] chars = text.replace('\r',' ').replace('\t',' ').toCharArray();
        boolean isKongGe = false;
        for(int i=0;i<chars.length;i++){
            if(chars[i]!=' '){
                s.append(chars[i]);
                isKongGe = false;
            }else{
                //对于空格，设置一个标志位，表示其前面一个字符是否为空格
                if(!isKongGe){
                    s.append(chars[i]);
                }
                isKongGe = true;
            }
        }
        //删除结尾的空格
        char[] newChars = s.toString().strip().toCharArray();
        StringBuilder ss = new StringBuilder();
        boolean flag = false;
        for(int i=0;i<newChars.length;i++){
            if(newChars[i]=='\n'){
                if(i!=newChars.length-1){
                    if(newChars[i+1]==' '){
                        flag = true;
                    }else{
                        flag = false;
                    }
                }else{
                    //\n出现于末尾
                    flag = false;
                }
                ss.append('\n');
            }else{
                if(!flag) {
                    ss.append(newChars[i]);
                    flag = false;
                }else{
                    flag = false;
                }
            }
        }
        return ss.toString().strip();
    }

    /**
     * 编译预处理第二步，删除\n，并记录\n出现的位置
     * 并向句子末尾加入#，表示eof
     * @param text
     * @return
     */
    private String getOffEnter(String text){
        StringBuilder s = new StringBuilder();
        char[] chars = text.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(chars[i]!='\n'){
                s.append(chars[i]);

            }else{
                //对于换行符
                //记录换行符的位置i,即新字符串的len(s)位置应为一个\n
                int enterI = s.length();
                //保存换行符的位置
                enterLines.add(enterI);
            }
        }
        s.append('#');
        //语法分析待使用
        TokenToMyChar.enterLines = enterLines;
        return s.toString();
    }

    /**
     * 获取text[peek]字符对应的行数
     * @param text
     * @param peek
     * @return
     */
    public int getLineNum(String text,int peek){
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

    /**
     * 预处理
     * @param text
     * @return
     */
    public String prePro(String text){
        //System.out.println(text);

        return this.getOffEnter(this.passKongGe(text));

    }

}
