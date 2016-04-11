package api;

import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kirillsuhanov on 12.10.15.
 */
public class TextExtractor {
    public static String getPlainText(Element element) {
        return getPlainText(element, false);
    }

    /**
     * Format an Element to plain-text
     *
     * @param element the root element to format
     * @return formatted text
     */
    public static String getPlainText(Element element, boolean includeLinks) {
        if (element == null) {
            return null;
        }
        FormattingVisitor formatter = new FormattingVisitor(includeLinks);
        NodeTraversor traversor = new NodeTraversor(formatter);
        traversor.traverse(element); // walk the DOM, and call .head() and .tail() for each node
        return formatter.toString();
    }


    /**
     * Gets substring that matches pattern
     *
     * @param str     string that maybe contain matched substring
     * @param pattern pattern
     * @return substring that matches pattern
     */
    public static String substringMatches(String str, Pattern pattern) {
        return substringMatches(str, pattern, 1);
    }

    /**
     * Gets substring that matches pattern
     *
     * @param str     string that maybe contain matched substring
     * @param pattern pattern
     * @param group   matcher group
     * @return substring that matches pattern
     */
    public static String substringMatches(String str, Pattern pattern, int group) {
        if ((str == null) || (pattern == null)) {
            return null;
        }
        str = TextCleaner.normalizeWhitespaceCharacters(str);
        Matcher matcher = pattern.matcher(str);
        if ((matcher.find()) && (matcher.groupCount() >= group)) {
            return matcher.group(group);
        }
        return null;
    }

    /**
     * <p>Gets the Strings that is matches pattern</p>
     *
     * @param str     the String to get substring from, can't be null
     * @param pattern the pattern to match substring
     * @return Array with matches strings
     */
    public static String[] substringsMatches(final String str, Pattern pattern) {
        return substringsMatches(str, pattern, 1);
    }

    /**
     * <p>Gets the Strings that is matches pattern</p>
     *
     * @param str     the String to get substring from, can't be null
     * @param pattern the pattern to match substring
     * @param group   matcher group
     * @return Array with matches strings
     */
    public static String[] substringsMatches(String str, Pattern pattern, int group) {
        List<String> matchesSubstrings = new ArrayList<String>();
        if ((str == null) || (pattern == null)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        str = TextCleaner.normalizeWhitespaceCharacters(str);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            if (matcher.groupCount() >= group) {
                matchesSubstrings.add(matcher.group(group));
            } else {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            }
        }
        return matchesSubstrings.toArray(new String[]{});
    }

    // the formatting rules, implemented in a breadth-first DOM traverse
    private static class FormattingVisitor implements NodeVisitor {
        private static final int maxWidth = 80;
        private int width = 0;
        boolean includeLinks;

        private FormattingVisitor(boolean includeLinks) {
            this.includeLinks = includeLinks;
        }

        private StringBuilder accum = new StringBuilder(); // holds the accumulated text

        // hit when the node is first seen
        public void head(Node node, int depth) {
            String name = node.nodeName();
            if (node instanceof TextNode)
                append(((TextNode) node).text()); // TextNodes carry all user-readable text in the DOM.
            else if (name.equals("li"))
                append("\n * ");
        }

        // hit when all of the node's children (if any) have been visited
        public void tail(Node node, int depth) {
            String name = node.nodeName();
            if (name.equals("br"))
                append("\n");
            else if (StringUtil.in(name, "tr"))
                append("\n");
            else if (StringUtil.in(name, "dd"))
                append("\n");
            else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5"))
                append("\n\n");
            else if (name.equals("a"))
                if (includeLinks) {
                    String url = node.absUrl("href");
                    if (url == null || url.isEmpty()) {
                        append(" ");
                    } else {
                        append(String.format(" (%s)", url));
                    }
                } else {
                    append(" ");
                }
        }

        // appends text to the string builder with a simple word wrap method
        private void append(String text) {
            if (text.startsWith("\n"))
                width = 0; // reset counter if starts with a newline. only from formats above, not in natural text
            if (text.equals(" ") &&
                    (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", "\n")))
                return; // don't accumulate long runs of empty spaces

            if (text.length() + width > maxWidth) { // won't fit, needs to wrap
                String words[] = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    boolean last = i == words.length - 1;
                    if (!last) // insert a space if not the last word
                        word = word + " ";
                    if (word.length() + width > maxWidth) { // wrap and reset counter
                        accum.append("\n").append(word);
                        width = word.length();
                    } else {
                        accum.append(word);
                        width += word.length();
                    }
                }
            } else { // fits as is, without need to wrap text
                accum.append(text);
                width += text.length();
            }
        }

        public String toString() {
            return accum.toString();
        }
    }
}
