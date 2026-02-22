import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static String[] soundFiles = new String[50];
    static int count = 0;

    // ── Sound file → Arabic display text ────────────────────────────────────
    private static final Map<String, String> SOUND_TEXT = new HashMap<>();
    static {
        SOUND_TEXT.put("صفر.wav",              "صفر");
        SOUND_TEXT.put("واحد.wav",             "واحد");
        SOUND_TEXT.put("اثنان.wav",            "اثنان");
        SOUND_TEXT.put("ثلاثه.wav",            "ثلاثة");
        SOUND_TEXT.put("اربعه.wav",            "أربعة");
        SOUND_TEXT.put("خمسه.wav",             "خمسة");
        SOUND_TEXT.put("سته.wav",              "ستة");
        SOUND_TEXT.put("سبعه.wav",             "سبعة");
        SOUND_TEXT.put("ثمانيه.wav",           "ثمانية");
        SOUND_TEXT.put("تسعه.wav",             "تسعة");
        SOUND_TEXT.put("عشره.wav",             "عشرة");
        SOUND_TEXT.put("أحدعشر.wav",           "أحد عشر");
        SOUND_TEXT.put("اثناعشر.wav",          "اثنا عشر");
        SOUND_TEXT.put("ثلاثةعشر.wav",         "ثلاثة عشر");
        SOUND_TEXT.put("اربعةعشر.wav",         "أربعة عشر");
        SOUND_TEXT.put("خمسةعشر.wav",          "خمسة عشر");
        SOUND_TEXT.put("ستةعشر.wav",           "ستة عشر");
        SOUND_TEXT.put("سبعةعشر.wav",          "سبعة عشر");
        SOUND_TEXT.put("ثمانيةعشر.wav",        "ثمانية عشر");
        SOUND_TEXT.put("تسعةعشر.wav",          "تسعة عشر");
        SOUND_TEXT.put("عشرون.wav",            "عشرون");
        SOUND_TEXT.put("ثلاثون.wav",           "ثلاثون");
        SOUND_TEXT.put("اربعون.wav",           "أربعون");
        SOUND_TEXT.put("خمسون.wav",            "خمسون");
        SOUND_TEXT.put("ستون.wav",             "ستون");
        SOUND_TEXT.put("سبعون.wav",            "سبعون");
        SOUND_TEXT.put("ثمانون.wav",           "ثمانون");
        SOUND_TEXT.put("تسعون.wav",            "تسعون");
        SOUND_TEXT.put("مئه.wav",              "مائة");
        SOUND_TEXT.put("مئتان.wav",            "مئتان");
        SOUND_TEXT.put("ثلاثمئه.wav",          "ثلاثمائة");
        SOUND_TEXT.put("اربعمئه.wav",          "أربعمائة");
        SOUND_TEXT.put("خمسمئه.wav",           "خمسمائة");
        SOUND_TEXT.put("ستمئه.wav",            "ستمائة");
        SOUND_TEXT.put("سبعمئه.wav",           "سبعمائة");
        SOUND_TEXT.put("ثمانمئه.wav",          "ثمانمائة");
        SOUND_TEXT.put("تسعمئه.wav",           "تسعمائة");
        SOUND_TEXT.put("الف.wav",              "ألف");
        SOUND_TEXT.put("الفان.wav",            "ألفان");
        SOUND_TEXT.put("الاف.wav",             "آلاف");
        SOUND_TEXT.put("مليون.wav",            "مليون");
        SOUND_TEXT.put("و.wav",                "و");
        SOUND_TEXT.put("فاصله.wav",            "فاصلة");
        SOUND_TEXT.put("من_عشره.wav",          "من عشرة");
        SOUND_TEXT.put("من_مئه.wav",           "من مائة");
        SOUND_TEXT.put("من_الف.wav",           "من ألف");
        SOUND_TEXT.put("من_عشره_الاف.wav",     "من عشرة آلاف");
    }

    // ── Public API ───────────────────────────────────────────────────────────

    /** Process a number string; returns sound file array or null if invalid. */
    public static String[] process(String numberStr) {
        soundFiles = new String[50];
        count = 0;

        String[] parts = numberStr.trim().split("\\.");
        if (parts.length > 2 || parts[0].isEmpty()) return null;

        for (char c : parts[0].toCharArray())
            if (!Character.isDigit(c)) return null;

        long intPart = Long.parseLong(parts[0]);
        if (intPart > 1_000_000) return null;

        processInteger((int) intPart);

        if (parts.length > 1) {
            String decStr = parts[1];
            if (decStr.isEmpty()) return null;

            for (char c : decStr.toCharArray())
                if (!Character.isDigit(c)) return null;

            int places;
            int decValue;

            if (decStr.length() > 4) {
                int first4 = Integer.parseInt(decStr.substring(0, 4));
                int fifth  = Character.getNumericValue(decStr.charAt(4));
                decValue   = first4 + (fifth >= 5 ? 1 : 0);
                places     = 4;
            } else {
                decValue = Integer.parseInt(decStr);
                places   = decStr.length();
            }

            soundFiles[count++] = "فاصله.wav";
            processInteger(decValue);

            switch (places) {
                case 1: soundFiles[count++] = "من_عشره.wav";      break;
                case 2: soundFiles[count++] = "من_مئه.wav";       break;
                case 3: soundFiles[count++] = "من_الف.wav";       break;
                case 4: soundFiles[count++] = "من_عشره_الاف.wav"; break;
            }
        }

        return Arrays.copyOf(soundFiles, count);
    }

    /** Build the Arabic display text from a sound file array. */
    public static String toArabicText(String[] sounds) {
        StringBuilder sb = new StringBuilder();
        for (String s : sounds) {
            if (s == null) continue;
            String word = SOUND_TEXT.getOrDefault(s, "");
            if (!word.isEmpty()) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(word);
            }
        }
        return sb.toString();
    }

    // ── Command-line entry point ─────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String realNumber = input.next();

        String[] sounds = process(realNumber);
        if (sounds == null) {
            System.out.println("Input is not valid!");
            return;
        }

        System.out.println(toArabicText(sounds));
        SoundPlayer.loadAllSounds(sounds);
        for (String s : sounds) SoundPlayer.play(s);
    }

    // ── Processing logic ─────────────────────────────────────────────────────

    static void processInteger(int n) {
        if (n == 0)         { soundFiles[count++] = "صفر.wav";    return; }
        if (n == 1_000_000) { soundFiles[count++] = "مليون.wav";  return; }

        if (n >= 1000) {
            processThousands(n / 1000);
            n = n % 1000;
            if (n > 0) soundFiles[count++] = "و.wav";
        }

        processUpTo999(n);
    }

    static void processThousands(int t) {
        if      (t == 1)           soundFiles[count++] = "الف.wav";
        else if (t == 2)           soundFiles[count++] = "الفان.wav";
        else if (t >= 3 && t <= 10) { addOnesSound(t); soundFiles[count++] = "الاف.wav"; }
        else                       { processUpTo999(t); soundFiles[count++] = "الف.wav"; }
    }

    static void processUpTo999(int n) {
        if (n == 0) return;

        if (n >= 100) {
            addHundredsSound(n / 100);
            n = n % 100;
            if (n > 0) soundFiles[count++] = "و.wav";
        }

        if (n > 0) {
            if (n <= 19) {
                addOnesSound(n);
            } else {
                int units  = n % 10;
                int tensVal = (n / 10) * 10;
                if (units > 0) { addOnesSound(units); soundFiles[count++] = "و.wav"; }
                addTensSound(tensVal);
            }
        }
    }

    static void addOnesSound(int n) {
        switch (n) {
            case 1:  soundFiles[count++] = "واحد.wav";      break;
            case 2:  soundFiles[count++] = "اثنان.wav";     break;
            case 3:  soundFiles[count++] = "ثلاثه.wav";     break;
            case 4:  soundFiles[count++] = "اربعه.wav";     break;
            case 5:  soundFiles[count++] = "خمسه.wav";      break;
            case 6:  soundFiles[count++] = "سته.wav";       break;
            case 7:  soundFiles[count++] = "سبعه.wav";      break;
            case 8:  soundFiles[count++] = "ثمانيه.wav";    break;
            case 9:  soundFiles[count++] = "تسعه.wav";      break;
            case 10: soundFiles[count++] = "عشره.wav";      break;
            case 11: soundFiles[count++] = "أحدعشر.wav";    break;
            case 12: soundFiles[count++] = "اثناعشر.wav";   break;
            case 13: soundFiles[count++] = "ثلاثةعشر.wav";  break;
            case 14: soundFiles[count++] = "اربعةعشر.wav";  break;
            case 15: soundFiles[count++] = "خمسةعشر.wav";   break;
            case 16: soundFiles[count++] = "ستةعشر.wav";    break;
            case 17: soundFiles[count++] = "سبعةعشر.wav";   break;
            case 18: soundFiles[count++] = "ثمانيةعشر.wav"; break;
            case 19: soundFiles[count++] = "تسعةعشر.wav";   break;
        }
    }

    static void addTensSound(int n) {
        switch (n) {
            case 20: soundFiles[count++] = "عشرون.wav";  break;
            case 30: soundFiles[count++] = "ثلاثون.wav"; break;
            case 40: soundFiles[count++] = "اربعون.wav"; break;
            case 50: soundFiles[count++] = "خمسون.wav";  break;
            case 60: soundFiles[count++] = "ستون.wav";   break;
            case 70: soundFiles[count++] = "سبعون.wav";  break;
            case 80: soundFiles[count++] = "ثمانون.wav"; break;
            case 90: soundFiles[count++] = "تسعون.wav";  break;
        }
    }

    static void addHundredsSound(int h) {
        switch (h) {
            case 1: soundFiles[count++] = "مئه.wav";     break;
            case 2: soundFiles[count++] = "مئتان.wav";   break;
            case 3: soundFiles[count++] = "ثلاثمئه.wav"; break;
            case 4: soundFiles[count++] = "اربعمئه.wav"; break;
            case 5: soundFiles[count++] = "خمسمئه.wav";  break;
            case 6: soundFiles[count++] = "ستمئه.wav";   break;
            case 7: soundFiles[count++] = "سبعمئه.wav";  break;
            case 8: soundFiles[count++] = "ثمانمئه.wav"; break;
            case 9: soundFiles[count++] = "تسعمئه.wav";  break;
        }
    }
}
