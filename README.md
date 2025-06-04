# Real-Time Syntax Highlighter

## Proje Hakkında
Bu proje, Java ile geliştirilmiş, gerçek zamanlı sözdizim renklendirmesi yapan bir masaüstü uygulamasıdır. Kullanıcı, kod yazarken anında analiz edilip, farklı token türleri (anahtar kelimeler, değişkenler, operatörler vb.) farklı renklerle vurgulanır. Bu sayede kod okunabilirliği artar ve yazım hataları daha kolay fark edilir.

## Özellikler
- **Gerçek zamanlı sözcüksel analiz:** Yazılan metin anlık olarak taranır ve tokenlara ayrılır.
- **Sözdizim analizi:** Basit bir parser ile kod yapısı kontrol edilir.
- **Kullanıcı dostu GUI:** Java Swing kullanılarak sade ve etkili bir arayüz tasarlanmıştır.
- **5 farklı token tipi için renklendirme:** Anahtar kelimeler, sayılar, değişken isimleri, operatörler ve yorumlar farklı renklerle gösterilir.
- **Hata mesajları:** Temel sözdizim hataları kullanıcıya bildirilir.

## Kullanılan Teknolojiler
- Java SE 8+
- Swing (GUI geliştirme)
- Regular Expressions (Lexical Analysis)

## Kurulum ve Çalıştırma
1. Depoyu bilgisayarınıza klonlayın:
2. Bilgisayarınızda Java 8 veya üzeri yüklü olduğundan emin olun.
3. Projeyi IntelliJ IDEA, Eclipse veya NetBeans gibi bir IDE’de açın.
4. `SyntaxHighlighterGUI.java` dosyasını çalıştırın.

## Proje Dosyaları
- `LexicalAnalyzer.java` : Sözcüksel analiz işlemlerini yapar.
- `SimpleParser.java` : Basit sözdizim kontrolü sağlar.
- `SyntaxHighlighterGUI.java` : Grafik kullanıcı arayüzünü yönetir.
- `Token.java` ve `TokenType.java` : Token yapısı ve türlerini tanımlar.

## Demo Video
Projeyi çalışırken görmek için aşağıdaki videoyu izleyebilirsiniz:  
[Demo Video](https://www.youtube.com/watch?v=örneklink)

## Rapor ve Makale
- [Final Teknik Raporu (PDF)](./finalRapor.pdf)
- [Projeye dair kişisel makalem (Medium)](https://medium.com/@selen-yakin/ger%C3%A7ek-zamanl%C4%B1-syntax-highlighter-projem-61235349e3eb)


**Hazırlayan:** SELEN YAKIN


