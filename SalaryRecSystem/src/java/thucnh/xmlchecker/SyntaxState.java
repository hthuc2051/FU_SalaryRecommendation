/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.xmlchecker;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
public class SyntaxState {
    
    public static final String CONTENT = "content";
    public static final String OPEN_BRACKET = "openBracket";
    public static final String OPEN_TAG_NAME = "openTagName";
    public static final String TAG_INNER = "tagInner";
    public static final String ATTR_NAME = "attributeName";
    public static final String EQUAL_WAIT = "equalWait";
    public static final String EQUAL = "equal";
    public static final String VALUE_WAIT = "valueWait";
    public static final String ATTR_VALUE_NQ = "nonQoutedAttributeValue";
    public static final String ATTR_VALUE_Q = "qoutedAttributeValue";
    public static final String EMPTY_SLASH = "emptyTagSlash";
    public static final String CLOSE_BRACKET = "closeBracket";
    public static final String CLOSE_TAG_SLASH = "closeTagSlash";
    public static final String CLOSE_TAG_NAME = "closeTagName";
    public static final String WAIT_END_TAG_CLOSE = "waitEndTagClose";
    public static final String PROCESS_INSTRUCTION = "processInstruction";
    
    public static final char LT = '<';
    public static final char SLASH = '/';
    public static final char GT = '>';
    public static final char EQ = '=';
    public static final char D_QOUT = '"';
    public static final char S_QOUT = '\'';
    public static final char QUESTION_MARK = '?';
    public static final char NEWL_LINE = '\n' ;
    
    public static final char UNDERSCORE = '_';
    public static final char COLON = ':';
    public static final char HYPHEN = '-';
    public static final char PERIOD = '.';
    
    // check kí tự bắt đầu
    private static boolean isStartChar(char c) {
        return Character.isLetter(c) || c == UNDERSCORE || c == COLON;
    }
    
    // check tên thẻ và tên attribute
    private static boolean isNameChar(char c) {
        return Character.isLetter(c) || UNDERSCORE == c || HYPHEN == c || PERIOD == c;
    }
    
    public static boolean isStartTagChars(char c) {
        return isStartChar(c);
    }
    
    public static boolean isStartAttrChars(char c) {
        return isStartChar(c);
    }
    
    public static boolean isTagChars(char c) {
        return isNameChar(c);
    }
    
    public static boolean isAttrChars(char c) {
        return isNameChar(c);
    }
    
    public static boolean isSpace(char c) {
        return Character.isSpaceChar(c);
    }
    
    // thẻ mà HTML cho phép không cần thẻ đóng => tự động thêm kí tự “/” cuối thẻ để well-formed
    public static final List<String> INLINE_TAGS = Arrays.asList(
            "area", "base", "br", "col", "command", "embed", "hr", "img",
            "input", "keygen", "link", "meta", "param", "source", "track", "wbr"
    );
}
