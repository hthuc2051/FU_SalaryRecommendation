/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.xmlchecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import static thucnh.xmlchecker.SyntaxState.*;
/**
 *
 * @author HP
 */
public class XmlSyntaxChecker {

    public String checkSyntax(String src) {
        src = src + " ";
        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();

        StringBuilder openTag = new StringBuilder();
        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        StringBuilder content = new StringBuilder(); //giá trị hiện thời của character data.

        boolean isEmptyTag = false;
        boolean isOpenTag = false;
        boolean isCloseTag = false;

        Map<String, String> attributes = new HashMap<>();
        Stack<String> stack = new Stack<>();

        String state = CONTENT;

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            switch (state) {
                case CONTENT:
                    if (c == LT) {
                        state = OPEN_BRACKET;
                        writer.append(content.toString().trim().replace("&", "&amp;"));
                    } else {
                        content.append(c);
                    }
                    break;

                case OPEN_BRACKET:
                    if (isStartTagChars(c)) { // bắt đầu tên của open tag
                        state = OPEN_TAG_NAME;

                        isOpenTag = true;
                        isCloseTag = false;
                        isEmptyTag = false;

                        openTag.setLength(0); // reset value
                        openTag.append(c);
                    } else if (c == SLASH) { // slash tag đóng
                        state = CLOSE_TAG_SLASH;

                        isOpenTag = false;
                        isCloseTag = true;
                        isEmptyTag = false;
                    }
                    break;
                case OPEN_TAG_NAME:
                    // Case number
                    if (isTagChars(c)) { // loop tag name
                        openTag.append(c);
                    } else if (isSpace(c)) { // chuẩn bị có attr
                        state = TAG_INNER;
                        attributes.clear();
                    } else if (c == GT) { // > đóng tag
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) { // thẻ empty
                        state = EMPTY_SLASH;
                    }
                    break;
                case TAG_INNER:
                    if (isSpace(c)) {
                        // loop
                    } else if (isStartAttrChars(c)) {
                        state = ATTR_NAME;
                        attrName.setLength(0);
                        attrName.append(c);
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) { // thẻ empty
                        state = EMPTY_SLASH;
                    }
                    break;
                case ATTR_NAME:
                    if (isAttrChars(c)) {
                        attrName.append(c);
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else if (isSpace(c)) {
                        state = EQUAL_WAIT;
                    } else { // exception
                        if (c == SLASH) { // 
                            attributes.put(attrName.toString(), "true");
                            state = EMPTY_SLASH;
                        } else if (c == GT) { // 
                            attributes.put(attrName.toString(), "true");
                            state = CLOSE_BRACKET;
                        }
                    }
                    break;
                case EQUAL_WAIT:
                    if (isSpace(c)) {
                        // loop cho đến khi gặp =
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else { // exception
                        if (isStartAttrChars(c)) {
                            attributes.put(attrName.toString(), "true");
                            state = ATTR_NAME;
                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case EQUAL:
                    if (isSpace(c)) {
                        //loop cho đến khi gặp dấu nháy
                    } else if (c == D_QOUT || c == S_QOUT) {
                        quote = c;
                        state = ATTR_VALUE_Q;
                        attrValue.setLength(0);
                    } else if (!isSpace(c) && c != GT) {
                        state = ATTR_VALUE_NQ;
                        attrValue.setLength(0);
                        attrValue.append(c);
                    }
                    break;
                case ATTR_VALUE_Q:
                    if (c != quote) { // loop
                        attrValue.append(c);
                    } else if (c == quote) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case ATTR_VALUE_NQ:
                    if (!isSpace(c) && c != GT) {
                        attrValue.append(c);
                    } else if (isSpace(c)) { // hết attr, còn tiếp inner tag
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    } else if (c == GT) { // hết attr, đóng tag
                        state = CLOSE_BRACKET;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case EMPTY_SLASH:
                    if (c == GT) {
                        state = CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;

                case CLOSE_TAG_SLASH:
                    if (isStartTagChars(c)) {
                        state = CLOSE_TAG_NAME;
                        closeTag.setLength(0);
                        closeTag.append(c);
                    }
                    break;
                case CLOSE_TAG_NAME:
                    if (isTagChars(c)) {
                        closeTag.append(c);
                    } else if (isSpace(c)) {
                        state = WAIT_END_TAG_CLOSE;
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;
                case WAIT_END_TAG_CLOSE:
                    if (isSpace(c)) {
                        // loop chờ đến >
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;

                case CLOSE_BRACKET: // xong 1 tag => tiến hành xử lý
                    if (isOpenTag) {
                        String openTagName = openTag.toString().toLowerCase();

                        if (INLINE_TAGS.contains(openTagName)) {
                            isEmptyTag = true;
                        }
                        writer.append(LT)
                                .append(openTagName)
                                .append(convert(attributes))
                                .append((isEmptyTag ? "/" : ""))
                                .append(GT);
                        attributes.clear();

                        // Stack ------
                        if (!isEmptyTag) {
                            stack.push(openTagName);
                        }
                    } else if (isCloseTag) {
                        String closeTagName = closeTag.toString().toLowerCase();
                        while (!stack.isEmpty() && !stack.peek().equals(closeTagName)) {
                            // đỉnh stack hiện ko trùng với tagname hiện tại
                            writer.append(LT)
                                    .append(SLASH)
                                    .append(stack.pop())
                                    .append(GT);
                        }
                        if (!stack.isEmpty() && stack.peek().equals(closeTagName)) {
                            writer.append(LT)
                                    .append(SLASH)
                                    .append(stack.pop())
                                    .append(GT);
                        }
                    } // end close missing tag

                    if (c == LT) {
                        state = OPEN_BRACKET;
                    } else {
                        state = CONTENT;
                        content.setLength(0);
                        content.append(c);
                    }
                    break;
            } // end switch
        } // end for reader
        
        if (CONTENT.equals(state)) {
            writer.append(content.toString().trim().replace("&", "&amp;"));
        }
        
        // xử lý những tag còn lại trong stack
        while (!stack.isEmpty()) {
            writer.append(LT)
                    .append(SLASH)
                    .append(stack.pop())
                    .append(GT);
        }
        
        // thêm một thẻ <roor> bọc bên ngoài để tránh lỗi
        return "<root>" + writer.toString() + "</root>";
    }

    private Character quote;

    private String convert(Map<String, String> attributes) {
        if (attributes.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");

            builder.append(entry.getKey())
                    .append("=")
                    .append("\"")
                    .append(value)
                    .append("\"")
                    .append(" ");
        }

        String result = builder.toString().trim();
        if (!result.isEmpty()) {
            result = " " + result;
        }
        return result;
    }
}

