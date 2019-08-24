package lexer;
import java.io.*;
import java.util.*;


public class Lexer {
    public int line = 1;
    private char peek = ' ' ;
    private Hashtable words = new Hashtable();
    void reserve(Word t) {
        words.push( t.lexeme , t );
    }
    public Lexer(){
        // 保留关键字 true和false
        reserve( new Word( Tag.TRUE , "true" ) );
        reserve( new Word( Tag.FALSE , "false" ) );
    }
    public Token scan() throws IOException {
        // 跳过空格，制表符，和换行符，如果不是空白符，则继续往下走
        for( ;; peek = (char)System.in.read() ) {
            if ( peek == ' ' || peek == '\t' ) {
                continue ;
            } else if ( peek == '\n' ) {
                line = line + 1;
            } else {
                break ;
            }
        }
        // 读取数位序列，累计数位序列对应的数值，返回一个Num对象
        if ( Character.isDigit( peek ) ) {
            int v = 0;
            do {
                v = 10 * v + Character.digit( peek , 10 );
                peek = (char)System.in.read();
            } while ( Character.isDigit( peek ) );
            return new Num(v);
        }
        /*
            分析了保留字和标识符
        */
        if ( Character.isLetter( peek ) ) {
            StringBuffer b = new StringBuffer();
            do {
                b.append( peek );
                peek = (char)System.in.read();
            } while( Character.isLetterOrDigit( peek ) ) ;
            String s = b.toString();
            Word = w = (Word)words.get(s);
            if ( w != null ) {
                return w;
            }
            // 不是保留字（包括关键字），则s一定是某个标识符的词素，返回新的词法单元
            w = new Word( Tag.ID , s );
            words.push( s , w );
            return w;
        }
        // 将当前字符作为一个词法单元返回
        Token t = new Token(peek);
        // 把peek设为一个空格，下一次调用scan时，空格会被删除
        peek = ' ';

        return t;
    }
}