package wordAny;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

//词法分析器主函数
public class wordAnalyse {

    /**
     * 读取文件，转为String字符串
     * @param filePath
     * @return
     */
    public String readFile(String filePath) {
        int len= 0;
        StringBuffer str= new StringBuffer("");
        File file= new File(filePath);
        try {
            FileInputStream is= new FileInputStream(file);
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader in= new BufferedReader(isr);
            String line= null;
            while ( (line=in.readLine())!=null) {
                if (len != 0)
                // 处理换行符的问题
                    {
                    str.append("\r\n" +line);
                }
                else {
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str.toString();
    }

    public static List<Token> runWordAnalyse(String filePath) {
        String text = new wordAnalyse().readFile(filePath);

        //预处理
        PreProgramm pre = new PreProgramm();
        String inText = pre.prePro(text);

        //词法分析
        DFA dfa = new DFA();
        boolean isSuccess = dfa.run(inText);
        if(isSuccess){
            //分析成功
            //获取并输出词法单元序列
            return dfa.getTokenList();
        }else{
            //分析失败
            //定位错误区间
            int Start = dfa.getErrorPeekStart();
            int End = dfa.getErrorPeekEnd();
            //获取错误词
            String ErrorWord = inText.substring(Start,End);
            //获取错误行数
            int LineStart = pre.getLineNum(inText,Start);
            System.out.println("词法分析错误");
            System.out.print("错误行数line = ");
            System.out.println(LineStart);
            System.out.print("错误的序列：");
            System.out.println(ErrorWord);
            return null;
        }
    }

    public static void main(String[ ]args){
        String text = new wordAnalyse().readFile("E:\\文件\\编译原理\\text.c");

        //预处理
        PreProgramm pre = new PreProgramm();
        String inText = pre.prePro(text);

        //词法分析
        DFA dfa = new DFA();
        boolean isSuccess = dfa.run(inText);
        if(isSuccess){
            //分析成功
            //获取并输出词法单元序列
            List<Token> tokens = dfa.getTokenList();
            dfa.printTokenList();
        }else{
            //分析失败
            //定位错误区间
            int Start = dfa.getErrorPeekStart();
            int End = dfa.getErrorPeekEnd();
            //获取错误词
            String ErrorWord = inText.substring(Start,End);
            //获取错误行数
            int LineStart = pre.getLineNum(inText,Start);
            System.out.println("词法分析错误");
            System.out.print("错误行数line = ");
            System.out.println(LineStart);
            System.out.print("错误的序列：");
            System.out.println(ErrorWord);
        }
    }
}
