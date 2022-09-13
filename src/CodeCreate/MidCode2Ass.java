package CodeCreate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

//中间代码转汇编语言
public class MidCode2Ass {

    //变量空间基地址寄存器为$s0

    /**
     * 中间代码转汇编语言
     * @param codes
     * @return
     */
    public static String Code2Ass(List<MidCode> codes){
        //寄存器使用情况 编号$t1~$t8
        int[] regfile = new int[8];
        for(int i=0;i<8;i++){
            regfile[i] = 0;
        }
        //变量-寄存器分配表
        Map<String,Integer> map = new HashMap<>();

        //指令偏移
        int offset = 0;

        //指令
        StringBuilder s = new StringBuilder();
        for(int i=0;i<codes.size();i++){
            //写时间
            int sum = 0;
            for(int j=0;j<8;j++){
                sum += regfile[j];
            }
            if(sum>4){
                //写内存
                for(int j=0;j<8;j++){
                    if(regfile[j]==1){
                        for(Map.Entry<String,Integer> m:map.entrySet()){
                            if(m.getValue()==j){
                                //存在使用寄存器j的变量
                                s = MidCode2Ass.OPoffset(s,1,i,codes);

                                s.append("sw $t");
                                s.append(j);
                                s.append(",");
                                s.append(MemAdm.getMemAddr(m.getKey()));
                                s.append("($s0)");
                                s.append("\n");
                                break;
                            }
                        }
                        regfile[j]=0;
                    }
                }
            }

            MidCode midCode = codes.get(i);
            if(MemAdm.map.get(midCode.getB())!=null && map.get(midCode.getB())==null){
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }

                //分配寄存器
                map.put(midCode.getB(),RegNum);

                s = MidCode2Ass.OPoffset(s,1,i,codes);
                offset+=1;
                s.append("lw $t");
                s.append(RegNum);
                s.append(",");
                s.append(MemAdm.getMemAddr(midCode.getB()));
                s.append("($s0)");
                s.append("\n");


            }
            if(MemAdm.map.get(midCode.getC())!=null && map.get(midCode.getC())==null ){
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }
                //分配寄存器
                map.put(midCode.getC(),RegNum);

                s = MidCode2Ass.OPoffset(s,1,i,codes);
                offset+=1;
                s.append("lw $t");
                s.append(RegNum);
                s.append(",");
                s.append(MemAdm.getMemAddr(midCode.getC()));
                s.append("($s0)");
                s.append("\n");
            }
            if(MemAdm.map.get(midCode.getD())!=null && map.get(midCode.getD())==null){
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }
                //分配寄存器
                map.put(midCode.getD(),RegNum);

            }

            if(CharList.get(midCode.getB())!=null && map.get(midCode.getB())==null){
                MemAdm.createVar(midCode.getB());
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }
                //分配寄存器
                map.put(midCode.getB(),RegNum);

                s = MidCode2Ass.OPoffset(s,1,i,codes);
                offset+=1;
                s.append("lw $t");
                s.append(RegNum);
                s.append(",");
                s.append(MemAdm.getMemAddr(midCode.getB()));
                s.append("($s0)");
                s.append("\n");
            }
            if(CharList.get(midCode.getC())!=null && map.get(midCode.getC())==null){
                MemAdm.createVar(midCode.getC());
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }
                //分配寄存器
                map.put(midCode.getC(),RegNum);

                s = MidCode2Ass.OPoffset(s,1,i,codes);
                offset+=1;
                s.append("lw $t");
                s.append(RegNum);
                s.append(",");
                s.append(MemAdm.getMemAddr(midCode.getC()));
                s.append("($s0)");
                s.append("\n");
            }
            if(CharList.get(midCode.getD())!=null && map.get(midCode.getD())==null){
                MemAdm.createVar(midCode.getD());
                int RegNum = 0;
                for(int j=0;j<8;j++){
                    if(regfile[j]==0){
                        regfile[j]=1;
                        RegNum = j;
                        break;
                    }
                }
                //分配寄存器
                map.put(midCode.getD(),RegNum);

            }

            if(midCode.getOp().equals("=")){
                if(MemAdm.map.get(midCode.getB())==null){
                    //立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB()));
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("add ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("+")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数相加
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())+Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("add ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("-")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())-Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    offset+=1;
                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("sub ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("subi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("sub ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("&")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())&Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("andi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("andi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("and ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("|")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())|Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("ori ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("ori ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("or ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("&&")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())&Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("andi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("andi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("and ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("||")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    //等价于立即数赋值语句
                    int regNum = map.get(midCode.getD());
                    s.append("addi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(Integer.parseInt(midCode.getB())|Integer.parseInt(midCode.getC()));
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("ori ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("ori ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("or ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("!")){
                if(MemAdm.map.get(midCode.getB())==null){
                    //立即数
                    int regNum = map.get(midCode.getD());
                    s.append("nori ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero,");
                    s.append(midCode.getB());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("nor ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");
                }
            }else if(midCode.getOp().equals(">")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    int regNum = map.get(midCode.getD());
                    offset+=1;
                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("sgti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("slti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("sgti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("sgt ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("<")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    int regNum = map.get(midCode.getD());
                    offset+=1;
                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("slti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("sgti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("slti ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("slt ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals(">=")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    int regNum = map.get(midCode.getD());
                    offset+=1;
                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("sgei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("slei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("sgei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("sge ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("<=")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    int regNum = map.get(midCode.getD());
                    offset+=1;
                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("slei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("sgei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("slei ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("sle ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("==")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);

                    int regNum = map.get(midCode.getD());
                    offset+=1;

                    //拆分为两条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");
                }else {
                    //两个变量
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("cmp ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("!=")){
                if(MemAdm.map.get(midCode.getB())==null && MemAdm.map.get(midCode.getC())==null){
                    //两个立即数
                    s = MidCode2Ass.OPoffset(s,2,i,codes);
                    int regNum = map.get(midCode.getD());
                    offset+=2;
                    //拆分为三条指令
                    int newRegNum = 0;
                    for(int j=0;j<8;j++){
                        if(regfile[j]==0){
                            regfile[j]=1;
                            newRegNum = j;
                            break;
                        }
                    }
                    s.append("addi ");
                    s.append("$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append("%zero,");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",$t");
                    s.append(newRegNum);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");

                    s.append("nor ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");


                }else if(MemAdm.map.get(midCode.getB())==null){
                    //B为立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    offset += 1;
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getC());
                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getB());
                    s.append("\n");

                    s.append("nor ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");
                }else if(MemAdm.map.get(midCode.getC())==null){
                    //C为立即数
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    offset += 1;
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    s.append("cmpi ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append(midCode.getC());
                    s.append("\n");

                    s.append("nor ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");
                }else {
                    //两个变量
                    s = MidCode2Ass.OPoffset(s,1,i,codes);
                    offset+=1;
                    int regNum = map.get(midCode.getD());
                    int regNum2 = map.get(midCode.getB());
                    int regNum3 = map.get(midCode.getC());
                    s.append("cmp ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum2);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum3);
                    s.append("\n");

                    s.append("nor ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero");
                    s.append("\n");
                }
            }else if(midCode.getOp().equals("j=")){
                if(MemAdm.map.get(midCode.getB())==null){
                    //B为常量
                    if(midCode.getB().equals("true")){
                        s.append("beqi ");
                        s.append("$zero");
                        s.append(",");
                        s.append(1);
                        s.append(",");
                        s.append(Integer.parseInt(midCode.getD())-i);
                        s.append("\n");
                    }else if(midCode.getB().equals("false")){
                        s.append("beqi ");
                        s.append("$zero");
                        s.append(",");
                        s.append(0);
                        s.append(",");
                        s.append(Integer.parseInt(midCode.getD())-i);
                        s.append("\n");
                    }
                }else{
                    //变量
                    int regNum = map.get(midCode.getB());
                    s.append("beq ");
                    s.append("$t");
                    s.append(regNum);
                    s.append(",");
                    s.append("$zero");
                    s.append(",");
                    s.append(Integer.parseInt(midCode.getD())-i);
                    s.append("\n");
                }

            }else if(midCode.getOp().equals("j")){
                s.append("j ");
                s.append(Integer.parseInt(midCode.getD())+offset);
                s.append("\n");
            }

            if(i==codes.size()-1){
                //写内存
                for(int j=0;j<8;j++){
                    if(regfile[j]==1){
                        for(Map.Entry<String,Integer> m:map.entrySet()){
                            if(m.getValue()==j){
                                //存在使用寄存器j的变量
                                s = MidCode2Ass.OPoffset(s,1,i,codes);

                                s.append("sw $t");
                                s.append(j);
                                s.append(",");
                                s.append(MemAdm.getMemAddr(m.getKey()));
                                s.append("($s0)");
                                s.append("\n");
                                break;
                            }
                        }
                        regfile[j]=0;
                    }
                }
            }
        }



        return s.toString();
    }

    //处理指令数目变多带来的偏移量不对齐问题
 private static StringBuilder OPoffset(StringBuilder s,int offset,int index,List<MidCode> midCodes){

        if(s.toString().length()>0) {
            String[] codes = s.toString().split("\n");
            StringBuilder ss = new StringBuilder();
            for (int i = 0; i < codes.length; i++) {
                String code = codes[i];
                if (code.substring(0, 3).equals("beq")) {
                    //beq指令
                    int off = Integer.parseInt(code.split(",")[2]) + i;
                    if (off > codes.length) {
                        //需要修改
                        ss.append(code.split(",")[0]);
                        ss.append(",");
                        ss.append(code.split(",")[1]);
                        ss.append(",");
                        ss.append(Integer.parseInt(code.split(",")[2]) + offset);
                        ss.append("\n");
                    } else {
                        ss.append(code);
                        ss.append("\n");
                    }
                }else {
                    ss.append(code);
                    ss.append("\n");
                }
            }
            for(int k = index+1;k<midCodes.size();k++){
                if(midCodes.get(k).getOp().equals("j")){
                    if(Integer.parseInt(midCodes.get(k).getD()) > codes.length){
                        //修正,加offset
                        int of = Integer.parseInt(midCodes.get(k).getD()) + offset;
                        midCodes.get(k).setD(String.valueOf(of));
                    }
                }
            }
            return ss;
        }else{
            for(int k = index+1;k<midCodes.size();k++){
                if(midCodes.get(k).getOp().equals("j")){
                    if(Integer.parseInt(midCodes.get(k).getD()) > 0){
                        //修正,加offset
                        int of = Integer.parseInt(midCodes.get(k).getD()) + offset;
                        midCodes.get(k).setD(String.valueOf(of));
                    }
                }
            }
            return new StringBuilder();
        }
    }
}
