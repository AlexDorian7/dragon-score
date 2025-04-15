package team.logica_populi.dragonscore.base.points;

import java.util.Random;

public class SubmissionCode {
    private final String code;

    private static String generateCode(String name, char lesson, int first, int last, int les) {
        Random random = new Random();

        String head = String.valueOf(random.nextInt(1000));
        String tail = String.valueOf(random.nextInt(1000));
        String data = String.valueOf((first * les) + (last * les));

        return head + "." + data + "." + tail + "(" + name + "-" + lesson + ")";
    }

    /**
     * Default constructor
     *
     * @param fName
     * @param lName
     * @param lesson
     */
    public SubmissionCode(String fName, String lName, String lesson) {
        String fullName = fName + " " + lName;

        char firstCh = fName.charAt(fName.length() - 1);
        char lastCh = lName.charAt(lName.length() - 1);
        char lessonCh = lesson.charAt(lesson.length() - 1);

        int firstNameIndex = 0;
        int lastNameIndex = 0;
        int lessonIdIndex = 0;

        char[] alphabet = "abcdefghjklmnopqrstuvwxyz".toCharArray();

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == firstCh) {
                firstNameIndex = i + 1;
            } else if (alphabet[i] == lastCh) {
                lastNameIndex = i + 1;
            } else if (alphabet[i] == lessonCh) {
                lessonIdIndex = i + 1;
            }

        }

        this.code = generateCode(fullName, lessonCh, firstNameIndex, lastNameIndex, lessonIdIndex);
    }

    public String getCode() {
        return code;
    }
}