package syntaxhighlighter;

public enum TokenType {
    KEYWORD("#5568AF"),        // Anahtar kelimeler (if, else, int, vb.)
    STRING_LITERAL("#CEEAEE"), // String ifadeleri ("metin" gibi)
    NUMBER("#F37826"),         // Sayılar
    OPERATOR("#EC1763"),       // Operatörler (+, -, *, /, =, vb.)
    IDENTIFIER("#F8C9DD"),     // Değişken ve metot isimleri
    COMMENT("#CDD629"),        // Yorum satırları
    PUNCTUATION("#AAAAAA"),    // Noktalama işaretleri (; , :)
    BRACKET("#FF8C00");        // Parantezler: (), {}, []

    private final String colorHex;

    TokenType(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }
}
