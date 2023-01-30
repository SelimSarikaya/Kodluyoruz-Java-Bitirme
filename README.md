
# Ticket

## Kodluyoruz & Solmaz Gümrük Müşavirliği
### Bootcamp Bitirme Projesi

#### Proje Konusu:
Online uçak ve otobüs bileti satışı yapılmak istenmektedir. Uygulamanın gereksinimleri
aşağıdaki gibidir.
#### Gereksinimler
• Kullanıcılar sisteme kayıt ve login olabilmelidir.

• Kullanıcı kayıt işleminden sonra mail gönderilmelidir.

• Kullanıcı şifresi istediğiniz bir hashing algoritmasıyla database kaydedilmelidir.

• Admin kullanıcı yeni sefer ekleyebilir, iptal edebilir, toplam bilet satışını, bu satıştan
elde edilen toplam ücreti görebilir.

• Kullanıcılar şehir bilgisi, taşıt türü(uçak & otobüs) veya tarih bilgisi ile tüm seferleri
arayabilmelidir.

• Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir.

• Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet alabilir.

• Kurumsal kullanıcı aynı sefer için en fazla 20 bilet alabilir.

• Satın alma işlemi başarılı ise işlem tamamlanmalı ve asenkron olarak bilet detayları
kullanıcının telefona numarasına sms gönderilmeli.

• SMS, mail ve push Notification gönderme işlemleri için sadece Database kayıt etme
işlemi yapılması yeterlidir. Fakat bu işlemler tek bir Servis(uygulama) üzerinden ve
polimorfik davranış ile yapılmalıdır.

• Kullancılar aldığı biletleri görebilmelidir.

#### Sistem Kabulleri

Kullanıcılar bireysel ve kurumsal olabilir.

SMS, Mail ve Push Notification gönderim işlemleri Asenkron olmalıdır.

Uçak yolcu kapasitesi: 189

Otobüs yolcu kapasitesi: 45

Ödeme şekli sadece Kredi kartı ve Havale / EFT olabilir.

Ödeme Servisi işlemleri Senkron olmalıdır.




####  Kullanılan Teknolojiler

Java

Spring

Restfull

PostgreSQL

RabbitMQ
