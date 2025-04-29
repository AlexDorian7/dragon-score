package team.logica_populi.dragonscore.base.points;

import java.util.Random;
import java.time.LocalDate;

/**
 *  Generates a submission code for completion of lesson exercises
 */
public class SubmissionCode {
    private final String code;

    /**
     * Generates a submission code through an encryption calculations.
     * @param name name of the user
     * @param lesson the lesson id
     * @param first index of last character of user's first name in the alphabet
     * @param last index last character of user's last name in the alphabet
     * @param les index of lesson character in the alphabet
     * @return The generated the submission code
     */
    private static String generateCode(String name, char lesson, int first, int last, int les) {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        String head = String.valueOf(random.nextInt(1000));
        String tail = String.valueOf(random.nextInt(1000));
        String data = String.valueOf((first * les) + (last * les));

        return head + "." + data + "." + tail + "(" + name + "-" + lesson + " " + date +")";
    }

    /**
     * Default constructor
     * Generates a submission code which students will turn in for their assignment
     * @param fName first name of the user
     * @param lName last name of the user
     * @param lesson the current lesson the user is working on
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

    /**
     * Gets the submission code
     * @return the generated submission code
     */
    public String getCode() {
        return code;
    }
}