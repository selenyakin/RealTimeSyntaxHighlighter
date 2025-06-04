package syntaxhighlighter;

import java.util.*;
import java.util.regex.*;

// Kaynak kodunu token'lara ayıran lexer (sözcük analizci) sınıfı
public class LexicalAnalyzer {
    // Java diline ait anahtar kelimeler kümesi
    private static final Set<String> KEYWORDS = Set.of(
        "int", "if", "else", "for", "while", "return", "class", 
        "public", "static", "void", "new", "String", "boolean"
    );

    // Farklı token türlerini tanımlayan regex desenleri
    private static final Pattern TOKEN_PATTERNS = Pattern.compile(
        "(?<COMMENT>//.*|/\\*.*?\\*/)|" +      // Yorumlar
        "(?<STRING>\"[^\"]*\")|" +             // String literaller
        "(?<NUMBER>\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b)|" +  // Sayılar
        "(?<OPERATOR>[+\\-*/=<>!&|]+)|" +      // Operatörler
        "(?<PUNCTUATION>[();,:])|" +           // Noktalama işaretleri
        "(?<BRACKET>[\\[\\]{}])|" +            // Köşeli ve süslü parantezler
        "(?<KEYWORD>\\b(" + String.join("|", KEYWORDS) + ")\\b)|" +  // Anahtar kelimeler
        "(?<IDENTIFIER>[a-zA-Z_][a-zA-Z0-9_]*)"  // Tanımlayıcılar (değişken adları vb.)
    );

    // Verilen metni token'lara ayırır
    public List<Token> analyze(String text) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERNS.matcher(text);

        // Metinde token desenlerini eşleştir
        while (matcher.find()) {
            if (matcher.group("COMMENT") != null) {
                tokens.add(createToken(matcher, "COMMENT", TokenType.COMMENT));
            } else if (matcher.group("STRING") != null) {
                tokens.add(createToken(matcher, "STRING", TokenType.STRING_LITERAL));
            } else if (matcher.group("NUMBER") != null) {
                tokens.add(createToken(matcher, "NUMBER", TokenType.NUMBER));
            } else if (matcher.group("OPERATOR") != null) {
                tokens.add(createToken(matcher, "OPERATOR", TokenType.OPERATOR));
            } else if (matcher.group("PUNCTUATION") != null) {
                tokens.add(createToken(matcher, "PUNCTUATION", TokenType.PUNCTUATION));
            } else if (matcher.group("BRACKET") != null) {
                tokens.add(createToken(matcher, "BRACKET", TokenType.BRACKET));
            } else if (matcher.group("KEYWORD") != null) {
                tokens.add(createToken(matcher, "KEYWORD", TokenType.KEYWORD));
            } else if (matcher.group("IDENTIFIER") != null) {
                tokens.add(createToken(matcher, "IDENTIFIER", TokenType.IDENTIFIER));
            }
        }
        return tokens;
    }

    // Eşleşen token bilgilerinden yeni Token nesnesi oluşturur
    private Token createToken(Matcher matcher, String groupName, TokenType type) {
        return new Token(
            matcher.group(groupName),  // Token değeri
            type,                      // Token türü
            matcher.start(groupName),   // Başlangıç pozisyonu
            matcher.end(groupName)     // Bitiş pozisyonu
        );
    }
}