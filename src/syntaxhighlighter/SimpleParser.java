package syntaxhighlighter;

import java.util.List;

// Basit dilbilgisi kontrolü yapan parser sınıfı
public class SimpleParser {
    
    // Token listesini analiz edip dilbilgisi kontrolü yapar
    public String checkGrammar(List<Token> tokens) {
        if (tokens.isEmpty()) return "⏳ Kod analiz ediliyor...";
        
        // Geçerli yapıları kontrol et
        if (isValidVariableDeclaration(tokens)) {
            return "Geçerli değişken tanımı";
        } else if (isValidIfStatement(tokens)) {
            return "Geçerli if ifadesi";
        }
        
        // Hata durumunda detaylı hata mesajı döndür
        return getDetailedError(tokens);
    }

    // Geçerli değişken tanımı kontrolü
    private boolean isValidVariableDeclaration(List<Token> tokens) {
        if (tokens.size() < 4) return false; // Minimum token sayısı kontrolü
        
        // Noktalı virgül varlığını kontrol et
        boolean hasSemicolon = tokens.size() > 4 && 
                             tokens.get(tokens.size()-1).getValue().equals(";");
        
        // Sırasıyla: anahtar kelime, tanımlayıcı, eşittir, geçerli değer
        return tokens.get(0).getType() == TokenType.KEYWORD &&
               tokens.get(1).getType() == TokenType.IDENTIFIER &&
               tokens.get(2).getValue().equals("=") &&
               isValidValue(tokens.get(3)) &&
               (tokens.size() == 4 || hasSemicolon);
    }

    // Geçerli if ifadesi kontrolü
    private boolean isValidIfStatement(List<Token> tokens) {
        return tokens.size() >= 5 && // Minimum token sayısı
               tokens.get(0).getValue().equals("if") && // if anahtar kelimesi
               tokens.get(1).getValue().equals("(") && // Açma parantezi
               tokens.get(tokens.size()-1).getValue().equals(")"); // Kapama parantezi
    }

    // Geçerli değer kontrolü (sayı, string veya tanımlayıcı)
    private boolean isValidValue(Token token) {
        return token.getType() == TokenType.NUMBER || 
               token.getType() == TokenType.STRING_LITERAL ||
               token.getType() == TokenType.IDENTIFIER;
    }

    // Detaylı hata mesajı oluşturma
    private String getDetailedError(List<Token> tokens) {
        // Eksik operatör kontrolü
        if (tokens.size() >= 3 && 
            tokens.get(0).getType() == TokenType.KEYWORD &&
            tokens.get(1).getType() == TokenType.IDENTIFIER &&
            !tokens.get(2).getValue().equals("=")) {
            return " Hata: '=' operatörü eksik";
        }
        
        // Yanlış noktalı virgül kontrolü
        if (tokens.stream().anyMatch(t -> t.getValue().equals(";"))) {
            return "️ Hata: Yanlış noktalı virgül kullanımı";
        }
        
        // Genel hata mesajı
        return " Gramer hatası tespit edildi";
    }
}