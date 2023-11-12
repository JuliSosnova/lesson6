import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
/** Данная программа фильтрует комментарии и выводит их в файл result.txt
 * */
public class Main implements BlackListFilter{
    /** метод readerBlackList() читает файл с черным списком и возвращает его в виде множества
     * */
    public static Set<String> readerBlackList() throws FileNotFoundException {
        File file= new File("BlackList.txt");
        Scanner in1 = new Scanner(file);
        Set<String> blackList = new HashSet<>();
        while(in1.hasNext()){
            String line=in1.next();
            blackList.add(line);
        }
        return blackList;
    }
    /** данный метод разбивает строку комментария, которую подали на вход, на слова и преобразует ее в список
     * @param line
     *             строка комментария
     * @return comments
     * */
    public static List<String> readerComments(String line)  {
        line=line.toLowerCase();
        String[] wordsArray = line.split("\\s+");
        List<String> comments = new ArrayList<>(Arrays.asList(wordsArray));
        System.out.println(comments);
        return comments;
    }
    /** данный метод отпределяет является ли комментарий "хорошим" (не содержит слов из черного списка)
     * @param comments
     *             один комментарий, который преобразован в список
     * @param blackList
     *              черный список в виде множества
     * @return ok
     * */
    public static boolean filterComments(List<String> comments, Set<String> blackList){
        boolean ok=true;
        for(String word : blackList){
            if (comments.contains(word)) {
                ok=false;
                break;
            }
        }
        return ok;
    }
    public static void main(String[] args) throws FileNotFoundException{
        String s="Comments.txt";
        File file= new File(s);
        Scanner in1 = new Scanner(file);
        try (OutputStream out = new FileOutputStream("result.txt", true)) {
            while(in1.hasNext()){
                String line=in1.nextLine()+"\n";
                if(filterComments(readerComments(line),readerBlackList())){
                    out.write(line.getBytes(Charset.forName("UTF-8")));
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
