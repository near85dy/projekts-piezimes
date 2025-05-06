# Piezīmju lietotne

## Apraksts

**Piezīmju lietotne** ir konsoles programma, kas paredzēta piezīmju pārvaldībai ar lietotāju reģistrāciju, autorizāciju, piezīmju izveidi, apskati un rediģēšanu, kā arī statistikas skatīšanu.

---

## Saturs

- [Ievads](#ievads)
- [Lietotāja interfeisa apraksts](#lietotāja-interfeisa-apraksts)
- [Funkciju apraksts](#funkciju-apraksts)
- [Datu modeļi](#datu-modeļi)
- [Lietotnes palaišana](#lietotnes-palaišana)

---

## Ievads

Programma ir paredzēta personīgo piezīmju glabāšanai un pārvaldībai. Katrs lietotājs var reģistrēties, autorizēties, izveidot, apskatīt un rediģēt savas piezīmes. Pieejama arī statistika par lietotājiem un piezīmēm.

---

## Lietotāja interfeisa apraksts

Programma darbojas konsolē un piedāvā šādas galvenās izvēlnes:

- **Galvenā izvēlne** (neregistrētiem lietotājiem):

  - Autorizēties
  - Reģistrēties
  - Skatīt statistiku
  - Iziet

- **Lietotāja izvēlne** (autorizētiem lietotājiem):
  - Skatīt manas piezīmes
  - Izveidot jaunu piezīmi
  - Atjaunināt profilu
  - Izrakstīties

---

## Funkciju apraksts

### Lietotāja funkcijas

- **Reģistrācija**: jauna lietotāja izveide ar e-pastu, vārdu, uzvārdu, paroli un vecumu.
- **Autorizācija**: pieteikšanās ar e-pastu un paroli.
- **Profila atjaunināšana**: vārda, uzvārda, paroles un vecuma maiņa.
- **Izrakstīšanās**: lietotāja sesijas pārtraukšana.

### Darbs ar piezīmēm

- **Piezīmes izveide**: jaunas piezīmes pievienošana ar virsrakstu un saturu.
- **Piezīmju apskate**: visu lietotāja piezīmju attēlošana.
- **Piezīmes rediģēšana**: piezīmes virsraksta un satura maiņa.

### Statistika

- Kopējais lietotāju skaits
- Vidējais lietotāju vecums
- Kopējais piezīmju skaits
- Vidējais piezīmes garums
- Pēdējās piezīmes datums

---

## Datu modeļi

### UserModel

- `id` — lietotāja unikālais identifikators
- `email` — lietotāja e-pasts
- `name` — vārds
- `surname` — uzvārds
- `password` — parole
- `age` — vecums

### NoteModel

- `id` — piezīmes unikālais identifikators
- `user_id` — lietotāja identifikators (īpašnieks)
- `title` — piezīmes virsraksts
- `content` — piezīmes saturs
- `created_at` — izveidošanas datums
- `updated_at` — pēdējās izmaiņas datums

---

## Lietotnes palaišana

1. Klonējiet repozitoriju.
2. Salieciet projektu ar Maven:  
   `mvn package`
3. Palaidiet lietotni:  
   `java -jar target/jūsu-jar-fails.jar`

---

## Autori

- Damians Geidels un Kirills Brežņevs

## Licence

Šī projekta licencēšana tiek veikta saskaņā ar mūsu darbu
