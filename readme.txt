"System zarządzania hotelem w Javie"

Opis systemu
------------
System zarządza hotelem posiadającym określoną liczbę pięter i pokoi.
Każdy pokój ma:
- numer (np. 101 – pierwszy pokój na pierwszym piętrze),
- krótki opis (dotyczący standardu pokoju)
- cenę za dobę,
- imię głównego gościa (na którego zapisywany jest pokój)
- daty zameldowania i wymeldowania.
- pojemność (maksymalną liczbę osób w pokoju),
- dane gości (jeśli są zameldowani)

Konfiguracja hotelu:
- dane są wczytywane i zapisywane do pliku
(w 1 rekordzie pliku data.txt znajdują się ilość pięter oraz liczba pokoi na piętrze)

Dostępne komendy
----------------
Interfejs programu jest tekstowy. Obsługiwane komendy (wielkość liter nie ma znaczenia):

- prices   – listuje wszystkie pokoje wraz z cenami za dobę
- view     – wyświetla szczegółowe informacje o wskazanym pokoju
- checkin  – rejestracja gościa w pokoju (dane gości, daty, czas pobytu)
- checkout – wymeldowanie gościa, obliczenie należności za nocleg
- list     – lista wszystkich pokoi wraz z informacjami o zajętości i gościach
- save     – zapis bieżącego stanu hotelu do pliku data.txt
- exit     – zakończenie działania programu

Struktura projektu
------------------
Projekt Java – Struktura katalogów


├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── commandFactory/
│   │   │   │   ├── Checkin.java
│   │   │   │   ├── Checkout.java
│   │   │   │   ├── Command.java
│   │   │   │   ├── CommandRegistry.java
│   │   │   │   ├── Exit.java
│   │   │   │   ├── List.java
│   │   │   │   ├── Prices.java
│   │   │   │   ├── Save.java
│   │   │   │   └── View.java
│   │   │   ├── map/
│   │   │   │   └── MyMap.java
│   │   │   ├── Models/
│   │   │   │   ├── Hotel.java
│   │   │   │   ├── Reservation.java
│   │   │   │   └── Room.java
│   │   │   └── org.example/
│   │   │       ├── CsvReader.java
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── data.txt
│
├── test/
│   └── java/
│       ├── commandFactory/ (testy klas komend)
│       │
│       ├── map/ (testy klasy MyMap)
│       │
│       └── org.example/ (testy głównego programu)



Testy
-----
Projekt zawiera testy jednostkowe pokrywające:
- operacje na klasie MyMap (dodawanie, pobieranie, usuwanie, lista kluczy),
- operacje hotelowe
Testowane są również działania w klasie Main oraz wczytywanie danych z pliku.

Uruchomienie
------------
1. Skompiluj projekt:
   javac Main.java
2. Uruchom program:
   java Main
3. Wpisuj komendy zgodnie z opisem powyżej.

Uwagi końcowe
-------------
- Program działa w trybie tekstowym (konsola).
- Wielkość liter w komendach nie ma znaczenia.
- W przypadku błędnych danych (np. nieistniejący pokój, próba zameldowania do zajętego pokoju) wyświetlany jest komunikat błędu.
- W przypadku podawania danych w złych formatach program wyrzuca odpowiednie błędy.
