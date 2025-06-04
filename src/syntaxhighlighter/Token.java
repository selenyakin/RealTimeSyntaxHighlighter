package syntaxhighlighter;

// Sözdizimi analizinde kullanılan token'ları temsil eden sınıf
public class Token {
    private final String value;  // Token'ın metinsel değeri
    private final TokenType type; // Token türü (keyword, operator vb.)
    private final int start;     // Metin içinde başlangıç pozisyonu
    private final int end;       // Metin içinde bitiş pozisyonu

    // Token oluşturucu metot
    public Token(String value, TokenType type, int start, int end) {
        this.value = value;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    // Getter metotları:
    public String getValue() { return value; }  // Token değerini döndürür
    public TokenType getType() { return type; } // Token türünü döndürür
    public int getStart() { return start; }     // Başlangıç pozisyonunu verir
    public int getEnd() { return end; }         // Bitiş pozisyonunu verir
}