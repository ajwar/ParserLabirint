package api;

public class TextCleaner {

    /**
     * clean text from empty rows, html/xml comments
     *
     * @param text - text
     * @return - cleaned text
     */
    public static String cleanText(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()) {
            return "";
        }
        text = text.replaceAll("\\n ", "\n");
        text = text.replaceAll(" \\n", "\n");
        while (text.contains("\n\n\n")) {
            text = text.replaceAll("\\n\\n\\n", "\n\n");
        }
        text = text.replaceAll("[\n]{3}", "\n");
        if (text.contains("<!--")) {
            String[] lines = text.split("\n");
            StringBuilder builder = new StringBuilder();
            for (String line : lines) {
                if (line.contains("<!--")) {
                    String prefix = line.substring(line.indexOf("<!--"), line.indexOf("-->") + "-->".length());
                    line = line.replace(prefix, "");
                }
                if (!line.isEmpty()) {
                    if (builder.length() > 0) {
                        builder.append("\n");
                    }
                    builder.append(line);
                }
            }
            text = builder.toString();
        }
        return text.trim();
    }

    /**
     * delete whitespace characters and replace '/' to '_' from input string
     *
     * @param juid string to clean
     * @return cleaned string
     */
    public static String normalizeJuid(String juid) {
        String normalized = juid
                .replace(String.valueOf((char) 160), " ")
                .replaceAll("\\s+", "")
                .replace("/", "_")
                .replace("?", "")
                .replace("#", "")
                .replace("&", "")
                .replace("%", "")
                .replace("\"","")
                .replace("+", "")
                .replace("*","")
                .replace("|","");
        return normalized;
    }

    /**
     * replace ascii symbol #160 to normal whitespace character
     *
     * @param text - text
     * @return - cleaned text
     */
    public static String normalizeWhitespaceCharacters(String text) {
        return text.replace(String.valueOf((char) 160), " ");
    }

    /**
     * replace all unavailable whitespace to empty
     *
     * @param text - text
     * @return - text without \u00a0
     */
    public static String removeIncorrectWhitespace(String text) {
        return text.replaceAll("\\u00a0", "");
    }
}
